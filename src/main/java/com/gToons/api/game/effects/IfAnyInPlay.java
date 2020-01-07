package com.gToons.api.game.effects;

import com.gToons.api.model.Card;
import com.gToons.api.game.Location;

import java.util.ArrayList;

public class IfAnyInPlay  extends Effect {
    IfAnyInPlay(int v, boolean m, String attr[]){
        super(v,m,attr);
    }
    public Effect copy(){
        IfAnyInPlay effect = new IfAnyInPlay(value,multiplier,attributes);
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

        for(Card c : cardsInPlay){
            if(c.getAttributes().contains(attributes[0])){
                return;
            }
        }
        value = 0;
    }

}
