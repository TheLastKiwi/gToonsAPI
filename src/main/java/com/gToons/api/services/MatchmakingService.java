package com.gToons.api.services;

import com.gToons.api.services.game.Game;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

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
            Game g = new Game(gameQueue.poll(),35, gameQueue.poll(),17);
            new Thread(g).start();
            return g;
        }
        return null;
    }
}
