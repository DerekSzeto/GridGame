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
        ArrayList<Tile> nearbyTiles = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((currentRow + i) > 0 && (currentRow + i) < tiles.length - 1 && (currentCol + j) > 0 && (currentCol + j) < tiles[currentRow + i].length - 1) {
                    nearbyTiles.add(tiles[currentRow + i][currentCol + j]);
                    System.out.println("yes");
                }
            }
        }
        for (int i = nearbyTiles.size()-1; i >= 0; i--) {
            if(nearbyTiles.get(i).getValue()==6 || nearbyTiles.get(i).getValue()==4 || nearbyTiles.get(i).getValue()==value){
                nearbyTiles.remove(i);
            }
        }

        return nearbyTiles.get((int)(Math.random()*nearbyTiles.size()));
    }
    public void action(Tile[][] tiles, ArrayList<Station> stations){
        int prob = (int)((Math.random()*100)+1);

        if(prob<=30){
            System.out.println("spread");
            spawn(tiles,stations);
        }
    }

    public void spawn(Tile[][] tiles, ArrayList<Station> stations){
        Tile randomTile = getRandomNearbyTile(tiles);
        stations.add(new Virus(randomTile.getRow(),randomTile.getCol(),value));
        randomTile.setValue(6);
    }
    @Override
    public boolean canCapturePoint() {
        return false;
    }
}
