package com.server.boot.socket;

import com.server.boot.socket.CustomHttpSessionHandshakeInterceptor;
import com.server.boot.socket.MyWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new MyWebSocketHandler(), "/websocket-endpoint")
                .addInterceptors(new CustomHttpSessionHandshakeInterceptor())
                .setAllowedOrigins("http://10.101.52.96:8081");
    }

}
