package com.SpringSecurityPart1.SpringSecurityPart1.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
//@EnableJpaAuditing(auditorAwareRef = "getAuditorAwareImpl")
public class AppConfig {
    @Bean
    public ModelMapper getmodelmapper(){
        return new ModelMapper();
    }

//    @Bean
//    public AuditorAware<String> getAuditorAwareImpl(){
//        return new AuditorAwareImpl();
//    }

}
