package com.gToons.api;

import com.gToons.api.game.effects.Effect;
import com.gToons.api.model.Card;

public class CardLoaderTemplate {
    private int id;
    private String name;
    private String character;
    private String color;
    private String description;
    private String rarity;
    private String groups[];
    private String attributes[];
    private Object[][] effects;
    public int points;

    public Card toCard(){
        Card c = new Card();
        c.setId(id);
        c.setName(name);
        c.addAttribute(name);
        c.setCharacter(character);
        c.addAttribute(character);
        c.setCharacter(color);
        c.addAttribute(color);
        c.setDescription(description);
        c.setRarity(rarity);
        c.addAttribute(rarity);
        c.setPoints(points);
        for(String g:groups) c.addAttribute(g);
        for(Object effectDescriber[] : effects){
            c.addEffect(Effect.describerToEffect(effectDescriber));
        }
        return c;
    }
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("\"id\" : " + id +"," + '\n');
        str.append("\"name\" : \"" + name + "\"," + '\n');
        str.append("\"character\" : \""+ character + "\"," + '\n');
        str.append("\"color\": \""+color+"\"," + '\n');
        str.append("\"description\" : \""+description+"\"," + '\n');
        str.append("\"rarity\" : \""+rarity+"\"," + '\n');
        str.append(("\"points\" : " + points + ",\n"));
        str.append("\"groups\" : [");
        for(String g:groups){
            str.append("\n\""+g+"\"");
        }
        if(groups.length > 0) str.append("\n");
        str.append("]," + '\n');
        str.append("\"attributes\" : [");
        for(String a:attributes){
            str.append("\""+ a +"\",\n");
        }
        if(attributes.length>0) str.deleteCharAt(str.lastIndexOf(","));
        str.append("],\n");
        str.append("\"effects\" : [");
	    for(Object e[]:effects){
	        str.append("\n[");
	        for(Object ee:e){
	            try {
	                if(ee instanceof Double) str.append(((Double) ee).intValue() + ",");
	                if(ee instanceof String) str.append(("\"" + ee + "\","));
	                if(ee instanceof Boolean) str.append(ee + ",");
                }
	            catch (Exception err){
                    System.out.println(err);
                }
            }
	        str.deleteCharAt(str.lastIndexOf(","));
            str.append("],");
        }
        if(effects.length>0)str.deleteCharAt(str.lastIndexOf(","));
        str.append("]" );

        return str.toString();
    }

}
