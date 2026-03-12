package uk.gnosisstudios.MidnightCaliber.sim;

public class MP5 extends Gun {
    public MP5() {
        super("MP5", Caliber.NINE_MM, 14, 24, 0.03, 0.75, FireMode.BURST);
    }

    @Override
    public ShotResult shoot(Target target) {
        return shoot(target, 1);
    }

    @Override
    public ShotResult shoot(Target target, int rounds) {
        int burst = Math.min(Math.max(1, rounds), 5);
        int totalDamage = 0;
        int roundsFired = 0;
        boolean hit = false;

        for (int i = 0; i < burst; i++) {
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
