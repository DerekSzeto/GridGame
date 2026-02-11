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
                tiles[i][j] = new Tile(0);
            }
        }
        for (int i = 0; i < row; i++) {
            tiles[i][0] = new Tile(4);
            tiles[i][tiles[0].length-1] = new Tile(4);
        }
        for (int i = 0; i < col; i++) {
            tiles[0][i] = new Tile(4);
            tiles[tiles.length-1][i] = new Tile(4);
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
                piecesActive.add(new Paint(r, c,1));
            }
            piecesActive.getFirst().setDirection(r,c,tiles);
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
        if(patternOf5(player)){
            return true;
        }
        return false;
    }
    public boolean shootersLocked(){
        int[] directionCount = new int[4];
        int rows = tiles.length-2;
        int cols = tiles[0].length-2;
        //right 0, left 1, up 2, down 3
        //right
        for (int i = 1; i < tiles[0].length-1; i++) {
            if(tiles[i][1].getValue()>0){
                directionCount[0]++;
            }
        }
        //left
        for (int i = 1; i < tiles[0].length-1; i++) {
            if(tiles[i][tiles.length-2].getValue()>0){
                directionCount[1]++;
            }
        }
        //down
        for (int i = 1; i < tiles.length-1; i++) {
            if(tiles[1][i].getValue()>0){
                directionCount[3]++;
            }
        }
        //up
        for (int i = 1; i < tiles.length-1; i++) {
            if(tiles[tiles.length-2][i].getValue()>0){
                directionCount[2]++;
            }
        }
        int lockCount = 0;
        //check the counts of the stuff and if it matches the row and col count tmr, if all are locked return true
    }
    public boolean mostAreaCovered(int player){
        //check if the borders can shoot, once cant shoot game is over, checks who has painted the most

        int purpleCount = 0;
        int greenCount = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j <tiles[i].length; j++) {
                if(player==1){
                    purpleCount++;
                }else if(player==2){
                    greenCount++;
                }
            }
        }
        if(purpleCount!=greenCount){
            return true;
        }
        return false;
    }
    public boolean patternOf5(int player){
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

        for (int i = 1; i < tiles[0].length-1; i++) {
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

    public void stopPiece(int piece) {
        if( piecesActive.get(piece) instanceof Paint){
            ((Paint) piecesActive.get(piece)).paintTiles(tiles,piecesActive.get(piece).getCurrentRow(),piecesActive.get(piece).getCurrentCol(),playerTurn);
        }
        if (tiles[piecesActive.get(piece).getCurrentRow()][piecesActive.get(piece).getCurrentCol()].getValue() == 0) {
            tiles[piecesActive.get(piece).getCurrentRow()][piecesActive.get(piece).getCurrentCol()].setValue(playerTurn);
        }
        if (tiles[piecesActive.get(piece).getCurrentRow()][piecesActive.get(piece).getCurrentCol()].getValue() < 4) {
            switchPlayer();
        }
        piecesActive.remove(piece);

    }
}
