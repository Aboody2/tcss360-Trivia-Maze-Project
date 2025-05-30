package view;

import controller.GameController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Direction;
import model.Question;
import model.MultipleChoiceQuestion;
import model.Room;

import java.util.Optional;

public class JavaFXGameView implements GameView {
    private final Stage stage;
    private GameController controller;
    private GridPane mazeGrid;
    private TextArea roomInfo;
    private Button northBtn, southBtn, eastBtn, westBtn;
    private final int CELL_SIZE = 60;

    public JavaFXGameView(Stage stage) {
        this.stage = stage;
        initializeUI();
    }

    @Override
    public void setController(GameController controller) {
        this.controller = controller;
    }

    private void initializeUI() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        MenuBar menuBar = createMenuBar();
        root.setTop(menuBar);

        mazeGrid = new GridPane();
        mazeGrid.setAlignment(Pos.CENTER);
        mazeGrid.setHgap(2);
        mazeGrid.setVgap(2);
        mazeGrid.setPadding(new Insets(10));
        root.setCenter(mazeGrid);

        VBox rightPanel = new VBox(10);
        rightPanel.setPadding(new Insets(10));

        roomInfo = new TextArea();
        roomInfo.setPrefRowCount(5);
        roomInfo.setEditable(false);
        roomInfo.setWrapText(true);

        GridPane navButtons = new GridPane();
        navButtons.setAlignment(Pos.CENTER);
        navButtons.setHgap(5);
        navButtons.setVgap(5);

        northBtn = new Button("North");
        southBtn = new Button("South");
        eastBtn = new Button("East");
        westBtn = new Button("West");

        navButtons.add(northBtn, 1, 0);
        navButtons.add(southBtn, 1, 2);
        navButtons.add(eastBtn, 2, 1);
        navButtons.add(westBtn, 0, 1);

        setupNavigationButtons();

        rightPanel.getChildren().addAll(new Label("Current Room:"), roomInfo, navButtons);
        root.setRight(rightPanel);

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Trivia Maze Game");
        stage.setScene(scene);
        stage.show();
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        MenuItem newGame = new MenuItem("New Game");
        MenuItem saveGame = new MenuItem("Save Game");
        MenuItem loadGame = new MenuItem("Load Game");
        MenuItem exit = new MenuItem("Exit");

        newGame.setOnAction(e -> {
            controller.startNewGame();
            updateRoomInfo();
        });
        saveGame.setOnAction(e -> controller.saveGame());
        loadGame.setOnAction(e -> controller.loadGame());
        exit.setOnAction(e -> stage.close());

        fileMenu.getItems().addAll(newGame, saveGame, loadGame, new SeparatorMenuItem(), exit);

        Menu helpMenu = new Menu("Help");
        MenuItem about = new MenuItem("About");
        about.setOnAction(e -> showAboutDialog());
        helpMenu.getItems().add(about);

        menuBar.getMenus().addAll(fileMenu, helpMenu);
        return menuBar;
    }

    private void setupNavigationButtons() {
        northBtn.setOnAction(e -> controller.requestQuestion(Direction.NORTH));
        southBtn.setOnAction(e -> controller.requestQuestion(Direction.SOUTH));
        eastBtn.setOnAction(e -> controller.requestQuestion(Direction.EAST));
        westBtn.setOnAction(e -> controller.requestQuestion(Direction.WEST));
    }

    @Override
    public void updateRoomInfo() {
        if (controller == null || controller.getGame() == null) return;

        Room currentRoom = controller.getGame().getCurrentRoom();
        int playerRow = controller.getGame().getPlayer().getRow();
        int playerCol = controller.getGame().getPlayer().getCol();

        StringBuilder info = new StringBuilder();
        info.append(String.format("Player Location: Row %d, Column %d%n", playerRow, playerCol));
        info.append("Doors:%n");
        info.append("NORTH - ").append(getDoorStatus(currentRoom, Direction.NORTH)).append("%n");
        info.append("SOUTH - ").append(getDoorStatus(currentRoom, Direction.SOUTH)).append("%n");
        info.append("EAST - ").append(getDoorStatus(currentRoom, Direction.EAST)).append("%n");
        info.append("WEST - ").append(getDoorStatus(currentRoom, Direction.WEST)).append("%n");

        roomInfo.setText(info.toString());

        updateNavigationButtons(currentRoom);

        updateMazeGrid();
    }

    private String getDoorStatus(Room room, Direction dir) {
        if (!room.hasDoor(dir)) return "No Door";
        return room.getDoor(dir).isLocked() ? "Locked" : "Available";
    }

    private void updateNavigationButtons(Room currentRoom) {
        northBtn.setDisable(!currentRoom.hasDoor(Direction.NORTH) || currentRoom.getDoor(Direction.NORTH).isLocked());
        southBtn.setDisable(!currentRoom.hasDoor(Direction.SOUTH) || currentRoom.getDoor(Direction.SOUTH).isLocked());
        eastBtn.setDisable(!currentRoom.hasDoor(Direction.EAST) || currentRoom.getDoor(Direction.EAST).isLocked());
        westBtn.setDisable(!currentRoom.hasDoor(Direction.WEST) || currentRoom.getDoor(Direction.WEST).isLocked());
    }

    private void updateMazeGrid() {
        mazeGrid.getChildren().clear();

        int rows = controller.getGame().getMaze().getRows();
        int cols = controller.getGame().getMaze().getCols();
        int playerRow = controller.getGame().getPlayer().getRow();
        int playerCol = controller.getGame().getPlayer().getCol();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                StackPane cell = new StackPane();
                cell.getStyleClass().add("grid-cell");

                Rectangle roomBg = new Rectangle(CELL_SIZE - 2, CELL_SIZE - 2);
                roomBg.setFill(Color.WHITE);
                roomBg.setStroke(Color.DARKGRAY);
                cell.getChildren().add(roomBg);

                if (row == playerRow && col == playerCol) {
                    Label playerLabel = new Label("P");
                    playerLabel.getStyleClass().add("player-label");
                    cell.getStyleClass().add("player-cell");
                    cell.getChildren().add(playerLabel);
                }

                cell.setMinSize(CELL_SIZE, CELL_SIZE);
                cell.setMaxSize(CELL_SIZE, CELL_SIZE);

                mazeGrid.add(cell, col, row);

                Room room = controller.getGame().getMaze().getRoom(row, col);
                if (room != null) {
                    addDoorIndicators(cell, room);
                }
            }
        }
    }

    private void addDoorIndicators(StackPane cell, Room room) {
        if (room.hasDoor(Direction.NORTH)) {
            Rectangle northDoor = new Rectangle(30, 8);
            northDoor.setFill(room.getDoor(Direction.NORTH).isLocked() ? Color.RED : Color.GREEN);
            northDoor.setStroke(Color.BLACK);
            northDoor.setStrokeWidth(1);
            StackPane.setAlignment(northDoor, Pos.TOP_CENTER);
            cell.getChildren().add(northDoor);
        }
        if (room.hasDoor(Direction.SOUTH)) {
            Rectangle southDoor = new Rectangle(30, 8);
            southDoor.setFill(room.getDoor(Direction.SOUTH).isLocked() ? Color.RED : Color.GREEN);
            southDoor.setStroke(Color.BLACK);
            southDoor.setStrokeWidth(1);
            StackPane.setAlignment(southDoor, Pos.BOTTOM_CENTER);
            cell.getChildren().add(southDoor);
        }
        if (room.hasDoor(Direction.EAST)) {
            Rectangle eastDoor = new Rectangle(8, 30);
            eastDoor.setFill(room.getDoor(Direction.EAST).isLocked() ? Color.RED : Color.GREEN);
            eastDoor.setStroke(Color.BLACK);
            eastDoor.setStrokeWidth(1);
            StackPane.setAlignment(eastDoor, Pos.CENTER_RIGHT);
            cell.getChildren().add(eastDoor);
        }
        if (room.hasDoor(Direction.WEST)) {
            Rectangle westDoor = new Rectangle(8, 30);
            westDoor.setFill(room.getDoor(Direction.WEST).isLocked() ? Color.RED : Color.GREEN);
            westDoor.setStroke(Color.BLACK);
            westDoor.setStrokeWidth(1);
            StackPane.setAlignment(westDoor, Pos.CENTER_LEFT);
            cell.getChildren().add(westDoor);
        }
    }

    @Override
    public void showQuestion(Direction dir, Question question) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Answer Question");
        dialog.setHeaderText(question.getQuestionText());

        ButtonType submitButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

        VBox content = new VBox(10);
        content.setPadding(new Insets(10));

        if (question instanceof MultipleChoiceQuestion) {
            ToggleGroup group = new ToggleGroup();
            for (String option : ((MultipleChoiceQuestion) question).getOptions()) {
                RadioButton rb = new RadioButton(option);
                rb.setToggleGroup(group);
                content.getChildren().add(rb);
            }

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == submitButtonType) {
                    RadioButton selected = (RadioButton) group.getSelectedToggle();
                    return selected == null ? null : selected.getText();
                }
                return null;
            });
        } else {
            TextField answerField = new TextField();
            content.getChildren().add(answerField);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == submitButtonType) {
                    return answerField.getText();
                }
                return null;
            });
        }

        dialog.getDialogPane().setContent(content);

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(answer -> controller.answerQuestion(dir, answer));
    }

    @Override
    public void showGameOver(boolean won) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(won ? "Congratulations!" : "Game Over");
        alert.setContentText(won ? "You've successfully completed the maze!" : "You're trapped in the maze!");
        alert.showAndWait();
    }

    @Override
    public void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showAboutDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Trivia Maze");
        alert.setHeaderText("Trivia Maze Game");
        alert.setContentText("Navigate through the maze by answering trivia questions correctly.\n" +
                "Wrong answers will lock doors permanently.\n" +
                "Reach the bottom-right corner to win!");
        alert.showAndWait();
    }
}
