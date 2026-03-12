package uk.gnosisstudios.MidnightCaliber.sim;

import java.util.ArrayList;
import java.util.List;

public class LevelManager {
    public void processPlayerShot(int damage) {
        for (Target t : activeTargets) {
            if (t.isVisible()) {
                t.onHit(damage);
                break;
            }
        }
    }

    public enum GameState { WAITING, IN_COMBAT, WAVE_CLEARED, GAME_OVER }

    private int currentWave = 1;
    private List<Target> activeTargets = new ArrayList<>();
    private GameState currentState = GameState.WAITING;
    private Player player;

    public void setPlayer(Player p) { this.player = p; }

    public void startWave() {
        this.activeTargets = new ArrayList<>();
        // Example: Add targets based on wave difficulty
        for (int i = 0; i < currentWave; i++) {
            activeTargets.add(new HighThreatEnemy());
        }
        this.currentState = GameState.IN_COMBAT;
        System.out.println("Wave " + currentWave + " started!");
    }

    public void update() {
        if (currentState != GameState.IN_COMBAT) return;

        // Logic for enemies attacking the player
        for (Target t : activeTargets) {
            if (t instanceof Enemy && t.isVisible()) {
                ((Enemy) t).attack(player);
            }
        }

        // Cleanup defeated targets
        activeTargets.removeIf(t -> !t.isVisible());

        // Check if wave is cleared
        if (activeTargets.isEmpty()) {
            this.currentState = GameState.WAVE_CLEARED;
            completeWave();
        }
    }

    private void completeWave() {
        System.out.println("Wave " + currentWave + " cleared.");
        this.currentWave++;
    }

    public GameState getCurrentState() { return currentState; }
    public List<Target> getActiveTargets() { return activeTargets; }
}