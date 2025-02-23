package com.SpringSecurityPart1.SpringSecurityPart1.controllers;

import com.SpringSecurityPart1.SpringSecurityPart1.dto.LoginDTO;
import com.SpringSecurityPart1.SpringSecurityPart1.dto.LoginResponseDTO;
import com.SpringSecurityPart1.SpringSecurityPart1.dto.SignUpDTO;
import com.SpringSecurityPart1.SpringSecurityPart1.dto.userDTO;
import com.SpringSecurityPart1.SpringSecurityPart1.services.AuthService;
import com.SpringSecurityPart1.SpringSecurityPart1.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.CookieProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
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

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO>refresh(HttpServletRequest request){
       String refereshToken = Arrays.stream(request.getCookies()).
                filter(cookie->"refreshtoken".equals(cookie.getName()))
                .findFirst().map(Cookie::getValue)
                .orElseThrow(()->new AuthenticationServiceException("Refresh Token Not Found"));
        LoginResponseDTO loginResponseDTO=authService.refreshToken(refereshToken);
        return ResponseEntity.ok(loginResponseDTO);
    }
}
