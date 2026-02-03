package com.example.gridgame;

public class Tile {
    //4 is a border, 0 is free,  1 is p1 and 2 is p2
    private int tileValue;
    public Tile(int value) {
        tileValue = value;

    }
    public int getValue() {
        return tileValue;
    }
    public void setValue(int value) {
        tileValue = value;
    }
}
