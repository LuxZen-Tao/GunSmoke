package uk.gnosisstudios.MidnightCaliber.sim;

public class Enemy extends Target {
    private final int threatLevel;

    public Enemy(String name, int health, int threatLevel) {
        super(name, health, true);
        this.threatLevel = threatLevel;
    }

    public int getThreatLevel() {
        return threatLevel;
    }

    public int attackDamage() {
        return 10 + (threatLevel * 3);
    }

    public void attack(Player player) {
        if (player != null && isAlive()) {
            player.takeDamage(attackDamage());
        }
    }
}
