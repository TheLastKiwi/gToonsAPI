package com.gToons.api.services.game.effects;

import com.gToons.api.domain.Card;
import com.gToons.api.services.game.Location;
import com.gToons.api.services.game.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class Effect {
    Player owner;
    protected int value;
    boolean multiplier;
    Location location;
    String attributes[];

    Effect(){

    }
    Effect(int v, boolean mult, String a[]){
        value = v;
        multiplier = mult;
        attributes = a;
    }

    public void finalizeLocation(Location l){
        location = l;
    }
    public abstract ArrayList<Location> getImpactedLocations();//{
//        ArrayList<Location> locations= new ArrayList<>();
//        return locations;
//    }
    public abstract boolean appliesTo(Card c, Card cardsInPlay[]);//{
//        return c.getAttributes().contains(attribute);
//    }
    public void applyTo(Card c){
        if(multiplier){
            c.setBonusPoints(c.getBonusPoints() + c.getPoints() * value);
        }
        else{
            c.setBonusPoints(c.getBonusPoints() + c.getPoints());
        }
    }
    public void setFullValue(Card cardsInPlay[]){
        //default is base value
    }
    protected Card getCard(){
        return owner.getBoard().getCard(location.getX(),location.getY());
    }

    //Todo set point value for effects that get have "for each"
}
