package com.SpringSecurityPart1.SpringSecurityPart1.services;

import com.SpringSecurityPart1.SpringSecurityPart1.dto.LoginDTO;
import com.SpringSecurityPart1.SpringSecurityPart1.dto.LoginResponseDTO;
import com.SpringSecurityPart1.SpringSecurityPart1.entities.Session;
import com.SpringSecurityPart1.SpringSecurityPart1.entities.User;
import com.SpringSecurityPart1.SpringSecurityPart1.repositories.SessionRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final SessionService sessionService;
    private final SessionRepository sessionRepository;
    public LoginResponseDTO login(LoginDTO loginDTO) {

        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword())
        );
        User user=(User)authentication.getPrincipal();
    //    User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String accessToken=jwtService.generateAccessToken(user);
        String refreshtoken=jwtService.generateRefreshToken(user);
        sessionService.generateNewSession(user,refreshtoken);
        return new LoginResponseDTO(user.getId(),accessToken,refreshtoken);

    }



    public LoginResponseDTO refreshToken(String refereshToken) {

        Long userId=jwtService.getUserIdFromToken(refereshToken);
        sessionService.validateSession(refereshToken);
        User user=userService.getUserById(userId);
        String accessToken=jwtService.generateAccessToken(user);
        return new LoginResponseDTO(user.getId(),accessToken,refereshToken);
    }
}
