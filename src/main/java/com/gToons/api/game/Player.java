package com.gToons.api.game;

import com.gToons.api.model.Card;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

@Getter
@Setter
@EqualsAndHashCode
public class Player {
    private WebSocketSession socket;
    private int id;
    private boolean isReady = false;
    private Deck deck;
    private Card hand[] = new Card[6];
    int points;
    Board board = new Board();

    static final int HAND_SIZE = 6;

    public Player(WebSocketSession wss, int pid){
        socket = wss;
        id = pid;
        //Todo get deck from DB
    }
    public WebSocketSession getSocket(){
        return socket;
    }
    public void draw(){
        //You only draw until full so draw a card for every open slot in your hand
        for(int i = 0; i < hand.length; i++){
            if(hand[i] == null){
                hand[i] = deck.draw();
            }
        }
    }
    public void playCards(Card[] cards){
        //remove cards from hand
        for(Card c : cards) {
            for (int i = 0; i < hand.length; i++) {
                if (c.equals(hand[i])) {
                    hand[i] = null;
                    break;
                }
            }
        }
    }
    public void discardCards(Card[] cards){
        playCards(cards);
    }
}
