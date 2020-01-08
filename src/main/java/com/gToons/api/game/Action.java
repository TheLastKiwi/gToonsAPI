package com.gToons.api.game;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Action {
    public enum UserAction{
        MATCHMAKE,GAME
    }

    UserAction action;
    String token;
    List<Integer> playedCards;
    List<Integer> discardedCards;
    boolean replace;
    int lastCard;
}
