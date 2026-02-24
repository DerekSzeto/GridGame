package com.example.gridgame;

import java.util.ArrayList;

public class Player {

    private ArrayList<String> pieces = new ArrayList<>();
    private ArrayList<Integer> arsenal = new ArrayList<>();

    private String selectedPiece = "Standard";
    private String selectedStation;

    private String selectedClickable;
    private ArrayList<Bomb> bombs = new ArrayList<>();
    private ArrayList<Wall> walls = new ArrayList<>();

    private ArrayList<String> stations = new ArrayList<>();
    private ArrayList<Integer> stationCount = new ArrayList<>();

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
    public void setSelectedClickable(int clickable){

    }
    public void setSelectedStation(int station){
        selectedStation = stations.get(station);
    }
    public String getSelectedStation(){
        return selectedStation;
    }
    public String getSelectedClickable(){
        return selectedClickable;
    }
    public String getSelectedPiece(){
        return selectedPiece;
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
    public ArrayList<String> getPlayerStations(){
        return stations;
    }
    public ArrayList<Integer> getStationCount(){
        return stationCount;
    }

    public ArrayList<Wall> getPlayerWalls() {
        return walls;
    }
}
