package com.gToons.api.game;

import lombok.Getter;

@Getter
public class Location {
    int x, y;
    Player owner;
    public Location(int x,int y, Player owner){
        this.x = x;
        this.y = y;
        this.owner = owner;
    }
}
