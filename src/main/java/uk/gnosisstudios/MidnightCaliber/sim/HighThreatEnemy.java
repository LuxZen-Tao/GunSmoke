package uk.gnosisstudios.MidnightCaliber.sim;

public class HighThreatEnemy extends Enemy {
    public HighThreatEnemy() { super(100, 5); }

    @Override
    public void onHit(int damage) {
        this.health -= damage;
        System.out.println("Enemy staggered!");
    }

    @Override
    public void attack(Player p) {
        // High threat enemies hit harder
        p.takeDamage(25);
    }
}
