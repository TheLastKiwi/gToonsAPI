package com.gToons.api.services.game;

import com.gToons.api.domain.Card;

public class Board {
    Card board[][] = new Card[4][4];

    public void playCards(Card cards[]){

        int startRow = 0;
        if(board[0][0]== null){
            startRow = 0;
        }
        else {
            startRow = 1;
        }
        for(int i = 0; i < cards.length; i++){
            board[startRow][i] = cards[i];
        }
    }
    public void playLastCard(Card c){
        board[1][2] = c;
    }
}
