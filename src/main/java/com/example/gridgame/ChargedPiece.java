package com.example.gridgame;

public class ChargedPiece extends Piece{
    private int pieceValue;

    public ChargedPiece(int r, int c, int v) {
        super(r, c);
        this.pieceValue = v;
    }

    public int getValue() {
        return pieceValue;
    }
}

