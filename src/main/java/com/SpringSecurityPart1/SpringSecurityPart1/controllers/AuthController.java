package com.SpringSecurityPart1.SpringSecurityPart1.controllers;

import com.SpringSecurityPart1.SpringSecurityPart1.dto.LoginDTO;
import com.SpringSecurityPart1.SpringSecurityPart1.dto.LoginResponseDTO;
import com.SpringSecurityPart1.SpringSecurityPart1.dto.SignUpDTO;
import com.SpringSecurityPart1.SpringSecurityPart1.dto.userDTO;
import com.SpringSecurityPart1.SpringSecurityPart1.entities.Session;
import com.SpringSecurityPart1.SpringSecurityPart1.entities.User;
import com.SpringSecurityPart1.SpringSecurityPart1.repositories.SessionRepository;
import com.SpringSecurityPart1.SpringSecurityPart1.services.AuthService;
import com.SpringSecurityPart1.SpringSecurityPart1.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.CookieProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final SessionRepository sessionRepository;
    @Value("${deploy.env}")
    private String deployenv;


    @PostMapping("/signup")
    public ResponseEntity<userDTO>signUp(@RequestBody SignUpDTO signUpDTO){
        userDTO userDTO=userService.signUp(signUpDTO);
        return ResponseEntity.ok(userDTO);

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO>login(@RequestBody LoginDTO loginDTO, HttpServletResponse response){
        LoginResponseDTO loginResponseDTO=authService.login(loginDTO);
        Cookie cookie=new Cookie("refreshtoken",loginResponseDTO.getRefreshToken());
        cookie.setHttpOnly(true);
       cookie.setSecure("production".equals(deployenv));
        response.addCookie(cookie);

        return ResponseEntity.ok(loginResponseDTO);
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = null;
            User user=null;

            if (authentication != null && authentication.getPrincipal() != null) {
                Object principal = authentication.getPrincipal();
                user=(User) authentication.getPrincipal();
                if (principal instanceof org.springframework.security.core.userdetails.User) {
                    username = ((org.springframework.security.core.userdetails.User) principal).getUsername();
                } else if (principal instanceof String) {
                    username = (String) principal;
                }
            }
            Cookie[] cookies = request.getCookies();
            String refreshToken = null;

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("refreshtoken".equals(cookie.getName())) {
                        refreshToken = cookie.getValue();
                        break;
                    }
                }
            }

            if (refreshToken != null) {
                Optional<Session> session = sessionRepository.findByRefreshToken(refreshToken);
                if (session.isPresent()) {
                    sessionRepository.delete(session.get());
                    log.info("Session deleted for user: " + username);
                }
            }

            if (request.getSession(false) != null) {
                request.getSession().invalidate();
            }

            Cookie cookie=new Cookie("refreshtoken","abc");
            cookie.setHttpOnly(true);
            cookie.setSecure("production".equals(deployenv));
            response.addCookie(cookie);
            // Clear security context
            SecurityContextHolder.clearContext();

            return ResponseEntity.ok("Logged out successfully");
        } catch (Exception e) {
            log.error("Logout error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Logout failed");
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO>refresh(HttpServletRequest request, HttpServletResponse response){

        LoginResponseDTO loginResponseDTO=authService.refreshToken(request, response);
        return ResponseEntity.ok(loginResponseDTO);
    }
}
