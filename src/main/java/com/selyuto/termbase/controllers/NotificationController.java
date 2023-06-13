package com.selyuto.termbase.controllers;

import com.selyuto.termbase.dto.NotificationMessage;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    private SimpMessagingTemplate ws;

    public NotificationController(SimpMessagingTemplate ws) {
        this.ws = ws;
    }

    public void sendMessage(NotificationMessage message) {
        ws.convertAndSend("/topic/incoming-notifications", message);
    }
}
