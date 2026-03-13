package uk.gnosisstudios.MidnightCaliber.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.List;
import uk.gnosisstudios.MidnightCaliber.sim.Target;

public class GameView {
    private double mouseX = 250;
    private double mouseY = 125;

    // Active sim targets to render
    private List<Target> activeTargets = new ArrayList<>();

    Label titleLabel = new Label("Midnight Caliber");
    Label scoreLabel = new Label("Score: 0");
    Label gameAreaLabel = new Label("Behind the bar");
    Label ammoLabel = new Label("Ammo: 6");
    Label healthLabel = new Label("HP: 100  Lives: 3");
    Button reloadButton = new Button("Reload");

    Button scoreAddButton = new Button("Fire");
    Button resetScoreButton = new Button("Restart");
    Button mainMenuButton = new Button("Main Menu");

    private Canvas canvas = new Canvas(500, 250);
    private GraphicsContext gc = canvas.getGraphicsContext2D();

    BorderPane root = new BorderPane();
    Scene scene;

    // Rendering constants
    private static final double ENEMY_SIZE = 50;
    private static final double ENEMY_ROW_Y = 40;
    private static final double PLAYER_X = 60;
    private static final double PLAYER_Y = 185;
    private static final double PLAYER_RADIUS = 25;
    private static final double HEALTH_BAR_HEIGHT = 6;
    private static final double ENEMY_MAX_HEALTH = 100.0;

    public GameView() {
        buildUI();
        render();
    }

    /** Sync the list of active sim targets so the renderer reflects the current sim state. */
    public void setActiveTargets(List<Target> targets) {
        this.activeTargets = targets != null ? targets : new ArrayList<>();
    }

    private void buildUI() {
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(20));
        topBar.setSpacing(15);

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topBar.getChildren().addAll(titleLabel, spacer, scoreLabel, ammoLabel, healthLabel);
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

    public void updateHealth(int health, int lives) {
        healthLabel.setText("HP: " + health + "  Lives: " + lives);
    }

    /** Returns the canvas-space [x, y] top-left corner of the enemy square at the given index. */
    private double[] enemyPosition(int index, int total) {
        double spacing = canvas.getWidth() / (total + 1.0);
        double x = spacing * (index + 1) - ENEMY_SIZE / 2;
        return new double[]{x, ENEMY_ROW_Y};
    }

    public void render() {
        double w = canvas.getWidth();
        double h = canvas.getHeight();

        gc.clearRect(0, 0, w, h);

        // Background
        gc.setFill(Color.DARKSLATEGRAY);
        gc.fillRect(0, 0, w, h);

        // Cover bar (brown bar at bottom — player hides behind it)
        gc.setFill(Color.SADDLEBROWN);
        gc.fillRect(0, h - 50, w, 50);

        // Player — blue circle peeking above the cover bar
        gc.setFill(Color.STEELBLUE);
        gc.fillOval(PLAYER_X - PLAYER_RADIUS, PLAYER_Y - PLAYER_RADIUS,
                PLAYER_RADIUS * 2, PLAYER_RADIUS * 2);
        gc.setStroke(Color.LIGHTBLUE);
        gc.setLineWidth(2);
        gc.strokeOval(PLAYER_X - PLAYER_RADIUS, PLAYER_Y - PLAYER_RADIUS,
                PLAYER_RADIUS * 2, PLAYER_RADIUS * 2);

        // Aim line from player to crosshair
        gc.setStroke(Color.color(1, 1, 0, 0.4));
        gc.setLineWidth(1);
        gc.strokeLine(PLAYER_X, PLAYER_Y, mouseX, mouseY);

        // Enemies — red squares with crosshair lines and health bars
        int total = activeTargets.size();
        for (int i = 0; i < total; i++) {
            Target target = activeTargets.get(i);
            double[] pos = enemyPosition(i, total);
            double ex = pos[0];
            double ey = pos[1];

            // Enemy body (square)
            gc.setFill(Color.CRIMSON);
            gc.fillRect(ex, ey, ENEMY_SIZE, ENEMY_SIZE);
            gc.setStroke(Color.DARKRED);
            gc.setLineWidth(2);
            gc.strokeRect(ex, ey, ENEMY_SIZE, ENEMY_SIZE);

            // Crosshair lines inside the square
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            gc.strokeLine(ex + ENEMY_SIZE / 2, ey, ex + ENEMY_SIZE / 2, ey + ENEMY_SIZE);
            gc.strokeLine(ex, ey + ENEMY_SIZE / 2, ex + ENEMY_SIZE, ey + ENEMY_SIZE / 2);

            // Health bar below the square
            double barY = ey + ENEMY_SIZE + 4;
            double hpFraction = Math.max(0.0, Math.min(1.0, target.getHealth() / ENEMY_MAX_HEALTH));
            gc.setFill(Color.DARKRED);
            gc.fillRect(ex, barY, ENEMY_SIZE, HEALTH_BAR_HEIGHT);
            gc.setFill(Color.LIMEGREEN);
            gc.fillRect(ex, barY, ENEMY_SIZE * hpFraction, HEALTH_BAR_HEIGHT);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            gc.strokeRect(ex, barY, ENEMY_SIZE, HEALTH_BAR_HEIGHT);
        }

        // Crosshair cursor (red)
        gc.setStroke(Color.RED);
        gc.setLineWidth(1.5);
        gc.strokeLine(mouseX - 10, mouseY, mouseX + 10, mouseY);
        gc.strokeLine(mouseX, mouseY - 10, mouseX, mouseY + 10);
        gc.strokeOval(mouseX - 15, mouseY - 15, 30, 30);
    }

    /**
     * Returns the first alive Target whose rendered square contains the given canvas coordinates,
     * or {@code null} if no enemy occupies that position.
     */
    public Target getTargetAt(double clickX, double clickY) {
        int total = activeTargets.size();
        for (int i = 0; i < total; i++) {
            double[] pos = enemyPosition(i, total);
            double ex = pos[0];
            double ey = pos[1];
            if (clickX >= ex && clickX <= ex + ENEMY_SIZE
                    && clickY >= ey && clickY <= ey + ENEMY_SIZE) {
                return activeTargets.get(i);
            }
        }
        return null;
    }

    /** @deprecated Use {@link #getTargetAt(double, double)} instead. */
    @Deprecated
    public boolean isTargetHit(double mouseX, double mouseY) {
        return getTargetAt(mouseX, mouseY) != null;
    }

    public void update() {
        // Entity positions are determined by sim state — nothing to animate here.
    }

    /** @deprecated Target layout is now driven by the sim's active-targets list. */
    @Deprecated
    public void moveTargetRandomly() {
        // No-op: target layout is driven by the sim's active-targets list.
    }

    public void updateMousePosition(double x, double y) {
        mouseX = x;
        mouseY = y;
    }
}