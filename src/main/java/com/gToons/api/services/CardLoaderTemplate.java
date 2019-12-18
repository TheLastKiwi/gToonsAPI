package com.gToons.api.services;

import com.gToons.api.domain.Card;
import com.gToons.api.services.game.effects.Effect;

public class CardLoaderTemplate {
    private int id;
    private String name;
    private String character;
    private String color;
    private String desription;
    private String rarity;
    private String groups[];
    private String attributes[];
    private Object[][] effects;

    public Card toCard(){
        Card c = new Card();
        c.setId(id);
        c.setName(name);
        c.addAttribute(name);
        c.setCharacter(character);
        c.addAttribute(character);
        c.setCharacter(color);
        c.addAttribute(color);
        c.setDescription(desription);
        c.setRarity(rarity);
        c.addAttribute(rarity);

        for(String g:groups) c.addAttribute(g);
        for(Object effectDescriber[] : effects){
            c.addEffect(Effect.describerToEffect(effectDescriber));
        }
        return c;
    }

}
