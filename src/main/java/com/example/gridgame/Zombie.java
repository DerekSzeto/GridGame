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
        //ArrayList<Tile> bombTiles = new ArrayList<>();
        //        for (int i = 0; i < tiles.length; i++) {
        //            for (int j = 0; j <tiles[i].length; j++) {
        //                if(tiles[i][j].getTileNum()==-1){
        //                    bombTiles.add(tiles[i][j]);
        //                }
        //            }
        //        }
        //        Tile randomTile = bombTiles.get((int)(Math.random()*bombTiles.size()));
        //        int row = randomTile.getRow();
        //        int col = randomTile.getCol();
        //        int[] neighboringIndexes = {-1,0,1};
        //        int i = neighboringIndexes[(int)(Math.random()*neighboringIndexes.length)];
        //        int j = neighboringIndexes[(int)(Math.random()*neighboringIndexes.length)];
        //        while(true) {
        //            if ((row + i) > -1 && (row + i) < tiles.length && (col + j) > -1 && (col + j) < tiles[row + i].length){
        //                if (i != 0 || j != 0) {
        //                    return tiles[row + i][col + j];
        //                } else {
        //                    i = neighboringIndexes[(int) (Math.random() * neighboringIndexes.length)];
        //                    j = neighboringIndexes[(int) (Math.random() * neighboringIndexes.length)];
        //
        //                }
        //            }
        //        }
        int[] neighboringIndexes = {-1,0,1};
        int i = neighboringIndexes[(int)(Math.random()*neighboringIndexes.length)];
        int j = neighboringIndexes[(int)(Math.random()*neighboringIndexes.length)];
        while(true) {
            if ((currentRow + i) > 0 && (currentRow + i) < tiles.length-1 && (currentCol + j) > 0 && (currentCol + j) < tiles[currentRow + i].length-1){
                if (i != 0 || j != 0) {
                    return tiles[currentRow + i][currentCol + j];
                } else {
                    i = neighboringIndexes[(int) (Math.random() * neighboringIndexes.length)];
                    j = neighboringIndexes[(int) (Math.random() * neighboringIndexes.length)];
                }
            }
        }
//        return nearbyTiles.get((int)(Math.random()*nearbyTiles.size()));
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
        Piece newZombie =new Zombie(randomTile.getRow(),randomTile.getCol(),value);
        newZombie.canMove = false;
        pieces.add(newZombie);
        randomTile.setValue(6);
    }
    public int getValue(){
        return value;
    }
}
