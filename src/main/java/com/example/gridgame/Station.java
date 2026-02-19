package com.example.gridgame;

import java.util.ArrayList;

public class Station {
    protected int row;
    protected int col;
    protected int value;;
    public Station(int row, int col,int value){
        this.row = row;
        this.col = col;
        this.value = value;
    }
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }
    public int getValue(){
        return value;
    }


}
