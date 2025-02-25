package com.SpringSecurityPart1.SpringSecurityPart1.entities;

import com.SpringSecurityPart1.SpringSecurityPart1.entities.enums.Permission;
import com.SpringSecurityPart1.SpringSecurityPart1.entities.enums.Role;
import com.SpringSecurityPart1.SpringSecurityPart1.utils.PermissionMapping;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true)
    private String email;
    private String password;
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;


//    @ElementCollection(fetch = FetchType.EAGER)
//    @Enumerated(EnumType.STRING)
//    private Set<Permission> permissions;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //Set<SimpleGrantedAuthority> authorities= roles.stream().map(role->new SimpleGrantedAuthority("ROLE_"+role.name())).collect(Collectors.toSet());
        Set<SimpleGrantedAuthority> authorities= new HashSet<>();
        roles.forEach(
                role->{
                    Set<SimpleGrantedAuthority>permissions= PermissionMapping.getAuthoritiesForRole(role);
                    authorities.addAll(permissions);
                    authorities.add(new SimpleGrantedAuthority("ROLE"+role.name()));
                }

        );

//        permissions.forEach(
//                permission -> authorities.add(new SimpleGrantedAuthority(permission.name()))
//        );

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
