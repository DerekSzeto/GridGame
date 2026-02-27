package com.example.gridgame;

import java.util.ArrayList;

public class Glitch extends Piece{
    //finds the nearest pair of 2 alike tiles, takes 2-3 tiles from the row closes to the row of thatpair, the tiles will switch values with 2-3 tiles in that row
    private int pieceValue;
    public Glitch(int r, int c, int v){
        super(r,c);
        pieceValue = v;
    }


    public ArrayList<Tile> getNearbyOppositeTiles(Tile[][] tiles){
        ArrayList<Tile> nearbyTiles = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((currentRow + i) > 0 && (currentRow + i) < tiles.length - 1 && (currentCol + j) > 0 && (currentCol + j) < tiles[currentRow + i].length - 1) {
                    if((i != 0 || j != 0)) {
                        nearbyTiles.add(tiles[currentRow + i][currentCol + j]);
                    }
                }
            }
        }
        for (int i = nearbyTiles.size()-1; i >= 0; i--) {
            if(nearbyTiles.get(i).getValue()==pieceValue){
                nearbyTiles.remove(i);
            }
        }

        return nearbyTiles;
    }

    public void transpose(Tile[][] tiles) {
        for (int i = 0; i < getNearbyOppositeTiles(tiles).size(); i++) {

        }
    }
    public void getNearestTile(){

    }
    public ArrayList<Tile> getOppositeRowTile(Tile[][] tiles){
        ArrayList<Tile> runOfTiles = new ArrayList<>();
         for (int i = 1; i < tiles.length - 1; i++) {
            int count = 0;

            for (int j = 1; j < tiles[i].length - 1; j++) {
                if (tiles[i][j].getValue() == getCheckingValue()) {
                    count++;
                    runOfTiles.add(tiles[i][j]);
                    if (count >= 2) {
                        return runOfTiles;
                    }
                } else {
                    count = 0;
                    runOfTiles.clear();
                }
            }
        }
         return null;
    }
    public int getCheckingValue(){
        if(pieceValue==1){
            return 2;
        }else{
            return 1;
        }
    }
}

