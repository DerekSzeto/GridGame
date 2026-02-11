package com.example.gridgame;

public class Zombie extends Piece{
    // have its own individual tile value,
    int player;
    public Zombie(int r, int c, int player){
        super(r,c);
        this.player = player;
    }
    public void infect(){
        //has a chance to spawn in another zombie or spread the color
    }
}
