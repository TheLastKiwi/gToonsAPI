package com.gToons.api.game.effects;

import com.gToons.api.model.Card;
import com.gToons.api.game.Location;

import java.util.ArrayList;

public class ToAll extends Effect {
    ToAll(int v, boolean m, String attr[]){
        super(v,m,attr);
    }
    public Effect copy(){
        ToAll effect = new ToAll(value,multiplier,attributes);
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
    //Todo card #45 broken atm, effect not applied in json file
    //Todo card #82 says "Megas" but that's not a property, type, or group, it's a show source but not an exact show
    //It's called Megas XLR and is not in the database
}
