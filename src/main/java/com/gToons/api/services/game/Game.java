package com.gToons.api.services.game;

import com.gToons.api.services.game.cards.Card;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

@Getter
public class Game implements Runnable{

    Player p1, p2;

    int p1pts = 0, p2pts = 0;

    Board p1Board, p2Board;

    public Game(WebSocketSession wss1, int p1id, WebSocketSession wss2, int p2id){
        p1 = new Player(wss1, p1id);
        p2 = new Player(wss2, p2id);
        //If possible also send jwt or user ID though here encapsulated from controller headers

        p1.draw();
        p2.draw();

    }

    public void action(int action, String user, String payload){
        switch (action){
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
        }

    }

    public void run(){


        //wait for both to be ready

    }
    private void discard(){


        //wait for both to be ready

    }
    private void secondRound(){

        //wait for both ready

    }

    private void replaceLastCard(){


    }
    private void gameOver(){

        //determine winner
        //give rewards

    }

}
