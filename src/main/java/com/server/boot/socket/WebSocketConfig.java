package com.server.boot.socket;

import com.server.boot.service.UserService;
import com.server.boot.socket.CustomHttpSessionHandshakeInterceptor;
import com.server.boot.socket.MyWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private  final UserService userService;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new MyWebSocketHandler(userService), "/websocket-endpoint")
                .addInterceptors(new CustomHttpSessionHandshakeInterceptor())
                .setAllowedOrigins("http://10.101.52.96:8081");
    }

}
