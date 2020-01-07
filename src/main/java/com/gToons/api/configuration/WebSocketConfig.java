package com.gToons.api.configuration;

import com.gToons.api.SocketMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{
    @Autowired
    SocketMessageHandler socketMessageHandler;
    //Could have this in a handler class and could call gethandler that
    //returns the instance of the handler class
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry){
        //define endpoint
        registry.addHandler(socketMessageHandler,"/gameAction")
            .setAllowedOrigins("*");
            //.addInterceptors(new WebSocketHandshakeInterceptor());
    }

}
