package com.example.gridgame;

public class ManipulatedPiece extends Piece {
    private int dH;
    private int dV;
    private int tileValue; // the tile value we are moving

    public ManipulatedPiece(int r, int c, int h, int v, int val) {
        super(r, c);
        this.dH = h;
        this.dV = v;
        tileValue = val;
    }

    @Override
    public void move(Tile[][] tiles) {
        int newRow = currentRow + dH;
        int newCol = currentCol + dV;

        if (tiles[newRow][newCol].getValue() == 0) {
            currentRow = newRow;
            currentCol = newCol;
            tiles[currentRow][currentCol].setValue(tileValue);
        }
        canMove = false;
    }

}
