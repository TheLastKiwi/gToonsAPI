package com.gToons.api.services.game.effects;

import com.gToons.api.domain.Card;
import com.gToons.api.services.game.Location;

import java.util.ArrayList;

public class IfBothInPlay extends Effect {
    IfBothInPlay(int v, boolean m, String attr[]){
        super(v,m,attr);
    }
    public Effect copy(){
        IfBothInPlay effect = new IfBothInPlay(value,multiplier,attributes);
        return effect;
    }
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
        int attributeFound = 0;
        for(String attribute : attributes) {
            for (Card c : cardsInPlay) {
                if (c.getAttributes().contains(attribute)) {
                    attributeFound++;
                    break;
                }
            }
        }
        if(attributeFound != 2){
            value = 0;
        }
    }
}
