package com.example.gridgame;

import java.util.ArrayList;

public class Zombie extends Piece{
    // have its own individual tile value of 6
    private int value;
    public Zombie(int r, int c, int value){
        super(r,c);
        this.value = value;
    }
    public Tile getRandomNearbyTile(Tile[][] tiles){
        Tile[] nearbyTiles = new Tile[8];
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((currentRow + i) > 0 && (currentRow + i) < tiles.length-1 && (currentCol + j) > 0 && (currentCol + j) < tiles[currentRow + i].length-1) {
                    tiles[currentRow + i][currentCol + j].setValue(value);
                }
            }
        }
        return nearbyTiles[(int)(Math.random()*nearbyTiles.length)];
    }
    public boolean infect(Tile[][] tiles){

        Tile randomTile = getRandomNearbyTile(tiles);

        int prob = (int)((Math.random()*100)+1);
        if(prob<=70){
            randomTile.setValue(value);
        }else{
            return true;
        }
        return false;
    }
    public void spawn(Tile[][] tiles, ArrayList<Piece> pieces){
        Tile randomTile = getRandomNearbyTile(tiles);
        pieces.add(new Zombie(randomTile.getRow(),randomTile.getCol(),value));
    }

}
