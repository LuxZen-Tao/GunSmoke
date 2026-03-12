package uk.gnosisstudios.MidnightCaliber.sim;

public class Shotgun extends Gun {
    public Shotgun() {
        super("Remington 870", 0.01, FireMode.SEMI_AUTO);
    }

    @Override
    public void shoot() {
        if (loadedMag != null && !loadedMag.isEmpty()) {
            loadedMag.popBullet();
            System.out.println("CH-CH... KABOOM! Wide spread damage.");
        }
    }

    @Override
    public String getRequiredCaliber() {
        return "12 Gauge";
    }
}
