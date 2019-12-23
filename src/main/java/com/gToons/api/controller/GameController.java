package com.gToons.api.controller;

import com.gToons.api.domain.Card;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    @CrossOrigin()
    @GetMapping("/game")
    public Card gameAction(){
        Card c = new Card();
        c.setName("Clyde");
        c.setRarity("Rare");
        c.setColor("Yellow");
        c.setDescription("Gives +5 to all green cards");

        return c;


    }
}
