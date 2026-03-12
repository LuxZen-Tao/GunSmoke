package uk.gnosisstudios.MidnightCaliber.sim;

public class Target {
    private final String name;
    private int health;
    private final boolean shootsBack;

    public Target(String name, int health, boolean shootsBack) {
        this.name = name;
        this.health = Math.max(0, health);
        this.shootsBack = shootsBack;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public boolean canShootBack() {
        return shootsBack;
    }

    public void takeDamage(int damage) {
        if (damage <= 0 || !isAlive()) {
            return;
        }
        health = Math.max(0, health - damage);
    }

    public void takeDamage(int damage, double multiplier) {
        int adjustedDamage = (int) Math.round(Math.max(0, damage) * Math.max(0.0, multiplier));
        takeDamage(adjustedDamage);
    }
}
