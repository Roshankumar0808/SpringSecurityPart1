package com.SpringSecurityPart1.SpringSecurityPart1.repositories;

import com.SpringSecurityPart1.SpringSecurityPart1.entities.Session;
import com.SpringSecurityPart1.SpringSecurityPart1.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session,Long> {

    List<Session> findByUser(User user);

    Optional<Session> findByRefreshToken(String refreshToken);
}
