package com.gToons.api;

import com.gToons.api.model.Card;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class CardService {

    private static final Card allCards[] = loadCards();

    public static Card[] getAllCards(){
        //if(allCards == null)allCards = loadCards();
        return allCards;
    }
    private static Card[] loadCards(){
//        long now = System.currentTimeMillis();
        String path = System.getProperty("user.dir") + "/src/main/resources/Cards100.cfg";
        String data;
        CardLoaderTemplate cardTemplates[] = new CardLoaderTemplate[0];
        Gson gson = new Gson();
        String pts[] = new String[0];
        try {
            data = new String(Files.readAllBytes(Paths.get(path)));
            cardTemplates = gson.fromJson(data,CardLoaderTemplate[].class);
//            String points = new String(Files.readAllBytes(Paths.get("pointsForCards.txt")));
//            pts = points.split("\n");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File read error");
        }
//        for(int i = 0; i < cardTemplates.length; i++){
//            cardTemplates[i].points = Integer.parseInt(pts[i]);
//        }
        Card cards[] = new Card[cardTemplates.length+1];
        cards[0] = null;
//        StringBuilder str = new StringBuilder();
//        str.append("[\n");
        for(int i = 0; i < cardTemplates.length; i++){
//            str.append("{\n");
//            str.append(cardTemplates[i]);
//            str.append("},\n");
            cards[i+1] = cardTemplates[i].toCard();
        }
//        str.append("]\n");
//        try {
//            Files.write(Paths.get("Cards100pts.cfg"),str.toString().getBytes());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println(System.currentTimeMillis()-now);
        return cards;
    }
}
