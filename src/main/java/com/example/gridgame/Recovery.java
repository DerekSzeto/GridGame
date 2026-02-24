package com.example.gridgame;

public class Recovery extends Station {
    //destroys all stations
    //recovers all values to previous values
    private int turnsTilActivation = 3;
    private Tile[][] savedGrid;
    public Recovery(int r, int c, int v, Tile[][] grid) {
        super(r, c, v);
        savedGrid = grid;
    }
    public void recover(){
        //recovers all tiles in col and in row
    }
    public void decrementTurns(){
        turnsTilActivation --;
    }
}
