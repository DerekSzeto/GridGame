package com.example.gridgame;

import java.util.ArrayList;

public class Game {
    private Tile[][] tiles;
    private int playerTurn = (int)(Math.random()*2+1);
    private ArrayList<Piece> piecesActive = new ArrayList<>();



    private int[][] board =
            {       {5,4,4,4,4,4,4,4,4,4,4,5},
                    {4,0,0,0,0,0,0,0,0,0,0,4},
                    {4,0,0,0,0,0,0,0,0,0,0,4},
                    {4,0,0,0,0,0,0,0,0,0,0,4},
                    {4,0,0,0,0,0,0,0,0,0,0,4},
                    {4,0,0,0,0,0,0,0,0,0,0,4},
                    {4,0,0,0,0,0,0,0,0,0,0,4},
                    {4,0,0,0,0,0,0,0,0,0,0,4},
                    {4,0,0,0,0,0,0,0,0,0,0,4},
                    {4,0,0,0,0,0,0,0,0,0,0,4},
                    {4,0,0,0,0,0,0,0,0,0,0,4},
                    {5,4,4,4,4,4,4,4,4,4,4,5}

            };




    public void createBoard(int row, int col){
        tiles = new Tile[row][col];

        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                tiles[i][j] = new Tile(board[i][j]);
            }
        }
    }

    public Tile[][] getTiles() {
        return tiles;
    }





    public void addStandardPiece(int r, int c){
        if(tiles[r][c].getValue()==4) {
            piecesActive.add(new Piece(r, c));
            piecesActive.getFirst().setDirection(r,c);
        }
    }
    public ArrayList<Piece> getPiecesActive(){
        return piecesActive;
    }
    public int getPlayerTurn() {
        return playerTurn;
    }
    public void switchPlayer(){
        if(playerTurn==1){
            playerTurn=2;
        }else{
            playerTurn = 1;
        }
    }
    public void checkGameOver(int r, int c,int player){
        boolean[] fiveTiles = {true, false,false,false,false};
        String[] patterns = {"row","column"};
        boolean doneChecking = false;
        while(!doneChecking) {
            for (int i = 0; i < patterns.length; i++) {
                for (int j = 0; j < 5; j++) {
                    if(patterns[i].equals("row")) {
                        if (tiles[r][c + j].getValue() == player) {
                            fiveTiles[j] = true;
                        } else {
                            for (int j = 1; j < fiveTiles.length; j++) {
                                fiveTiles[j] = false;
                            }
                        }
                    }else{
                        if (tiles[r+j][c].getValue() == player) {
                            fiveTiles[j] = true;
                        } else {
                            for (int j = 1; j < fiveTiles.length; j++) {
                                fiveTiles[j] = false;
                            }
                        }
                    }
                }
            }
        }
    }
    public boolean patternOf5(boolean[] fiveTiles){
        int count = 0;
        for (int j = 1; j < fiveTiles.length; j++) {
            if(fiveTiles[j]){
                count++;
            };
        }
        if(count == 5){
            return true;
        }
        return false;
    }
}
