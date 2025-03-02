package com.SpringSecurityPart1.SpringSecurityPart1.dto;

import lombok.Data;

@Data
public class userDTO {
    private Long id;
    private String email;
    private String name;
    private Long sessionCount;
}
