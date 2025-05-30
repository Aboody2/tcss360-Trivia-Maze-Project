package model;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

public class Room implements Serializable {
    private final int myRow;
    private final int myCol;
    private final Map<Direction, Door> myDoors;

    public Room(int theRow, int theCol) {
        if (theRow < 0 || theCol < 0) {
            throw new IllegalArgumentException("Coordinates must be non-negative");
        }
        myRow = theRow;
        myCol = theCol;
        myDoors = new EnumMap<>(Direction.class);
    }

    public void addDoor(Direction theDir, Door theDoor) {
        myDoors.put(theDir, theDoor);
    }

    public Door getDoor(Direction theDir) {
        return myDoors.get(theDir);
    }

    public boolean hasDoor(Direction theDir) {
        return myDoors.containsKey(theDir);
    }

    public int getRow() {
        return myRow;
    }

    public int getCol() {
        return myCol;
    }
}
