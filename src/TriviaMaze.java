import controller.GameController;
import javafx.application.Application;
import javafx.stage.Stage;
import view.MainMenuView;

public class TriviaMaze extends Application {

    @Override
    public void start(Stage primaryStage) {
        GameController controller = new GameController();
        MainMenuView mainMenu = new MainMenuView(primaryStage, controller);
        mainMenu.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

