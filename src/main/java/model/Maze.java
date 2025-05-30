package model;

import java.io.Serializable;
import java.util.Random;

public class Maze implements Serializable {
    private final Room[][] myGrid;
    private final int myRows;
    private final int myCols;
    private final QuestionFactory myQuestionFactory;

    public Maze(int theRows, int theCols, QuestionFactory theFactory) {
        if (theRows < 4 || theCols < 4) {
            throw new IllegalArgumentException("Maze must be at least 4x4");
        }
        myRows = theRows;
        myCols = theCols;
        myQuestionFactory = theFactory;
        myGrid = generateMaze();
    }

    private Room[][] generateMaze() {
        Room[][] grid = new Room[myRows][myCols];
        Random rand = new Random();

        for (int i = 0; i < myRows; i++) {
            for (int j = 0; j < myCols; j++) {
                grid[i][j] = new Room(i, j);

                if (i > 0) {
                    grid[i][j].addDoor(Direction.NORTH, new Door(myQuestionFactory.createQuestion()));
                }
                if (j > 0) {
                    grid[i][j].addDoor(Direction.WEST, new Door(myQuestionFactory.createQuestion()));
                }
            }
        }
        return grid;
    }

    public Room getRoom(int theRow, int theCol) {
        if (theRow < 0 || theRow >= myRows || theCol < 0 || theCol >= myCols) {
            return null;
        }
        return myGrid[theRow][theCol];
    }

    public int getRows() {
        return myRows;
    }

    public int getCols() {
        return myCols;
    }
}
