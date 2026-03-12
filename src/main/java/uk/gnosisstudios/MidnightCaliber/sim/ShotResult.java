package uk.gnosisstudios.MidnightCaliber.sim;

public class ShotResult {
    private final boolean hit;
    private final boolean jammed;
    private final boolean outOfAmmo;
    private final int damage;
    private final int roundsFired;

    public ShotResult(boolean hit, boolean jammed, boolean outOfAmmo, int damage, int roundsFired) {
        this.hit = hit;
        this.jammed = jammed;
        this.outOfAmmo = outOfAmmo;
        this.damage = damage;
        this.roundsFired = roundsFired;
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

    public int getDamage() {
        return damage;
    }

    public int getRoundsFired() {
        return roundsFired;
    }
}
