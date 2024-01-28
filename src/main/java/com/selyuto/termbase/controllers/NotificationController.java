package com.selyuto.termbase.controllers;

import com.selyuto.termbase.configs.WebSocketSessionHolder;
import com.selyuto.termbase.dto.NotificationMessage;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.WebSocketSession;


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

    @MessageMapping("/table-resizing")
    public void login(@Payload String message, SimpMessageHeaderAccessor headerAccessor, WebSocketSession wss) {
        System.out.println(wss);
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        webSocketSessionHolder.getUserWebSocketSessionById((Long) sessionAttributes.get("userId"));
        ws.convertAndSend("/topic/test", message + " test");
        ws.convertAndSend("/topic/notifications", message + "notifications");
    }
}
