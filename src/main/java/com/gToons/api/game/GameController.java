package com.gToons.api.game;

import com.gToons.api.game.Action;
import com.gToons.api.game.Game;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;

@Component
public class GameController {

    private HashMap<String, Game> userInGame = new HashMap<>();

    public void handleUserAction(WebSocketSession session, Action action){
        System.out.println("handleUserAction - GameController");
        userInGame.get(session.getId()).action(action,session);
    }
    public void playerLeft(WebSocketSession session){
        Game g = userInGame.get(session.getId());
        //g.playerLeft(session)
    }

}
