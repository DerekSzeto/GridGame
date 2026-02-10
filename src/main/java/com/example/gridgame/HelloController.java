package com.example.gridgame;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class HelloController {

    //on tuesday add in another randomly moving robot
    //then inheritance

    public GridPane gpane;
    public Button startButton;
    public TextField dimensionsTextField;
    public Label winnerLabel;
    public Label playerTurnLabel;
    public ListView playerPiecesListView;
    public Label pieceSelectedLabel;
    public ListView playerClickableListView;
    public Label clickableSelectedLabel;
    private Button[][] btn;
    public void initialize(){
        game.addPlayers();
        game.getPlayers()[0].getPlayerBombs().add(new Bomb(1));
        game.getPlayers()[1].getPlayerBombs().add(new Bomb(2));
    }
    private Game game = new Game();

    public void handleClick(ActionEvent actionEvent) {
        int x = Integer.parseInt(dimensionsTextField.getText().substring(0,dimensionsTextField.getText().indexOf(",")));
        int y = Integer.parseInt(dimensionsTextField.getText().substring(dimensionsTextField.getText().indexOf(",")+1));
        btn = new Button[x][y];

        game.createBoard(x, y);
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
                System.out.println(event.getSource());

                Button clicked = (Button) event.getSource();
                int row = GridPane.getRowIndex(clicked);
                int col = GridPane.getColumnIndex(clicked);
                System.out.println("(" + row + "," + col + ")" + " Value: "+ game.getTiles()[row][col].getValue());

                if (event.getButton() == MouseButton.PRIMARY & !game.isGameOver()) { //shoot
                    game.shootPiece(row, col, game.getPlayers()[game.getPlayerTurn()-1].getSelectedPiece());
                } else if (event.getButton() == MouseButton.SECONDARY) { //clickables
                    if(game.getPlayers()[game.getPlayerTurn()-1].getPlayerBombs().size()>=1){
                        game.useBomb(game.getPlayerTurn()-1, row, col);
                        System.out.println("used bomb");
                    }
                }

                updateScreen();
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
                } else if (game.getTiles()[i][j].getValue() == 4|game.getTiles()[i][j].getValue() == 5){
                    btn[i][j].setStyle("-fx-background-color: navy");
                }else if (game.getTiles()[i][j].getValue() == 4|game.getTiles()[i][j].getValue() == 2){
                    btn[i][j].setStyle("-fx-background-color: lime");
                }
                else if (game.getTiles()[i][j].getValue() == 4|game.getTiles()[i][j].getValue() == 1){
                    btn[i][j].setStyle("-fx-background-color: magenta");
                }
            }
        }

        //does the motion
        for (Piece x : game.getPiecesActive()) {
            if (game.getPlayerTurn() == 1) {
                btn[x.getCurrentRow()][x.getCurrentCol()].setStyle("-fx-background-color: #cd0be3");
            } else {
                btn[x.getCurrentRow()][x.getCurrentCol()].setStyle("-fx-background-color: #33a109");
            }
        }


        updatePlayerListViews();

        playerTurnLabel.setText("Player Turn: "+ game.getPlayerTurn());
        if(game.getPlayers()[game.getPlayerTurn()-1].getSelectedPiece().equals(null)){
            pieceSelectedLabel.setText("Select a Piece");
        }else {
            pieceSelectedLabel.setText(game.getPlayers()[game.getPlayerTurn() - 1].getSelectedPiece() + " is selected");
        }

        checkGameOver();
    }
    public void updatePlayerListViews(){
        playerPiecesListView.getItems().clear();
        playerClickableListView.getItems().clear();

        for (int j = 0; j < game.getPlayers()[game.getPlayerTurn()-1].getPieces().size(); j++) {
            playerPiecesListView.getItems().add(game.getPlayers()[game.getPlayerTurn()-1].getPieces().get(j));
        }
        for (int i = 0; i < game.getPlayers()[game.getPlayerTurn()-1].getPlayerBombs().size(); i++) {
            playerClickableListView.getItems().add("Bomb: "+game.getPlayers()[game.getPlayerTurn()-1].getPlayerBombs().get(i).getRadius());
        }

    }
    public void checkGameOver(){
        for (int i = 1; i < 3; i++) {
           if(game.checkGameOver(i)){
               winnerLabel.setText("Player "+ i + " won");
               break;
           };
        }
    }
    public void handleTimer() {
        new AnimationTimer(){

            @Override
            public void handle(long l) {
                if(!game.isGameOver()){
                    for (int i = 0; i < game.getPiecesActive().size(); i++) {
                        if (game.getPiecesActive().get(i).canMove()) {
                            game.getPiecesActive().get(i).move(game.getTiles());

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
        if(playerPiecesListView.getSelectionModel().getSelectedIndex()==0){

        }
        game.getPlayers()[game.getPlayerTurn()-1].setSelectedPiece(playerPiecesListView.getSelectionModel().getSelectedIndex());
        updateScreen();

    }
}
