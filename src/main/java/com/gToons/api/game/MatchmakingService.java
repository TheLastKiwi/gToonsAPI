package com.gToons.api.game;

import com.gToons.api.game.Game;
import com.gToons.api.game.Player;
import com.gToons.api.model.UserDeckCard;
import com.gToons.api.repository.UserDeckCardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class MatchmakingService {
    @Autowired
    UserDeckCardRepository udcr;
    @Autowired
    GameController gameController;
    BlockingQueue<Player> gameQueue = new LinkedBlockingQueue<>();
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public Game findGame(WebSocketSession s, int id){
        Optional<List<UserDeckCard>> opCards = udcr.findByUserId(id);
        if(opCards.isPresent()) {
            logger.info("User added to gameQueue");
            gameQueue.add(new Player(s, id));
            return createGame();
        }
        return null;
    }

    //Synchronized to only create one game at a time so gameQueue is never overpolled
    private synchronized Game createGame(){
        if(gameQueue.size()>1){
            logger.info("Game created");
            System.out.println("Game created between 2 users");

            Game g = new Game(gameQueue.poll(),gameQueue.poll(),udcr);
            gameController.addGame(g);
            return g;
        }
        return null;
    }
}
