package uk.gnosisstudios.MidnightCaliber.sim;

public class AssaultRifle extends Gun {
    public AssaultRifle() {
        super("M4A1", 0.08, FireMode.FULL_AUTO);
    }

    @Override
    public void shoot() {
        if (loadedMag != null && !loadedMag.isEmpty()) {
            loadedMag.popBullet();
            System.out.println("BOOM! 5.56mm round away.");
        }
    }

    @Override
    public String getRequiredCaliber() {
        return "5.56mm";
    }
}
