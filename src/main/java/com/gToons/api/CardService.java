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
import java.util.Random;

@Component
public class CardService {

    private static final List<Card> allCards = loadCards();
    private static List<Card> commons;
    private static List<Card> uncommons;
    private static List<Card> rares;
    private static List<Card> slams;



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

        //Initialization
        commons = new ArrayList<>();
        uncommons = new ArrayList<>();
        rares = new ArrayList<>();
        slams = new ArrayList<>();
        for(CardLoaderTemplate clt : cardTemplates){
//            str.append("{\n");
//            str.append(cardTemplates[i]);
//            str.append("},\n");
            Card c = clt.toCard();
            //TODO change rarity to enum
            switch (c.getRarity()){
                case "COMMON":
                    commons.add(c);
                    break;
                case "UNCOMMON":
                    uncommons.add(c);
                    break;
                case "RARE":
                    rares.add(c);
                    break;
                case "SLAM":
                    slams.add(c);
                    break;
            }
            cards.add(c);
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

    public static List<Card> generatePack(){
        Random rnd = new Random(System.currentTimeMillis());
        List<Card> pack = new ArrayList<>();
        int rnum;
        int rplus = 0;
        for(int i = 0; i < 5; i++){
            rnum = rnd.nextInt(allCards.size());
            if(rnum>allCards.size()*.95){
                rplus++;
                pack.add(slams.get(rnd.nextInt(slams.size())).copy());
            } else if(rnum > allCards.size()*.88){
                rplus++;
                pack.add(rares.get(rnd.nextInt(rares.size())).copy());
            } else  if(rnum > allCards.size()*.79){
                pack.add(uncommons.get(rnd.nextInt(uncommons.size())).copy());
            } else{
                pack.add(commons.get(rnd.nextInt(commons.size())).copy());
            }
        }
        //no rares or slams
        if(rplus == 0){
            rnum = rnd.nextInt(allCards.size());
            if(rnum > allCards.size()*.97){
                pack.set(rnd.nextInt(5), slams.get(rnd.nextInt(slams.size())).copy());
            } else{
                pack.set(rnd.nextInt(5), rares.get(rnd.nextInt(rares.size())).copy());
            }
        }
        return pack;
    }
}
