package com.example.gridgame;

import java.util.ArrayList;

public class Glitch extends Piece{
    //finds the nearest pair of 2 alike tiles, takes 2-3 tiles from the row closes to the row of thatpair, the tiles will switch values with 2-3 tiles in that row
    private int pieceValue;
    public Glitch(int r, int c, int v){
        super(r,c);
        pieceValue = v;
    }


    public ArrayList<Tile> getNearbyOppositeTiles(Tile[][] tiles){
        ArrayList<Tile> nearbyTiles = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((currentRow + i) > 0 && (currentRow + i) < tiles.length - 1 && (currentCol + j) > 0 && (currentCol + j) < tiles[currentRow + i].length - 1) {
                    if((i != 0 || j != 0)) {
                        if(tiles[currentRow+i][currentCol+j].getValue()==getCheckingValue()){
                            nearbyTiles.add(tiles[currentRow + i][currentCol + j]);
                        }
                    }
                }
            }
        }

        return nearbyTiles;
    }

    public void transpose(Tile[][] tiles) {
        ArrayList<Tile> nearbyOppositeTiles = getNearbyOppositeTiles(tiles);
        ArrayList<Tile> sameTiles = getTiles(tiles,"same");
        ArrayList<Tile> oppTiles = getTiles(tiles,"opposite");

        int glitchChance = (int)((Math.random()*10)+1);
        if(glitchChance<=3) {
            for (int i = 0; i < nearbyOppositeTiles.size(); i++) {
                ArrayList<Tile> nearestRow = getOppositeRowTile(tiles);
                for (int j = 0; j < nearestRow.size(); j++) {
                    nearestRow.get(j).setValue(pieceValue);
                }
            }
        }else if (glitchChance<=7){
            for (int i = 0; i < nearbyOppositeTiles.size(); i++) {
                int randomTileIndex = (int)(Math.random()*sameTiles.size());
                Tile randomSameTile = sameTiles.get(randomTileIndex);
                randomSameTile.setValue(getCheckingValue());
                nearbyOppositeTiles.get(i).setValue(pieceValue);
                sameTiles.remove(randomTileIndex);
            }
        }else{
            for (int i = 0; i < nearbyOppositeTiles.size(); i++) {
                Tile randomSameTile = sameTiles.get((int)(Math.random()*sameTiles.size()));
                Tile randomOppTile = oppTiles.get((int)(Math.random()*oppTiles.size()));

                randomSameTile.setValue(getCheckingValue());
                randomOppTile.setValue(pieceValue);

                sameTiles.remove(randomSameTile);
                sameTiles.remove(randomOppTile);
            }
        }
    }
    public ArrayList<Tile> getTiles(Tile[][] tiles, String tileType){
        ArrayList<Tile> tilesReturning = new ArrayList<>();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if(tileType.equals("same")) {
                    if (tiles[i][j].getValue() == pieceValue) {
                        tilesReturning.add(tiles[i][j]);
                    }
                }else if(tileType.equals("opposite")) {
                    if (tiles[i][j].getValue() == getCheckingValue()) {
                        tilesReturning.add(tiles[i][j]);
                    }
                }
            }
        }
        return tilesReturning;
    }
    public ArrayList<Tile> getOppositeRowTile(Tile[][] tiles){
        ArrayList<Tile> runOfTiles = new ArrayList<>();
         for (int i = 1; i < tiles.length - 1; i++) {
            int count = 0;

            for (int j = 1; j < tiles[i].length - 1; j++) {
                if (tiles[i][j].getValue() == getCheckingValue()) {
                    count++;
                    runOfTiles.add(tiles[i][j]);
                    if (count >= 2) {
                        return runOfTiles;
                    }
                } else {
                    count = 0;
                    runOfTiles.clear();
                }
            }
        }
         return null;
    }
    public int getCheckingValue(){
        if(pieceValue==1){
            return 2;
        }else{
            return 1;
        }
    }
}

