package uk.gnosisstudios.MidnightCaliber.sim;

import java.util.ArrayList;
import java.util.List;

public class LevelManager {
    public enum GameState { WAITING, IN_COMBAT, WAVE_CLEARED, GAME_OVER }

    private int currentWave = 1;
    private List<Target> activeTargets = new ArrayList<>();
    private GameState currentState = GameState.WAITING;
    private Player player;

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void reset() {
        currentWave = 1;
        activeTargets = new ArrayList<>();
        currentState = GameState.WAITING;
    }

    public void startWave() {
        activeTargets = new ArrayList<>();
        for (int i = 0; i < currentWave; i++) {
            activeTargets.add(new HighThreatEnemy());
        }
        currentState = GameState.IN_COMBAT;
    }

    public boolean processPlayerShot(int damage) {
        for (Target target : activeTargets) {
            if (target.isAlive()) {
                target.takeDamage(damage);
                return true;
            }
        }
        return false;
    }

    public void update() {
        if (currentState != GameState.IN_COMBAT) {
            return;
        }

        if (player != null && player.isAlive()) {
            for (Target target : activeTargets) {
                if (target instanceof Enemy enemy && enemy.isAlive()) {
                    enemy.attack(player);
                }
            }
        }

        activeTargets.removeIf(t -> !t.isAlive());

        if (activeTargets.isEmpty()) {
            currentState = GameState.WAVE_CLEARED;
            currentWave++;
        }

        if (player != null && !player.isAlive()) {
            currentState = GameState.GAME_OVER;
        }
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public List<Target> getActiveTargets() {
        return activeTargets;
    }
}
