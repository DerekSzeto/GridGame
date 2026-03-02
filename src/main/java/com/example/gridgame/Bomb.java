package com.example.gridgame;

import javafx.scene.control.Cell;

import java.util.ArrayList;

public class Bomb {
    private int radius;
    public Bomb(int r){
        this.radius = r;
    }
    public void explode(int r, int c, Tile[][] tiles,ArrayList<Station> stations){
        int leftBound = -1 * radius;
        int rightBound = 1 + radius;
        for (int i = leftBound; i < rightBound; i++) {
            for (int j = leftBound; j < rightBound; j++) {
                if ((r + i) > 0 && (r + i) < tiles.length-1 && (c + j) > 0 && (c + j) < tiles[r + i].length-1) {
                    if(tiles[r+i][c+j].getValue()!=12) {
                        tiles[r + i][c + j].setValue(0);
                    }
                    for (int k = stations.size()-1; k >= 0; k--) {
                        if(stations.get(k).getRow()== r+i && stations.get(k).getCol()== c+j){
                            stations.remove(k);
                            System.out.println("Stations: "+ stations.size());
                        }
                    }
                }
            }
        }
    }

    public int getRadius(){
        return radius;
    }
}
