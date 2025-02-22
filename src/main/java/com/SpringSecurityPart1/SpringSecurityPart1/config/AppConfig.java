package com.SpringSecurityPart1.SpringSecurityPart1.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
//@EnableJpaAuditing(auditorAwareRef = "getAuditorAwareImpl")
public class AppConfig {
    @Bean
    public ModelMapper getmodelmapper(){
        return new ModelMapper();
    }


    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public AuditorAware<String> getAuditorAwareImpl(){
//        return new AuditorAwareImpl();
//    }

}
