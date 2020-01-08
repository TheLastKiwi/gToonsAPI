package com.gToons.api.game;

import com.gToons.api.CardLoaderTemplate;
import com.gToons.api.CardService;
import com.gToons.api.game.effects.Effect;
import com.gToons.api.model.Card;
import com.gToons.api.model.UserDeckCard;
import com.gToons.api.repository.UserDeckCardRepository;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.gToons.api.game.Player.HAND_SIZE;

@Getter
@NoArgsConstructor
public class Game {
    //TODO REWORK EFFECTS TO BE AGNOSTIC OF CARD

    //TODO add functionality to send back game state at each card reveal

    Player p1, p2;
    int phase = 0;
    Gson gson = new Gson();
    UserDeckCardRepository userDeckCardRepository;
    public Game(Player p1, Player p2, @Autowired UserDeckCardRepository  udcr){
        userDeckCardRepository = udcr;
        List<Card> d = getDeckFromDB(p1.getId());
        p1.setDeck(new Deck(d));
        d = getDeckFromDB(p2.getId());
        p2.setDeck(new Deck(d));
        //Todo pick a random card from both decks, return both to users, record the color
        //If possible also send jwt or user ID though here encapsulated from controller headers
    }
    private List<Card> getDeckFromDB(int pid){
        List<Integer> intCards = new ArrayList<>();
        ArrayList<Card> cards = new ArrayList<>();
        Optional<List<UserDeckCard>> opCards = userDeckCardRepository.findByUserId(pid);
        if(opCards.isPresent()) {
            List<UserDeckCard> pCards = opCards.get();
            for (UserDeckCard c : pCards) {
                intCards.add(c.getCardId());
            }

//        if(pid == 100) {
//            //DB Call return card from deck as a List<Integer>
//            intCards = Arrays.asList(1,2,3,4,5,6);
//        }
//        else {
//            intCards = Arrays.asList(21,22,23,24,25,26);
//        }


            for (int i = 0; i < intCards.size(); i++) {
                cards.add(CardService.getAllCards().get(intCards.get(i)).copy());
            }
        }

        return cards;
    }
    public void action(Action action, WebSocketSession session){
        Player player = getPlayer(session);

        //***Returned items are only returned after both players submit their action, either through a timer or through ready button

        //Todo verify that the player can perform said actions, for example they can only play cards they have in hand.
        //Todo verify that user is not ready before executing anything in case of a double send

            switch (phase){
                case 0: //Start - Receive nothing | ***Return 6 cards
                    player.draw();
                    player.setReady(true);

                    ifBothReadySendHands();
                    ifBothReadyAdvancePhase();
                    break;
                case 1: //Played cards round 1 - Receive 4 cards in playedCards | ***Return opponents cards
                    List<Card> r1PlayedCards = getCardsFromInts(action.getPlayedCards());
                    player.playCards(r1PlayedCards);
                    playCards(r1PlayedCards,player.getBoard());

                    player.setReady(true);
                    ifBothReadySendR1Cards(player);
                    ifBothReadyAdvancePhase();
                    break;
                case 2: //Discard option - Receive 0-2 cards in discardedCards | ***Return a hand of 6 cards with discarded cards removed
                    List<Card>  discardedCards = getCardsFromInts(action.getDiscardedCards());
                    player.discardCards(discardedCards);
                    player.draw();

                    player.setReady(true);
                    ifBothReadySendHands();
                    ifBothReadyAdvancePhase();
                    break;
                case 3: //Played cards round 2 - receive 2 cards in playedCards | ***Return opponents cards
                    List<Card>  r2PlayedCards = getCardsFromInts(action.getPlayedCards());
                    player.playCards(r2PlayedCards);
                    playCards(r2PlayedCards,player.getBoard());

                    player.setReady(true);
                    ifBothReadySendR2Cards(player);
                    ifBothReadyAdvancePhase();
                    break;
                case 4: //Replace card option - receive 1 card in lastCard and replace(boolean) | Return winner/loser
                    List<Card>  lastCard =  getCardsFromInts(new ArrayList<>(action.lastCard));
                    if(action.isReplace()){
                        player.setPoints(-10);
                    }
                    playLastCard(lastCard.get(0),player);

                    player.setReady(true);
                    ifBothReadySendLastCard(player);
                    ifBothReadySendResults();
                    ifBothReadyAdvancePhase();
                    break;
                    //todo Player selects which color they want silver to be when both selected send both back to both users
            }

    }
    private void ifBothReadySendR1Cards(Player p){
        try {
            if(p1.isReady() && p2.isReady()) {
                p1.getSocket().sendMessage(new TextMessage(gson.toJson(p2.getBoard().board[0])));
                p2.getSocket().sendMessage(new TextMessage(gson.toJson(p1.getBoard().board[0])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void ifBothReadySendR2Cards(Player p){
        try {
            if(p1.isReady() && p2.isReady()) {
                p1.getSocket().sendMessage(new TextMessage(gson.toJson(p2.getBoard().board[1])));
                p2.getSocket().sendMessage(new TextMessage(gson.toJson(p1.getBoard().board[1])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void ifBothReadySendLastCard(Player p){
        try {
            if(p1.isReady() && p2.isReady()) {
                p1.getSocket().sendMessage(new TextMessage(gson.toJson(p2.getBoard().board[1][2])));
                p2.getSocket().sendMessage(new TextMessage(gson.toJson(p1.getBoard().board[1][2])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void ifBothReadyAdvancePhase(){
        if(p1.isReady() && p2.isReady()) {
            phase++;
            p1.setReady(false);
            p2.setReady(false);
        }
    }
    private void ifBothReadySendResults(){

        if(p1.isReady() && p2.isReady()) {
            Player winner = calculateWinner();
            Player loser;
            if(winner.equals(p1)) {
                loser = p2;
            }
            else {
                loser = p1;
            }

            try {
                winner.getSocket().sendMessage(new TextMessage("Winner" + winner.getPoints()));
                loser.getSocket().sendMessage(new TextMessage("Loser" + loser.getPoints()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    Card allCardsInPlay[] = new Card[14];
    private Player calculateWinner(){
        //If dual-color game check color count of both
        int position = 0;
        for(int i = 0; i < 4; i++){
            allCardsInPlay[position++] = p1.getBoard().board[0][i];
            allCardsInPlay[position++] = p2.getBoard().board[0][i];
        }
        for(int i = 0; i < 3; i++){
            allCardsInPlay[position++] = p1.getBoard().board[1][i];
            allCardsInPlay[position++] = p2.getBoard().board[1][i];
        }

        ArrayList<Effect> cardEffects;
        for(Card c : allCardsInPlay){
            if(c.isNullified()){
                continue;
            }
            cardEffects = c.getEffects();
            ArrayList<Location> location;
            for(Effect e : cardEffects){
                location = e.getImpactedLocations();
                for(Location l : location){
                    Card effectedCard = getCardFromLocation(l);
                    if(e.appliesTo(effectedCard,allCardsInPlay)) {
                        effectedCard.addUnappliedEffect(e);
                    }
                }
            }
        }
        for(Card c: allCardsInPlay){
            for(Effect e : c.getUnappliedEffects()){
                e.applyTo(c);
            }
        }

        /*
        for every card in play
            Effect[] = card.getEffects()
            for every effect
                Location[] = effect.getImpactedLocations()
                    for every location
                        effectedCard = board.getcard(xy)
                        effectedCard.addUnappliedEffect(effect)


         for every card in play
            card.applyEffects -> if effect.appliesTo(card)
                                        then effect.applyTo(card)


         */

        return p1;
    }

    private Card getCardFromLocation(Location location){
        if(location.owner.equals(p1)){
            return p1.getBoard().board[location.getY()][location.getX()];
        }
        return p2.getBoard().board[location.getY()][location.getX()];
    }
    private void ifBothReadySendHands(){

        if(p1.isReady() && p2.isReady()){
            try {
                p1.getSocket().sendMessage(new TextMessage(gson.toJson(p1.getHand(), Card[].class)));
                p2.getSocket().sendMessage(new TextMessage(gson.toJson(p2.getHand(), Card[].class)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void playLastCard(Card c, Player player){
        player.board.playLastCard(c);
    }
    private void playCards(List<Card>  cards, Board board){
        board.playCards(cards);
    }
    private List<Card>  getCardsFromInts(List<Integer> intCards){
        List<Card> cards= new ArrayList<>();
        for(int i = 0; i < intCards.size(); i++){
            cards.add(CardService.getAllCards().get(intCards.get(i)));
        }
        return cards;
    }
    private Player getPlayer(WebSocketSession session){
        if(p1.getSocket().getId().equals(session.getId())){
            return p1;
        }
        return p2;
    }
}
