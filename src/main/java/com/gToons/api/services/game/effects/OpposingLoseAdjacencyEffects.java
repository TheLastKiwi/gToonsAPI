package com.gToons.api.services.game.effects;

import com.gToons.api.domain.Card;
import com.gToons.api.services.game.Location;

import java.util.ArrayList;

public class OpposingLoseAdjacencyEffects  extends Effect {
    //Todo whothe fuck knows
    @Override
    public ArrayList<Location> getImpactedLocations() {
        return null;
    }

    @Override
    public boolean appliesTo(Card c, Card cardsInPlay[]) {
        return false;
    }
}
