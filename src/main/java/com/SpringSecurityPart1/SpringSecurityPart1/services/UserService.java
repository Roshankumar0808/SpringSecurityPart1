package com.SpringSecurityPart1.SpringSecurityPart1.services;

import com.SpringSecurityPart1.SpringSecurityPart1.dto.LoginDTO;
import com.SpringSecurityPart1.SpringSecurityPart1.dto.SignUpDTO;
import com.SpringSecurityPart1.SpringSecurityPart1.dto.userDTO;
import com.SpringSecurityPart1.SpringSecurityPart1.entities.User;
import com.SpringSecurityPart1.SpringSecurityPart1.exceptions.ResourceNotFoundException;
import com.SpringSecurityPart1.SpringSecurityPart1.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
   private final UserRepository userRepository;
   private final ModelMapper modelMapper;
   private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(()->new BadCredentialsException("user with email"+username+"not found"));
    }

    public User getUserById(Long userId){
        return userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user with id"+userId+"not found"));
    }


    public userDTO signUp(SignUpDTO signUpDTO) {
      Optional<User> user= userRepository.findByEmail(signUpDTO.getEmail());
      if(user.isPresent()){
          throw new BadCredentialsException("User With email already exist"+signUpDTO.getEmail());
      }
      User toCreateUser=modelMapper.map(signUpDTO,User.class);
      toCreateUser.setPassword(passwordEncoder.encode(toCreateUser.getPassword()));
      User savedUser=userRepository.save(toCreateUser);
      return modelMapper.map(savedUser,userDTO.class);
    }


}
