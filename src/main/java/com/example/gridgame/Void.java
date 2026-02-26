package com.example.gridgame;

import java.util.ArrayList;

public class Void extends Station{
    private ArrayList<Tile> area = new ArrayList<>();
    private int radius = 1;
    public Void(int r, int c, int v){
        super(r,c,v);
    }
    public void toggleStage(){
        if(radius==1){
            radius = 2;
        }else{
            radius  =1;
        }
    }
    public void deletion(Tile[][] tiles){
        int leftBound = -1 * radius ;
        int rightBound = 1 + radius ;
        for (int i = leftBound; i < rightBound; i++) {
            for (int j = leftBound; j < rightBound; j++) {
                if ((row + i) > 0 && (row + i) < tiles.length-1 && (col + j) > 0 && (col + j) < tiles[row + i].length-1) {
                    if(i != 0 || j != 0) {
                        tiles[row + i][col + j].setValue(0);
                    }else{
                        tiles[row + i][col + j].setValue(10);
                    }
                }
            }
        }
    }
    public void deflect(ArrayList<Piece> piecesShot) {

        for (int i = 0; i < piecesShot.size(); i++) {
            Piece piece = piecesShot.get(i);

            int pieceCurrentRow = piece.getCurrentRow();
            int pieceCurrentCol = piece.getCurrentCol();

            if (pieceCurrentRow >= (row - 1) && pieceCurrentRow <= (row + 1) && pieceCurrentCol >= (col - 1) && pieceCurrentCol <= (col + 1)){
                int dH = 0;
                int dV = 0;
                if (piece.getDirection().equals("left")) {
                    dH = 0;
                    dV = 1;
                } else if (piece.getDirection().equals("right")) {
                    dH = 0;
                    dV = -1;
                } else if (piece.getDirection().equals("up")) {
                    dH = 1;
                    dV = 0;
                } else if (piece.getDirection().equals("down")) {
                    dH = -1;
                    dV = 0;
                }
                piecesShot.set(i, new ManipulatedPiece(pieceCurrentRow, pieceCurrentCol, dH, dV, 0));
            }
        }
    }
}
