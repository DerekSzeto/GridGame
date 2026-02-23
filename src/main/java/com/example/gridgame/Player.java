package com.example.gridgame;

import java.util.ArrayList;

public class Player {

    private ArrayList<String> pieces = new ArrayList<>();
    private ArrayList<Integer> arsenal = new ArrayList<>();

    private String selectedPiece = "Standard";

    private ArrayList<Bomb> bombs = new ArrayList<>();

    private int credits;

    private String name;
    public Player(String name){
        this.name = name;
        this.credits = 99;
        pieces.add("Standard");
        arsenal.add(99);
        //test
    }
    public void setSelectedPiece(int piece){
        selectedPiece = pieces.get(piece);

    }
    public String getSelectedPiece(){
        return selectedPiece;
    }
    public ArrayList<String> getPlayerPieces(){
        return pieces;
    }
    public ArrayList<Integer> getArsenal(){
        return arsenal;
    }
    public ArrayList<Bomb> getPlayerBombs(){
        return bombs;
    }
    public ArrayList<String> getPieces(){
        return pieces;
    }

    public int getCredits(){
        return credits;
    }
    public void setCredits(int num){
        credits = num;
    }
    public String getName(){
        return name;
    }

}
