package com.example.gridgame;

public class Bomb {
    private int radius;
    private int lvl;
    public Bomb(int r){
        this.radius = r;
    }
    public void explode(int r, int c, Tile[][] tiles){
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((r + i) > 0 && (r + i) < tiles.length-1 && (c + j) > 0 && (c + j) < tiles[r + i].length-1) {
                    tiles[r+i][c+j].setValue(0);
                }
            }
        }
    }
}
