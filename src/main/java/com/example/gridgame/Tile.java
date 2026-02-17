package com.example.gridgame;

public class Tile {
    //4 is a border, 0 is free,  1 is p1 and 2 is p2
    private int tileValue;
    private int row;
    private int col;
    public Tile(int r, int c,int value) {
        this.row = r;
        this.col = c;
        tileValue = value;

    }
    public int getRow(){
        return row;
    }
    public int getCol(){
        return col;
    }
    public int getValue() {
        return tileValue;
    }
    public void setValue(int value) {
        tileValue = value;
    }
}
