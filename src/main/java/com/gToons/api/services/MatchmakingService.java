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
    final Card cards[] = loadCards();
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
            Game g = new Game(gameQueue.poll(),35, gameQueue.poll(),17);
            return g;
        }
        return null;
    }
    private Card[] loadCards(){
        long now = System.currentTimeMillis();
        String path = "cards100.cfg";
        String data = "";
        CardLoaderTemplate cardTemplates[] = new CardLoaderTemplate[0];
        Gson gson = new Gson();
        try {
            data = new String(Files.readAllBytes(Paths.get(path)));
            cardTemplates = gson.fromJson(data,CardLoaderTemplate[].class);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File read error");
        }
        Card cards[] = new Card[cardTemplates.length];
        for(int i = 0; i < cardTemplates.length; i++){
            cards[i] = cardTemplates[i].toCard();
        }
        System.out.println(System.currentTimeMillis()-now);
        return cards;

    }
}
