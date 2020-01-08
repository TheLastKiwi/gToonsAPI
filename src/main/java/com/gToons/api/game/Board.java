package com.gToons.api.game;

import com.gToons.api.model.Card;

import java.util.List;

public class Board {
    Card board[][] = new Card[4][4];
    Card nullCard = new Card();

    public void playCards(List<Card> cards){

        int startRow = 0;
        if(board[0][0]== null){
            startRow = 0;
        }
        else {
            startRow = 1;
        }
        for(int i = 0; i < cards.size(); i++){
            board[startRow][i] = cards.get(i);
            board[startRow][i].finalizeLocation(new Location(startRow,i,cards.get(i).getOwner()));
        }

    }
    public void playLastCard(Card c){
        board[1][2] = c;
        board[1][2].finalizeLocation(new Location(1,2,c.getOwner()));

    }
    public Card getCard(int x, int y){
        //valid position
        if(isValidPosition(x,y)) {
            return board[y][x];
        }
        return nullCard;
    }
    private boolean isValidPosition(int x, int y){
        return !(x < 0 || y < 0 || x > 3 || y > 1 || (y == 1 && x == 3));
    }

}
