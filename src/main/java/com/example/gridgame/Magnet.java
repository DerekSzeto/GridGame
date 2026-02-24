package com.example.gridgame;

import java.util.ArrayList;

public class Magnet extends Station{
    private String mode;
    private int strength;
    public Magnet(int r,int c, int v, int str){
        super(r,c,v);
        strength = str;
    }
    public void ring(Tile[][] tiles){


            ArrayList<Tile> borderingTiles = new ArrayList<>();
            int[] dr = {
                    -2,-2,-2,-2,-2,
                    -1,        -1,
                    0,         0,
                    1,         1,
                    2, 2, 2, 2, 2
            };

            int[] dc = {
                    -2,-1, 0, 1, 2,
                    -2,        2,
                    -2,        2,
                    -2,        2,
                    -2,-1, 0, 1, 2
            };

            for (int k = 0; k < dr.length; k++) {
                int rr = row + dr[k];
                int cc = col + dc[k];

                // follow your bounds (skips edges)
                if (rr > 0 && rr < tiles.length - 1 && cc > 0 && cc < tiles[rr].length - 1) {
                    borderingTiles.add(tiles[rr][cc]);
                }
            }
    }
    public void repel(Tile[][] tiles, ArrayList<Piece> piecesShot) {

        int leftBound = -1 * strength;
        int rightBound = 1 + strength;

        for (int i = leftBound; i <= rightBound; i++) {
            for (int j = -2; j <= 2; j++) {

                if (!(i == 0 && j == 0)) {

                    int nRow = row + i;
                    int nCol = col + j;

                    if (nRow > 0 && nRow < tiles.length - 1 && nCol > 0 && nCol < tiles[nRow].length - 1) {
                        int v = tiles[nRow][nCol].getValue();

                        if (v != 0 && v != 4) {

                            int directionH;
                            if (i > 0) directionH = 1;
                            else if (i < 0) directionH = -1;
                            else directionH = 0;

                            int directionV;
                            if (j > 0) directionV = 1;
                            else if (j < 0) directionV = -1;
                            else directionV = 0;

                            int finalRow = nRow + directionH;
                            int finalCol = nCol + directionV;

                            // only spawn a mover if the destination 1 step away is empty and in-bounds
                            if (finalRow > 0 && finalRow < tiles.length - 1 &&
                                    finalCol > 0 && finalCol < tiles[finalRow].length - 1 &&
                                    tiles[finalRow][finalCol].getValue() == 0) {

                                tiles[nRow][nCol].setValue(0); // remove from source
                                piecesShot.add(0, new RepelledPiece(nRow, nCol, directionH, directionV, v));
                            }
                        }
                    }
                }
            }
        }
    }
}
