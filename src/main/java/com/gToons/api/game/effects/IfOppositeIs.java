package com.gToons.api.game.effects;

import com.gToons.api.model.Card;
import com.gToons.api.game.Location;

import java.util.ArrayList;

public class IfOppositeIs  extends Effect {
    Card opposite;
    IfOppositeIs(int v, boolean m, String attr[]){
        super(v,m,attr);
    }
    public Effect copy(){
        IfOppositeIs effect = new IfOppositeIs(value,multiplier,attributes);
        effect.opposite=opposite;
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

    public Card getOpposite(Card c, Card cardsInPlay[]){
        int oneDLocation = getOneDLocation(c,cardsInPlay);
        if(oneDLocation > 7){
            return cardsInPlay[oneDLocation-7];
        }
        return cardsInPlay[oneDLocation+7];
    }
    public int getOneDLocation(Card c, Card cardsInPlay[]){
        for(int i =0 ; i < cardsInPlay.length; i ++){
            if(cardsInPlay[i] == c)return i;
        }
        return -1;//will always be in play
    }
    public void setFullValue(Card cardsInPlay[]) {
        opposite = getOpposite(getCard(),cardsInPlay);
        if (!opposite.getAttributes().contains(attributes[0])) {
            value = 0;
        }
    }

}
