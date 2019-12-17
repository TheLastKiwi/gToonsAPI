package com.gToons.api.services.game.effects;

import com.gToons.api.domain.Card;
import com.gToons.api.services.game.Location;

import java.util.ArrayList;

public class ForEachOpponent extends Effect {
    @Override
    public ArrayList<Location> getImpactedLocations() {
        return null;
    }

    @Override
    public boolean appliesTo(Card c, Card cardsInPlay[]) {
        return true;
    }

    @Override
    public void setFullValue(Card cardsInPlay[]) {
        int newVal = 0;
        for(Card c : cardsInPlay){
            if(c.getOwner() != owner && c.getAttributes().contains(attributes[0])){
                newVal += value;
            }
        }
        value = newVal;
    }

}
