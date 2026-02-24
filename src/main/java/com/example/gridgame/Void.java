package com.example.gridgame;

import java.util.ArrayList;

public class Void extends Station{
    private ArrayList<Tile> area = new ArrayList<>();
    private boolean expansion = false;
    private int radius = 1;
    public Void(int r, int c, int v){
        super(r,c,v);
    }
    public void stage(){
        if(expansion){
            radius = 2;
        }else{
            radius = 1;
        }
    }
    public void deletion(int r, int c, Tile[][] tiles){
        int leftBound = -1 * radius ;
        int rightBound = 1 + radius ;
        for (int i = leftBound; i < rightBound; i++) {
            for (int j = leftBound; j < rightBound; j++) {
                if ((r + i) > 0 && (r + i) < tiles.length-1 && (c + j) > 0 && (c + j) < tiles[r + i].length-1) {
                    if(i != 0 & j!= 0) {
                        tiles[r + i][c + j].setValue(0);
                    }else{
                        tiles[r + i][c + j].setValue(10);
                    }
                }
            }
        }
    }
    public void pull(int r, int c){
        //this should decide what pieces to pull closer to the void station
    }
    public void deflect(Piece piece){
        ;//fix this at home
        if(piece.getDirection().equals("left")) {
            piece.setDirection("right");
        }else if(piece.getDirection().equals("right")){
            piece.setDirection("left");
        }else if(piece.getDirection().equals("up")){
            piece.setDirection("down");
        } else if(piece.getDirection().equals("down")){
            piece.setDirection("up");
        }
    }
}
