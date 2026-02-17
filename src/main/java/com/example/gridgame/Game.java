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
                tiles[i][j] = new Tile(i,j,0);
            }
        }
        for (int i = 0; i < row; i++) {
            tiles[i][0] = new Tile(i,0,4);
            tiles[i][tiles[0].length-1] = new Tile(i,tiles.length-1,4);
        }
        for (int i = 0; i < col; i++) {
            tiles[0][i] = new Tile(0,i,4);
            tiles[tiles.length-1][i] = new Tile(tiles.length-1,i,4);
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
            }else if(type.equals("Shadow")) {
                piecesActive.add(new Shadow(r,c));
            }else if(type.equals("Rocket")){
                piecesActive.add(new Rocket(r,c));
            }else if(type.equals("Eraser")){
                piecesActive.add(new Eraser(r,c));
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
    public String checkGameOver(int player){
        if(patternOf5(player)){
            return "Player "+ player + " wins";
        }else if(shootersLocked()){
            return mostAreaCovered();
        }
        return null;
    }
    public boolean shootersLocked(){
        //not tested yet
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
        if(directionCount[0]==rows&directionCount[1]==rows&directionCount[2]==cols&directionCount[3]==cols){
            System.out.println("check shoot");
            return true;
        }
        return false;
        //check the counts of the stuff and if it matches the row and col count tmr, if all are locked return true
    }
    public String mostAreaCovered(){
        //check if the borders can shoot, once cant shoot game is over, checks who has painted the most

        int purpleCount = 0;
        int greenCount = 0;
        for (int i = 1; i < tiles.length-1; i++) {
            for (int j = 1; j <tiles[i].length-1; j++) {
                if(tiles[i][j].getValue()==1){
                    purpleCount++;
                }else if(tiles[i][j].getValue()==2){
                    greenCount++;
                }
            }
        }
        if(purpleCount>greenCount) {
            return "Player 1 wins";
        }else if(purpleCount<greenCount){
            return "Player 2 wins";
        }
        return "Tie";
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
        }else if(piecesActive.get(piece) instanceof Shadow){
            ((Shadow) piecesActive.get(piece)).convert(playerTurn,tiles);
        }else if(piecesActive.get(piece) instanceof Zombie) {
            if(((Zombie) piecesActive.get(piece)).infect(tiles)){

            };
        }
        //rocket trail temp value
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if(tiles[i][j].getValue()==5){
                    tiles[i][j].setValue(playerTurn);
                }
            }
        }
        if (tiles[piecesActive.get(piece).getCurrentRow()][piecesActive.get(piece).getCurrentCol()].getValue() == 0 & !(piecesActive.get(piece) instanceof Eraser)) {
            tiles[piecesActive.get(piece).getCurrentRow()][piecesActive.get(piece).getCurrentCol()].setValue(playerTurn);
        }
        if (tiles[piecesActive.get(piece).getCurrentRow()][piecesActive.get(piece).getCurrentCol()].getValue() < 4) {
            switchPlayer();
        }
        piecesActive.remove(piece);

    }
}
