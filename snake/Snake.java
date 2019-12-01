package com.javarush.games.snake;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private List<GameObject> snakeParts = new ArrayList<>();
    private static final String HEAD_SIGN = "\u2689";
    private static final String BODY_SIGN = "\u26AB";

    public Snake(int x, int y) {
        for (int i =0; i < 3; i++)
            snakeParts.add(new GameObject(x + i, y));

    }

    public void draw(Game game){
        game.setCellValue(snakeParts.get(0).x, snakeParts.get(0).y, HEAD_SIGN);
        for (int i = 1; i < snakeParts.size(); i++){
            game.setCellValue(snakeParts.get(i).x, snakeParts.get(i).y, BODY_SIGN);
        }

    }

}
