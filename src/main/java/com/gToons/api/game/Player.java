package com.gToons.api.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gToons.api.model.Card;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class Player {
    @JsonIgnore
    private WebSocketSession socket;
    private int id;
    @JsonIgnore
    private boolean isReady = false;
    @JsonIgnore
    private Deck deck;
    @JsonIgnore
    private List<Card> hand = new ArrayList<>();
    @JsonIgnore
    int points;
    @JsonIgnore
    Board board = new Board();

    @JsonIgnore
    static final int HAND_SIZE = 6;

    public Player(WebSocketSession wss, int pid){
        socket = wss;
        id = pid;
        for(int i = 0; i < HAND_SIZE; i++)hand.add(null);
    }
    public WebSocketSession getSocket(){
        return socket;
    }
    public void draw(){
        //You only draw until full so draw a card for every open slot in your hand
        for(int i = 0; i < HAND_SIZE; i++){
            if(hand.get(i) == null){
                hand.set(i, deck.draw());
            }
        }
    }
    public void playCards(List<Card> cards){
        //remove cards from hand
        for(Card c : cards) {
            for (int i = 0; i < hand.size(); i++) {
                if (c.equals(hand.get(i))) {
                    hand.set(i, null);
                    break;
                }
            }
        }
    }
    public void discardCards(List<Card> cards){
        playCards(cards);
    }
}
