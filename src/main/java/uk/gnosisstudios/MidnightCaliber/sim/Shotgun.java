package uk.gnosisstudios.MidnightCaliber.sim;

public class Shotgun extends Gun {
    public Shotgun() {
        super("Remington 870", Caliber.TWELVE_GAUGE, 40, 90, 0.01, 0.6, FireMode.SEMI_AUTO);
    }

    @Override
    public ShotResult shoot(Target target) {
        if (!canShoot()) {
            return new ShotResult(false, false, true, 0, 0);
        }

        if (isJammed()) {
            return new ShotResult(false, true, false, 0, 0);
        }

        magazine.removeBullet();
        boolean hit = target != null && target.isAlive() && rollHit();
        int damage = hit ? rollDamage() : 0;
        if (hit) {
            target.takeDamage(damage, 1.2);
            damage = (int) Math.round(damage * 1.2);
        }

        return new ShotResult(hit, false, false, damage, 1);
    }
}
