package com.example.gridgame;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Refract extends Piece{
    private int value;

    public Refract(int r, int c, int v) {
        super(r, c);
        this.value = v;
    }
    @Override
    public void move(Tile[][] tiles) {
        int newRow = getNewRow();
        int newCol = getNewCol();

        int collidedValue = tiles[newRow][newCol].getValue();

        if (collidedValue == 0) {
            currentRow = newRow;
            currentCol = newCol;

        } else if (collidedValue == value) {
            reflect();

            int bounceRow = getNewRow();
            int bounceCol = getNewCol();

            if (tiles[bounceRow][bounceCol].getValue() == 0) {
                currentRow = bounceRow;
                currentCol = bounceCol;
            }else{
                canMove = false;
            }

        }else if(collidedValue == getCheckingValue()) {
            canMove = false;

        }else{
            canMove = false;
        }
    }
    public int getCheckingValue() {
        if (value == 1) {
            return 2;
        }else{
            return 1;
        }
    }

    public int getNewRow() {
        int newRow = currentRow;
        if (direction.equals("up")) {
            newRow = currentRow - 1;
        } else if (direction.equals("down")) {
            newRow = currentRow + 1;
        }
        return newRow;
    }

    public int getNewCol() {
        int newCol = currentCol;
        if (direction.equals("right")) {
            newCol = currentCol + 1;
        } else if (direction.equals("left")) {
            newCol = currentCol - 1;
        }
        return newCol;
    }

    public void refract(Tile[][] tiles, ArrayList<Piece> piecesShot) {
        ArrayList<Tile> diagonals = getDiagonals(tiles);

        for (int i = 0; i < diagonals.size(); i++) {
            Tile diagonalTile = diagonals.get(i);
            int v = diagonalTile.getValue();

            if (v == getCheckingValue()) {
                int directionH;
                if (diagonalTile.getRow() < currentRow) {
                    directionH = -1;
                } else {
                    directionH = 1;
                }

                int directionV;
                if (diagonalTile.getCol() < currentCol) {
                    directionV = -1;
                } else {
                    directionV = 1;
                }

                int finalRow = diagonalTile.getRow() + directionH;
                int finalCol = diagonalTile.getCol() + directionV;

                if (finalRow > 0 && finalRow < tiles.length - 1 &&
                        finalCol > 0 && finalCol < tiles[finalRow].length - 1 &&
                        tiles[finalRow][finalCol].getValue() == 0) {
                    diagonalTile.setValue(0);
                    piecesShot.add(new ManipulatedPiece(diagonalTile.getRow(), diagonalTile.getCol(), directionH, directionV, v));
                }
            }
        }
    }
    public void reflect() {
        if (direction.equals("right")) {
            direction = "left";
        } else if (direction.equals("left")) {
            direction = "right";
        } else if (direction.equals("up")) {
            direction = "down";
        } else if (direction.equals("down")) {
            direction = "up";
        }
    }

    public ArrayList<Tile> getDiagonals(Tile[][] tiles) {
        ArrayList<Tile> diagonals = new ArrayList<>();

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i != 0 && j != 0) {
                    int r = currentRow + i;
                    int c = currentCol + j;

                    if (r > 0 && r < tiles.length - 1 &&
                            c > 0 && c < tiles[r].length - 1) {
                        diagonals.add(tiles[r][c]);
                    }
                }
            }
        }

        return diagonals;
    }
}
