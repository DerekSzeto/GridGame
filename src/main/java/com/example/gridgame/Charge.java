package com.example.gridgame;

import java.util.ArrayList;

public class Charge extends Piece {
    private int charge;
    private int pieceValue;
    public Charge(int r, int c, int v,int charge) {
        super(r, c);
        this.charge = charge;
        this.pieceValue = v;
    }



    public int releaseChargePiece(Tile[][] tiles, ArrayList<Piece> piecesShot) {
        if (charge == 1) {
            explosion(tiles);
            return 0;
        } else if (charge == 2) {
            return burstPieces(tiles, piecesShot);
        } else {
            return allSides(tiles, piecesShot);
        }
    }

    public void explosion(Tile[][] tiles) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (currentRow + i > 0 && currentRow + i < tiles.length - 1 && currentCol + j > 0 && currentCol + j < tiles[currentRow + i].length - 1) {
                    tiles[currentRow + i][currentCol + j].setValue(0);
                }
            }
        }
    }

    // 3 pieces from same side, same direction, same row/line
    public int burstPieces(Tile[][] tiles, ArrayList<Piece> piecesShot) {
        int piecesCreated = 0;
        int line = getRandomIndex(tiles);

        if (direction.equals("right")) {
            for (int i = -1; i < 3; i++) {
                int r = line + i;
                if (r > 0 && r < tiles.length - 1) {
                    ChargedPiece piece = new ChargedPiece(r, 0, pieceValue);
                    piece.setInitialDirection(r, 0, tiles);
                    piecesShot.add(piece);
                    piecesCreated++;
                }
            }
        } else if (direction.equals("left")) {
            for (int i = -1; i < 3; i++) {
                int r = line + i;
                if (r > 0 && r < tiles.length - 1) {
                    ChargedPiece piece = new ChargedPiece(r, tiles[0].length - 1, pieceValue);
                    piece.setInitialDirection(r, tiles[0].length - 1, tiles);
                    piecesShot.add(piece);
                    piecesCreated++;
                }
            }
        } else if (direction.equals("down")) {
            for (int i = -1; i < 3; i++) {
                int c = line + i;
                if (c > 0 && c < tiles[0].length - 1) {
                    ChargedPiece piece = new ChargedPiece(0, c, pieceValue);
                    piece.setInitialDirection(0, c, tiles);
                    piecesShot.add(piece);
                    piecesCreated++;
                }
            }
        } else if (direction.equals("up")) {
            for (int i = -1; i < 3; i++) {
                int c = line + i;
                if (c > 0 && c < tiles[0].length - 1) {
                    ChargedPiece piece = new ChargedPiece(tiles.length - 1, c, pieceValue);
                    piece.setInitialDirection(tiles.length - 1, c, tiles);
                    piecesShot.add(piece);
                    piecesCreated++;
                }
            }
        }

        return piecesCreated;
    }

    public int allSides(Tile[][] tiles, ArrayList<Piece> piecesShot) {
        int piecesCreated = 0;

        int topCol = getRandomIndex(tiles);
        ChargedPiece top = new ChargedPiece(0, topCol, pieceValue);
        top.setInitialDirection(0, topCol, tiles);
        piecesShot.add(top);
        piecesCreated++;

        int bottomCol = getRandomIndex(tiles);
        ChargedPiece bottom = new ChargedPiece(tiles.length - 1, bottomCol, pieceValue);
        bottom.setInitialDirection(tiles.length - 1, bottomCol, tiles);
        piecesShot.add(bottom);
        piecesCreated++;

        int leftRow = getRandomIndex(tiles);
        ChargedPiece left = new ChargedPiece(leftRow, 0, pieceValue);
        left.setInitialDirection(leftRow, 0, tiles);
        piecesShot.add(left);
        piecesCreated++;

        int rightRow = getRandomIndex(tiles);
        ChargedPiece right = new ChargedPiece(rightRow, tiles[0].length - 1, pieceValue);
        right.setInitialDirection(rightRow, tiles[0].length - 1, tiles);
        piecesShot.add(right);
        piecesCreated++;

        return piecesCreated;
    }

    public int getRandomIndex(Tile[][] tiles) {
        return (int)(Math.random() * (tiles.length - 2)) + 1;
    }
}
