package uk.gnosisstudios.MidnightCaliber.sim;

public class HighThreatEnemy extends Enemy {
    public HighThreatEnemy() { super(100, 5); }

    @Override
    public void onHit(int damage) {
        this.health -= damage;
        System.out.println("Enemy staggered!");
        if (health <= 0) {
            this.setVisible(false);
            System.out.println("High-threat enemy neutralized.");
        }
    }

    @Override
    public void attack(Player p) {
        // High threat enemies hit harder
        p.takeDamage(25);
    }
}
