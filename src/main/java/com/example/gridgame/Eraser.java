package com.example.gridgame;

public class Eraser extends Piece{
    // if the piece stops at the center of an odd grid or one of the four tiles in the center of an even grid, random number
    //chooses whether n/4 (8/4 = 2, 10/4 = 2, 12/4 = 3) rows or columns will be erased, <---- has a chance to do both rows and columns of n/4

    public Eraser(int r, int c){
        super(r,c);
    }
    @Override
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


        if  (tiles[newRow][newCol].getValue() < 4){
            currentCol = newCol;
            currentRow = newRow;
            tiles[newRow][newCol].setValue(0);
        }else if (tiles[newRow][newCol].getValue() == 4){
            canMove = false;
        }
    }

}
