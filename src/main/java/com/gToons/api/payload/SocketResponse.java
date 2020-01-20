package com.gToons.api.payload;

import com.gToons.api.model.Card;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SocketResponse {

    /*An effect would have a location and the new stats of that card
        So maybe it would have a from-playerId, a to-playerId, position (0-6 for board slot), then the new card to be placed there
        This way the front end just has to place the card in the new slot and the card can render itself with the new stats
        So instead of setting the state of the cards to be the returned card array we will process each card as it comes
        and overwrite all the values of the cards one at a time. It's a nuclear option but it allows the front end to just do what
        it does best, and that's display what it's given. We won't have to have it make any calculations at all.
        The front end will only have to deduce who owns the card(them or not) and place it in the position it's told.

        This way every action it receives is either SHOWING a card or it's showing a BUFF to a card. Both which can look similar.
        Maybe if there is already a card there there can be a highlighted animation to show it's getting a buff so it's not going unseen
    */
    private ArrayList<Card> cards = new ArrayList<>();
    private int nextPhase;
    private Card lastCard;

    public SocketResponse(ArrayList<Card> cards, int nextPhase){
        this.cards=cards;
        this.nextPhase=nextPhase;
    }
    public SocketResponse(Card lastCard, int nextPhase){
        this.lastCard=lastCard;
        this.nextPhase=nextPhase;
    }
}
