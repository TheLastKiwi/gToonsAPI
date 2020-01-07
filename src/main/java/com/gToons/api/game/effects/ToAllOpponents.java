package com.gToons.api.game.effects;

import com.gToons.api.game.Location;
import com.gToons.api.model.Card;

import java.util.ArrayList;

public class ToAllOpponents  extends Effect {
    ToAllOpponents(int v, boolean m, String attr[]){
       super(v,m,attr);
    }
    public Effect copy(){
        ToAllOpponents effect = new ToAllOpponents(value,multiplier,attributes);
        return effect;
    }
    @Override
    public ArrayList<Location> getImpactedLocations() {
        return null;
    }

    @Override
    public boolean appliesTo(Card c, Card cardsInPlay[]) {
        return location.getOwner() != c.getLocation().getOwner() && c.getAttributes().contains(attributes[0]);
    }
}
