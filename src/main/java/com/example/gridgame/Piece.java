package com.example.gridgame;

public class Piece {
    protected int currentRow;
    protected int currentCol;


    protected boolean canMove= true;
    protected String direction;

    public Piece(int r, int c){
        this.currentRow = r;
        this.currentCol = c;
        this.direction = direction();
    }


    public int getCurrentRow() {
        return currentRow;
    }

    public int getCurrenCol() {
        return currentCol;
    }
    public String direction(){
        if(currentCol==0){
           return "right";
        }else if(currentCol==11){
            return "left";
        }else if(currentRow==11){
            return "up";
        }else if(currentRow==0){
            return "down";
        }
        return null;
    }
    public void move(Tile[][] tiles){

        int newRow = currentRow;
        int newCol = currentCol;

        if(direction.equals("right")){
            newCol = currentCol+1;
        }else if(direction.equals("left")){
            newCol = currentCol-1;
        }else if(direction.equals("up")){
            newRow = currentRow+1;
        }else if(direction.equals("down")){
            newRow = currentRow-1;
        }


        if  (tiles[newRow][newCol].getValue() == 0){
            currentCol = newCol;
            currentRow = newRow;
        }else if (tiles[newRow][newCol].getValue() == 4){
            canMove = false;
        }


    }

    public boolean canMove() {
        return canMove;
    }
}
