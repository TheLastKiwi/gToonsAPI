package com.gToons.api.game.effects;

import com.gToons.api.game.Location;
import com.gToons.api.model.Card;
import com.gToons.api.game.Player;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor
public abstract class Effect {
    Player owner;
    protected int value;
    boolean multiplier;
    Location location;
    String attributes[];

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

    //Todo make sure this works
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
    public abstract Effect copy();

    //Todo set point value for effects that get have "for each"
    public static Effect describerToEffect(Object describer[]){
        int id = ((Double)describer[0]).intValue();
        int value = ((Double)describer[describer.length-2]).intValue();
        boolean multiplier = (Boolean)describer[describer.length-1];
        String attributes[] = new String[describer.length-3];
        attributes[0] = (String)describer[1];
        Effect effect;
        switch (id){
            case 1:
                effect = new To(value,multiplier,attributes); break;
            case 2:
                effect = new ToAll(value,multiplier,attributes); break;
            case 3:
                effect = new ToAllOpponents(value,multiplier,attributes); break;
            case 4:
                effect = new ToAllOther(value,multiplier,attributes); break;
            case 5:
                effect = new ToNeighbor(value,multiplier,attributes); break;
            case 6:
                effect = new ToOpposing(value,multiplier,attributes); break;
            case 7:
                effect = new ToOpposingIfNot(value,multiplier,attributes); break;
            case 8:
                attributes[1] = (String)describer[2];
                effect = new ToIfInPlay(value,multiplier,attributes);
                break;
            case 9:
                effect = new IfAnyInPlay(value,multiplier,attributes); break;
            case 10:
                effect = new IfBothInPlay(value,multiplier,attributes); break;
            case 11:
                effect = new IfNextTo(value,multiplier,attributes); break;
            case 12:
                effect = new IfOppositeIs(value,multiplier,attributes); break;
            case 13:
                effect = new ForEach(value,multiplier,attributes); break;
            case 14:
                effect = new ForEachNeighboring(value,multiplier,attributes); break;
            case 15:
                effect = new ForEachOpponent(value,multiplier,attributes); break;
            case 16:
                attributes[1] = (String)describer[2];
                effect = new AllGetForEach(value,multiplier,attributes);
                break;
            case 17:
                effect = new AdjacentGetForEach(value,multiplier,attributes); break;
            case 18:
                effect = new OpposingLoseAdjacencyEffects(value,multiplier,attributes); break;
            case 19:
                effect = new TreatNeighboringAs(value,multiplier,attributes); break;
            case 20:
                effect = new TreatOpposingAs(value,multiplier,attributes); break;
            case 21:
                effect = new LastCardPlayed(value,multiplier,attributes); break;
            default:
                effect= null; //results in null pointer if parsing error

        }
        return effect;
    }
}
