package uk.gnosisstudios.MidnightCaliber.sim;

public class AssaultRifle extends Gun {
    public AssaultRifle() {
        super("M4A1", Caliber.FIVE_FIVE_SIX, 22, 34, 0.025, 0.85, FireMode.FULL_AUTO);
    }

    @Override
    public ShotResult shoot(Target target) {
        return shoot(target, 3);
    }

    @Override
    public ShotResult shoot(Target target, int rounds) {
        int burstSize = Math.min(Math.max(1, rounds), 6);
        int totalDamage = 0;
        int roundsFired = 0;
        boolean hit = false;

        for (int i = 0; i < burstSize; i++) {
            if (!canShoot()) {
                return new ShotResult(hit, false, roundsFired == 0, totalDamage, roundsFired);
            }
            if (isJammed()) {
                return new ShotResult(hit, true, false, totalDamage, roundsFired);
            }

            magazine.removeBullet();
            roundsFired++;

            boolean roundHit = target != null && target.isAlive() && rollHit();
            if (roundHit) {
                int damage = rollDamage();
                totalDamage += damage;
                hit = true;
                target.takeDamage(damage);
            }
        }

        return new ShotResult(hit, false, false, totalDamage, roundsFired);
    }
}
