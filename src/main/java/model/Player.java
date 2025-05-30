package model;

import java.io.Serializable;

public class Player implements Serializable {
    private int myRow;
    private int myCol;

    public Player(int theStartRow, int theStartCol) {
        setPosition(theStartRow, theStartCol);
    }

    public void setPosition(int theRow, int theCol) {
        if (theRow < 0 || theCol < 0) {
            throw new IllegalArgumentException("Position cannot be negative");
        }
        myRow = theRow;
        myCol = theCol;
    }

    public int getRow() {
        return myRow;
    }

    public int getCol() {
        return myCol;
    }

    public void move(Direction theDirection) {
        switch (theDirection) {
            case NORTH -> myRow--;
            case SOUTH -> myRow++;
            case EAST -> myCol++;
            case WEST -> myCol--;
        }
    }
}
