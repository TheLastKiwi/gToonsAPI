package com.gToons.api.services.game;

import com.gToons.api.services.game.cards.Card;

import java.util.Random;
import java.util.Stack;

public class Deck {
    private Stack<Card> deck = new Stack<>();

    public Card draw(){
        return deck.pop();
    }
    Deck(Card cards[]){
        //shuffle then set in deck
        Random rnd = new Random(System.currentTimeMillis());
        int swapIndex;
        for(int i = 0; i < cards.length; i++){
            swapIndex= rnd.nextInt(cards.length);
            Card c = cards[i];
            cards[i] = cards[swapIndex];
            cards[swapIndex] = c;
        }
        for(Card c : cards){
            deck.push(c);
        }
    }
}
