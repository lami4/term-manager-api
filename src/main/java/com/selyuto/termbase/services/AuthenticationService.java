package com.selyuto.termbase.services;

import com.selyuto.termbase.authentication.Authenticator;
import com.selyuto.termbase.models.Privilege;
import com.selyuto.termbase.models.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class AuthenticationService {

    private static final int COOKIE_EXPIRE_PERIOD_IN_SECONDS = 84000;
    private final Authenticator authenticator;
    private final UserService userService;
    private final PrivilegeService privilegeService;

    public AuthenticationService(Authenticator authenticator, UserService userService, PrivilegeService privilegeService) {
        this.authenticator = authenticator;
        this.userService = userService;
        this.privilegeService = privilegeService;
    }

    public ResponseEntity<Map<String, Object>> signIn(String email, String password, HttpServletResponse response) {
        Map<String, Object> signInResult = new HashMap<>();
        User user = userService.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            if (authenticator.isUserAlreadyAuthenticated(user.getId())) {
                authenticator.voidSessionByUserId(user.getId());
            }
            String sessionId = generateUserId();
            Cookie cookie = buildSessionIdCookie(sessionId, COOKIE_EXPIRE_PERIOD_IN_SECONDS);
            response.addCookie(cookie);
            authenticator.addSession(user.getId(), sessionId, cookie.getMaxAge());
            signInResult.put("privileges", getPrivilegesByUserId(user.getId()));
            signInResult.put("status", "Signed in successfully");
            return new ResponseEntity<>(signInResult, HttpStatus.OK);
        }
        signInResult.put("status", "Wrong credentials");
        return new ResponseEntity<>(signInResult, HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<String> signOut(Cookie sessionIdCookie, HttpServletResponse response) {
        if (sessionIdCookie != null) {
            authenticator.voidSessionBySessionId(sessionIdCookie.getValue());
        }
        Cookie cookie = buildSessionIdCookie(null, 0);
        response.addCookie(cookie);
        return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Object>> validateSession(Cookie sessionIdCookie, HttpServletResponse response) {
        Map<String, Object> validationResult = new HashMap<>();
        if (sessionIdCookie == null) {
            validationResult.put("status", "No session ID provided");
            return new ResponseEntity<>(validationResult, HttpStatus.FORBIDDEN);
        } else if (!authenticator.isSessionIdAlreadyUsed(sessionIdCookie.getValue())) {
            validationResult.put("status", "No session found");
            return new ResponseEntity<>(validationResult, HttpStatus.FORBIDDEN);
        } else {
            Optional<Long> userId = authenticator.findUserIdBySessionId(sessionIdCookie.getValue());
            if (userId.isPresent()) {
                validationResult.put("privileges", getPrivilegesByUserId(userId.get()));
            } else {
                validationResult.put("privileges", Collections.emptyList());
            }
            validationResult.put("status", "Already signed in");
            return new ResponseEntity<>(validationResult, HttpStatus.OK);
        }
    }

    private String generateUserId() {
        String sessionId = UUID.randomUUID().toString();
        if (authenticator.isSessionIdAlreadyUsed(sessionId)) return generateUserId();
        return sessionId;
    }

    private Cookie buildSessionIdCookie(String sessionId, int maxAge) {
        Cookie cookie = new Cookie("sessionIdTm", sessionId);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        return cookie;
    }

    private List<Long> getPrivilegesByUserId(Long userId) {
        return privilegeService.getPrivilegesByUserId(userId)
                .stream()
                .map(Privilege::getId)
                .collect(Collectors.toList());
    }

}
