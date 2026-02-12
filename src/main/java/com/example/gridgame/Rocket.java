package com.example.gridgame;

public class Rocket extends Piece{
    private boolean redirected = false;
    private int[] indexStopped = new int[2];
    public Rocket(int r, int c){
        super(r,c);
    }

    public void move(Tile[][] tiles){

        int newRow = currentRow;
        int newCol = currentCol;

        if(direction.equals("right")){
            newCol = currentCol+1;
        }else if(direction.equals("left")){
            newCol = currentCol-1;
        }else if(direction.equals("up")){
            newRow = currentRow-1;
        }else if(direction.equals("down")){
            newRow = currentRow+1;
        }

        if(!redirected) {
            if (tiles[newRow][newCol].getValue() == 0) {
                currentCol = newCol;
                currentRow = newRow;
            } else if (tiles[newRow][newCol].getValue() > 0) {
                redirected = true;
                redirect(tiles);
            }
        }else{
            if (tiles[newRow][newCol].getValue() == 0) {
                currentCol = newCol;
                currentRow = newRow;
                trail(tiles);
            } else if (tiles[newRow][newCol].getValue() > 0) {
                canMove = false;
            }
        }
    }
    public void redirect(Tile[][] tiles){
        // use recursion?? redirect twice
//        if(times == 0){
//
//        }
        int[] bounds = new int[4];
        //0 left, 1 right, 2 top, 3 down
        if(direction.equals("up")|direction.equals("down")) {
            for (int i = 1; i < currentCol; i++) {
                if (tiles[currentRow][i].getValue() == 0) {
                    bounds[0]++;
                }
            }
            //right bound check
            for (int i = currentCol+1; i < tiles.length-1; i++) {
                if (tiles[currentRow][i].getValue() == 0) {
                    bounds[1]++;
                }
            }


            if(bounds[0]>bounds[1]){
                System.out.println("left redirect");
                direction = "left";

            }else if(bounds[1]>bounds[0]){
                System.out.println("right redirect");
                direction = "right";

            }else{
                int randomDirection = (int) (Math.random()*2);
                if(randomDirection==0){
                    direction = "left";
                }else{
                    direction = "right";
                }
            }
        }else if(direction.equals("left")|direction.equals("right")){

        }


//        for (int i = 1; i < tiles[0].length-1; i++) {
//            int colCount = 0;
//            for (int j = 1; j < tiles.length-1; j++) {
//                if (tiles[j][i].getValue() == player) {
//                    colCount++;
//                } else {
//                    colCount = 0;
//                }
//                if(colCount==5){
//
//
//                }
//            }
//        }
    }
    public void trail(Tile[][] tiles){
        tiles[currentRow][currentCol].setValue(tiles[currentRow][currentCol]);

    }
}
