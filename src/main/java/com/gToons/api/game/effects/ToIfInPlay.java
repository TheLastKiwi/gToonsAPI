package com.gToons.api.game.effects;

import com.gToons.api.model.Card;
import com.gToons.api.game.Location;

import java.util.ArrayList;

public class ToIfInPlay extends Effect {
    ToIfInPlay(int v, boolean m, String attr[]){
        super(v,m,attr);
    }
    public Effect copy(){
        ToIfInPlay effect = new ToIfInPlay(value,multiplier,attributes);
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
    public void setFullValue(Card cardsInPlay[]) {
        for(Card c : cardsInPlay){
            if(c.getAttributes().contains(attributes[1])){
                return;
            }
        }
        value = 0;
    }
}
