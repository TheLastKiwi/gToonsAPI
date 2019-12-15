package com.gToons.api.services.game;

import com.gToons.api.services.game.cards.Card;
import org.springframework.web.socket.WebSocketSession;

public class Player {
    WebSocketSession socket;
    int id;
    Deck deck;
    Card hand[] = new Card[6];

    Player(WebSocketSession wss, int pid){
        socket = wss;
        id = pid;
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
        for(int i = 0; i < hand.length; i++){
            if(hand[i].equals(cards[i])){
                hand[i] = null;
            }
        }
    }
}
