package com.codingshuttle.SecurityApp.SecurityApplication.services;

import com.codingshuttle.SecurityApp.SecurityApplication.entities.Session;
import com.codingshuttle.SecurityApp.SecurityApplication.entities.User;
import com.codingshuttle.SecurityApp.SecurityApplication.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final int SESSION_LIMIT=2;

    public void generateNewSession(User user, String refreshToken) {
        List<Session> userSessions = sessionRepository.findByUser(user);

        if(userSessions.size() == SESSION_LIMIT) {
            userSessions.sort(Comparator.comparing(Session::getLastUsedAt));
            Session lastRecentlyUsedSession = userSessions.getFirst();

            sessionRepository.delete(lastRecentlyUsedSession);
        }

        Session newSession = Session.builder()
                .user(user)
                .refreshToken(refreshToken)
                .build();

        sessionRepository.save(newSession);
    }

    public void validateSession(String refreshToken) {
        Session session = sessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new SessionAuthenticationException("session not found with refreshToken: "+refreshToken));
        session.setLastUsedAt(LocalDateTime.now());

        sessionRepository.save(session);
    }
}
