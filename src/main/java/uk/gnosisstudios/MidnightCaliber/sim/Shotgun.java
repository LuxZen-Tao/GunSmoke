package uk.gnosisstudios.MidnightCaliber.sim;

public class Shotgun extends Gun {
    public Shotgun() {
        super("Remington 870", Caliber.TWELVE_GAUGE, 40, 90, 0.01, 0.6, FireMode.SEMI_AUTO);
    }

    @Override
    protected int adjustDamage(Target target, int baseDamage) {
        return (int) Math.round(baseDamage * 1.2);
    }

    @Override
    public ShotResult shoot(Target target) {
        return super.shoot(target);
    }
}
