package uk.gnosisstudios.MidnightCaliber.sim;

public abstract class Gun {
    protected String name;
    protected Magazine loadedMag;
    protected double jamChance; // e.g., 0.02 for 2%
    protected FireMode fireMode;

    public Gun(String name, double jamChance, FireMode fireMode) {
        this.name = name;
        this.jamChance = jamChance;
        this.fireMode = fireMode;
    }

    // Abstract method: Every gun shoots differently!
    public abstract void shoot();

    // Overloaded method: Shoot multiple times (Polymorphism)
    public void shoot(int rounds) {
        for (int i = 0; i < rounds; i++) {
            shoot();
        }
    }

    public void reload(Magazine mag) {
        if (mag.getCaliber().equals(this.getRequiredCaliber())) {
            this.loadedMag = mag;
            System.out.println(name + " reloaded.");
        } else {
            System.out.println("Wrong magazine caliber!");
        }
    }

    public Magazine ejectMag() {
        Magazine oldMag = this.loadedMag;
        this.loadedMag = null;
        return oldMag;
    }

    // Every specific gun must define its caliber
    public abstract String getRequiredCaliber();
}
