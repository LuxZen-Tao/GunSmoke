package uk.gnosisstudios.MidnightCaliber.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;
import java.util.Random;

public class GameView {
    private Random random = new Random();
    private Image targetSprite = new Image("Art/Sprites/test.png");
    private double mouseX = 250;
    private double mouseY = 125;


    Label titleLabel = new Label("Midnight Caliber");
    Label scoreLabel = new Label("Score: 0");
    Label gameAreaLabel = new Label("Behind the bar");
    Label ammoLabel = new Label("Ammo: 6");
    Button reloadButton = new Button("Reload");

    Button scoreAddButton = new Button("Add Score");
    Button resetScoreButton = new Button("Reset Score");
    Button mainMenuButton = new Button("Main Menu");

    private Canvas canvas = new Canvas(500, 250);
    private GraphicsContext gc = canvas.getGraphicsContext2D();

    BorderPane root = new BorderPane();
    Scene scene;

    //oval
    private double targetX = 220;
    private double targetY = 70;
    private double targetSize = 60;
    private double targetSpeedX = 2.5;
    private double targetSpeedY = 1.5;

    public GameView() {
        buildUI();
        render();
    }

    private void buildUI() {
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(20));
        topBar.setSpacing(15);

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topBar.getChildren().addAll(titleLabel, spacer, scoreLabel, ammoLabel);
        VBox centerBox = new VBox();
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setSpacing(10);
        centerBox.setPadding(new Insets(20));
        centerBox.getChildren().addAll(canvas, gameAreaLabel);

        HBox controls = new HBox();
        controls.setPadding(new Insets(20));
        controls.setAlignment(Pos.CENTER);
        controls.setSpacing(15);
        controls.getChildren().addAll(scoreAddButton, resetScoreButton, reloadButton, mainMenuButton);
        root.setTop(topBar);
        root.setCenter(centerBox);
        root.setBottom(controls);

        scene = new Scene(root, 800, 600);
    }

    public Scene getScene() {
        return scene;
    }

    public Button getScoreAddButton() {
        return scoreAddButton;
    }


    public Button getResetScoreButton() {
        return resetScoreButton;
    }

    public Button getMainMenuButton() {
        return mainMenuButton;
    }

    public Label getTitleLabel() {
        return titleLabel;
    }

    public Label getScoreLabel() {
        return scoreLabel;
    }

    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    public void showMessage(String message) {
        gameAreaLabel.setText(message);
    }
    public Canvas getCanvas() {
        return canvas;
    }
    public Button getReloadButton() {
        return reloadButton;
    }

    public void updateAmmo(int ammo) {
        ammoLabel.setText("Ammo: " + ammo);
    }

    //ButtonCalls

    private void returnToMainMenu() {
        MainMenuView mainMenuView = new MainMenuView();
    }
    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setFill(Color.DARKSLATEGRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setFill(Color.SADDLEBROWN);
        gc.fillRect(0, canvas.getHeight() - 50, canvas.getWidth(), 50);

        gc.drawImage(targetSprite, targetX, targetY, targetSize, targetSize);

        gc.strokeLine(targetX + targetSize / 2, targetY, targetX + targetSize / 2, targetY + targetSize);
        gc.strokeLine(targetX, targetY + targetSize / 2, targetX + targetSize, targetY + targetSize / 2);

        gc.setStroke(Color.RED);
        gc.strokeLine(mouseX - 10, mouseY, mouseX + 10, mouseY);
        gc.strokeLine(mouseX, mouseY - 10, mouseX, mouseY + 10);
        gc.strokeOval(mouseX - 15, mouseY - 15, 30, 30);
    }
    public void moveTargetRandomly() {
        targetX = random.nextDouble() * (canvas.getWidth() - targetSize);
        targetY = random.nextDouble() * (canvas.getHeight() - targetSize);
    }

    //hit
    public boolean isTargetHit(double mouseX, double mouseY) {

        double centerX = targetX + targetSize / 2;
        double centerY = targetY + targetSize / 2;
        double radius = targetSize / 2;

        double dx = mouseX - centerX;
        double dy = mouseY - centerY;

        return dx * dx + dy * dy <= radius * radius;
    }

    public void update() {
        targetX += targetSpeedX;
        targetY += targetSpeedY;

        if (targetX <= 0 || targetX + targetSize >= canvas.getWidth()) {
            targetSpeedX *= -1;
        }

        if (targetY <= 0 || targetY + targetSize >= canvas.getHeight()) {
            targetSpeedY *= -1;
        }
    }

    public void updateMousePosition(double x, double y) {
        mouseX = x;
        mouseY = y;
    }
}