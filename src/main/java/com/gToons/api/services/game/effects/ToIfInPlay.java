package com.gToons.api.services.game.effects;

import com.gToons.api.domain.Card;
import com.gToons.api.services.game.Location;

import java.util.ArrayList;

public class ToIfInPlay extends Effect {
    @Override
    public ArrayList<Location> getImpactedLocations() {
        return null;
    }

    @Override
    public boolean appliesTo(Card c, Card cardsInPlay[]) {
        return c.getAttributes().contains(attributes[0]);
    }
    public void setFullValue(Card cardsInPlay[]) {
        for(Card c : cardsInPlay){
            if(c.getAttributes().contains(attributes[1])){
                return;
            }
        }
        value = 0;
    }
}
