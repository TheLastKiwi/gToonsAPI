package com.gToons.api.services.game;

import com.gToons.api.domain.Card;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    Player(WebSocketSession wss, int pid){
        socket = wss;
        id = pid;
        //Todo get deck from DB
        Card d[] = getDeckFromDB(pid);
        //Card d[] = {new Card(1), new Card(2), new Card(3), new Card(4), new Card(5), new Card(6), new Card(7), new Card(8), new Card(9), new Card(10), new Card(11), new Card(12)};
        deck = new Deck(d);
    }
    private Card[] getDeckFromDB(int pid){
        List<Integer> intCards;
        if(pid == 100) {
            //DB Call return card from deck as a List<Integer>
            intCards = Arrays.asList(1,2,3,4,5,6);
        }
        else {
            intCards = Arrays.asList(21,22,23,24,25,26);
        }
        Card cards[] = new Card[HAND_SIZE];
        for(int i = 0; i < intCards.size(); i++){
            cards[i] = Game.allCards[intCards.get(i)];
        }
        return cards;
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
