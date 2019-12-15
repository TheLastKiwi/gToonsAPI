package com.gToons.api;

import com.gToons.api.services.GameActionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer{

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry){
        //define endpoint

        registry.addHandler(new GameActionHandler(),"/gameAction").setAllowedOrigins("*");
    }


}
