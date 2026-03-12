package uk.gnosisstudios.MidnightCaliber.sim;

public class Bullet {
    private final Caliber caliber;

    public Bullet(Caliber caliber) {
        this.caliber = caliber;
    }

    public Caliber getCaliber() {
        return caliber;
    }
}
