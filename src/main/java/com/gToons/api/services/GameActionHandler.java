package com.gToons.api.services;

import com.gToons.api.services.game.Action;
import com.gToons.api.services.game.Game;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.websocket.Session;
import java.util.HashMap;

public class GameActionHandler extends TextWebSocketHandler {
    Gson gson = new Gson();
    MatchmakingService matchmakingService = new MatchmakingService();

    private HashMap<String, Game> userInGame = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Game g = matchmakingService.findGame(session);
        System.out.println("Connected");
        if(g != null){
            userInGame.put(g.getP1().getSocket().getId(),g);
            userInGame.put(g.getP2().getSocket().getId(),g);
            g.getP1().getSocket().sendMessage(new TextMessage("Game started, you're P1"));
            g.getP2().getSocket().sendMessage(new TextMessage("Game started, you're P2"));
        }
    }

    int messageCounter = 0;
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //Use gson to deserialize message payload into Action object

        Action action = gson.fromJson(message.getPayload(), Action.class);
        userInGame.get(session.getId()).action(action, session);

        //System.out.println("Acquired message: " + message.getPayload());
        //session.sendMessage(new TextMessage(message.getPayload() + messageCounter++));

    }

    private int getAction(String s){
        return s.charAt(0) - '0';
    }
}
