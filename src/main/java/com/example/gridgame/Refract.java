package com.example.gridgame;

import java.util.ArrayList;

public class Refract extends Piece{
    private int value;
    // The piece must collide with a piece of the same color, if the tile the piece collides with has an adjacent of the same color, the piece will make the adjacent piece move towards other pieces of the same color
    public Refract(int r, int c ,int v) {
        super(r,c);
        this.value = v;
    }
    public int getCheckingValue(){
        if(value == 1){
            return 2;
        }else{
            return 1;
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
            if(tiles[newRow][newCol].getValue() == value){
                refract(tiles);
                canMove = false;
            }else if(tiles[newRow][newCol].getValue() == getCheckingValue()){
                reflect();
            }
        }


    }
    public void refract(Tile[][] tiles){
        ArrayList<Tile> diagonals = getDiagonals(tiles);
        for (int i = 0; i < diagonals.size(); i++) {
            if(diagonals.get(i).getValue() == getCheckingValue()){

            }
        }
    }
    public void reflect(){
        //redirects to the opposite direction if the tile has the value of the opposite player
    }
    public ArrayList<Tile> getDiagonals(Tile[][] tiles){
        ArrayList<Tile> diagonals = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i != 0 && j != 0) {
                    if (currentRow + i > 0 && currentRow + i < tiles.length - 1 && currentCol + j > 0 && currentCol + j < tiles[currentRow + i].length - 1) {
                        diagonals.add(tiles[currentRow + i][currentCol + j]);
                    }
                }
            }
        }
        return diagonals;
    }
    public void reflection(){
        //same tile moves back, makes other same tile move as well, mirrored movment
    }
}
