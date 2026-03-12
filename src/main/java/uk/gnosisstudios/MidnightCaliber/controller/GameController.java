package uk.gnosisstudios.MidnightCaliber.controller;

import javafx.animation.AnimationTimer;
import javafx.stage.Stage;
import uk.gnosisstudios.MidnightCaliber.sim.*;
import uk.gnosisstudios.MidnightCaliber.UI.*;

public class GameController {
    private Stage stage;
    private MainMenuView mainMenuView;
    private GameView gameView;
    private AnimationTimer gameLoop;

    // Logic Model components
    private Player player;
    private LevelManager levelManager;

    // Game state
    private int score = 0;
    private int maxAmmo = 6;
    private int ammo = maxAmmo;

    public GameController(Stage stage, MainMenuView mainMenuView, GameView gameView) {
        this.stage = stage;
        this.mainMenuView = mainMenuView;
        this.gameView = gameView;

        // Initialize your simulation model
        this.player = new Player("Operator");
        this.player.setGun(new Pistol()); // Default equipped gun
        this.levelManager = new LevelManager();
        this.levelManager.setPlayer(player);

        wireEvents();
        setupLoop();
    }

    private void setupLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Update the level logic
                levelManager.update();

                // Update target positions
                gameView.update();

                // Keep the UI in sync with the model
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