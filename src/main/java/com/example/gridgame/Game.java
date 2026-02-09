package com.example.gridgame;

import java.util.ArrayList;

public class Game {
    private Tile[][] tiles;
    private int playerTurn = (int)(Math.random()*2+1);
    private ArrayList<Piece> piecesActive = new ArrayList<>();
    private Player player1 = new Player("boi1");
    private Player player2 = new Player("boi2");
    private Player[] players = new Player[2];

    private boolean gameOver = false;

    public void addPlayers(){
        players[0] = player1;
        players[1] = player2;
    }
    public Player[] getPlayers(){
        return players;
    }

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





    public void shootPiece(int r, int c, String type){
        if(tiles[r][c].getValue()==4) {
            if(type.equals("Standard")) {
                piecesActive.add(new Piece(r, c));
            }
            else if(type.equals("Paint")) {
                piecesActive.add(new Paint(r, c));
            }
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
    public boolean checkGameOver(int player){

        for (int i = 1; i < tiles.length-1; i++) {
            int rowCount = 0;
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j].getValue() == player) {
                    rowCount++;
                } else {
                    rowCount = 0;
                }
                if(rowCount==5){
                    gameOver = true;
                    return true;
                }
            }
        }

        for (int i = 1; i < tiles[i].length-1; i++) {
            int colCount = 0;
            for (int j = 1; j < tiles.length-1; j++) {
                if (tiles[j][i].getValue() == player) {
                    colCount++;
                } else {
                    colCount = 0;
                }
                if(colCount==5){
                    gameOver = true;
                    return true;
                }
            }
        }

        return false;
    }
    public boolean isGameOver(){
        return this.gameOver;
    }
    public void setGameOver(boolean b){
        gameOver = b;
    }

    public void useBomb(int player, int r, int c) {
        players[player].getPlayerBombs().getFirst().explode(r,c,tiles);
        players[player].getPlayerBombs().removeFirst();
        switchPlayer();
    }

}
