package com.example.gridgame;

public class Friction extends Piece{
    //martin idea and he said that the piece will only move a specific amount of tiles and then stop without reaching its end destination that it is supposed to reach
    //the friction is determined randomly
    private int stoppingPower  = 0;
    private int dimensions;
    public Friction(int r, int c, int d){
        super(r,c);
        dimensions = d-2;
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


        if  (tiles[newRow][newCol].getValue() == 0){
            if(stopByFriction()){
                canMove = false;
            }else {
                currentCol = newCol;
                currentRow = newRow;
                stoppingPower++;
            }
        }else if (tiles[newRow][newCol].getValue() > 0){
            canMove = false;
        }


    }
    public boolean stopByFriction() {
        if (stoppingPower < 0) {
            stoppingPower = 0;
        }

        int prob = (int)(Math.random() * dimensions+1);
        if (prob < stoppingPower) {
            return true;
        }
        return false;
    }
}
