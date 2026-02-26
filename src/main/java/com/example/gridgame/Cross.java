package com.example.gridgame;

import java.util.ArrayList;

public class Cross extends Piece{
    //only diagonals needs to be at center
    //make the X last 4 turns !!!!!!!!!!!!!!!!!!!!!!!!
    private String mode; //color or erase
    private ArrayList<Tile> activationPoints = new ArrayList<>();
    public Cross(int r, int c){
        super(r,c);
        this.mode = mode;
    }
    public void diagonals(Tile[][] tiles,int v){
        //downward to the right
        for (int i = 1; i < tiles.length-1 ; i++) {
            tiles[i][i].setValue(v);
        }

        int tileCount = 0;
        int row = 1;
        int col = tiles.length-2;
        while(tileCount<tiles.length-2){
            tiles[row][col].setValue(v);
            row++;
            col--;
            tileCount++;
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
}
