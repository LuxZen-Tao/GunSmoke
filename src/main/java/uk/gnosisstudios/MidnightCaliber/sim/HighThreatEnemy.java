package uk.gnosisstudios.MidnightCaliber.sim;

public class HighThreatEnemy extends Enemy {
    public HighThreatEnemy() {
        super("High Threat Enemy", 100, 5);
    }

    @Override
    public int attackDamage() {
        return 25;
    }
}
