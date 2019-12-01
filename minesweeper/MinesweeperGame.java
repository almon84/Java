package com.javarush.games.minesweeper;


import com.javarush.engine.cell.*;

import java.util.ArrayList;

public class MinesweeperGame extends Game {
    private static final int SIDE =15;
    private GameObject[][] gameField = new GameObject[SIDE][SIDE];
    private int countMinesOnField = 0;
    private static final String MINE = "\uD83D\uDCA3";
    private static final String FLAG = "\u26F3";
    private int countFlags;
    private boolean isGameStopped;
    private int countClosedTiles = SIDE * SIDE;
    private int score = 0;
    public void initialize(){
        setScreenSize(SIDE, SIDE);
        createGame();

    }

    private void createGame(){
        for(int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                setCellValue(j, i, "");
                boolean flag = getRandomNumber(10) < 2;
                if (flag)  countMinesOnField++;
                gameField[j][i]= new GameObject(i, j, flag);
                setCellColor(j, i, Color.GREEN);
            }
        }
        countMineNeighbors();
        countFlags = countMinesOnField;



    }

    private void countMineNeighbors() {
        int mine;
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                mine = 0;
                if (!gameField[j][i].isMine) {
                    for (int k = 0; k < getNeighbors(gameField[j][i]).size(); k++) {
                        if (getNeighbors(gameField[j][i]).get(k).isMine) mine++;
                    }
                    gameField[j][i].countMineNeighbors = mine;
                }
            }
        }
    }

    private ArrayList<GameObject> getNeighbors(GameObject gameObject){
        ArrayList<GameObject> list = new ArrayList<GameObject>();
        for (int i = gameObject.x - 1; i < gameObject.x + 2; i++)
            for (int j = gameObject.y - 1; j < gameObject.y + 2; j++) {
                if (((i >= 0) && (i < SIDE) && (j >= 0) && (j < SIDE)) && ((i != gameObject.x) || (j != gameObject.y))) {
                    list.add(gameField[j][i]);
                }
            }
        return list;
    }

    private void openTile(int x, int y) {
        if ((!gameField[y][x].isOpen) && (!isGameStopped) && (!gameField[y][x].isFlag)) {
            if (gameField[y][x].isMine) {setCellValueEx(x, y, Color.RED, MINE); gameOver();}
                else
                    if (gameField[y][x].countMineNeighbors == 0) {
                        gameField[y][x].isOpen = true;
                        setCellColor(x, y, Color.YELLOW);
                        countClosedTiles--;
                        setScore(score += 5);
                        //System.out.println(countClosedTiles + "      " + countMinesOnField + "  !");
                        if (countClosedTiles == countMinesOnField) win();
                        //setCellValue(x,y,"");
                        for (int i = 0; i < getNeighbors(gameField[y][x]).size(); i++)
                            openTile(getNeighbors(gameField[y][x]).get(i).x, getNeighbors(gameField[y][x]).get(i).y);
                    } else
                        if (!gameField[y][x].isOpen) {
                            gameField[y][x].isOpen = true;
                            setCellColor(x, y, Color.YELLOW);
                            setCellNumber(x, y, gameField[y][x].countMineNeighbors);
                            countClosedTiles--;
                            setScore(score += 5);
                            //System.out.println(countClosedTiles + "      " + countMinesOnField);
                            if (countClosedTiles == countMinesOnField) win();
            }
        }
    }

    private void markTile(int x, int y){
        if ((!gameField[y][x].isOpen) && (!isGameStopped)){
            if ((!gameField[y][x].isFlag) && (countFlags > 0)){
                gameField[y][x].isFlag = true;
                countFlags--;
                //countClosedTiles--;
                setCellValue(x,y,FLAG);
                setCellColor(x,y, Color.GREEN);
            }
            else{
                if (gameField[y][x].isFlag) { countFlags++; } //countClosedTiles++;}
                gameField[y][x].isFlag = false;
                setCellValue(x,y,"");
                setCellColor(x,y, Color.GREEN);
            }

        }
    }

    private void gameOver(){
        isGameStopped = true;
        showMessageDialog(Color.RED, "GAME OVER!", Color.WHITE, 80);
    }

    private void win(){
        isGameStopped = true;
        showMessageDialog(Color.BLUE, "YOUR WINNER!", Color.WHITE, 80);
    }

    private void restart(){
        isGameStopped = false;
        countClosedTiles = SIDE * SIDE;
        setScore(score = 0);
        countMinesOnField = 0;
        createGame();
    }

    @Override
    public void onMouseLeftClick(int x, int y) {

        if (isGameStopped) restart();
        else openTile(x,y);
    }

    @Override
    public void onMouseRightClick(int x, int y) { markTile(x,y); }

}

