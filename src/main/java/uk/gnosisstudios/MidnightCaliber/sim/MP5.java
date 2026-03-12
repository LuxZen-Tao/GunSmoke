package uk.gnosisstudios.MidnightCaliber.sim;

public class MP5 extends Gun {
    public MP5() {
        super("MP5", 0.05, FireMode.BURST);
    }

    @Override
    public void shoot() {
        if (loadedMag != null && !loadedMag.isEmpty()) {
            loadedMag.popBullet();
            System.out.println("Rat-tat-tat!");
        }
    }

    @Override
    public String getRequiredCaliber() {
        return "9mm";
    }
}
