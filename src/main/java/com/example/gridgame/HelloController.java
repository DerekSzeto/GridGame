package com.example.gridgame;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class HelloController {

    //on tuesday add in another randomly moving robot
    //then inheritance

    public GridPane gpane;
    public Button startButton;
    private Button[][] btn;

    private Game game = new Game();
    public void handleClick(ActionEvent actionEvent) {
        int x = 12;
        int y = 12;
        btn = new Button[x][y];

        game.createBoard(x, y);
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

                if (event.getButton() == MouseButton.PRIMARY) {
                    System.out.println("LEFT click");
                    game.addStandardPiece(row,col);
                    handleTimer();
                } else if (event.getButton() == MouseButton.SECONDARY) {
                    System.out.println("RIGHT click");
                }

                btn[row][col].setText("clicked");
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
                }  else if (game.getTiles()[i][j].getValue() == 1){
                    btn[i][j].setStyle("-fx-background-color: red");
                }  else if (game.getTiles()[i][j].getValue() == 2){
                    btn[i][j].setStyle("-fx-background-color: blue");
                } else if (game.getTiles()[i][j].getValue() == 4|game.getTiles()[i][j].getValue() == 5){
                    btn[i][j].setStyle("-fx-background-color: goldenrod");
                }
            }
        }

//        btn[game.getAllBots().getCurrentRow()][game.getBot().getCurrenCol()].setStyle("-fx-background-color: #1aff00");

        for (Piece x: game.getPiecesActive()){
            btn[x.getCurrentRow()][x.getCurrenCol()].setStyle("-fx-background-color: #22ff00");
        }



    }
    public void handleTimer() {
        new AnimationTimer(){

            @Override
            public void handle(long l) {
                for (int i = 0; i<game.getPiecesActive().size(); i++){
                    if (game.getPiecesActive().get(i).canMove()){
                        game.getPiecesActive().get(i).move(game.getTiles());
                        System.out.println("move");
                        System.out.println(game.getPiecesActive().get(i).getCurrentRow()+" "+game.getPiecesActive().get(i).getCurrenCol());

                    }else{
                        game.getPiecesActive().clear();
                        System.out.println("stop");
                        System.out.println(game.getPiecesActive().get(i).getCurrentRow()+" "+game.getPiecesActive().get(i).getCurrenCol());
                    }
                    updateScreen();
                }


            }
        }.start();

    }

}
