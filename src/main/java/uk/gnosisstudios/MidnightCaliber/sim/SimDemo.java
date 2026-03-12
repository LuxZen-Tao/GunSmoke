package uk.gnosisstudios.MidnightCaliber.sim;

public class SimDemo {
    public static void main(String[] args) {
        Pistol pistol = new Pistol();
        Magazine mag = new Magazine(6, Caliber.NINE_MM);

        while (!mag.isFull()) {
            mag.loadBullet(Caliber.NINE_MM);
        }

        pistol.insertMagazine(mag);

        Target target = new Target("Training Dummy", 120, false);
        ShotResult result = pistol.shoot(target);

        System.out.println("Gun: " + pistol.getName());
        System.out.println("Rounds fired: " + result.getRoundsFired());
        System.out.println("Hit: " + result.isHit());
        System.out.println("Jammed: " + result.isJammed());
        System.out.println("Out of ammo: " + result.isOutOfAmmo());
        System.out.println("Damage dealt: " + result.getDamage());
        System.out.println("Target health: " + target.getHealth());
        System.out.println("Remaining rounds: " + pistol.getMagazine().getBulletCount());
    }
}
