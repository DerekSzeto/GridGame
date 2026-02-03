package com.example.gridgame;

public class Piece {
    protected int currentRow;
    protected int currentCol;


    protected boolean canMove= true;
    protected String direction;

    public Piece(int r, int c){
        this.currentRow = r;
        this.currentCol = c;

    }


    public int getCurrentRow() {
        return currentRow;
    }

    public int getCurrentCol() {
        return currentCol;
    }
    public void setDirection(int r, int c){
        if(c==0){

            direction=  "right";
        }else if(c==11){
            direction=  "left";
        }else if(r==11){
            direction=  "up";
        }else if(r==0){
            direction=  "down";
        }

    }
    public void move(Tile[][] tiles){

        int newRow = currentRow;
        int newCol = currentCol;

        if(direction.equals("right")){
            newCol = currentCol+1;
        }else if(direction.equals("left")){
            newCol = currentCol-1;
        }else if(direction.equals("up")){
            newRow = currentRow-1;
        }else if(direction.equals("down")){
            newRow = currentRow+1;
        }


        if  (tiles[newRow][newCol].getValue() == 0){
            currentCol = newCol;
            currentRow = newRow;
        }else if (tiles[newRow][newCol].getValue() > 0){
            canMove = false;
        }


    }

    public boolean canMove() {
        return canMove;
    }
}
