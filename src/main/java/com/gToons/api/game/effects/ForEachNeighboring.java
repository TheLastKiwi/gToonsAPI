package com.gToons.api.game.effects;

import com.gToons.api.game.Location;
import com.gToons.api.model.Card;


import java.util.ArrayList;

public class ForEachNeighboring  extends Effect {
    ForEachNeighboring(int v, boolean m, String attr[]){
        super(v,m,attr);
    }
    public Effect copy(){
        ForEachNeighboring effect = new ForEachNeighboring(value,multiplier,attributes);
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
        if(location.getOwner().getBoard().getCard(location.getX()+1, location.getY()).getAttributes().contains(attributes[0])){
            newVal += value;
        }
        if(location.getOwner().getBoard().getCard(location.getX()-1, location.getY()).getAttributes().contains(attributes[0])){
            newVal += value;
        }
        value = newVal;
    }
}
