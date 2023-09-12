package com.selyuto.termbase.authentication;

import com.selyuto.termbase.models.Privilege;
import com.selyuto.termbase.models.User;
import com.selyuto.termbase.repositories.UserRepository;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;

@Component
public class Authenticator {

    private ConcurrentHashMap <Long, String> activeSessions = new ConcurrentHashMap<>();
    private ConcurrentHashMap <String, Long> cookieExpireDates = new ConcurrentHashMap<>();
    private ConcurrentHashMap <String, List<Long>> sessionPrivileges = new ConcurrentHashMap<>();

    private final UserRepository userRepository;

    public Authenticator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Map<Long, String> getActiveSessions() {
        return activeSessions;
    }

    public void addSession(Long userId, String sessionId, int cookieMaxAge) {
        userRepository.findById(userId).ifPresent(user -> {
            activeSessions.put(userId, sessionId);
            cookieExpireDates.put(sessionId, Instant.now().getEpochSecond() + (long) cookieMaxAge);
            List<Long> privilegeIds = user
                    .getPrivileges()
                    .stream()
                    .map(Privilege::getId)
                    .collect(Collectors.toList());
            sessionPrivileges.put(sessionId, privilegeIds);
        });
    }

    public void voidSessionBySessionId(String sessionId) {
        findUserIdBySessionId(sessionId).ifPresent(this::voidSession);
    }

    public void voidSessionByUserId(Long userId) {
        voidSession(userId);
    }

    private void voidSession(Long userId) {
        String deletedSessionId = activeSessions.remove(userId);
        cookieExpireDates.remove(deletedSessionId);
        sessionPrivileges.remove(deletedSessionId);
    }

    public Optional<Long> findUserIdBySessionId(String sessionId) {
        return activeSessions
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(sessionId))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    @Scheduled(fixedRate = 5000)
    private void removeExpiredSessions() {
        Long currentTime = Instant.now().getEpochSecond();
        cookieExpireDates.forEach((key, value) -> {
            if (value.compareTo(currentTime) < 0) {
                voidSessionBySessionId(key);
            }
        });
    }

    public boolean isSessionIdAlreadyUsed(String sessionId) {
        return activeSessions
                .entrySet()
                .stream()
                .anyMatch(entry -> entry.getValue().equals(sessionId));
    }

    public boolean isUserAlreadyHasSessionId(Long userId) {
        return activeSessions.get(userId) != null;
    }

    public User getUserBySessionIdCookie(Cookie sessionIdCookie) {
        if (sessionIdCookie == null) return null;
        Optional<Long> id = findUserIdBySessionId(sessionIdCookie.getValue());
        if (!id.isPresent()) return null;
        return userRepository.findById(id.get()).orElse(null);
    }

    public List<Long> getSessionPrivileges(String sessionId) {
        return sessionPrivileges.get(sessionId);
    }

    public void setSessionPrivileges(String sessionId, List<Long> privilegeIds) {
        sessionPrivileges.put(sessionId, privilegeIds);
    }
}
