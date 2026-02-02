package com.example.gridgame;

public class Tile {
    private int tileValue;
    private boolean taken;
    public Tile(int value) {
        tileValue = value;
        if(this.tileValue==4){
            taken = true;
        }else{
            taken = false;
        }
    }
    public boolean isTaken(){
        return taken;
    }
    public void setTileTaken(boolean b){
        taken = b;
    }
    public int getValue() {
        return tileValue;
    }
    public void setValue(int value) {
        tileValue = value;
    }
}
