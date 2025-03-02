package com.SpringSecurityPart1.SpringSecurityPart1.services;

import com.SpringSecurityPart1.SpringSecurityPart1.entities.Session;
import com.SpringSecurityPart1.SpringSecurityPart1.entities.User;
import com.SpringSecurityPart1.SpringSecurityPart1.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.ILoggerFactory;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    //private final int SESSION_LIMIT=2;
    public void generateNewSession(User user,String refreshToken){
        List<Session> userSession=sessionRepository.findByUser(user);
        log.info(Integer.toString(userSession.size()));
        if(userSession.size()>=user.getSessionCount()){
            userSession.sort(Comparator.comparing(Session::getLastUsedAt));
                Session leastRecentlyUsedSession = userSession.getFirst();
                sessionRepository.delete(leastRecentlyUsedSession);

        }
        Session newSession=Session.builder().user(user).refreshToken(refreshToken).build();
        sessionRepository.save(newSession);
    }

    public void validateSession(String refreshToken){
     Session session=sessionRepository.findByRefreshToken(refreshToken).orElseThrow(()->new SessionAuthenticationException("Session not found for refresh token "+refreshToken));
     session.setLastUsedAt(LocalDateTime.now());
     sessionRepository.save(session);
    }
}
