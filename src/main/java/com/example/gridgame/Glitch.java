package com.example.gridgame;

import java.util.ArrayList;

public class Glitch extends Piece{
    //finds the nearest pair of 2 alike tiles, takes 2-3 tiles from the row closes to the row of thatpair, the tiles will switch values with 2-3 tiles in that row
    public Glitch(int r, int c){
        super(r,c);
    }
    @Override
    public void move(Tile[][] tiles){

        int newRow = currentRow;
        int newCol = currentCol;

        if(direction.equals("right")){
            newCol = currentCol+1;
        }else if(direction.equals("left")){
            newCol = currentCol-1;
        }else if(direction.equals("up")){
            newRow = currentRow-1;
        }else if(direction.equals("down")){
            newRow = currentRow+1;
        }


        if  (tiles[newRow][newCol].getValue() == 0){
            currentCol = newCol;
            currentRow = newRow;
        }else if (tiles[newRow][newCol].getValue() > 0){
            canMove = false;
        }


    }
    public ArrayList<Tile> getRandomNearbyTile(Tile[][] tiles){
        ArrayList<Tile> nearbyTiles = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((currentRow + i) > 0 && (currentRow + i) < tiles.length - 1 && (currentCol + j) > 0 && (currentCol + j) < tiles[currentRow + i].length - 1) {
                    nearbyTiles.add(tiles[currentRow + i][currentCol + j]);
                }
            }
        }
        for (int i = nearbyTiles.size()-1; i >= 0; i--) {
            if(nearbyTiles.get(i).getValue()==6||nearbyTiles.get(i).getValue()==4){
                nearbyTiles.remove(i);
            }
        }

        return nearbyTiles;
    }

    public void transpose(){

    }
    public void getNearestTile(){

    }
    public void getOppositeRowTile(){

    }
}

