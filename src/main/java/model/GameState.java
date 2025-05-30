package model;

import java.io.Serializable;

public class GameState implements Serializable {
    private final Player myPlayer;
    private final Maze myMaze;

    public GameState(Player thePlayer, Maze theMaze) {
        myPlayer = thePlayer;
        myMaze = theMaze;
    }

    public Player getPlayer() {
        return myPlayer;
    }

    public Maze getMaze() {
        return myMaze;
    }
}
