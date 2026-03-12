package uk.gnosisstudios.MidnightCaliber.sim;

public class Civilian extends Target {
    public Civilian() {
        super("Civilian", 1, false);
    }

    @Override
    public void takeDamage(int damage) {
        if (damage > 0 && isAlive()) {
            super.takeDamage(getHealth());
        }
    }
}
