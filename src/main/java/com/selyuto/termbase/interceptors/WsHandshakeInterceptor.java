package com.selyuto.termbase.interceptors;

import com.selyuto.termbase.authentication.Authenticator;
import com.selyuto.termbase.models.User;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static com.selyuto.termbase.authentication.AuthenticationConstants.SESSION_ID_COOKIE_NAME;

@Component
public class WsHandshakeInterceptor implements HandshakeInterceptor {

    private final Authenticator authenticator;

    public WsHandshakeInterceptor(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) serverHttpRequest;
        HttpServletRequest servletRequest = servletServerHttpRequest.getServletRequest();
        Cookie sessionId = WebUtils.getCookie(servletRequest, SESSION_ID_COOKIE_NAME);
        if (sessionId != null) {
            User user = authenticator.getUserBySessionIdCookie(sessionId);
            map.put(SESSION_ID_COOKIE_NAME, sessionId.getValue());
            map.put("userId", user.getId());
            map.put("userEmail", user.getEmail());
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
    }
}
