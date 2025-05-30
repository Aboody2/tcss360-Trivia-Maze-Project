package view;

import controller.GameController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenuView {
    private final Stage stage;
    private final GameController controller;

    public MainMenuView(Stage stage, GameController controller) {
        this.stage = stage;
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);

        Button newGameBtn = new Button("New Game");
        Button loadGameBtn = new Button("Load Game");
        Button exitBtn = new Button("Exit");

        newGameBtn.setOnAction(e -> {
            JavaFXGameView gameView = new JavaFXGameView(stage);
            controller.setView(gameView);
            gameView.setController(controller);
            controller.startNewGame();
            gameView.updateRoomInfo();
        });

        loadGameBtn.setOnAction(e -> {
            controller.loadGame();
        });

        exitBtn.setOnAction(e -> stage.close());

        root.getChildren().addAll(newGameBtn, loadGameBtn, exitBtn);

        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Trivia Maze - Main Menu");
        stage.show();
    }

    public void show() {
        stage.show();
    }
}
