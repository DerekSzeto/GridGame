package com.example.gridgame;

public class Refract extends Piece{
    private int value;
    // The piece must collide with a piece of the same color, if the tile the piece collides with has an adjacent of the same color, the piece will make the adjacent piece move towards other pieces of the same color
    public Refract(int r, int c ,int v) {
        super(r,c);
        this.value = v;
    }
    public int getCheckingValue(){
        if(value == 1){
            return 2;
        }else{
            return 1;
        }
    }
    public void reflect(){
        //redirects to the opposite direction if the tile has the value of the opposite player
    }
}
