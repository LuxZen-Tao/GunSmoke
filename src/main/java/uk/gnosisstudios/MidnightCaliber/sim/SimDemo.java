package uk.gnosisstudios.MidnightCaliber.sim;

public class SimDemo {
    public static void main(String[] args) {
        Player player = new Player("Rookie");
        Gun pistol = new Pistol();

        Magazine mag = new Magazine(6, Caliber.NINE_MM);
        while (!mag.isFull()) {
            mag.loadBullet(Caliber.NINE_MM);
        }

        player.equipGun(pistol);
        player.reloadGun(mag);

        Target target = new StaticTarget(120);

        int ammoBefore = pistol.getMagazine() != null ? pistol.getMagazine().getBulletCount() : 0;
        int healthBefore = target.getHealth();

        ShotResult result = null;
        for (int i = 0; i < 3 && target.isAlive(); i++) {
            result = player.pullTrigger(target);
            if (result.isHit() || result.isOutOfAmmo() || result.isJammed()) {
                break;
            }
        }

        int ammoAfter = pistol.getMagazine() != null ? pistol.getMagazine().getBulletCount() : 0;
        int healthAfter = target.getHealth();

        System.out.println("Shot result: " + result);
        System.out.println("Target health: " + healthBefore + " -> " + healthAfter);
        System.out.println("Ammo count: " + ammoBefore + " -> " + ammoAfter);
    }
}
