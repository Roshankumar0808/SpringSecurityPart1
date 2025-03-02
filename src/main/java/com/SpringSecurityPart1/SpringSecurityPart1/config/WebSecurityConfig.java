package com.SpringSecurityPart1.SpringSecurityPart1.config;

import com.SpringSecurityPart1.SpringSecurityPart1.entities.enums.Permission;
import com.SpringSecurityPart1.SpringSecurityPart1.entities.enums.Role;
import com.SpringSecurityPart1.SpringSecurityPart1.filters.JwtAuthFilter;
import com.SpringSecurityPart1.SpringSecurityPart1.handlers.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.util.SessionConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.SpringSecurityPart1.SpringSecurityPart1.entities.enums.Role.ADMIN;
import static com.SpringSecurityPart1.SpringSecurityPart1.entities.enums.Role.CREATOR;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {
    private  final JwtAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private static final String[] publicRoutes={
            "/error","/auth/**","/home.html"
    };
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .authorizeHttpRequests(auth-> auth
                        .requestMatchers(publicRoutes).permitAll()
                        .requestMatchers("/posts/**").authenticated()
//                     .requestMatchers(HttpMethod.GET,"/posts/**").permitAll()
//                        .requestMatchers(HttpMethod.POST,"/posts/**").hasAnyRole(ADMIN.name(), CREATOR.name())
//                        .requestMatchers(HttpMethod.POST,"/posts/**").hasAnyAuthority(Permission.POST_CREATE.name())
//                     //   .requestMatchers(HttpMethod.GET,"/posts/**").hasAnyAuthority(Permission.POST_VIEW.name())
//                        .requestMatchers(HttpMethod.PUT,"/posts/**").hasAnyAuthority(Permission.POST_UPDATE.name())
//                        .requestMatchers(HttpMethod.DELETE,"/posts/**").hasAnyAuthority(Permission.POST_DELETE.name())

                        .anyRequest().authenticated())
                .csrf(csrf->csrf.disable())
                .sessionManagement(sessionConfig-> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2Config -> oauth2Config
                                .failureUrl("/login?error=true")
                                .successHandler(oAuth2SuccessHandler)

                );

             //   .formLogin(Customizer.withDefaults());


        return httpSecurity.build();
    }

//    @Bean
//    UserDetailsService myInMemoryUserDetailsService(){
//        UserDetails userDetails= User
//                .withUsername("roshan")
//                .password(passwordEncoder().encode("pass"))
//                .roles(("USER")).build();
//        UserDetails adminUser=User
//                .withUsername("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(userDetails,adminUser);
//    }


    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }
}
