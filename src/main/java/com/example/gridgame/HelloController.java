package com.example.gridgame;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class HelloController {

    //on tuesday add in another randomly moving robot
    //then inheritance

    public Button btnClick;
    public Label lblDisplay;
    public TextField txtInput;
    public GridPane gpane;
    public TextField userCoord;
    private Button[][] btn;

    private Game game = new Game();

    //get two bots on the screen solving the maze
    // 1. have them both go at same time 2. one at a time
    // see which accomplished the task in the least amount of moves


    public void handleClick(ActionEvent actionEvent) {
//        int x = (int)(Math.random()*20);
//        int y = (int)(Math.random()*20);
        int x= 10;
        int y= 10;
        btn = new Button[x][y];

        game.createBoard(x,y);
        game.makeRobots();
        gpane.getChildren().clear();
        gpane.setGridLinesVisible(true);
        for (int i = 0; i < btn.length; i++) {
            for (int j = 0; j < btn[0].length; j++) {
                btn[i][j] = new Button("A");
                gpane.add(btn[i][j], j, i);
                btn[i][j].setMinSize(40, 40);
                btn[i][j].setPrefSize(40, 40);
                btn[i][j].setMaxSize(40, 40);
            }
        }

        blank();
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
                } else if (event.getButton() == MouseButton.SECONDARY) {
                    System.out.println("RIGHT click");
                }

                btn[row][col].setText("clicked");
                blank();
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
    }
    public void blank(){

        for (int i=0; i< btn.length;i++){
            for (int j=0; j< btn[0].length;j++){
                if (game.getTiles()[i][j].getValue() == 0){
                    btn[i][j].setStyle("-fx-background-color: white");
                }  else if (game.getTiles()[i][j].getValue() == 1){
                    btn[i][j].setStyle("-fx-background-color: red");
                }  else if (game.getTiles()[i][j].getValue() == 2){
                    btn[i][j].setStyle("-fx-background-color: blue");
                }
                else if (game.getTiles()[i][j].getValue() == 3){
                    btn[i][j].setStyle("-fx-background-color: yellow");
                } else if (game.getTiles()[i][j].getValue() == 4){
                    btn[i][j].setStyle("-fx-background-color: orange");
                }
            }
        }

//        btn[game.getAllBots().getCurrentRow()][game.getBot().getCurrenCol()].setStyle("-fx-background-color: #1aff00");

        for (Robot x: game.getAllBots()){
            btn[x.getCurrentRow()][x.getCurrenCol()].setStyle("-fx-background-color: #22ff00");
        }



    }

    public void handleRedButton(ActionEvent actionEvent) {
        game.getTiles()[0][0].setValue(1);
        blank();
    }

    public void handleYellowButton(ActionEvent actionEvent) {
        String userInput = userCoord.getText();
        int comma = userInput.indexOf(',');
        int row = Integer.parseInt(userInput.substring(0,comma));
        int col = Integer.parseInt(userInput.substring(comma+1));

        game.getTiles()[row][col].setValue(3);
        blank();
    }

    public void handleMoveRight(ActionEvent actionEvent) {
        blank();
    }

    public void handleTimer(ActionEvent actionEvent) {
        new AnimationTimer(){

            @Override
            public void handle(long l) {
                for (int i = 0; i<game.getAllBots().size(); i++){
                    if ( game.getAllBots().get(i).canMove()){
                        game.getAllBots().get(i).move(game.getTiles());
                    }
                }

                blank();

            }
        }.start();

    }

}