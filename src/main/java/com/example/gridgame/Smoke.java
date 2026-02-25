package com.example.gridgame;

import java.util.ArrayList;

public class Smoke extends Station{
    private ArrayList<Tile> areaSmoked = new ArrayList<>();
    public Smoke(int r, int c, int v){
        super(r,c,v);
    }
    //refactor this to a smoke that lasts 4 turns
    public void dropSmokes(Tile[][] tiles){
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((row + i) > 0 && (row + i) < tiles.length-1 && (col + j) > 0 && (col + j) < tiles[row + i].length-1) {
                    if(tiles[row+i][col+j].getValue()!=value&tiles[row+i][col+j].getValue()!=4&tiles[row+i][col+j].getValue()!=value&tiles[row+i][col+j].getValue()!=0) {
                        areaSmoked.add(tiles[row+i][col+j]);
                        tiles[row+i][col+j].setValue(11);
                    }
                }
            }
        }
    }
    public void smokesFade(Tile[][] tiles){
        int smokeCount = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if(tiles[i][j].getValue()==11){
                    smokeCount++;
                }
            }
        }
        for (int i = 0; i < smokeCount; i++) {

        }
    }
}
