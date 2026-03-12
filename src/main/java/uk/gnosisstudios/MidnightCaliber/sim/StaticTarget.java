package uk.gnosisstudios.MidnightCaliber.sim;

public class StaticTarget extends Target {
    public StaticTarget(int health) { this.health = health; }

    @Override
    public void onHit(int damage) {
        this.health -= damage;
        System.out.println("Object damaged. Splinters everywhere!");
        if (health <= 0) this.setVisible(false);
    }
}
