package com.example.gridgame;

public class Spiral extends Rocket {
    private int numOfRedirections;
    private int value;
    public Spiral(int r, int c, int times, int value) {
        super(r, c);
        this.numOfRedirections = times;
        this.value = value;
    }
    @Override
    public void move(Tile[][] tiles) {
        numOfRedirections++;

        int newRow = currentRow;
        int newCol = currentCol;

        if (direction.equals("right")) {
            newCol = currentCol + 1;
        } else if (direction.equals("left")) {
            newCol = currentCol - 1;
        } else if (direction.equals("up")) {
            newRow = currentRow - 1;
        } else if (direction.equals("down")) {
            newRow = currentRow + 1;
        }


        if (tiles[newRow][newCol].getValue() == 0 || tiles[newRow][newCol].getValue() == getCheckingValue()) {
            currentCol = newCol;
            currentRow = newRow;
            tiles[currentRow][currentCol].setValue(9);
        } else if (tiles[newRow][newCol].getValue() == value || tiles[newRow][newCol].getValue() >= 4) {
            if (numOfRedirections > 0) {
                redirect(tiles);
                numOfRedirections--;
            }
        }
        if(numOfRedirections==0){
            canMove = false;
        }
    }


    @Override
    public void checkHorizontalBounds(Tile[][] tiles, int[] bounds){
        for (int i = 1; i < currentCol; i++) {
            if (tiles[currentRow][i].getValue() == getCheckingValue()){
                bounds[0]++;
            }
        }
        for (int i = currentCol + 1; i < tiles[0].length - 1; i++) { // FIXED
            if (tiles[currentRow][i].getValue() == getCheckingValue()){
                bounds[1]++;
            }
        }
        compareHorizontalBounds(bounds);
    }

    @Override
    public void checkVerticalBounds(Tile[][] tiles, int[] bounds){
        for (int i = 1; i < currentRow; i++) {
            if (tiles[i][currentCol].getValue() == getCheckingValue()) {
                bounds[2]++;
            }
        }
        for (int i = currentRow + 1; i < tiles.length - 1; i++) {
            if (tiles[i][currentCol].getValue() == getCheckingValue()) {
                bounds[3]++;
            }
        }
        compareVerticalBounds(bounds);
    }
    public int getCheckingValue(){
        if(value==1){
            return 2;
        }else{
            return 1;
        }
    }
}
