package com.gToons.api.services.game.cards;

import com.gToons.api.services.game.effects.Effect;

public class Card {
    String name = "";
    int id;
    Effect actions[];
    public Card(int id){
        this.id = id;
    }
    @Override
    public String toString(){
        return id + "";
    }

    public void doActions(){
        for(Effect e : actions){
            e.doAction();
        }
    }
}
