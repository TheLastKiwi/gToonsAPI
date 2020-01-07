package com.gToons.api;

import com.gToons.api.game.Action;
import com.gToons.api.game.Game;
import com.gToons.api.game.GameController;
import com.gToons.api.game.MatchmakingService;
import com.gToons.api.jwt.JwtUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
@Component
public class SocketMessageHandler extends TextWebSocketHandler{


/*
Option 1:
    Could establish connection then immediately send a message back
    asking for their token, when message is received check if valid
    if valid then add to matchmaking queue, if not then close conn

Option 2:
    Have front end send a get request to the endpoint along with auth token
    and a uuid then if auth is successful add uuid to global state
    with token, next they attempt a socket connection and first message
    sent is the uuid they generated so we can associate a valid user
    to a socket session
    This would stop unnecessary websockets from being opened if they aren't
    logged in
    I do like option 1 though
 */
    static Gson gson = new Gson();
    @Autowired
    GameController gameController;

    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    MatchmakingService matchmakingService;

    //@Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        String tokenInProtocol = (String)((session.getHandshakeHeaders().get("sec-websocket-protocol")).get(0));
//        System.out.println("After conn est: " + tokenInProtocol);
//        session.close();
        System.out.println("ConnEst");
//        potentially ask for token
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        System.out.println("Msg rec:" + message.getPayload());
        Action action = gson.fromJson(message.getPayload(),Action.class);
        switch(action.getAction()){
            case MATCHMAKE:
                if(jwtUtil.validateToken(action.getToken())) {
                    int id = jwtUtil.getUserIdFromJWT(action.getToken());
                    Game g = matchmakingService.findGame(session,id);
                }
                break;
            case GAME:
                gameController.handleUserAction(session,action);
                break;
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        //remove game from active games
        System.out.println("ConnClosed");
        gameController.playerLeft(session);
    }


}
