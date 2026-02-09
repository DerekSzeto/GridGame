package com.example.gridgame;

public class Bomb {
    private int radius;
    public Bomb(int r){
        this.radius = r;
    }
    public void explode(int r, int c, Tile[][] tiles){
        int leftBound = -1 * radius;
        int rightBound = 1 + radius;
        for (int i = leftBound; i < rightBound; i++) {
            for (int j = leftBound; j < rightBound; j++) {
                if ((r + i) > 0 && (r + i) < tiles.length-1 && (c + j) > 0 && (c + j) < tiles[r + i].length-1) {
                    tiles[r+i][c+j].setValue(0);
                }
            }
        }
    }
    public int getRadius(){
        return radius;
    }
}
