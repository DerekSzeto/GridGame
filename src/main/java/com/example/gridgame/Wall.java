package com.example.gridgame;

import java.util.ArrayList;

public class Wall {
    //adjust Wall dynamics and interactions with different pieces
    private ArrayList<Tile> barrierTiles;
    private String orientation;
    private int wallLength;
    private int startingRow;
    private int startingCol;
    public Wall(String o,int numTiles, int r, int c){
        orientation = o;
        wallLength = numTiles;
    }
    public void constructWall(Tile[][] tiles){
        if(orientation.equals("h")) {
            for (int i = 0; i < wallLength; i++) {
                if(tiles[i][startingCol+i].getValue()==4) {
                    break;
                }
                tiles[startingRow][startingCol + i].setValue(9);
            }
        }else{
            for (int i = 0; i < wallLength; i++) {
                if(tiles[startingRow+i][startingCol].getValue()==4) {
                    break;
                }
                tiles[startingRow + i][startingCol].setValue(9);
            }
        }
    }

}
