package com.example.gridgame;

public class Paint extends Piece {
    private int radius;
    public Paint(int r, int c,int rad){
        super(r,c);
        this.radius = rad;
    }
    public void paintTiles(Tile[][] tiles, int r, int c, int value){
        int leftBound = -1 * radius;
        int rightBound = 1 + radius;
        for (int i = leftBound; i < rightBound; i++) {
            for (int j = leftBound; j < rightBound; j++) {
                if ((r + i) > 0 && (r + i) < tiles.length-1 && (c + j) > 0 && (c + j) < tiles[r + i].length-1) {
                    if(tiles[r+i][c+j].getValue()!=value&tiles[r+i][c+j].getValue()!=4&tiles[r+i][c+j].getValue()!=value&tiles[r+i][c+j].getValue()!=0) {
                        System.out.println("painted x2");
                        tiles[r + i][c + j].setValue(value);
                    }
                }
            }
        }
    }
}
