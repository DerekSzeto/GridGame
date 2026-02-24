package com.example.gridgame;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import javax.swing.*;
import java.awt.*;

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
    public ListView playerClickableListView;
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

    private Button[][] btn;
    public void initialize(){
        //test get rid of this after

        selectGridSize.getItems().addAll("10","11","24","25");
        selectGridSize.setValue("10");


        game.addPlayers();

        for (int i = 0; i < game.getPlayers().length; i++) {
            selectPlayerBuyingListView.getItems().add(game.getPlayers()[i].getName() + " | Credit: " + game.getPlayers()[i].getCredits());
        }

        game.getPlayers()[0].getPlayerBombs().add(new Bomb(1));
        game.getPlayers()[1].getPlayerBombs().add(new Bomb(2));
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
                btn[i][j].setMinSize(40, 40);
                btn[i][j].setPrefSize(40, 40);
                btn[i][j].setMaxSize(40, 40);
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
                        if(currentPlayer.getArsenal().get(currentPlayer.getPieces().indexOf(currentPlayer.getSelectedPiece()))>0) {
                            game.shootPiece(row, col, currentPlayer.getSelectedPiece(),
                                    currentPlayer.getPieces().indexOf(currentPlayer.getSelectedPiece()));

                            game.activateStations();
                        }else{
                            warningLabel.setText("You have no more of this piece");
                        }
                    } else if (event.getButton() == MouseButton.SECONDARY & value==0) { //clickables
                        if (currentPlayer.getPlayerBombs().size() >= 1) {
                            game.useBomb(game.getPlayerTurn() - 1, row, col);
                            System.out.println("used bomb");
                        }
                    } else if(event.getButton() == MouseButton.MIDDLE && value==0) {
                        if(currentPlayer.getStationCount().get(currentPlayer.getPlayerStations().indexOf(currentPlayer.getSelectedStation()))>0){
                            System.out.println("middle");
                            game.spawnStation(row,col,currentPlayer.getSelectedStation());

                            game.activateStations();
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
        playerClickableListView.getItems().clear();
        playerStationsListView.getItems().clear();
        Player currentPlayer = game.getPlayers()[game.getPlayerTurn()-1];
        for (int j = 0; j < currentPlayer.getPieces().size(); j++) {
            playerPiecesListView.getItems().add(currentPlayer.getPieces().get(j) + " | Left: "+ currentPlayer.getArsenal().get(j));

        }
        for (int i = 0; i < currentPlayer.getPlayerBombs().size(); i++) {
            if(currentPlayer.getPlayerBombs().get(i).getRadius()==1){
                playerClickableListView.getItems().add("Grenade: "+currentPlayer.getPlayerBombs().get(i).getRadius());
            }else{
                playerClickableListView.getItems().add("Bomb: "+currentPlayer.getPlayerBombs().get(i).getRadius());
            }
        }
        for (int i = 0; i < currentPlayer.getPlayerWalls().size(); i++) {
            playerClickableListView.getItems().add("Wall: " +currentPlayer.getPlayerWalls().get(i).getLength());
        }
        for (int i = 0; i < currentPlayer.getPlayerStations().size(); i++) {
            playerStationsListView.getItems().add(currentPlayer.getPlayerStations().get(i) + " | Left: "+ currentPlayer.getStationCount().get(i));
        }

    }
    public void checkGameOver(){
        int player = game.checkGameOver();
        if(player!=0) {
            restartButton.setVisible(true);
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

                        } else {
                            game.stopPiece(i);
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
        restartButton.setVisible(false);
        winnerLabel.setText("");
        game.setGameOver(false);

        game.resetPlayerInventory();
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

    }

    public void selectStation(MouseEvent mouseEvent) {
        game.getPlayers()[game.getPlayerTurn()-1].setSelectedStation(playerStationsListView.getSelectionModel().getSelectedIndex());
        updateScreen();
    }

    public void selectClickable(MouseEvent mouseEvent) {
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

    }

    public void selectStationBuying(MouseEvent mouseEvent) {
        disableShopButtons();
        buyStationButton.setDisable(false);
        int stationBuying = shopStationListView.getSelectionModel().getSelectedIndex();
        buyStationButton.setVisible(true);
        buyStationButton.setText("Buy "+ game.getShop().getStationNames()[stationBuying] + " for " + game.getShop().getStationPrices()[stationBuying] + " Credits?");
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
    }

    public void selectOrientation(MouseEvent mouseEvent) {

    }
}
