package uk.gnosisstudios.MidnightCaliber.sim;

public class Pistol extends Gun {
    public Pistol() {
        super("M9 Beretta", 0.02, FireMode.SEMI_AUTO); // 2% jam chance
    }

    @Override
    public void shoot() {
        if (loadedMag == null || loadedMag.isEmpty()) {
            System.out.println(" *Click* ... Out of ammo!");
            return;
        }

        // Logic for Jamming (Basic Java Skill: Random)
        if (Math.random() < jamChance) {
            System.out.println("JAMMED! Eject the mag to clear.");
            return;
        }

        loadedMag.popBullet();
        System.out.println("BANG! Pistol fired.");
    }

    @Override
    public String getRequiredCaliber() {
        return "9mm";
    }
}
