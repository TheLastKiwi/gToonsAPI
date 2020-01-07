package com.gToons.api.game.effects;

import com.gToons.api.model.Card;
import com.gToons.api.game.Location;

import java.util.ArrayList;

public class ToOpposing  extends Effect {
    ToOpposing(int v, boolean m, String attr[]){
        super(v,m,attr);
    }
    public Effect copy(){
        ToOpposing effect = new ToOpposing(value,multiplier,attributes);
        return effect;
    }
    @Override
    public ArrayList<Location> getImpactedLocations() {
        return null;
    }

    @Override
    public boolean appliesTo(Card c, Card cardsInPlay[]) {
        //TODO fix
        return true;
    }

}
