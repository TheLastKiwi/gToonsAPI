package com.gToons.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Builder
@Getter
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

    String color;
    int points;

    public Card(int id){
        this.id = id;
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

}
