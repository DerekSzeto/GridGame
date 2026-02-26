package com.example.gridgame;

public class ManipulatedPiece extends Piece {
    private int dH;
    private int dV;
    private int tileValue;

    public ManipulatedPiece(int r, int c, int h, int v, int val) {
        super(r, c);
        this.dH = h;
        this.dV = v;
        this.tileValue = val;
    }

    @Override
    public void move(Tile[][] tiles) {
        int newRow = currentRow + dH;
        int newCol = currentCol + dV;

        if (newRow > 0 && newRow < tiles.length - 1 &&
                newCol > 0 && newCol < tiles[newRow].length - 1) {

            if (tiles[newRow][newCol].getValue() == 0) {
                currentRow = newRow;
                currentCol = newCol;
                tiles[currentRow][currentCol].setValue(tileValue);
            } else {
                canMove = false;
            }
        } else {
            canMove = false;
        }
    }
}
