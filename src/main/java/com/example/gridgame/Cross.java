package com.example.gridgame;

import java.util.ArrayList;

public class Cross extends Piece{
    //only diagonals needs to be at center
    private String mode; //color or erase
    private ArrayList<Tile> activationPoints = new ArrayList<>();
    public Cross(int r, int c,String mode){
        super(r,c);
    }
    public void diagonals(Tile[][] tiles){
        //downward to the right
        for (int i = 1; i < tiles.length-2 ; i++) {
            if(mode.equals("erase")){

            }
        }
    }
    public boolean atDestination(){
        for (int i = 0; i < activationPoints.size(); i++) {
            if(activationPoints.get(i).getRow() == currentRow & activationPoints.get(i).getCol() == currentCol){
                return true;
            };
        }
        return false;
    }
    public void determineActivationPoints(Tile[][] tiles){
        int dimension = tiles.length-2;
        if(dimension%2==0){
            evenGrid(dimension,tiles);
        }else{
            oddGrid(dimension,tiles);
        }
    }
    public void evenGrid(int dimension, Tile[][] tiles){
        int centerIndex = dimension/2;
        for (int i = centerIndex; i < centerIndex+2; i++) {
            activationPoints.add(tiles[centerIndex][i]);
            activationPoints.add(tiles[centerIndex+1][i]);
        }
    }
    public void oddGrid(int dimension,Tile[][] tiles){
        int centerIndex = ((1+dimension)/2)+1;
        activationPoints.add(tiles[centerIndex][centerIndex]);
    }
    public String getMode(){
        return mode;
    }
}
