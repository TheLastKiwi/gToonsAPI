package com.gToons.api;

import com.gToons.api.model.Card;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CardService {

    private static final List<Card> allCards = loadCards();

    public static List<Card> getAllCards(){
        //if(allCards == null)allCards = loadCards();
        return allCards;
    }
    private static List<Card>  loadCards(){
//        long now = System.currentTimeMillis();
        String path = System.getProperty("user.dir") + "/src/main/resources/Cards100.cfg";
        String data;
        List<CardLoaderTemplate> cardTemplates = new ArrayList<>();
        Gson gson = new Gson();
        String pts[] = new String[0];
        try {
            data = new String(Files.readAllBytes(Paths.get(path)));
            CardLoaderTemplate cardTemplatesArr[] = gson.fromJson(data,CardLoaderTemplate[].class);
            cardTemplates = Arrays.asList(cardTemplatesArr);
//            String points = new String(Files.readAllBytes(Paths.get("pointsForCards.txt")));
//            pts = points.split("\n");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File read error");
        }
//        for(int i = 0; i < cardTemplates.length; i++){
//            cardTemplates[i].points = Integer.parseInt(pts[i]);
//        }
        List<Card> cards = new ArrayList<>();
        cards.add(null);
//        StringBuilder str = new StringBuilder();
//        str.append("[\n");
        for(CardLoaderTemplate c : cardTemplates){
//            str.append("{\n");
//            str.append(cardTemplates[i]);
//            str.append("},\n");
            cards.add(c.toCard());
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
