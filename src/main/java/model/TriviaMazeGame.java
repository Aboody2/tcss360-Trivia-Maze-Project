package model;

public class TriviaMazeGame {
    private Maze myMaze;
    private Player myPlayer;
    private boolean myGameOver;
    private boolean myGameWon;

    // Constructor for new game
    public TriviaMazeGame(int rows, int cols, QuestionFactory factory) {
        if (rows < 4 || cols < 4) {
            throw new IllegalArgumentException("Maze size must be at least 4x4.");
        }
        myMaze = new Maze(rows, cols, factory);
        myPlayer = new Player(0, 0);  // Start at top-left corner
        myGameOver = false;
        myGameWon = false;
    }

    // Constructor for loading from saved state
    public TriviaMazeGame(GameState state) {
        myMaze = state.getMaze();
        myPlayer = state.getPlayer();
        myGameOver = false;
        myGameWon = false;
        checkGameStatus();
    }

    public Maze getMaze() {
        return myMaze;
    }

    public Player getPlayer() {
        return myPlayer;
    }

    public Room getCurrentRoom() {
        return myMaze.getRoom(myPlayer.getRow(), myPlayer.getCol());
    }

    /**
     * Attempts to move the player in the given direction.
     * Returns true if move successful, false if blocked or door locked.
     */
    public boolean movePlayer(Direction direction) {
        Room currentRoom = getCurrentRoom();
        if (currentRoom == null || !currentRoom.hasDoor(direction)) {
            return false; // No door in that direction
        }
        Door door = currentRoom.getDoor(direction);
        if (door.isLocked()) {
            return false; // Door is locked
        }
        myPlayer.move(direction);
        checkGameStatus();
        return true;
    }

    /**
     * Returns the Question associated with the door in given direction
     */
    public Question getQuestionForDoor(Direction direction) {
        Room currentRoom = getCurrentRoom();
        if (currentRoom != null && currentRoom.hasDoor(direction)) {
            return currentRoom.getDoor(direction).getQuestion();
        }
        return null;
    }

    /**
     * Answer the question for the door in the given direction.
     * Returns true if correct, false if incorrect (door locks).
     */
    public boolean answerQuestion(Direction direction, String answer) {
        Room currentRoom = getCurrentRoom();
        if (currentRoom != null && currentRoom.hasDoor(direction)) {
            Door door = currentRoom.getDoor(direction);
            boolean correct = door.answerQuestion(answer);
            return correct;
        }
        return false;
    }

    /**
     * Checks if the game is over and if the player won (reached exit).
     */
    private void checkGameStatus() {
        Room current = getCurrentRoom();
        if (current.getRow() == myMaze.getRows() - 1 && current.getCol() == myMaze.getCols() - 1) {
            myGameWon = true;
            myGameOver = true;
        }
    }

    public boolean isGameOver() {
        return myGameOver;
    }

    public boolean isGameWon() {
        return myGameWon;
    }
}
