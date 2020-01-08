package com.gToons.api.game;

import com.gToons.api.model.Card;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class Deck {
    private Queue<Card> deck = new LinkedList<>();
    //private Stack<Card> deck = new Stack<>();

    public Card draw(){
        return deck.poll();
        //return deck.pop();
    }
    Deck(List<Card> cards){
        //shuffle then set in deck

        //shuffle(cards);
        for(Card c : cards){
            deck.add(c);
            //deck.push(c);
        }
    }
    void shuffle(List<Card> cards){
        int swapIndex;
        Random rnd = new Random(System.currentTimeMillis());
        for(int i = 0; i < cards.size(); i++){
            swapIndex= rnd.nextInt(cards.size());
            Card c = cards.get(i);
            cards.set(i, cards.get(swapIndex));
            cards.set(swapIndex, c);
        }
    }
}
