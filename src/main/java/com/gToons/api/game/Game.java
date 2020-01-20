package com.gToons.api.game;

import com.gToons.api.CardLoaderTemplate;
import com.gToons.api.CardService;
import com.gToons.api.game.effects.Effect;
import com.gToons.api.model.Card;
import com.gToons.api.model.UserDeckCard;
import com.gToons.api.payload.SocketResponse;
import com.gToons.api.repository.UserDeckCardRepository;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.lang.reflect.Array;
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
    Board p1Board, p2Board;

    int phase = 0;
    Gson gson = new Gson();
    UserDeckCardRepository userDeckCardRepository;
    Card noCardTemplate = Card.builder().id(-1).name("Default").build();
    public Game(Player player1, Player player2, @Autowired UserDeckCardRepository  udcr){
        userDeckCardRepository = udcr;
        p1=player1;
        p2=player2;
        List<Card> d = getDeckFromDB(p1.getId());
        p1.setDeck(new Deck(d));
        d = getDeckFromDB(p2.getId());
        p2.setDeck(new Deck(d));
        //Todo pick a random card from both decks, return both to users, record the color
        action(null,p1.getSocket());
        action(null,p2.getSocket());
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
                    Card lastCard = CardService.getAllCards().get(action.lastCard);
                    if(action.isReplace()){
                        player.setPoints(-10);
                    }
                    playLastCard(lastCard,player);

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

                //calculate buffs and return
                ArrayList<Card> buffs  = calculateBuffsFirstFour();
                p1.getSocket().sendMessage(new TextMessage(gson.toJson(new SocketResponse(buffs,phase+1))));
                p2.getSocket().sendMessage(new TextMessage(gson.toJson(new SocketResponse(buffs,phase+1))));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private ArrayList<Card> calculateBuffsFirstFour(){
        ArrayList<Card> buffs = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            Card c = p1.board.getCard(i,0);
            buffs.add(c.copy());

            //get buffs that are applied to this card, and buffs that this card applies to others
            ArrayList<Location> locations = new ArrayList<>();
            for(Effect e : c.getEffects()){
                applyBuffs(p1,e.getImpactedLocations(),e);
            }


            buffs.add(p2.board.getCard(i,0).copy());
            //get buffs that are applied to this card, and buffs that this card applies to others
            buffs.addAll(applyBuffs(i,0));
        }
        return buffs;
    }
    private ArrayList<Card> applyBuffs(Player sourcePlayer, ArrayList<Location> impactedLocations, Effect e){
        //These are just cards being sent to the front end, they don't need to be fully complete cards.

    }
    private void ifBothReadySendR2Cards(Player p){
        try {
            if(p1.isReady() && p2.isReady()) {
                p1.getSocket().sendMessage(new TextMessage(gson.toJson(new SocketResponse(p2.getBoard().board[1],phase+1))));
                p2.getSocket().sendMessage(new TextMessage(gson.toJson(new SocketResponse(p1.getBoard().board[1],phase+1))));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void ifBothReadySendLastCard(Player p){
        try {
            if(p1.isReady() && p2.isReady()) {
                p1.getSocket().sendMessage(new TextMessage(gson.toJson(new SocketResponse(p2.getBoard().board[1][2],phase+1))));
                p2.getSocket().sendMessage(new TextMessage(gson.toJson(new SocketResponse(p1.getBoard().board[1][2],phase+1))));
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

        List<Effect> cardEffects;
        int index =-1;
        for(Card c : allCardsInPlay){
            index++;
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
                p1.getSocket().sendMessage(new TextMessage(gson.toJson(new SocketResponse(cardListToArray(p1.getHand()),phase+1))));
                p2.getSocket().sendMessage(new TextMessage(gson.toJson(new SocketResponse(cardListToArray(p2.getHand()),phase+1))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private Card[] cardListToArray(List<Card> cards){
        Card[] retCards = new Card[cards.size()];
        for(int i =0;i < cards.size(); i++){
            retCards[i] = cards.get(i);
        }
        return  retCards;
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
            if(intCards.get(i) == -1){
                cards.add(noCardTemplate);
                continue;
            }
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
