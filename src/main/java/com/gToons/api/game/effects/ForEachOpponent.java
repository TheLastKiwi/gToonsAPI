package com.gToons.api.game.effects;

import com.gToons.api.model.Card;
import com.gToons.api.game.Location;

import java.util.ArrayList;

public class ForEachOpponent extends Effect {
    ForEachOpponent(int v, boolean m, String attr[]){
        super(v,m,attr);
    }
    public Effect copy(){
        ForEachOpponent effect = new ForEachOpponent(value,multiplier,attributes);
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
        int newVal = 0;
        for(Card c : cardsInPlay){
            if(c.getOwner() != owner && c.getAttributes().contains(attributes[0])){
                newVal += value;
            }
        }
        value = newVal;
    }

}
