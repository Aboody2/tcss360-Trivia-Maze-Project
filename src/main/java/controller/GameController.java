package controller;

import model.*;
import persistence.DatabaseHandler;
import persistence.SaveLoadHandler;
import view.GameView;

public class GameController {
    private TriviaMazeGame game;
    private GameView view;
    private final DatabaseHandler dbHandler;
    private final SaveLoadHandler saveLoadHandler;
    private final QuestionFactory questionFactory;

    public GameController() {
        dbHandler = new DatabaseHandler();
        dbHandler.initializeDatabase();
        saveLoadHandler = new SaveLoadHandler();
        questionFactory = new QuestionFactory(dbHandler);
    }

    public void setView(GameView view) {
        this.view = view;
    }

    public TriviaMazeGame getGame() {
        return game;
    }

    public void startNewGame() {
        game = new TriviaMazeGame(4, 4, questionFactory);
        if (view != null) {
            view.updateRoomInfo();
        }
    }

    public void requestQuestion(Direction dir) {
        if (game != null) {
            Room currentRoom = game.getCurrentRoom();
            if (currentRoom != null && currentRoom.hasDoor(dir) && !currentRoom.getDoor(dir).isLocked()) {
                Question question = game.getQuestionForDoor(dir);
                if (question != null) {
                    view.showQuestion(dir, question);
                }
            }
        }
    }

    public void movePlayer(Direction dir) {
        if (game != null && game.movePlayer(dir)) {
            view.updateRoomInfo();
            if (game.isGameOver()) {
                view.showGameOver(game.isGameWon());
            }
        } else {
            view.showErrorMessage("Cannot move in that direction!");
        }
    }

    public void answerQuestion(Direction dir, String answer) {
        if (game != null) {
            boolean correct = game.answerQuestion(dir, answer);
            if (correct) {
                movePlayer(dir);
            } else {
                view.showErrorMessage("Wrong answer! The door is now locked.");
                view.updateRoomInfo();
            }
        }
    }

    public void saveGame() {
        if (game != null) {
            GameState state = new GameState(game.getPlayer(), game.getMaze());
            saveLoadHandler.saveGame("savegame.dat", state);
            view.showErrorMessage("Game saved successfully!");
        }
    }

    public void loadGame() {
        GameState state = saveLoadHandler.loadGame("savegame.dat");
        if (state != null) {
            game = new TriviaMazeGame(state);
            view.updateRoomInfo();
            view.showErrorMessage("Game loaded successfully!");
        } else {
            view.showErrorMessage("Failed to load saved game.");
        }
    }
}
