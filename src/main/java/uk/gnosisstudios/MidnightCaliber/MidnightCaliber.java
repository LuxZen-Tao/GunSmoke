package uk.gnosisstudios.MidnightCaliber;

import javafx.application.Application;
import javafx.stage.Stage;
import uk.gnosisstudios.MidnightCaliber.UI.GameView;
import uk.gnosisstudios.MidnightCaliber.UI.MainMenuView;
import uk.gnosisstudios.MidnightCaliber.controller.GameController;

public class MidnightCaliber extends Application {

    @Override
    public void start(Stage stage) {
        MainMenuView mainMenuView = new MainMenuView();
        GameView gameView = new GameView();

        new GameController(stage, mainMenuView, gameView);

        stage.setScene(mainMenuView.getScene());
        stage.setTitle("Midnight Caliber");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}