package com.example.gridgame;

import java.util.ArrayList;

public class Smoke extends Station{
    private ArrayList<Tile> areaSmoked = new ArrayList<>();
    private ArrayList<Integer> tileValues = new ArrayList<>();
    private boolean smokesActive = true;
    private int turnsLeft = 4;
    public Smoke(int r, int c, int v){
        super(r,c,v);
    }
    //refactor this to a smoke that lasts 4 turns
    public void dropSmokes(Tile[][] tiles){
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((row + i) > 0 && (row + i) < tiles.length-1 && (col + j) > 0 && (col + j) < tiles[row + i].length-1) {
                    if(tiles[row+i][col+j].getValue()==1||tiles[row+i][col+j].getValue()==1) {
                        tileValues.add(tiles[row + i][col + j].getValue());
                    }
                    areaSmoked.add(tiles[row + i][col + j]);
                    tiles[row+i][col+j].setValue(11);
                }
            }
        }
    }
    public void getTilesNearby(Tile[][] tiles){
        for (int i = 0; i < areaSmoked.size(); i++) {
            for (int j = -1; j < 2; j++) {
                for (int k = -1; k < 2; k++) {
                    if ((areaSmoked.get(i).getRow() + j) > 0 && (areaSmoked.get(i).getRow() + j) < tiles.length-1 && (areaSmoked.get(i).getCol() + k) > 0 && (areaSmoked.get(i).getCol() + k) < tiles[areaSmoked.get(i).getRow() + j].length-1) {
                        if (tiles[areaSmoked.get(i).getRow() + j][areaSmoked.get(i).getCol() + k].getValue() == 1 || tiles[areaSmoked.get(i).getRow() + j][areaSmoked.get(i).getCol() + k].getValue() == 2) {
                            tileValues.add(tiles[areaSmoked.get(i).getRow() + j][col + k].getValue());
                            tiles[areaSmoked.get(i).getRow() + j][areaSmoked.get(i).getCol() + k].setValue(0);
                        }
                    }
                }
            }
        }
    }
    public void smokesFade(Tile[][] tiles){
        for (int i = 0; i < areaSmoked.size(); i++) {
            tiles[areaSmoked.get(i).getRow()][areaSmoked.get(i).getCol()].setValue(getRandomStoredValue());
        }

    }
    public int getRandomStoredValue(){
        int tileIndex = (int)(Math.random()*tileValues.size());
        int returningValue = tileValues.get(tileIndex);
        tileValues.remove(tileIndex);
        return returningValue;
    }
    public void nextTurn(Tile[][] tiles) {
        if(smokesActive) {
            turnsLeft--;

            if (turnsLeft <= 0) {
                smokesActive = false;
                smokesFade(tiles);
            }
        }
    }
    public boolean isSmokesActive(){
        return smokesActive;
    }
}
