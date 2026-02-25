package com.example.gridgame;

import java.util.ArrayList;

public class Magnet extends Station{
    private String mode;
    private int strength;
    private int reach;
    public Magnet(int r,int c, int v, int str, int reach, String mode){
        super(r,c,v);
        strength = str;
        this.reach = reach;
        this.mode = mode; //attract or repel
    }
    public void manipulatePieces(Tile[][] tiles, ArrayList<Piece> piecesShot) {

        int leftBound = -1 * reach;
        int rightBound = 1 + reach;

        for (int i = leftBound; i < rightBound; i++) {
            for (int j = leftBound; j < rightBound; j++) {

                if (i != 0 || j != 0) {

                    int nRow = row + i;
                    int nCol = col + j;

                    if (nRow > 0 && nRow < tiles.length - 1 && nCol > 0 && nCol < tiles[nRow].length - 1) {
                        int v = tiles[nRow][nCol].getValue();
                        if (v != 0 && v < 4 && v == determineMovingTiles()) {

                            int directionH = determineDistance(i);
                            int directionV = determineDistance(j);

                            int finalRow = nRow + directionH * strength;
                            int finalCol = nCol + directionV * strength;


                            if (finalRow > 0 && finalRow < tiles.length - 1 &&
                                    finalCol > 0 && finalCol < tiles[finalRow].length - 1 &&
                                    tiles[finalRow][finalCol].getValue() == 0) {
                                tiles[nRow][nCol].setValue(0);

                                if(mode.equals("repel")) {
                                    piecesShot.add(0, new ManipulatedPiece(nRow, nCol, directionH, directionV, v));
                                }else{
                                    piecesShot.add(0, new ManipulatedPiece(nRow, nCol, -directionH, -directionV, v));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public int determineDistance(int index){
        int direction;
        if (index > 0) {
            direction = 1;
        } else if (index < 0) {
            direction = -1;
        } else {
            direction = 0;
        }
        return direction;
    }
    public int determineMovingTiles(){

        if(mode.equals("repel")) {
            return getCheckingValue();
        }else{
            return value;
        }
    }
    public int getCheckingValue(){
        if(value==1){
            return 2;
        }else{
            return 1;
        }
    }
    public void alternateModes(){
        if(mode.equals("repel")){
            mode = "attract";
        }else{
            mode = "repel";
        }
    }
}
