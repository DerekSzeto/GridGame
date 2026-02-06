package com.example.gridgame;

import java.util.ArrayList;

public class Player {
//    private boolean[] availablePieces = {true,true};
    private boolean[] selectedPiece = {true,false};
    private ArrayList<Bomb> bombs = new ArrayList<>();
    private String name;
    public Player(String name){

    }
    public void unselectAllPieces(){
        for (int i = 0; i <selectedPiece.length; i++) {
            selectedPiece[i] = false;
        }
    }
    public void setSelectedPiece(int piece){
        unselectAllPieces();
        selectedPiece[piece] = true;
    }
    public int getSelectedPiece(){
        for (int i = 0; i < selectedPiece.length; i++) {
            if(selectedPiece[i]){
                return i;
            }
        }
        return 0;
    }
    public ArrayList<Bomb> getPlayerBombs(){
        return bombs;
    }

}
