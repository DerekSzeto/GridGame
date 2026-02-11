package com.example.gridgame;

public class Rocket extends Piece{
    public Rocket(int r, int c){
        super(r,c);
    }
    public void redirect(Tile[][] tiles){

        int[] bounds = new int[4];
        //0 left, 1 right, 2 top, 3 down
        for (int i = 1; i < tiles.length-1; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j].getValue() == 0) {

                }
            }
        }

        for (int i = 1; i < tiles[0].length-1; i++) {
            int colCount = 0;
            for (int j = 1; j < tiles.length-1; j++) {
                if (tiles[j][i].getValue() == player) {
                    colCount++;
                } else {
                    colCount = 0;
                }
                if(colCount==5){


                }
            }
        }
    }
}
