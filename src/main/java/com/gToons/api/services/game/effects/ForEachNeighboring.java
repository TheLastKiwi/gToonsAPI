package com.gToons.api.services.game.effects;

import com.gToons.api.domain.Card;
import com.gToons.api.services.game.Location;

import java.util.ArrayList;

public class ForEachNeighboring  extends Effect {
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
        if(location.getOwner().getBoard().getCard(location.getX()+1, location.getY()).getAttributes().contains(attributes[0])){
            newVal += value;
        }
        if(location.getOwner().getBoard().getCard(location.getX()-1, location.getY()).getAttributes().contains(attributes[0])){
            newVal += value;
        }
        value = newVal;
    }
}
