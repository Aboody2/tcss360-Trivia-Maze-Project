package view;

import controller.GameController;
import model.Direction;
import model.Question;

public interface GameView {
    void setController(GameController theController);

    void updateRoomInfo();

    void showQuestion(Direction theDirection, Question theQuestion);

    void showGameOver(boolean theWon);

    void showErrorMessage(String theMessage);
}
