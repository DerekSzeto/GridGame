package com.example.gridgame;

public class Charge {
    private int charge = 0;
    // charge of 1 2 3 will do diff things just think of what
    //charge and release button does not use listview only use listview to select
    public boolean chargePiece(){
        if(charge<=3) {
            charge++;
            return true;
        }
        return false;
    }
    public void releaseChargePiece(){

    }
    public void explosion(){

    }
    public void burstPieces(){

    }
    public void allSides(){

    }
    //figure out how to override
    public void move(){

    }
    public void spawnRandomPiece(){
        //paint shadow, rocket

    }
}
