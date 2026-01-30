package com.example.gridgame;

public class Tile {
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
