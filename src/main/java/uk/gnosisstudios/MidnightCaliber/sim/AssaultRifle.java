package uk.gnosisstudios.MidnightCaliber.sim;

public class AssaultRifle extends Gun {
    public AssaultRifle() {
        super("M4A1", Caliber.FIVE_FIVE_SIX, 22, 34, 0.025, 0.85, FireMode.FULL_AUTO);
    }

    @Override
    public ShotResult shoot(Target target) {
        return super.shoot(target, 3);
    }

    @Override
    public ShotResult shoot(Target target, int rounds) {
        int burstSize = Math.min(Math.max(1, rounds), 6);
        return super.shoot(target, burstSize);
    }
}
