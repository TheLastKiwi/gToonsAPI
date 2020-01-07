package com.gToons.api.game.effects;

import com.gToons.api.game.Location;
import com.gToons.api.model.Card;

import java.util.ArrayList;

public class AllGetForEach extends Effect {
    AllGetForEach(int v, boolean m, String attr[]){
        super(v,m,attr);
    }
    public Effect copy(){
        AllGetForEach effect = new AllGetForEach(value,multiplier,attributes);
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
                newVal += value;
            }
        }
    }
}
