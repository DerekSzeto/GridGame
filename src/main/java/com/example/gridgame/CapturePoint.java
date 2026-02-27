package com.example.gridgame;

public class CapturePoint {
    private int row;
    private int col;
    private int occupiedBy; // 0 = uncaptured, 1 = player1, 2 = player2

    public CapturePoint(int row, int col) {
        this.row = row;
        this.col = col;
        this.occupiedBy = 0;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getOccupiedBy() {
        return occupiedBy;
    }

    public void setOccupiedBy(int occupiedBy) {
        this.occupiedBy = occupiedBy;
    }

    public boolean isCaptured() {
        if (occupiedBy != 0) {
            return true;
        }
        return false;
    }
}
