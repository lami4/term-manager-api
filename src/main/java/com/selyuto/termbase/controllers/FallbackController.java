package com.selyuto.termbase.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {
    @RequestMapping(path = "/unauthorized")
    public ResponseEntity<String> unauthorizedFallback() {
        return new ResponseEntity<>("Unauthorized action", HttpStatus.FORBIDDEN);
    }
}
