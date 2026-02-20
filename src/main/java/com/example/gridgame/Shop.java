package com.example.gridgame;

public class Shop {
    private Player[] players = new Player[2];
    private int whoIsBuying;
    public Shop(Player[] players){
        for (int i = 0; i <this.players.length; i++) {
            this.players[i] = players[i];
        }
    }
    public void selectWhoIsBuying(int player){
        whoIsBuying = player;
    }

}
