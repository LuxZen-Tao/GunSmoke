package uk.gnosisstudios.MidnightCaliber.sim;

public class MP5 extends Gun {
    public MP5() {
        super("MP5", Caliber.NINE_MM, 14, 24, 0.03, 0.75, FireMode.BURST);
    }

    @Override
    public ShotResult shoot(Target target) {
        return super.shoot(target, 3);
    }

    @Override
    public ShotResult shoot(Target target, int rounds) {
        int burst = Math.min(Math.max(1, rounds), 5);
        return super.shoot(target, burst);
    }
}
