package com.gToons.api.services.game;

import lombok.Getter;

@Getter
public class Action {

    int action;
    int playedCards[];
    int discardedCards[];
    boolean replace;
    int lastCard;
}
