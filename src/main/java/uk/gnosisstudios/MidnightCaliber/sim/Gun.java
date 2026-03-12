package uk.gnosisstudios.MidnightCaliber.sim;

import java.util.Random;

public abstract class Gun {
    protected final String name;
    protected final Caliber caliber;
    protected final int minDamage;
    protected final int maxDamage;
    protected final double jamChance;
    protected final double accuracy;
    protected Magazine magazine;
    protected final FireMode fireMode;
    protected final Random random = new Random();

    protected Gun(String name, Caliber caliber, int minDamage, int maxDamage, double jamChance, double accuracy, FireMode fireMode) {
        this.name = name;
        this.caliber = caliber;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.jamChance = jamChance;
        this.accuracy = accuracy;
        this.fireMode = fireMode;
    }

    public String getName() {
        return name;
    }

    public Caliber getCaliber() {
        return caliber;
    }

    public FireMode getFireMode() {
        return fireMode;
    }

    public Magazine getMagazine() {
        return magazine;
    }

    public boolean hasMagazine() {
        return magazine != null;
    }

    public void insertMagazine(Magazine magazine) {
        if (magazine == null || magazine.getCaliber() != caliber) {
            return;
        }
        this.magazine = magazine;
    }

    public Magazine ejectMagazine() {
        Magazine old = this.magazine;
        this.magazine = null;
        return old;
    }

    public boolean canShoot() {
        return hasMagazine() && !magazine.isEmpty();
    }

    public boolean isJammed() {
        return random.nextDouble() < jamChance;
    }

    public int rollDamage() {
        if (maxDamage <= minDamage) {
            return minDamage;
        }
        return random.nextInt(maxDamage - minDamage + 1) + minDamage;
    }

    protected boolean rollHit() {
        return random.nextDouble() <= accuracy;
    }

    protected int adjustDamage(Target target, int baseDamage) {
        return baseDamage;
    }

    public ShotResult shoot(Target target) {
        return shoot(target, 1);
    }

    public ShotResult shoot(Target target, int rounds) {
        int attempts = Math.max(0, rounds);
        int totalDamage = 0;
        int roundsFired = 0;
        boolean hit = false;

        for (int i = 0; i < attempts; i++) {
            if (!canShoot()) {
                return new ShotResult(hit, false, true, totalDamage, roundsFired);
            }

            if (isJammed()) {
                return new ShotResult(hit, true, false, totalDamage, roundsFired);
            }

            magazine.removeBullet();
            roundsFired++;

            boolean roundHit = target != null && target.isAlive() && rollHit();
            if (roundHit) {
                int damage = adjustDamage(target, rollDamage());
                target.takeDamage(damage);
                totalDamage += damage;
                hit = true;
            }

            if (target != null && !target.isAlive()) {
                break;
            }
        }

        return new ShotResult(hit, false, false, totalDamage, roundsFired);
    }
}
