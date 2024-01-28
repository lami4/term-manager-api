package com.selyuto.termbase.controllers;

import com.selyuto.termbase.configs.WebSocketSessionHolder;
import com.selyuto.termbase.dto.NotificationMessage;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


import java.util.Map;

@Controller
public class NotificationController {


    private SimpMessagingTemplate ws;

    private WebSocketSessionHolder webSocketSessionHolder;

    public NotificationController(SimpMessagingTemplate ws, WebSocketSessionHolder webSocketSessionHolder) {
        this.ws = ws;
        this.webSocketSessionHolder = webSocketSessionHolder;
    }

    public void sendMessage(NotificationMessage message) {
        ws.convertAndSend("/topic/notifications", message);
    }

    @MessageMapping("/table-resizing/{userId}")
    public void login(@Payload String message, @DestinationVariable String userId, SimpMessageHeaderAccessor headerAccessor) {
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        webSocketSessionHolder.getUserWebSocketSessionsById((Long) sessionAttributes.get("userId"));
        ws.convertAndSendToUser(userId, "/topic/test", message + " test");
        ws.convertAndSend("/general", message + " notifications");
    }
}
