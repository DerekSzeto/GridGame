package com.example.gridgame;

import java.util.ArrayList;

public class Wall {
    //adjust Wall dynamics and interactions with different pieces
    private ArrayList<Tile> barrierTiles;
    private String orientation;
    private int wallLength;
    private int startingRow;
    private int startingCol;
    public Wall(int l){
        wallLength = l;
    }
    public void setOrientation(String o){
        orientation = o;
    }
    public void setStartingRow(int r){
        startingRow = r;
    }
    public void setStartingCol(int c){
        startingCol = c;
    }
    public void constructWall(Tile[][] tiles){
        if(orientation.equals("H")) {
            for (int i = 0; i < wallLength; i++) {
                if(tiles[startingRow][startingCol+i].getValue()>0) {
                    break;
                }else {
                    tiles[startingRow][startingCol + i].setValue(9);
                }
            }
        }else if(orientation.equals("V")) {
            for (int i = 0; i < wallLength; i++) {
                if(tiles[startingRow+i][startingCol].getValue()==4) {
                    break;
                }
                tiles[startingRow + i][startingCol].setValue(9);
            }
        }
    }
    public String getOrientation(){
        return orientation;
    }
    public int getLength() {
        return wallLength;
    }
}
