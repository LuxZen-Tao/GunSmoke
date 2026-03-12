package uk.gnosisstudios.MidnightCaliber.sim;

public class Pistol extends Gun {
    public Pistol() {
        super("M9 Beretta", Caliber.NINE_MM, 20, 35, 0.02, 0.8, FireMode.SEMI_AUTO);
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
            target.takeDamage(damage);
        }

        return new ShotResult(hit, false, false, damage, 1);
    }
}
