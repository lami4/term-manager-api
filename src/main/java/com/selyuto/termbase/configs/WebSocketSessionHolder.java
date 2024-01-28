package com.selyuto.termbase.configs;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WebSocketSessionHolder {
    private Map<Long, List<WebSocketSession>> websocketSessions = new HashMap<>();

    public void addWebSocketSessionByUserId(Long userId, WebSocketSession session) {
        List<WebSocketSession> userWebsocketSessions = websocketSessions.get(userId);
        if (userWebsocketSessions == null) userWebsocketSessions = new ArrayList<>();
        userWebsocketSessions.add(session);
        websocketSessions.put(userId, userWebsocketSessions);
    }

    public void closeWebSocketSessionsByUserId(Long userId) throws IOException {
        List<WebSocketSession> userWebsocketSessions = websocketSessions.get(userId);
        if (userWebsocketSessions != null) {
            for (WebSocketSession session : userWebsocketSessions) {
                session.close(CloseStatus.POLICY_VIOLATION);
            }
            websocketSessions.remove(userId);
        }
    }

    public List<WebSocketSession> getUserWebSocketSessionsById(Long userId) {
       return websocketSessions.get(userId);
    }
}
