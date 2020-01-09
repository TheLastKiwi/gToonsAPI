package com.gToons.api.controller;

import com.gToons.api.CardService;
import com.gToons.api.game.Game;
import com.gToons.api.model.Card;
import com.gToons.api.model.UserCollection;
import com.gToons.api.model.UserDeckCard;
import com.gToons.api.payload.ApiResponse;
import com.gToons.api.payload.UserDeckRequest;
import com.gToons.api.repository.UserCollectionRepository;
import com.gToons.api.repository.UserDeckCardRepository;
import com.gToons.api.repository.UserRepository;
import com.gToons.api.security.UserPrincipal;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class CardsController {
    @Autowired
    private UserDeckCardRepository userDeckCardRepository;

    @Autowired
    private UserCollectionRepository userCollectionRepository;

    @Autowired
    UserRepository userRepository;

    final Gson gson = new Gson();

    @GetMapping("/getDeck")
    public ResponseEntity<?> getDeck(){
        UserPrincipal user = getUser();

        Optional<List<UserDeckCard>> oudc = userDeckCardRepository.findByUserId(user.getId());
        if(!oudc.isPresent()){
            return ResponseEntity.ok(new ApiResponse(false, "{[]}"));
        }
        List<UserDeckCard> udc = oudc.get();

        ArrayList<Card> cards = new ArrayList<>();
        for(int i = 0; i < 12; i++){
            cards.add(CardService.getAllCards().get(udc.get(i).getCardId()).copy());
        }

        return ResponseEntity.ok(new ApiResponse(true,gson.toJson(cards)));
    }

    @GetMapping("/getCollection")
    public ResponseEntity<?> getCollection(){
        UserPrincipal user = getUser();

        ArrayList<Card> cards = new ArrayList<>();
        Optional<List<UserCollection>> ouc = userCollectionRepository.findByUserId(user.getId());
        if(ouc.isPresent()) {
            List<UserCollection> userCollection = ouc.get();
            userCollection.sort((Comparator.comparingInt(UserCollection::getCardId)));
            for (UserCollection uc : userCollection) {
                if(cards.size()==0 || cards.get(cards.size()-1).getId() != uc.getCardId()){
                    cards.add(CardService.getAllCards().get(uc.getCardId())
                            .copy());
                }
            }
        }
        return ResponseEntity.ok(new ApiResponse(true,gson.toJson(cards)));
    }

    @Transactional
    @PostMapping("/buyPack")
    public ResponseEntity<?> buyPack() {
        UserPrincipal user = getUser();
        if(user.getPoints() < 200){
            return ResponseEntity.badRequest().body(new ApiResponse(false,"Insufficient Points"));
        }
        userRepository.setPoints(user.getId(),user.getPoints()-200);

        List<Card> pack = CardService.generatePack();
        List<UserCollection> saveCards= new ArrayList<>();
        for(Card c : pack){
            saveCards.add(new UserCollection(user.getId(), c.getId()));
        }
        userCollectionRepository.saveAll(saveCards);
        return ResponseEntity.ok(new ApiResponse(true, gson.toJson(pack)));
    }

    private UserPrincipal getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
        return user;
    }

    @PostMapping("/saveDeck")
    @Transactional
    public ResponseEntity<?> saveDeck(@RequestBody UserDeckRequest userDeckRequest) {
        //TODO Validate that the user has the cards in his collection that they are claiming to put in their deck
        UserPrincipal user = getUser();
        int userId = user.getId();
        //Delete deck
        userDeckCardRepository.deleteByUserId(userId);
        List<UserDeckCard>  newDeck = new ArrayList<>();
        for(Integer c : userDeckRequest.getCardIds()){
            newDeck.add(new UserDeckCard(userId, c));
        }
        //save new deck
        userDeckCardRepository.saveAll(newDeck);

        return ResponseEntity.ok(new ApiResponse(true,"Deck Updated"));
    }

    @GetMapping("/getPoints")
    public ResponseEntity<?> getPoints() {
        //TODO Validate that the user has the cards in his collection that they are claiming to put in their deck
        UserPrincipal user = getUser();

        return ResponseEntity.ok(new ApiResponse(true,Integer.toString(user.getPoints())));
    }

}
