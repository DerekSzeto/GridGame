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
    private int turnsUntilEndGame = 0;
    private int whoIsBuying = 1;
    private ArrayList<CapturePoint> capturePoints = new ArrayList<>();
    private boolean endGame = false;
    private boolean crossActive = false;
    private int crossTick = 0;
    private int activeChargePieces = 0;
    private boolean gameOver = false;
    public void incrementEndTurns(){
        turnsUntilEndGame++;
    }
    public int getEndGameTurns(){
        return turnsUntilEndGame;
    }

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

    public void shootPiece(int r, int c, String pieceName, int pieceIndex){
        if(tiles[r][c].getValue()==4) {
            if(pieceName.equals("Standard")) {
                piecesShot.add(0,new Piece(r, c));
            } else if(pieceName.equals("Paint")) {
                piecesShot.add(0,new Paint(r, c,1));
            }else if(pieceName.equals("Shadow")) {
                piecesShot.add(0,new Shadow(r,c));
            }else if(pieceName.equals("Rocket")){
                piecesShot.add(0,new Rocket(r,c));
            }else if(pieceName.equals("Eraser")){
                piecesShot.add(0,new Eraser(r,c));
            }else if(pieceName.equals("Zombie")){
                piecesShot.add(0,new Zombie(r,c,playerTurn));
            }else if(pieceName.equals("Cross")){
                piecesShot.add(0,new Cross(r,c));
                ((Cross) piecesShot.getFirst()).determineActivationPoints(tiles);
            }else if(pieceName.equals("Spiral")){
                piecesShot.add(0,new Spiral(r,c,2,playerTurn));
            } else if (pieceName.equals("Friction")) {
                piecesShot.add(0,new Friction(r,c,tiles.length));
            } else if (pieceName.equals("Refract")) {
                piecesShot.add(0,new Refract(r,c,playerTurn));
            }else if(pieceName.equals("Glitch")){
                piecesShot.add(0,new Glitch(r,c,playerTurn));
            }else if(pieceName.equals("Charge")){
                int chargeAmount = players[playerTurn - 1].getChargeLevel();

                if (chargeAmount == 0) {
                    chargeAmount = 1;
                }

                piecesShot.add(0, new Charge(r, c, playerTurn, chargeAmount));
                players[playerTurn - 1].resetCharge();
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
    public void earnCredit() {
        Player currentPlayer = players[playerTurn - 1];

        for (int i = 1; i < tiles.length - 1; i++) {
            int count = 0;

            for (int j = 1; j < tiles[i].length - 1; j++) {
                if (tiles[i][j].getValue() == playerTurn) {
                    count++;
                    if (count >= 2) {
                        currentPlayer.setCredits(currentPlayer.getCredits() + 1);
                    }
                } else {
                    count = 0;
                }
            }
        }
        for (int j = 1; j < tiles[0].length - 1; j++) {
            int count = 0;
            for (int i = 1; i < tiles.length - 1; i++) {
                if (tiles[i][j].getValue() == playerTurn) {
                    count++;
                    if (count >= 2) {
                        currentPlayer.setCredits(currentPlayer.getCredits() + 1);
                    }
                } else {
                    count = 0;
                }
            }
        }
    }
    public int checkGameOver() {
        if (endGame && allCapturePointsCaptured()) {
            gameOver = true;
            return mostCapturePoints();
        }

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
            for (int j = players[i].getPieces().size()-1; j >= 0; j--) {
                players[i].getPieces().remove(j);
                players[i].getStationCount().set(j,0);
                players[i].getArsenal().remove(j);
            }
            players[i].getPieces().add(0,"Standard");
            players[i].getArsenal().add(0,99);
            players[i].setCredits(0);
            players[i].resetCharge();
        }
        resetGameStuff();
    }
    public void resetGameStuff(){
        crossTick = 0;
        crossActive = false;
        stations.clear();
        turnsUntilEndGame = 0;
        activeChargePieces = 0;
        capturePoints.clear();
        endGame = false;
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

    public void useBomb(int bombIndex, int r, int c) {
        Player playerWhoBombed = players[playerTurn-1];
        playerWhoBombed.getPlayerBombs().get(bombIndex).explode(r,c,tiles,stations);

        playerWhoBombed.getPlayerBombs().remove(bombIndex);

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
    //after collisions
    public void stopPiece(int piece) {
        boolean setColor = false;
        boolean removeNow = false;
        if (piecesShot.get(piece).canCapturePoint()) {
            if (tiles[piecesShot.get(piece).getCurrentRow()][piecesShot.get(piece).getCurrentCol()].getValue() == 12) {
                capturePoint(piecesShot.get(piece).getCurrentRow(), piecesShot.get(piece).getCurrentCol(), playerTurn);
            }
        }
        if( piecesShot.get(piece) instanceof Paint){
            ((Paint) piecesShot.get(piece)).paintTiles(tiles, piecesShot.get(piece).getCurrentRow(), piecesShot.get(piece).getCurrentCol(),playerTurn);
        }else if(piecesShot.get(piece) instanceof Shadow){
            ((Shadow) piecesShot.get(piece)).convert(playerTurn,tiles);
        }else if(piecesShot.get(piece) instanceof Zombie){
            ((Zombie) piecesShot.get(piece)).action(tiles,stations);
        }else if(piecesShot.get(piece) instanceof Cross){
            if(((Cross) piecesShot.get(piece)).atDestination()){
                ((Cross) piecesShot.get(piece)).diagonals(tiles,playerTurn);
                crossTick = 4;
                crossActive = true;
            };
        }else if(piecesShot.get(piece) instanceof Glitch){
            ((Glitch) piecesShot.get(piece)).transpose(tiles);
        }else if (piecesShot.get(piece) instanceof Charge) {
                int piecesCreated = ((Charge) piecesShot.get(piece)).releaseChargePiece(tiles, piecesShot);
                activeChargePieces += piecesCreated;

                if (piecesCreated == 0) {
                    switchPlayer();
                }

                setColor = true;
        }else if (piecesShot.get(piece) instanceof Refract) {


            int collidedRow = ((Refract) piecesShot.get(piece)).getNewRow();
            int collidedCol = ((Refract) piecesShot.get(piece)).getNewCol();

            if (tiles[collidedRow][collidedCol].getValue() == ((Refract) piecesShot.get(piece)).getCheckingValue()) {
                ((Refract) piecesShot.get(piece)).refract(tiles, piecesShot);
            }
        }else if (piecesShot.get(piece) instanceof ManipulatedPiece) {
            if (tiles[(piecesShot.get(piece)).getCurrentRow()][(piecesShot.get(piece)).getCurrentCol()].getValue() == 0) {
                tiles[(piecesShot.get(piece)).getCurrentRow()][(piecesShot.get(piece)).getCurrentCol()].setValue(((ManipulatedPiece) piecesShot.get(piece)).getStoredValue());
            }

        }

        if(!setColor) {
            //rocket trail temp value
            rocketTrail();


            if (tiles[piecesShot.get(piece).getCurrentRow()][piecesShot.get(piece).getCurrentCol()].getValue() == 0 & !(piecesShot.get(piece) instanceof Eraser) & !(piecesShot.get(piece) instanceof Spiral)) {
                tiles[piecesShot.get(piece).getCurrentRow()][piecesShot.get(piece).getCurrentCol()].setValue(playerTurn);
            }

            inhalePieces();

            if ((tiles[piecesShot.get(piece).getCurrentRow()][piecesShot.get(piece).getCurrentCol()].getValue() < 4) | tiles[piecesShot.get(piece).getCurrentRow()][piecesShot.get(piece).getCurrentCol()].getValue() == 6
                    && !(piecesShot.get(piece) instanceof ManipulatedPiece) && !(piecesShot.get(piece) instanceof ChargedPiece)) {
                switchPlayer();
            }

        }

        piecesShot.remove(piece);

    }
    public boolean chargePiece() {
        Player currentPlayer = players[playerTurn - 1];

        if (!currentPlayer.getSelectedPiece().equals("Charge")) {
            return false;
        }

        int chargeIndex = currentPlayer.getPieces().indexOf("Charge");
        if (chargeIndex == -1) {
            return false;
        }

        if (currentPlayer.getArsenal().get(chargeIndex) <= 0) {
            return false;
        }

        currentPlayer.addCharge();
        switchPlayer();
        return true;
    }
    public void inhalePieces(){
        for (int i = 0; i < stations.size(); i++) {
            if(stations.get(i) instanceof Smoke){
                if(((Smoke)stations.get(i)).isSmokesActive()) {
                    ((Smoke) stations.get(i)).getTilesNearby(tiles);
                    ((Smoke) stations.get(i)).nextTurn(tiles);
                }
            }
        }
    }
    public void magnetize() {
        for (int i = 0; i < stations.size(); i++) {
            if(stations.get(i) instanceof Magnet){
               ((Magnet) stations.get(i)).manipulatePieces(tiles,piecesShot);
                ((Magnet) stations.get(i)).alternateModes();
            }
        }

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
    public boolean isEndGame(){
        return endGame;
    }
    public void spawnCapturePoints(){
        int capPoints = (int)(Math.pow(tiles.length,2)/tiles.length);
        for (int i = 0; i < capPoints; i++) {
            int row = (int)(Math.random()* (tiles.length-2) + 1);
            int col = (int)(Math.random()* (tiles.length-2) + 1);
            if(tiles[row][col].isSpecial()){
                i--;
            }else{
                capturePoints.add(new CapturePoint(row,col));
                tiles[row][col].setTileSpecial(true);
            }
        }
        changeToSpecialTiles();
    }
    public void changeToSpecialTiles(){
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if(tiles[i][j].isSpecial()){
                    tiles[i][j].setValue(12);
                }
            }
        }
    }
    public void spawnStation(int r, int c,String stationName){
        if(stationName.equals("Magnet")){
            stations.add(new Magnet(r,c,playerTurn,2,2,"repel"));
            tiles[r][c].setValue(7);
        }else if(stationName.equals("Void")){
            stations.add(new Void(r,c,playerTurn));
            ((Void)(stations.get(stations.size()-1))).deletion(tiles);
        }else if(stationName.equals("Smoke")){
            stations.add(new Smoke(r,c,playerTurn));
            ((Smoke)(stations.get(stations.size()-1))).dropSmokes(tiles);
        }
        int stationIndex = players[playerTurn-1].getPlayerStations().indexOf(stationName);
        players[playerTurn-1].getStationCount().set(stationIndex,players[playerTurn-1].getStationCount().get(stationIndex)-1);
        switchPlayer();
    }
    public void voiding(){
        int voidAction = (int)(Math.random()*2);
        if(voidAction == 0){
            voidDeflection();
        }else{
            voidDeletion();
        }
    }
    public void voidDeflection(){
        for (int i = 0; i < stations.size(); i++) {
            if(stations.get(i) instanceof Void){
                ((Void) stations.get(i)).deflect(piecesShot);
            }
        }
    }
    public void voidDeletion(){
        for (int i = 0; i < stations.size(); i++) {
            if(stations.get(i) instanceof Void){
                ((Void) stations.get(i)).deletion(tiles);
                ((Void) stations.get(i)).toggleStage();
            }
        }
    }
    public void decrementCrossTick(){
        if(crossActive) {
            if (crossTick > 0) {
                crossTick--;
            }else{
                crossActive = false;
                clearDiagonals();
            }
        }
    }
    public void clearDiagonals(){
        for (int i = 1; i < tiles.length-1 ; i++) {
            tiles[i][i].setValue(0);
        }

        int tileCount = 0;
        int row = 1;
        int col = tiles.length-2;
        while(tileCount<tiles.length-2){
            tiles[row][col].setValue(0);
            row++;
            col--;
            tileCount++;
        }
    }

    public void constructWall(int wallIndex, int r, int c) {
        Player currentPlayer = players[playerTurn-1];
        System.out.println("wall built");
        currentPlayer.getPlayerWalls().get(wallIndex).setStartingRow(r);
        currentPlayer.getPlayerWalls().get(wallIndex).setStartingCol(c);
        currentPlayer.getPlayerWalls().get(wallIndex).constructWall(tiles);

        currentPlayer.getPlayerWalls().remove(wallIndex);

        switchPlayer();
    }

    public void setEndGame(boolean b) {
        endGame = b;
    }
    public ArrayList<CapturePoint> getCapturePoints() {
        return capturePoints;
    }
    public CapturePoint getCapturePointAt(int row, int col) {
        for (int i = 0; i < capturePoints.size(); i++) {
            if (capturePoints.get(i).getRow() == row && capturePoints.get(i).getCol() == col) {
                return capturePoints.get(i);
            }
        }
        return null;
    }
    public void capturePoint(int row, int col, int player) {
        CapturePoint cp = getCapturePointAt(row, col);
        if (cp != null && !cp.isCaptured()) {
            cp.setOccupiedBy(player);
            tiles[row][col].setValue(player); // captured point now shows player color
            tiles[row][col].setTileSpecial(false);
        }
    }

    public boolean allCapturePointsCaptured() {
        if (capturePoints.isEmpty()) {
            return false;
        }

        for (int i = 0; i < capturePoints.size(); i++) {
            if (!capturePoints.get(i).isCaptured()) {
                return false;
            }
        }
        return true;
    }
    public int mostCapturePoints() {
        int player1Count = 0;
        int player2Count = 0;

        for (int i = 0; i < capturePoints.size(); i++) {
            if (capturePoints.get(i).getOccupiedBy() == 1) {
                player1Count++;
            } else if (capturePoints.get(i).getOccupiedBy() == 2) {
                player2Count++;
            }
        }

        if (player1Count > player2Count) {
            return 1;
        } else if (player2Count > player1Count) {
            return 2;
        }
        return 3;
    }
}

