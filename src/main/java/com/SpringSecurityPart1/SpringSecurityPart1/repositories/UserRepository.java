package com.SpringSecurityPart1.SpringSecurityPart1.repositories;

import com.SpringSecurityPart1.SpringSecurityPart1.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User>findByEmail(String email);
}
