package com.SpringSecurityPart1.SpringSecurityPart1.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String refreshToken;
    @CreationTimestamp
    private LocalDateTime lastUsedAt;
    @ManyToOne
    private User user;

}
