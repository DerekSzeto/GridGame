package com.example.gridgame;

public class Player {
    private boolean[] availablePieces = {true,true};
    private boolean[] selectedPiece = {true,false};
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
    }
}
