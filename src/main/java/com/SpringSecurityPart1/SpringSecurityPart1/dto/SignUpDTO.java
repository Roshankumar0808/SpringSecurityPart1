package com.SpringSecurityPart1.SpringSecurityPart1.dto;

import com.SpringSecurityPart1.SpringSecurityPart1.entities.enums.Permission;
import com.SpringSecurityPart1.SpringSecurityPart1.entities.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDTO {
    private String email;
    private String password;
    private String name;
    private Long sessionCount;
    private Set<Role> roles;
    private Set<Permission> permissions;
}
