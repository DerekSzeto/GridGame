package com.example.gridgame;

public class Shadow extends Piece{
    public Shadow(int r,int c){
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


        if  (tiles[newRow][newCol].getValue() <4){
            currentCol = newCol;
            currentRow = newRow;
        }else if (tiles[newRow][newCol].getValue() >= 4){
            canMove = false;
        }
    }
    public void convert(int player, Tile[][] tiles){
        tiles[currentRow][currentCol].setValue(player);
    }
}
