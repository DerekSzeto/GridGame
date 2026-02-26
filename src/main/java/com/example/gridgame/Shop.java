package com.example.gridgame;

import java.util.ArrayList;


public class Shop {
    private static String[] piecesNames = {"Shadow","Friction","Eraser","Paint","Zombie","Cross","Glitch","Refract","Rocket","Spiral","Charge"};
    private static ArrayList<String> pieces = new ArrayList<>();
    private static int[] piecePrices = new int[piecesNames.length]; // <---- change ts later
    private static String[] piecesInfo = {


    };


    private static String[] clickableNames = {"Grenade","Bomb","Wall"};
    private static int[] clickablePrices = {0,0,0};
    private int[] clickablesStock = {(int)(Math.random() * 4) + 1 ,(int)(Math.random() * 4) + 1 ,(int)(Math.random() * 4) + 1 };
    private static String[] clickableInfo = {"Detonates on use, clearing all pieces and obstacles in the eight surrounding tiles (1-tile radius) while leaving the center tile intact."
    ,"Upon activation, this device clears all pieces and obstacles within an extended radius, affecting a significantly larger area than the standard shockwave item. Its wide-range effect makes it strategically valuable for disrupting clustered formations and shifting board control."
    ,"When placed, this structure automatically extends outward in a straight line from the placement tile, building horizontally from left to right and vertically from top to bottom according to its orientation. The wall continues constructing until it reaches the board boundary or an occupied tile, creating a continuous barrier that can block movement and control space."};
    private static String[] stationNames = {"Void","Smoke","Magnet"};
    private static int[] stationPrices = {0,0,0,0,0};
    private static String[] stationInfo = {};
    public Shop(Player[] players){
        //need indexOf function
        for (int i = 0; i < piecesNames.length; i++) {
            pieces.add(piecesNames[i]);
        }
        for (int i = 0; i < players.length; i++) {
            for (int j = 0; j < stationNames.length; j++) {
                players[i].getPlayerStations().add(stationNames[j]);
                players[i].getStationCount().add(0);
            }
        }
    }
    public boolean buyPiece(int piece,Player player){
        String pieceBuying = piecesNames[piece];
        //use .contains to see if the piece is in there do not initialize arsenal and pieces
        if(player.getCredits()>=piecePrices[piece]){
            if(player.getPieces().contains(pieceBuying)) {
                player.getArsenal().set(player.getPieces().indexOf(pieceBuying),player.getArsenal().get(player.getPieces().indexOf(pieceBuying))+1) ;
            }
            else {
                player.getPieces().add(pieceBuying);
                player.getArsenal().add(1);
            }

            player.setCredits(player.getCredits()-piecePrices[piece]);
            return true;
        }
        return false;

    }
    public boolean buyClickable(int index, Player player){
        String clickableBuying = clickableNames[index];
        if(player.getCredits()>=clickablePrices[index]) {
            if (clickableBuying.equals("Grenade")) {
                player.getPlayerBombs().add(new Bomb(1));
            } else if (clickableBuying.equals("Bomb")) {
                int nukeChance = (int)((Math.random()*10)+1);
                int radius;
                if(nukeChance==1){
                    radius = 3;
                }else{
                    radius = 2;
                }
                player.getPlayerBombs().add(new Bomb(radius));
            }else{
                int lengthLuck = (int)(Math.random() * (36 - 9 + 1)) + 9;
                int length = (int)(Math.sqrt(lengthLuck));
                player.getPlayerWalls().add(new Wall(length));
            }
            return true;
        }
        return false;
    }

    public boolean buyStation(int stationBuying,Player player){
        if(player.getCredits()>=stationPrices[stationBuying]){
            player.getStationCount().set(stationBuying,player.getStationCount().get(stationBuying)+1);
            player.setCredits(player.getCredits()-stationPrices[stationBuying]);
            return true;
        }
        return false;

    }
    public String[] getPieceInfo(){
        return piecesInfo;
    }
    public String[] getPieceNames(){
        return piecesNames;
    }
    public int[] getPiecePrices(){
        return piecePrices;
    }
    public String[] getClickableNames(){
        return clickableNames;
    }
    public int[] getClickablePrices(){
        return clickablePrices;
    }
    public int[] getClickablesStock(){
        return clickablesStock;
    }
    public String[] getClickableInfo(){
        return clickableInfo;
    }
    public String[] getStationNames(){
        return stationNames;
    }
    public int[] getStationPrices(){
        return stationPrices;
    }
    public String[] getStationInfo(){
        return stationInfo;
    }



}
