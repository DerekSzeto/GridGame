package com.example.gridgame;

import java.util.ArrayList;


public class Shop {
    private static String[] piecesNames = {"Shadow","Friction","Eraser","Paint","Zombie","Cross","Glitch","Refract","Rocket","Spiral"};
    private static ArrayList<String> pieces = new ArrayList<>();
    private static int[] piecePrices = new int[piecesNames.length]; // <---- change ts later
    private static String[] piecesInfo = {


    };


    private static String[] clickableNames = {"Grenade","Bomb","Wall","Lock"};
    private int[] clickablesStock = {3,5,4};
    private static String[] stationNames = {"Void","Cloud","Sparkle"};
    private static int[] stationPrices = {};
    public Shop(Player[] players){
        //need indexOf function
        for (int i = 0; i < piecesNames.length; i++) {
            pieces.add(piecesNames[i]);
        }
    }
    public boolean buyPiece(int piece,Player player){
        String pieceBuying = pieces.get(piece);
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
    public String getPieceInfo(int selectedPiece){
        return piecesInfo[selectedPiece];
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
    public int[] getClickablesStock(){
        return clickablesStock;
    }
    public String[] getStationNames(){
        return stationNames;
    }
    public int[] getStationPrices(){
        return stationPrices;
    }



}
