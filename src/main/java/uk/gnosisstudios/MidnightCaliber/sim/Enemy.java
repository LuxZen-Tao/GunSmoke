package uk.gnosisstudios.MidnightCaliber.sim;

public abstract class Enemy extends Target {
    protected int threatLevel; // 1 (Low) to 5 (High)

    public Enemy(int health, int threatLevel) {
        this.health = health;
        this.threatLevel = threatLevel;
    }

    public abstract void attack(Player p);
}
