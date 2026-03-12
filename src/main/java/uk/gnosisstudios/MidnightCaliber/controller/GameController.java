package uk.gnosisstudios.MidnightCaliber.controller;

import javafx.animation.AnimationTimer;
import javafx.stage.Stage;
import uk.gnosisstudios.MidnightCaliber.UI.GameView;
import uk.gnosisstudios.MidnightCaliber.UI.MainMenuView;
import uk.gnosisstudios.MidnightCaliber.sim.LevelManager;
import uk.gnosisstudios.MidnightCaliber.sim.Pistol;
import uk.gnosisstudios.MidnightCaliber.sim.Player;

public class GameController {
    private final Stage stage;
    private final MainMenuView mainMenuView;
    private final GameView gameView;
    private AnimationTimer gameLoop;

    // Logic Model components
    private final Player player;
    private final LevelManager levelManager;

    // Game state
    private int score = 0;
    private final int maxAmmo = 6;
    private int ammo = maxAmmo;

    public GameController(Stage stage, MainMenuView mainMenuView, GameView gameView) {
        this.stage = stage;
        this.mainMenuView = mainMenuView;
        this.gameView = gameView;

        this.player = new Player("Operator");
        this.player.setGun(new Pistol());
        this.levelManager = new LevelManager();
        this.levelManager.setPlayer(player);

        wireEvents();
        setupLoop();
    }

    private void setupLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                levelManager.update();
                gameView.update();
                gameView.render();
            }
        };
    }

    private void wireEvents() {
        mainMenuView.getStartButton().setOnAction(e -> startGame());
        mainMenuView.getExitButton().setOnAction(e -> exitGame());
        mainMenuView.getSettingsButton().setOnAction(e -> gameView.showMessage("Settings coming soon."));
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

        boolean hitViewTarget = gameView.isTargetHit(mouseX, mouseY);
        boolean hitSimTarget = levelManager.processPlayerShot(50);

        if (hitViewTarget && hitSimTarget) {
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
        resetStateForNewRun();
        levelManager.startWave();

        stage.setScene(gameView.getScene());
        stage.setTitle("Midnight Caliber - Game");
        gameView.showMessage("Game started!");
        gameView.updateScore(score);
        gameView.updateAmmo(ammo);
        gameView.render();
        gameLoop.start();
    }

    private void resetStateForNewRun() {
        score = 0;
        ammo = maxAmmo;
        levelManager.reset();
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
        levelManager.reset();
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
