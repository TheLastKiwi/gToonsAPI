package com.gToons.api.payload;

import com.gToons.api.model.Card;

public class SocketResponse {
    private Card[] cards;
    private int nextPhase;
    private Card lastCard;
    public SocketResponse(Card[] cards, int nextPhase){
        this.cards=cards;
        this.nextPhase=nextPhase;
    }
    public SocketResponse(Card lastCard, int nextPhase){
        this.lastCard=lastCard;
        this.nextPhase=nextPhase;
    }
}
