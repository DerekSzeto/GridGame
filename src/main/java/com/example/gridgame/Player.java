package com.example.gridgame;

import java.util.ArrayList;

public class Player {
    private String[] pieceInfo = {};
    private ArrayList<String> pieces = new ArrayList<>();
    private String selectedPiece = "Standard";
    private ArrayList<Bomb> bombs = new ArrayList<>();
    private String name;
    public Player(String name){
        this.name = name;
        pieces.add("Standard");
        pieces.add("Paint");
        pieces.add("Shadow");
        pieces.add("Rocket");
        pieces.add("Eraser");
        pieces.add("Zombie");
        pieces.add("Spiral");
    }
    public void setSelectedPiece(int piece){
        selectedPiece = pieces.get(piece);

    }
    public String getSelectedPiece(){
        return selectedPiece;
    }
    public ArrayList<Bomb> getPlayerBombs(){
        return bombs;
    }
    public ArrayList<String> getPieces(){
        return pieces;
    }

}
