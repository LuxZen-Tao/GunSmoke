package uk.gnosisstudios.MidnightCaliber.sim;

public class Pistol extends Gun {
    public Pistol() {
        super("M9 Beretta", Caliber.NINE_MM, 20, 35, 0.02, 0.8, FireMode.SEMI_AUTO);
    }

    @Override
    public ShotResult shoot(Target target) {
        return super.shoot(target);
    }
}
