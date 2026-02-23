package com.example.gridgame;

import java.util.ArrayList;

public class Game {
    private Tile[][] tiles;
    private int playerTurn = (int)(Math.random()*2+1);
    private ArrayList<Piece> piecesShot = new ArrayList<>();
    private ArrayList<Station> stations = new ArrayList<>();
    private Player player1 = new Player("boi1");
    private Player player2 = new Player("boi2");
    private Player[] players = {player1, player2};
    private Shop shop = new Shop(players);
    private int whoIsBuying;
    private boolean gameOver = false;

    public void selectWhoIsBuying(int player){
        whoIsBuying = player;
    }
    public int getWhoIsBuying(){
        return whoIsBuying;
    }
    public Shop getShop(){
        return shop;
    }
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

    public void shootPiece(int r, int c, String type, int pieceIndex){
        if(tiles[r][c].getValue()==4) {
            if(type.equals("Standard")) {
                piecesShot.add(new Piece(r, c));
            } else if(type.equals("Paint")) {
                piecesShot.add(0,new Paint(r, c,1));
            }else if(type.equals("Shadow")) {
                piecesShot.add(0,new Shadow(r,c));
            }else if(type.equals("Rocket")){
                piecesShot.add(0,new Rocket(r,c));
            }else if(type.equals("Eraser")){
                piecesShot.add(0,new Eraser(r,c));
            }else if(type.equals("Zombie")){
                piecesShot.add(0,new Zombie(r,c,playerTurn));
            }else if(type.equals("Cross")){
                piecesShot.add(0,new Cross(r,c,"color"));
                ((Cross) piecesShot.getFirst()).determineActivationPoints(tiles);
            }else if(type.equals("Spiral")){
                piecesShot.add(0,new Spiral(r,c,2,playerTurn));
            }
            piecesShot.getFirst().setInitialDirection(r,c,tiles);
            players[playerTurn-1].getArsenal().set(pieceIndex,players[playerTurn-1].getArsenal().get(pieceIndex)-1);
        }
    }
    public ArrayList<Piece> getPiecesShot(){
        return piecesShot;
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
    public int checkGameOver(){
        for (int i = 1; i < 3; i++) {
            if (patternOf5(i)) {
                System.out.println("game over");
                return i;
            } else if (shootersLocked()) {
                return mostAreaCovered();
            }
        }
        return 0;
    }
    public void resetPlayerInventory(){
        for (int i = 0; i < players.length; i++) {
            for (int j = players[i].getPieces().size(); j > 0; j--) {
                players[i].getPieces().remove(j);
                players[i].getArsenal().remove(j);

            }
            players[i].getArsenal().set(0,99);
        }
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
    public int mostAreaCovered(){
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
            return 1;
        }else if(purpleCount<greenCount){
            return 2;
        }
        return 3;
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
    public void rocketTrail(){
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if(tiles[i][j].getValue()==5){
                    tiles[i][j].setValue(playerTurn);
                }
            }
        }
    }
    public void stopPiece(int piece) {
        if( piecesShot.get(piece) instanceof Paint){
            ((Paint) piecesShot.get(piece)).paintTiles(tiles, piecesShot.get(piece).getCurrentRow(), piecesShot.get(piece).getCurrentCol(),playerTurn);
        }else if(piecesShot.get(piece) instanceof Shadow){
            ((Shadow) piecesShot.get(piece)).convert(playerTurn,tiles);
        }else if(piecesShot.get(piece) instanceof Zombie){
            ((Zombie) piecesShot.get(piece)).action(tiles,stations);
        }else if(piecesShot.get(piece) instanceof Cross){
            if(((Cross) piecesShot.get(piece)).atDestination()){
                ((Cross) piecesShot.get(piece)).diagonals(tiles,playerTurn);
            };
        }


        //rocket trail temp value
        rocketTrail();

        if (tiles[piecesShot.get(piece).getCurrentRow()][piecesShot.get(piece).getCurrentCol()].getValue() == 0 & !(piecesShot.get(piece) instanceof Eraser) & !(piecesShot.get(piece) instanceof Spiral)) {
            tiles[piecesShot.get(piece).getCurrentRow()][piecesShot.get(piece).getCurrentCol()].setValue(playerTurn);
        }

        if ((tiles[piecesShot.get(piece).getCurrentRow()][piecesShot.get(piece).getCurrentCol()].getValue() < 4 )|tiles[piecesShot.get(piece).getCurrentRow()][piecesShot.get(piece).getCurrentCol()].getValue() == 6){
            switchPlayer();
        }

        piecesShot.remove(piece);


    }
    public void activateStations() {
        virus();
    }
    public void virus(){
        int virusCount = 0;
        for (int i = 0; i < stations.size(); i++) {
            if(stations.get(i) instanceof Virus){
                virusCount++;
            }
        }

        for (int i = 0; i < virusCount; i++) {
            if(stations.get(i) instanceof Virus) {
                ((Virus) stations.get(i)).action(tiles, stations);
            }
        }
    }
    public ArrayList<Station> getStations() {
        return stations;
    }
    public void determineSpecialCond(){

    }
    public void spawnCapturePoints(){

    }
}
