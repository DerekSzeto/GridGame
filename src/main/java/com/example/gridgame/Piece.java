package com.example.gridgame;

public class Piece {
    protected int currentRow;
    protected int currentCol;


    protected boolean canMove= true;

    protected int numOfMoves;

    public Piece(int r, int c){
        this.currentRow = r;
        this.currentCol = c;
    }


    public int getCurrentRow() {
        return currentRow;
    }

    public int getCurrenCol() {
        return currentCol;
    }

    public void move(Tile[][] tiles){
        //random movement
        //random num 0-3 -> right, left, up, down
        //move robot

        numOfMoves+=1;

        int newRow = currentRow;
        int newCol = currentCol;

        int rand = (int) (Math.random() * 4) ;

        if (rand == 0) {
            newRow =  currentRow - 1;
        }else if (rand == 1) {
            newRow = currentRow + 1;
        }else if (rand == 2) {
            newCol = currentCol - 1;

        }else if (rand == 3) {
            newCol = currentCol + 1;
            newRow = currentRow;
        }


//        if  (tiles[newRow][newCol].getValue() == 0){
//            // this means I'm allowed to move, so move
//            currentCol = newCol;
//            currentRow = newRow;
//        }else if (tiles[newRow][newCol].getValue() == 6){
//            currentCol = newCol;
//            currentRow = newRow;
//            canMove = false;
//            System.out.println("STOP");
//        }


    }

    public boolean canMove() {
        return canMove;
    }
}
