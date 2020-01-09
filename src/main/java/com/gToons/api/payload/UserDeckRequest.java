package com.gToons.api.payload;


import lombok.Getter;

import java.util.ArrayList;

@Getter
public class UserDeckRequest {

    private ArrayList<Integer> cardIds;
}
