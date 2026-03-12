package uk.gnosisstudios.MidnightCaliber.controller;

import javafx.stage.Stage;
import uk.gnosisstudios.MidnightCaliber.UI.GameView;
import uk.gnosisstudios.MidnightCaliber.UI.MainMenuView;
import javafx.animation.AnimationTimer;

public class GameController {

    private Stage stage;
    private MainMenuView mainMenuView;
    private GameView gameView;
    private AnimationTimer gameLoop;

    private int score = 0;
    private int ammo = 6;
    private final int maxAmmo = 6;

    public GameController(Stage stage, MainMenuView mainMenuView, GameView gameView) {
        this.stage = stage;
        this.mainMenuView = mainMenuView;
        this.gameView = gameView;

        wireEvents();
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gameView.update();
                gameView.render();
            }
        };

    }

    private void wireEvents() {
        mainMenuView.getStartButton().setOnAction(e -> startGame());
        mainMenuView.getExitButton().setOnAction(e -> exitGame());
        gameView.getReloadButton().setOnAction(e -> reloadAmmo());

        gameView.getMainMenuButton().setOnAction(e -> returnToMenu());
        gameView.getScoreAddButton().setOnAction(e -> addScore());
        gameView.getResetScoreButton().setOnAction(e -> resetScore());
        gameView.getCanvas().setOnMouseClicked(e -> handleCanvasClick(e.getX(), e.getY()));
        gameView.getCanvas().setOnMouseMoved(e -> handleMouseMoved(e.getX(), e.getY()));
    }

    private void handleCanvasClick(double mouseX, double mouseY) {
        if (ammo <= 0) {
            gameView.showMessage("Out of ammo! Reload!");
            gameView.render();
            return;
        }

        ammo--;
        gameView.updateAmmo(ammo);

        if (gameView.isTargetHit(mouseX, mouseY)) {
            score += 10;
            gameView.updateScore(score);
            gameView.showMessage("Hit!");
            gameView.moveTargetRandomly();
        } else {
            gameView.showMessage("Miss!");
        }

        gameView.render();
    }

    private void handleMouseMoved(double mouseX, double mouseY) {
        gameView.updateMousePosition(mouseX, mouseY);
        gameView.render();
    }

    private void startGame() {
        stage.setScene(gameView.getScene());
        stage.setTitle("Midnight Caliber - Game");
        gameView.showMessage("Game started!");
        gameView.updateScore(score);
        gameView.updateAmmo(ammo);
        gameView.render();
        gameLoop.start();
    }

    private void returnToMenu() {
        gameLoop.stop();
        stage.setScene(mainMenuView.getScene());
        stage.setTitle("Midnight Caliber");
    }

    private void addScore() {
        score += 10;
        gameView.updateScore(score);
        gameView.showMessage("Score increased!");
        gameView.render();
    }

    private void resetScore() {
        score = 0;
        ammo = maxAmmo;
        gameView.updateScore(score);
        gameView.updateAmmo(ammo);
        gameView.showMessage("Game reset!");
        gameView.render();
    }

    private void exitGame() {
        stage.close();
    }
    private void reloadAmmo() {
        ammo = maxAmmo;
        gameView.updateAmmo(ammo);
        gameView.showMessage("Reloaded!");
        gameView.render();
    }
}