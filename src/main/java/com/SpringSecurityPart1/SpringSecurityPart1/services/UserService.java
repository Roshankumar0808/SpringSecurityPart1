package com.SpringSecurityPart1.SpringSecurityPart1.services;

import com.SpringSecurityPart1.SpringSecurityPart1.exceptions.ResourceNotFoundException;
import com.SpringSecurityPart1.SpringSecurityPart1.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
   private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("user with email"+username+"not found"));
    }
}
