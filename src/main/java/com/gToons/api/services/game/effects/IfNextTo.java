package com.gToons.api.services.game.effects;

import com.gToons.api.domain.Card;
import com.gToons.api.services.game.Location;

import java.util.ArrayList;

public class IfNextTo extends Effect {
    IfNextTo(int v, boolean m, String attr[]){
        super(v,m,attr);
    }
    public Effect copy(){
        IfNextTo effect = new IfNextTo(value,multiplier,attributes);
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
        if(!owner.getBoard().getCard(location.getX()-1,location.getY()).getAttributes().contains(attributes[0]) &&
           !owner.getBoard().getCard(location.getX()+1,location.getY()).getAttributes().contains(attributes[0])){
            value = 0;
        }
    }
}
