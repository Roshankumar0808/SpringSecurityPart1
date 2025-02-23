package com.SpringSecurityPart1.SpringSecurityPart1.services;

import com.SpringSecurityPart1.SpringSecurityPart1.dto.LoginDTO;
import com.SpringSecurityPart1.SpringSecurityPart1.dto.LoginResponseDTO;
import com.SpringSecurityPart1.SpringSecurityPart1.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public LoginResponseDTO login(LoginDTO loginDTO) {
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword())
        );
        User user=(User)authentication.getPrincipal();
        String accessToken=jwtService.generateAccessToken(user);
        String refreshtoken=jwtService.generateRefreshToken(user);
        return new LoginResponseDTO(user.getId(),accessToken,refreshtoken);

    }

    public LoginResponseDTO refreshToken(String refereshToken) {
        Long userId=jwtService.getUserIdFromToken(refereshToken);
        User user=userService.getUserById(userId);
        String accessToken=jwtService.generateAccessToken(user);
        return new LoginResponseDTO(user.getId(),accessToken,refereshToken);
    }
}
