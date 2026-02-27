package com.example.gridgame;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class HelloController {

    //on tuesday add in another randomly moving robot
    //then inheritance

    public GridPane gpane;
    public Button startButton;
    public ChoiceBox<String> selectGridSize;
    public Label winnerLabel;
    public Label playerTurnLabel;
    public ListView playerPiecesListView;
    public Label pieceSelectedLabel;
    public Label clickableSelectedLabel;
    public Label pieceInfoLabel;
    public ListView playerStationsListView;
    public Button restartButton;
    public Button resetButton;
    public ListView selectPlayerBuyingListView;
    public ListView shopPiecesListView;
    public ListView shopClickablesListView;
    public ListView shopStationListView;
    public Label playerBuyingLabel;
    public Label purchaseLabel;
    public Button buyPieceButton;
    public Button buyClickableButton;
    public Button buyStationButton;
    public Label warningLabel;
    public Label stationSelectedLabel;
    public Label clickableInfoLabel;
    public Label stationInfoLabel;
    public ListView playerBombListView;
    public ListView wallOrientationListView;
    public ListView playerWallListView;
    public Label player1CreditLabel;
    public Label player2CreditLabel;
    public Button releasePieceButton;
    public Button chargePieceButton;

    private Button[][] btn;
    public void initialize(){
        //test get rid of this after

        selectGridSize.getItems().addAll("10","11","20","21");
        selectGridSize.setValue("10");
        wallOrientationListView.getItems().addAll("Horizontal","Vertical");

        game.addPlayers();

        for (int i = 0; i < game.getPlayers().length; i++) {
            selectPlayerBuyingListView.getItems().add(game.getPlayers()[i].getName() + " | Credit: " + game.getPlayers()[i].getCredits());
        }
        playerBuyingLabel.setText("Player 1 is Buying");
    }
    private Game game = new Game();

    public void updateShop(){
        selectPlayerBuyingListView.getItems().clear();
        shopPiecesListView.getItems().clear();
        shopClickablesListView.getItems().clear();
        shopStationListView.getItems().clear();
        purchaseLabel.setText("");

        for (int i = 0; i < game.getPlayers().length; i++) {
            selectPlayerBuyingListView.getItems().add(game.getPlayers()[i].getName() + " | Credit: " + game.getPlayers()[i].getCredits());
        }
        for (int i = 0; i < game.getShop().getPieceNames().length; i++) {
            shopPiecesListView.getItems().add(game.getShop().getPieceNames()[i] + " Piece");
        }
        for (int i = 0; i < game.getShop().getClickableNames().length; i++) {
            shopClickablesListView.getItems().add(game.getShop().getClickableNames()[i]);
        }
        for (int i = 0; i < game.getShop().getStationNames().length; i++) {
            shopStationListView.getItems().add(game.getShop().getStationNames()[i]);
        }
    }

    public void handleClick() {
        startButton.setVisible(false);

        int gridSize = Integer.parseInt(selectGridSize.getValue())+2;
        selectGridSize.setDisable(true);

        btn = new Button[gridSize][gridSize];
        game.createBoard(gridSize,gridSize);
        handleTimer();
        gpane.getChildren().clear();
        gpane.setGridLinesVisible(true);

        for (int i = 0; i < btn.length; i++) {
            for (int j = 0; j < btn[0].length; j++) {
                btn[i][j] = new Button(" ");
                gpane.add(btn[i][j], j, i);
                btn[i][j].setMinSize(37, 37);
                btn[i][j].setPrefSize(37, 37);
                btn[i][j].setMaxSize(37, 37);
            }
        }

        updateScreen();
        EventHandler<MouseEvent> z = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Button clicked = (Button) event.getSource();
                int row = GridPane.getRowIndex(clicked);
                int col = GridPane.getColumnIndex(clicked);
                System.out.println("(" + row + "," + col + ")" + " Value: "+ game.getTiles()[row][col].getValue());
                if(!game.isGameOver()) {
                    Player currentPlayer = game.getPlayers()[game.getPlayerTurn()-1];
                    int value = game.getTiles()[row][col].getValue();
                    if (event.getButton() == MouseButton.PRIMARY & value==4) {//shoot
                        if (game.isEndGame() && !currentPlayer.getSelectedPiece().equals("Standard")) {
                            warningLabel.setText("During sudden death, only Standard pieces can capture points.");
                        } else if(currentPlayer.getArsenal().get(currentPlayer.getPieces().indexOf(currentPlayer.getSelectedPiece()))>0) {

                            game.shootPiece(row, col, currentPlayer.getSelectedPiece(),
                                    currentPlayer.getPieces().indexOf(currentPlayer.getSelectedPiece()));

                           runGameEvents();
                        }else{
                            warningLabel.setText("You have no more of this piece");
                        }
                    } else if (event.getButton() == MouseButton.SECONDARY & value==0) { //clickables
                        //player selected bombs
                        if(playerBombListView.getSelectionModel().getSelectedIndex()>=0) {

                            if (!currentPlayer.getPlayerBombs().isEmpty()) {
                                game.useBomb(playerBombListView.getSelectionModel().getSelectedIndex(), row, col);
                                System.out.println("used bomb");
                            }
                        }else if(playerWallListView.getSelectionModel().getSelectedIndex()>=0) {
                            int selectedWall = playerWallListView.getSelectionModel().getSelectedIndex();
                            System.out.println(selectedWall);
                            wallOrientationListView.setDisable(true);
                            if(!currentPlayer.getPlayerWalls().isEmpty()) {
                                if(currentPlayer.getPlayerWalls().get(selectedWall).getOrientation()!=null) {
                                    game.constructWall(selectedWall,row,col);
                                }
                            }
                        }
                    } else if(event.getButton() == MouseButton.MIDDLE && value==0) {

                        if(currentPlayer.getStationCount().get(currentPlayer.getPlayerStations().indexOf(currentPlayer.getSelectedStation()))>0){
                            System.out.println("middle");
                            game.spawnStation(row,col,currentPlayer.getSelectedStation());
                            //actions in background
                            runGameEvents();
                        }else{
                            warningLabel.setText("You have no more of this piece");
                        }
                    }

                    updateScreen();
                }
            }
        };

        for (int i = 0; i < btn.length; i++) {
            for (int j = 0; j < btn[0].length; j++) {
                btn[i][j].setOnMouseClicked(z);   // same pattern as your setOnAction
            }
        }
    }

    //resets the gridlines
//        gpane.setGridLinesVisible(false);
//        gpane.setGridLinesVisible(true);
    public void runGameEvents(){
        game.magnetize();
        game.virus();
        game.decrementCrossTick();
        game.earnCredit();
        warningLabel.setText("");
        game.incrementEndTurns();
    }
    public void updateScreen(){
        //updates all tiles

        for (int i=0; i< btn.length;i++){
            for (int j=0; j< btn[0].length;j++){
                if (game.getTiles()[i][j].getValue() == 0){
                    btn[i][j].setStyle("-fx-background-color: white");
                } else if (game.getTiles()[i][j].getValue() == 4){
                    btn[i][j].setStyle("-fx-background-color: navy");
                }else if (game.getTiles()[i][j].getValue() == 4|game.getTiles()[i][j].getValue() == 2){
                    btn[i][j].setStyle("-fx-background-color: lime");
                }
                else if (game.getTiles()[i][j].getValue() == 4|game.getTiles()[i][j].getValue() == 1){
                    btn[i][j].setStyle("-fx-background-color: magenta");
                }else if(game.getTiles()[i][j].getValue() == 6){
                    btn[i][j].setStyle("-fx-background-color: olive"); // virus
                }else if(game.getTiles()[i][j].getValue() == 7){
                    btn[i][j].setStyle("-fx-background-color: goldenrod"); // Magnet
                }else if(game.getTiles()[i][j].getValue() == 9){
                    btn[i][j].setStyle("-fx-background-color: crimson"); // wall
                }else if(game.getTiles()[i][j].getValue() == 10){
                    btn[i][j].setStyle("-fx-background-color: black"); // void
                }else if(game.getTiles()[i][j].getValue() == 11){
                    btn[i][j].setStyle("-fx-background-color: grey"); // smoke
                }else if(game.getTiles()[i][j].getValue() == 12){
                    btn[i][j].setStyle("-fx-background-color: cyan"); // win condition
                }
            }
        }

        //does the motion
        for (Piece x : game.getPiecesShot()) {
            if (game.getPlayerTurn() == 1) {
                btn[x.getCurrentRow()][x.getCurrentCol()].setStyle("-fx-background-color: #cd0be3");
            } else {
                btn[x.getCurrentRow()][x.getCurrentCol()].setStyle("-fx-background-color: #33a109");
            }
        }


        updatePlayerListViews();

        updateShop();

        player1CreditLabel.setText("Player 1's Credits: " + game.getPlayers()[0].getCredits());
        player2CreditLabel.setText("Player 2's Credits: " + game.getPlayers()[1].getCredits());
        playerTurnLabel.setText("Player Turn: "+ game.getPlayerTurn());

        Player currentPlayer =  game.getPlayers()[game.getPlayerTurn()-1];
        if (currentPlayer.getSelectedPiece() == null) {
            pieceSelectedLabel.setText("Select a Piece");
        } else {
            pieceSelectedLabel.setText(currentPlayer.getSelectedPiece() + " is selected");
        }

        if (currentPlayer.getSelectedStation() != null) {
            stationSelectedLabel.setText(currentPlayer.getSelectedStation() + " is selected");
        }

        checkGameOver();
    }
    public void updatePlayerListViews(){
        playerPiecesListView.getItems().clear();
        playerBombListView.getItems().clear();
        playerWallListView.getItems().clear();
        playerStationsListView.getItems().clear();
        Player currentPlayer = game.getPlayers()[game.getPlayerTurn()-1];
        for (int j = 0; j < currentPlayer.getPieces().size(); j++) {
            playerPiecesListView.getItems().add(currentPlayer.getPieces().get(j) + " | Left: "+ currentPlayer.getArsenal().get(j));

        }
        for (int i = 0; i < currentPlayer.getPlayerBombs().size(); i++) {
            if(currentPlayer.getPlayerBombs().get(i).getRadius()==1){
                playerBombListView.getItems().add("Grenade: "+currentPlayer.getPlayerBombs().get(i).getRadius());
            }else{
                playerBombListView.getItems().add("Bomb: "+currentPlayer.getPlayerBombs().get(i).getRadius());
            }
        }
        for (int i = 0; i < currentPlayer.getPlayerWalls().size(); i++) {
            if(currentPlayer.getPlayerWalls().get(i).getOrientation()==null) {
                playerWallListView.getItems().add("Wall: " + currentPlayer.getPlayerWalls().get(i).getLength());

            }else{
                playerWallListView.getItems().add("Wall: " +currentPlayer.getPlayerWalls().get(i).getLength() + currentPlayer.getPlayerWalls().get(i).getOrientation());

            }
        }
        for (int i = 0; i < currentPlayer.getPlayerStations().size(); i++) {
            playerStationsListView.getItems().add(currentPlayer.getPlayerStations().get(i) + " | Left: "+ currentPlayer.getStationCount().get(i));
        }

    }
    public void checkGameOver(){
        int player = game.checkGameOver();
        if(player!=0) {
            restartButton.setDisable(false);
            selectGridSize.setDisable(false);
            winnerLabel.setText("Player " + player + " wins");
            if (player == 3) {
                winnerLabel.setText("Tie");
            }
        }
    }
    public void handleTimer() {
        new AnimationTimer(){

            @Override
            public void handle(long l) {
                if(!game.isGameOver()){
                    for (int i = 0; i < game.getPiecesShot().size(); i++) {
                        if (game.getPiecesShot().get(i).canMove()) {
                            game.getPiecesShot().get(i).move(game.getTiles());
                            game.voiding();
                        } else {
                            game.stopPiece(i);
                            endGame();
                        }
                        updateScreen();
                    }
                }
            }
        }.start();

    }

    public void selectPiece(MouseEvent mouseEvent) {
        game.getPlayers()[game.getPlayerTurn()-1].setSelectedPiece(playerPiecesListView.getSelectionModel().getSelectedIndex());
        updateScreen();
    }


    public void restartGame(ActionEvent actionEvent) {
        restartButton.setDisable(true);
        winnerLabel.setText("");
        game.setGameOver(false);

        game.resetPlayerInventory();
        updatePlayerListViews();
        resetGrid(actionEvent);
    }

    public void resetGrid(ActionEvent actionEvent) {
        for (int i = 0; i < btn.length; i++) {
            for (int j = 0; j < btn[0].length; j++) {
                gpane.getChildren().remove(btn[i][j]);
            }
        }

        handleClick();
    }

    public void buyPiece(ActionEvent actionEvent) {
        int pieceBuying = shopPiecesListView.getSelectionModel().getSelectedIndex();
        if(game.getShop().buyPiece(pieceBuying,game.getPlayers()[game.getWhoIsBuying()-1])){
            purchaseLabel.setText("Successful Purchase");
        }else{
            purchaseLabel.setText("Insufficient Funds");
        }
        disableShopButtons();
        clearSelection();
    }

    public void selectPlayerBuying(MouseEvent mouseEvent) {
        int player = selectPlayerBuyingListView.getSelectionModel().getSelectedIndex();
        game.selectWhoIsBuying(player+1);
        playerBuyingLabel.setText("Player " + game.getWhoIsBuying() + " is buying");
    }

    public void selectPieceBuying(MouseEvent mouseEvent) {
        disableShopButtons();
        buyPieceButton.setDisable(false);
        int pieceBuying = shopPiecesListView.getSelectionModel().getSelectedIndex();
        buyPieceButton.setVisible(true);
        buyPieceButton.setText("Buy "+ game.getShop().getPieceNames()[pieceBuying] + " for " + game.getShop().getPiecePrices()[pieceBuying] + " Credits?");
        pieceInfoLabel.setText(game.getShop().getPieceInfo()[pieceBuying]);

    }

    public void selectStation(MouseEvent mouseEvent) {
        game.getPlayers()[game.getPlayerTurn()-1].setSelectedStation(playerStationsListView.getSelectionModel().getSelectedIndex());
        updateScreen();
    }


    public void buyClickable(ActionEvent actionEvent) {
        int clickBuying = shopClickablesListView.getSelectionModel().getSelectedIndex();
        if(game.getShop().buyClickable(clickBuying,game.getPlayers()[game.getWhoIsBuying()-1])){
            purchaseLabel.setText("Successful Purchase");
        }else{
            purchaseLabel.setText("Insufficient Funds");
        }
        disableShopButtons();
        clearSelection();
    }

    public void buyStation(ActionEvent actionEvent) {
        int stationBuying = shopStationListView.getSelectionModel().getSelectedIndex();
        if(game.getShop().buyStation(stationBuying,game.getPlayers()[game.getWhoIsBuying()-1])){
            purchaseLabel.setText("Successful Purchase");
        }else{
            purchaseLabel.setText("Insufficient Funds");
        }
        disableShopButtons();
        clearSelection();
    }

    public void selectClickableBuying(MouseEvent mouseEvent) {
        disableShopButtons();
        buyClickableButton.setDisable(false);
        int clickBuying = shopClickablesListView.getSelectionModel().getSelectedIndex();

        buyClickableButton.setVisible(true);
        buyClickableButton.setText("Buy "+ game.getShop().getClickableNames()[clickBuying] + " for " + game.getShop().getClickablePrices()[clickBuying] + " Credits?");
        clickableInfoLabel.setText(game.getShop().getClickableInfo()[clickBuying]);
    }

    public void selectStationBuying(MouseEvent mouseEvent) {
        disableShopButtons();
        buyStationButton.setDisable(false);
        int stationBuying = shopStationListView.getSelectionModel().getSelectedIndex();
        buyStationButton.setVisible(true);
        buyStationButton.setText("Buy "+ game.getShop().getStationNames()[stationBuying] + " for " + game.getShop().getStationPrices()[stationBuying] + " Credits?");
        stationInfoLabel.setText(game.getShop().getStationInfo()[stationBuying]);

    }
    public void disableShopButtons() {
        buyStationButton.setDisable(true);
        buyPieceButton.setDisable(true);
        buyClickableButton.setDisable(true);

        updatePlayerListViews();

    }
    public void clearSelection(){
        shopPiecesListView.getSelectionModel().clearSelection();
        shopClickablesListView.getSelectionModel().clearSelection();
        shopStationListView.getSelectionModel().clearSelection();
        stationInfoLabel.setText("");
        clickableInfoLabel.setText("");
        pieceInfoLabel.setText("");
    }

    public void selectOrientation(MouseEvent mouseEvent) {
        Player currentPlayer = game.getPlayers()[game.getPlayerTurn()-1];
        String orientation = wallOrientationListView.getSelectionModel().getSelectedItem().toString().substring(0,1);

        for (int i = 0; i < currentPlayer.getPlayerWalls().size(); i++) {
            currentPlayer.getPlayerWalls().get(i).setOrientation(orientation);
        }
        updatePlayerListViews();
        wallOrientationListView.setDisable(true);
    }

    public void chargePiece(ActionEvent actionEvent) {
    }

    public void releasePiece(ActionEvent actionEvent) {
    }

    public void selectWall(MouseEvent mouseEvent) {
        System.out.println(playerWallListView.getSelectionModel().getSelectedIndex());
        wallOrientationListView.setDisable(false);
    }
    public void endGame(){
        if (!game.isEndGame()) {
            if (game.getEndGameTurns() >= 30) {
                game.setEndGame(true);
                resetGrid(new ActionEvent());
                game.spawnCapturePoints();
                shopStationListView.setDisable(true);
                shopPiecesListView.setDisable(true);
                shopClickablesListView.setDisable(true);
                playerBombListView.setDisable(true);
                playerStationsListView.setDisable(true);
                updateScreen();
                winnerLabel.setText("SUDDEN DEATH: Capture The Most Highlighted Areas to Win");
            }
        }
    }
}
