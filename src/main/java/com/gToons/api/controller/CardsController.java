package com.gToons.api.controller;

import com.gToons.api.game.Game;
import com.gToons.api.model.Card;
import com.gToons.api.model.UserDeckCard;
import com.gToons.api.payload.ApiResponse;
import com.gToons.api.repository.UserDeckCardRepository;
import com.gToons.api.security.UserPrincipal;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class CardsController {
    @Autowired
    UserDeckCardRepository userDeckCardRepository;

    @GetMapping("/getDeck")
    public ResponseEntity<?> getDeck(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
//
//        Optional<List<UserDeckCard>> oudc = userDeckCardRepository.findByUserId(user.getId());
//        if(!oudc.isPresent()){
//            return ResponseEntity.ok(new ApiResponse(false, "{[]}"));
//        }
//        List<UserDeckCard> udc = oudc.get();
//
//        ArrayList<Card> cards = new ArrayList<>();
//        for(int i = 0; i < 12; i++){
//            cards.add(Game.allCards[udc.get(i).getCardId()].copy());
//        }
        ArrayList<Card> cards = new ArrayList<>();
        Game g = new Game();
        for(int i = 1; i < 13; i++)cards.add(Game.allCards[i].copy());
        Gson gson = new Gson();

        return ResponseEntity.ok(new ApiResponse(true,gson.toJson(cards)));
    }

    @GetMapping("/getCollection")
    public ResponseEntity<?> getCollection(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
//
//        List<UserDeckCard> udc = userDeckCardRepository.findByUserId(user.getId()).get();
//
//        ArrayList<Card> cards = new ArrayList<>();
//        for(int i = 0; i < udc.size(); i++){
//            cards.add(Game.allCards[udc.get(i).getCardId()].copy());
//        }
        ArrayList<Card> cards = new ArrayList<>();
        Game g = new Game();
        for(int i = 1; i < 15; i++)cards.add(Game.allCards[i].copy());
        Gson gson = new Gson();

        return ResponseEntity.ok(new ApiResponse(true,gson.toJson(cards)));
    }

}
