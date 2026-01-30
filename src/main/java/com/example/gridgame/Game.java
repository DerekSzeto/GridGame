package com.example.gridgame;

import java.util.ArrayList;

public class Game {
    private Tile[][] tiles;

    //make 2D array of tiles to match the size of your 2D array of btns in the controller

    private ArrayList<Piece> piecesActive = new ArrayList<>();



    private int[][] board =
            {       {4,4,4,4,4,4,4,4,4,4},
                    {4,0,0,0,0,0,0,0,0,4},
                    {4,0,0,0,0,0,0,0,0,4},
                    {4,0,0,0,0,0,0,0,0,4},
                    {4,0,0,0,0,0,0,0,0,4},
                    {4,0,0,0,0,0,0,0,0,4},
                    {4,0,0,0,0,0,0,0,0,4},
                    {4,0,0,0,0,0,0,0,0,4},
                    {4,0,0,0,0,0,0,0,0,4},
                    {4,4,4,4,4,4,4,4,4,4}
            };




    public void createBoard(int row, int col){
        tiles = new Tile[row][col];

        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                tiles[i][j] = new Tile(board[i][j]);
            }
        }

        makeBorder();

        //bot = new Robot(1,1);
        //  makeRobot();

    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void makeBorder(){
        for (int i = 0; i < tiles.length; i++){
            for (int j = 0; j < tiles[0].length; j++){
                if (i==0||j==0||i== tiles.length-1||j==tiles[i].length-1){
                    tiles[i][j].setValue(4);
                }
            }
        }
    }



    public void addStandardPiece(int r, int c){
        piecesActive.add(new Piece(r,c));
    }
    public ArrayList<Piece> getPiecesActive(){
        return piecesActive;
    }

}
