package com.example.gridgame;

import java.util.ArrayList;


public class Shop {
    private static String[] piecesNames = {"Shadow","Friction","Cross","Paint","Zombie","Charge","Glitch","Refract","Rocket","Spiral","Eraser",};
    private static int[] piecePrices = new int[piecesNames.length]; // <---- change ts later
    private static String[] piecesInfo = {
        "The Shadow moves with subtlety, making it difficult for opponents to predict its path and respond effectively. It is best used to create pressure in contested spaces while disrupting the enemy’s sense of control.",
            "Friction interferes with movement, slowing the flow of nearby pieces and making repositioning more difficult. Its effect is especially useful for limiting mobility and controlling key lanes on the board.",
            "The Cross affects tiles in both diagonal directions at once, giving it broad influence over the board. Its shape makes it effective for striking through multiple lanes and disrupting structured formations. It lasts 4 turns until it disappears",
            "Paint changes the condition of affected tiles, marking territory and altering how pieces interact with that space. It is valuable for claiming control of important areas and shaping future movement.",
            "The Zombie converts or corrupts what it reaches, turning opposing strength into an advantage for its own side. This makes it especially dangerous in crowded sections of the board where influence can spread.",
            "Charge builds momentum as it moves, rewarding commitment to a direct and aggressive line of play. It is best used to pressure opponents quickly and force reactions before they can fully prepare.",
            "Glitch introduces instability to the board, causing interactions to become less predictable and more difficult to counter. It is most effective when used to interrupt stable patterns and force opponents into mistakes.",
            "Refract redirects movement or effects into new directions, creating unexpected outcomes and unusual board angles. This makes it useful for extending influence and turning straightforward plays into more complex threats.",
            "The Rocket travels with speed and force along a direct path, allowing it to break through space and apply immediate pressure. It is most effective when used to challenge long lanes and punish exposed positions.",
            "The Spiral advances in a rotating pattern, covering space in a way that is difficult to anticipate. Its unusual motion makes it effective for surrounding targets and disrupting predictable defenses."

    };


    private static String[] clickableNames = {"Grenade","Bomb","Wall"};
    private static int[] clickablePrices = {0,0,0};
    private int[] clickablesStock = {(int)(Math.random() * 4) + 1 ,(int)(Math.random() * 4) + 1 ,(int)(Math.random() * 4) + 1 };
    private static String[] clickableInfo = {"Detonates on use, clearing all pieces and obstacles in the eight surrounding tiles (1-tile radius) while leaving the center tile intact."
    ,"Upon activation, this device clears all pieces and obstacles within an extended radius, affecting a significantly larger area than the standard shockwave item. Its wide-range effect makes it strategically valuable for disrupting clustered formations and shifting board control."
    ,"When placed, this structure automatically extends outward in a straight line from the placement tile, building horizontally from left to right and vertically from top to bottom according to its orientation. The wall continues constructing until it reaches the board boundary or an occupied tile, creating a continuous barrier that can block movement and control space."};
    private static String[] stationNames = {"Void","Smoke","Magnet"};
    private static int[] stationPrices = {0,0,0,0,0};
    private static String[] stationInfo = {
            "The Void removes whatever occupies the selected tile, creating an empty space that can disrupt formations and open new paths on the board. Its precision makes it especially useful for breaking key positions without affecting surrounding tiles.",
            "Smoke spreads across the surrounding area and temporarily obscures nearby tiles, making movement through that space less predictable. It is best used to interfere with positioning and create short-term control over a section of the grid.",
            "The Magnet pulls nearby pieces toward its position, altering the arrangement of the board and disrupting established patterns. This makes it effective for drawing targets out of place and setting up stronger offensive or defensive plays."

    };
    public Shop(Player[] players){
        //need indexOf function
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
