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
    public ListView player1PiecesListView;
    public Label pieceSelectedLabel;
    private Button[][] btn;
    public void initialize(){
        player1PiecesListView.getItems().add("Standard Piece");
        player1PiecesListView.getItems().add("Bomb");

    }
    private Game game = new Game();
    private Player player1 = new Player("boi");
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
                System.out.println("(" + row + "," + col + ")");

                if (event.getButton() == MouseButton.PRIMARY & !game.isGameOver()) {
                    game.addStandardPiece(row,col);
                } else if (event.getButton() == MouseButton.SECONDARY) {

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

        for (int i=0; i< btn.length;i++){
            for (int j=0; j< btn[0].length;j++){
                if (game.getTiles()[i][j].getValue() == 0){
                    btn[i][j].setStyle("-fx-background-color: white");
                } else if (game.getTiles()[i][j].getValue() == 4|game.getTiles()[i][j].getValue() == 5){
                    btn[i][j].setStyle("-fx-background-color: navy");
                }
            }
        }

        for (Piece x: game.getPiecesActive()){
            if(game.getPlayerTurn()==1) {
                btn[x.getCurrentRow()][x.getCurrentCol()].setStyle("-fx-background-color: #cd0be3");
            }else{
                btn[x.getCurrentRow()][x.getCurrentCol()].setStyle("-fx-background-color: #41e30b");
            }
        }
        playerTurnLabel.setText("Player Turn: "+ game.getPlayerTurn());
        checkGameOver();
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
                            if (game.getTiles()[game.getPiecesActive().get(i).getCurrentRow()][game.getPiecesActive().get(i).getCurrentCol()].getValue() == 0) {
                                game.getTiles()[game.getPiecesActive().get(i).getCurrentRow()][game.getPiecesActive().get(i).getCurrentCol()].setValue(game.getPlayerTurn());
                            }
                            if (game.getTiles()[game.getPiecesActive().get(i).getCurrentRow()][game.getPiecesActive().get(i).getCurrentCol()].getValue() < 4) {
                                game.switchPlayer();
                            } else {
                                System.out.println("cannot shoot");
                            }
                            game.getPiecesActive().remove(i);


                        }
                        updateScreen();
                    }
                }
            }
        }.start();

    }

    public void selectPiece(MouseEvent mouseEvent) {
        if(player1PiecesListView.getSelectionModel().getSelectedIndex()==0){
            pieceSelectedLabel.setText("Standard Piece");
        }else if(player1PiecesListView.getSelectionModel().getSelectedIndex()==1){
            pieceSelectedLabel.setText("Bomb");
        }
        player1.setSelectedPiece(player1PiecesListView.getSelectionModel().getSelectedIndex());

    }
}
