package uk.gnosisstudios.MidnightCaliber.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainMenuView {

    Label gameTitle = new Label("Midnight Caliber");
    Button startButton = new Button("Start Game");
    Button settingsButton = new Button("Settings");
    Button exitButton = new Button("Exit");

    VBox menuBox = new VBox();
    BorderPane root = new BorderPane();

    Scene scene;

    public MainMenuView() {

        menuBox.getChildren().addAll(gameTitle, startButton, settingsButton, exitButton);
        menuBox.setSpacing(20);
        menuBox.setAlignment(Pos.CENTER);

        root.setCenter(menuBox);
        BorderPane.setAlignment(menuBox, Pos.CENTER);
        BorderPane.setMargin(menuBox, new Insets(50));

        scene = new Scene(root, 800, 600);
    }

    public Scene getScene() {
        return scene;
    }

    public Button getStartButton() {
        return startButton;
    }

    public Button getSettingsButton() {
        return settingsButton;
    }

    public Button getExitButton() {
        return exitButton;
    }
}