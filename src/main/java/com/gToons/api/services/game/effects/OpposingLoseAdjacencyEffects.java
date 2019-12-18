package com.gToons.api.services.game.effects;

import com.gToons.api.domain.Card;
import com.gToons.api.services.game.Location;

import java.util.ArrayList;

public class OpposingLoseAdjacencyEffects  extends Effect {

    OpposingLoseAdjacencyEffects(int v, boolean m, String attr[]){
        super(v,m,attr);
    }
    public Effect copy(){
        OpposingLoseAdjacencyEffects effect = new OpposingLoseAdjacencyEffects(value,multiplier,attributes);
        return effect;
    }

    //Todo whothe fuck knows what todo here
    @Override
    public ArrayList<Location> getImpactedLocations() {
        return null;
    }

    @Override
    public boolean appliesTo(Card c, Card cardsInPlay[]) {
        return false;
    }
}
