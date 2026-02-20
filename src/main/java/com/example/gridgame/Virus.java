package com.example.gridgame;

import java.util.ArrayList;

public class Virus extends Station{

    public Virus(int r, int c, int value){
        super(r,c,value);
    }
    public Tile getRandomNearbyTile(Tile[][] tiles){
        ArrayList<Tile> nearbyTiles = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((row + i) > 0 && (row + i) < tiles.length - 1 && (col + j) > 0 && (col + j) < tiles[row + i].length - 1) {
                    nearbyTiles.add(tiles[row + i][col + j]);
                }
            }
        }
        for (int i = nearbyTiles.size()-1; i > 0; i--) {
            if(nearbyTiles.get(i).getValue()==6||nearbyTiles.get(i).getValue()==4){
                nearbyTiles.remove(i);
            }
        }

        return nearbyTiles.get((int)(Math.random()*nearbyTiles.size()));
    }
    public void action(Tile[][] tiles, ArrayList<Station> stations){

        int prob = (int)((Math.random()*100)+1);

        if(prob<=80){
            infect(tiles);
        }else if(prob<=90){
            spawn(tiles,stations);
        }
        //else does nothing
    }
    public void infect(Tile[][] tiles){

        Tile randomTile = getRandomNearbyTile(tiles);

        randomTile.setValue(value);
    }
    public Tile getRandomOppositeTile(Tile[][] tiles){
        ArrayList<Tile> nearbyTiles = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((row + i) > 0 && (row + i) < tiles.length - 1 && (col + j) > 0 && (col + j) < tiles[row + i].length - 1) {
                    if(i!=0&j!=0) {
                        if(tiles[row + i][col + j].getValue()==getCheckingValue() ||tiles[row + i][col + j].getValue()==getCheckingValue()) {
                            nearbyTiles.add(tiles[row + i][col + j]);
                        }
                    }
                }
            }
        }


        return nearbyTiles.get((int)(Math.random()*nearbyTiles.size()));
    }
    public void spawn(Tile[][] tiles, ArrayList<Station> stations){
        Tile randomTile = getRandomOppositeTile(tiles);
        stations.add(new Virus(randomTile.getRow(),randomTile.getCol(),value));
        randomTile.setValue(6);
        System.out.println("size: "+stations.size());
    }
    public int getCheckingValue(){
        if(value==1){
            return 2;
        }else{
            return 1;
        }
    }
}
