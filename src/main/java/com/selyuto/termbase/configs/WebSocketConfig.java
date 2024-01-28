package com.selyuto.termbase.configs;

import com.selyuto.termbase.interceptors.WsHandshakeInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Value("${api.hostname}")
    private String hostname;
    WsHandshakeInterceptor wsHandshakeInterceptor;

    WebSocketSessionHolder webSocketSessionHolder;

    public WebSocketConfig(WsHandshakeInterceptor wsHandshakeInterceptor, WebSocketSessionHolder webSocketSessionHolder) {
        this.wsHandshakeInterceptor = wsHandshakeInterceptor;
        this.webSocketSessionHolder = webSocketSessionHolder;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/general", "/private/user");
        config.setApplicationDestinationPrefixes("");
        config.setUserDestinationPrefix("/private/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/tb-websocket")
                .addInterceptors(wsHandshakeInterceptor)
                .setAllowedOrigins(hostname)
                .withSockJS();
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        registry.addDecoratorFactory(new WebSocketHandlerDecoratorFactory() {
            @Override
            public WebSocketHandler decorate(final WebSocketHandler handler) {
                return new WebSocketHandlerDecorator(handler) {
                    @Override
                    public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
                        Long userId = (Long) session.getAttributes().get("userId");
                        webSocketSessionHolder.addWebSocketSessionByUserId(userId, session);
                        super.afterConnectionEstablished(session);
                    }
                };
            }
        });
    }
}
