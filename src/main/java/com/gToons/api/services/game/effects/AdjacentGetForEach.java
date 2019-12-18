package com.gToons.api.services.game.effects;

import com.gToons.api.domain.Card;
import com.gToons.api.services.game.Location;

import java.util.ArrayList;


public class AdjacentGetForEach extends Effect {
    AdjacentGetForEach(int v, boolean m, String attr[]){
        super(v,m,attr);
    }
    public Effect copy(){
        AdjacentGetForEach effect = new AdjacentGetForEach(value,multiplier,attributes);
        return effect;
    }
    @Override
    public ArrayList<Location> getImpactedLocations() {
        return null;
    }

    @Override
    public boolean appliesTo(Card c, Card cardsInPlay[]) {
        return c.getAttributes().contains(attributes[0]);
    }

    @Override
    public void setFullValue(Card cardsInPlay[]) {
        int newVal = 0;
        for(Card c : cardsInPlay){
            if(c.getAttributes().contains(attributes[1])){
                newVal+= value;
            }
        }
        value = newVal;
    }

}
