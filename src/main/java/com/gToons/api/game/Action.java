package com.gToons.api.game;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Action {
    public enum UserAction{
        MATCHMAKE,GAME
    }

    UserAction action;
    String token;
    int playedCards[];
    int discardedCards[];
    boolean replace;
    int lastCard;
}
