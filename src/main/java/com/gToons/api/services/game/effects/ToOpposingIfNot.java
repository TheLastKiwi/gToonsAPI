package com.gToons.api.services.game.effects;

import com.gToons.api.domain.Card;
import com.gToons.api.services.game.Location;

import java.util.ArrayList;

public class ToOpposingIfNot  extends Effect {
    ToOpposingIfNot(int v, boolean m, String attr[]){
        super(v,m,attr);
    }
    public Effect copy(){
        ToOpposingIfNot effect = new ToOpposingIfNot(value,multiplier,attributes);
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
