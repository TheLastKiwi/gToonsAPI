package com.gToons.api.services.game;

import com.google.gson.Gson;
import lombok.Getter;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import com.gToons.api.domain.Card;
import javax.websocket.Session;
import java.io.IOException;

@Getter
public class Game {
    static Card[] allCards = {new Card(1), new Card(2), new Card(3), new Card(4), new Card(5), new Card(6), new Card(7), new Card(8), new Card(9), new Card(10), new Card(11), new Card(12)};

    Player p1, p2;
    int phase = 0;
    Gson gson = new Gson();
    public Game(WebSocketSession wss1, int p1id, WebSocketSession wss2, int p2id){
        p1 = new Player(wss1, p1id);
        p2 = new Player(wss2, p2id);
        //Todo pick a random card from both decks, return both to users, record the color
        //If possible also send jwt or user ID though here encapsulated from controller headers
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
                    Card r1PlayedCards[] = getCardsFromInts(action.getPlayedCards());
                    player.playCards(r1PlayedCards);
                    playCards(r1PlayedCards,player.getBoard());

                    player.setReady(true);
                    ifBothReadySendR1Cards(player);
                    ifBothReadyAdvancePhase();
                    break;
                case 2: //Discard option - Receive 0-2 cards in discardedCards | ***Return a hand of 6 cards with discarded cards removed
                    Card discardedCards[] = getCardsFromInts(action.getDiscardedCards());
                    player.discardCards(discardedCards);
                    player.draw();

                    player.setReady(true);
                    ifBothReadySendHands();
                    ifBothReadyAdvancePhase();
                    break;
                case 3: //Played cards round 2 - receive 2 cards in playedCards | ***Return opponents cards
                    Card r2PlayedCards[] = getCardsFromInts(action.getPlayedCards());
                    player.playCards(r2PlayedCards);
                    playCards(r2PlayedCards,player.getBoard());

                    player.setReady(true);
                    ifBothReadySendR2Cards(player);
                    ifBothReadyAdvancePhase();
                    break;
                case 4: //Replace card option - receive 1 card in lastCard and replace(boolean) | Return winner/loser
                    Card lastCard[] =  getCardsFromInts(new int[]{action.lastCard});
                    if(action.isReplace()){
                        player.setPoints(-10);
                    }
                    playLastCard(lastCard[0],player);

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
            try {
                p1.getSocket().sendMessage(new TextMessage(gson.toJson(p1.getHand(), Card[].class)));
                p2.getSocket().sendMessage(new TextMessage(gson.toJson(p2.getHand(), Card[].class)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private Player calculateWinner(){
        //If dual-color game check color count of both

        return p1;
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
    private void playCards(Card cards[], Board board){
        board.playCards(cards);
    }
    private Card[] getCardsFromInts(int intCards[]){
        Card cards[] = new Card[intCards.length];
        for(int i = 0; i < intCards.length; i++){
            cards[i] = allCards[intCards[i]];
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
