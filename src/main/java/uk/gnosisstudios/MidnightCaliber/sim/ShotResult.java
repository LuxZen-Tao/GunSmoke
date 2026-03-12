package uk.gnosisstudios.MidnightCaliber.sim;

public class ShotResult {
    private final boolean hit;
    private final boolean jammed;
    private final boolean outOfAmmo;
    private final boolean noWeapon;
    private final int damage;
    private final int roundsFired;

    public ShotResult(boolean hit, boolean jammed, boolean outOfAmmo, int damage, int roundsFired) {
        this(hit, jammed, outOfAmmo, false, damage, roundsFired);
    }

    public ShotResult(boolean hit, boolean jammed, boolean outOfAmmo, boolean noWeapon, int damage, int roundsFired) {
        this.hit = hit;
        this.jammed = jammed;
        this.outOfAmmo = outOfAmmo;
        this.noWeapon = noWeapon;
        this.damage = damage;
        this.roundsFired = roundsFired;
    }

    public static ShotResult noWeapon() {
        return new ShotResult(false, false, false, true, 0, 0);
    }

    public boolean isHit() {
        return hit;
    }

    public boolean isJammed() {
        return jammed;
    }

    public boolean isOutOfAmmo() {
        return outOfAmmo;
    }

    public boolean isNoWeapon() {
        return noWeapon;
    }

    public int getDamage() {
        return damage;
    }

    public int getRoundsFired() {
        return roundsFired;
    }

    @Override
    public String toString() {
        return "ShotResult{" +
                "hit=" + hit +
                ", jammed=" + jammed +
                ", outOfAmmo=" + outOfAmmo +
                ", noWeapon=" + noWeapon +
                ", damage=" + damage +
                ", roundsFired=" + roundsFired +
                '}';
    }
}
