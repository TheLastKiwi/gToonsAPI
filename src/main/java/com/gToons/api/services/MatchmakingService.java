package com.gToons.api.services;

import com.gToons.api.domain.Card;
import com.gToons.api.services.game.Game;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MatchmakingService {
    BlockingQueue<WebSocketSession> gameQueue = new LinkedBlockingQueue<>();

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public Game findGame(WebSocketSession s){
        logger.info("User added to gameQueue");
        gameQueue.add(s);
        return createGame();
    }

    private synchronized Game createGame(){
        if(gameQueue.size()>1){
            logger.info("Game created");
            System.out.println("Game created between 2 users");
            //TODO replace hardcoded ids
            Game g = new Game(gameQueue.poll(),100, gameQueue.poll(),200);
            return g;
        }
        return null;
    }
}
