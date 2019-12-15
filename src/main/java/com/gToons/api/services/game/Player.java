package com.gToons.api.services.game;

import com.gToons.api.domain.Card;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

@Getter
@Setter
public class Player {
    private WebSocketSession socket;
    private int id;
    private boolean isReady = false;
    private Deck deck;
    private Card hand[] = new Card[6];
    int points;
    Board board = new Board();


    Player(WebSocketSession wss, int pid){
        socket = wss;
        id = pid;
        //Todo get deck from DB
        Card d[] = {new Card(1), new Card(2), new Card(3), new Card(4), new Card(5), new Card(6), new Card(7), new Card(8), new Card(9), new Card(10), new Card(11), new Card(12)};
        deck = new Deck(d);
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
                if (hand[i].equals(c)) {
                    hand[i] = null;
                }
            }
        }
    }
    public void discardCards(Card[] cards){
        playCards(cards);
    }
}
