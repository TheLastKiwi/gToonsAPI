package com.gToons.api.domain;

import com.gToons.api.services.game.Location;
import com.gToons.api.services.game.Player;
import com.gToons.api.services.game.effects.Effect;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.HashSet;

@Entity
@Builder
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "card", catalog = "gtoons")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false)
    String name = "";

    @Transient
    Effect effects[];

    @Transient
    HashSet<String> attributes = new HashSet<>();
    @Transient
    ArrayList<Effect> unappliedEffects = new ArrayList<>();
    @Transient
    Location location;

    String color;
    int points;

    @Transient
    public boolean nullified;
    @Transient
    Player owner;
    @Transient
    int bonusPoints;

    public Card(int id){
        this.id = id;
        attributes.add("");
    }
    public void addAttribute(String s){
        attributes.add(s);
    }
    @Override
    public String toString(){
        return id + "";
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Card){
            Card c = (Card)o;
            if(c.getId()==id){
                return true;
            }
        }
        return false;
    }

    public void finalizeLocation(Location l){
        for(Effect e: effects){
            e.finalizeLocation(l);
        }
    }
    public void addUnappliedEffect(Effect e){
        unappliedEffects.add(e);
    }

}
