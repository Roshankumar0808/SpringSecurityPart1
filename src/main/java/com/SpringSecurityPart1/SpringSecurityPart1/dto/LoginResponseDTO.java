package com.SpringSecurityPart1.SpringSecurityPart1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginResponseDTO {
   private Long Id;
    private String accessToken;
    private String refreshToken;
}
