package com.example.gridgame;

public class Zombie extends Piece{
    // have its own individual tile value of 6
    private int player;
    private int turnsAlive = 0;
    public Zombie(int r, int c, int player){
        super(r,c);
        this.player = player;
    }
    public void infect(){
        //has a chance to spawn in another zombie or spread the color
    }
    public void spawn(){

    }

}
