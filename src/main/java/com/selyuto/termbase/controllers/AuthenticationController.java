package com.selyuto.termbase.controllers;


import com.selyuto.termbase.services.AuthenticationService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/login")
    public ResponseEntity<Map<String, Object>> signIn(HttpServletRequest request, HttpServletResponse response) {
        return authenticationService.signIn(request.getHeader("email"), request.getHeader("password"), response);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> signOut(HttpServletRequest request, HttpServletResponse response) {
        return authenticationService.signOut(WebUtils.getCookie(request, "sessionIdTm"), response);
    }

    @GetMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateSession(HttpServletRequest request, HttpServletResponse response) {
        return authenticationService.validateSession(WebUtils.getCookie(request, "sessionIdTm"), response);
    }
}