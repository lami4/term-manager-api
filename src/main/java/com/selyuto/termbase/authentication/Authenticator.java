package com.selyuto.termbase.authentication;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Authenticator {

    private ConcurrentHashMap <Long, String> activeSessions = new ConcurrentHashMap<>();
    private ConcurrentHashMap <String, Long> cookieExpireDates = new ConcurrentHashMap<>();

    public Map<Long, String> getActiveSessions() {
        return activeSessions;
    }

    public void addSession(Long userId, String sessionId, int cookieMaxAge) {
        activeSessions.put(userId, sessionId);
        cookieExpireDates.put(sessionId, Instant.now().getEpochSecond() + (long) cookieMaxAge);
    }

    public void voidSessionBySessionId(String sessionId) {
        findUserIdBySessionId(sessionId).ifPresent(userId -> cookieExpireDates.remove(activeSessions.remove(userId)));
    }

    public void voidSessionByUserId(Long userId) {
        cookieExpireDates.remove(activeSessions.remove(userId));
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

    public boolean isUserAlreadyAuthenticated(Long userId) {
        return activeSessions.get(userId) != null;
    }
}
