package uk.gnosisstudios.MidnightCaliber.sim;

import java.util.Stack;

public class Magazine {
    private final int capacity;
    private final Stack<Bullet> bullets;
    private final Caliber caliber;

    public Magazine(int capacity, Caliber caliber) {
        this.capacity = capacity;
        this.caliber = caliber;
        this.bullets = new Stack<>();
    }

    public int getCapacity() {
        return capacity;
    }

    public int getBulletCount() {
        return bullets.size();
    }

    public Caliber getCaliber() {
        return caliber;
    }

    public boolean isFull() {
        return bullets.size() == capacity;
    }

    public boolean isEmpty() {
        return bullets.isEmpty();
    }

    public boolean loadBullet(Bullet bullet) {
        if (bullet == null || isFull() || bullet.getCaliber() != caliber) {
            return false;
        }

        bullets.push(bullet);
        return true;
    }

    public boolean loadBullet(Caliber caliber) {
        return loadBullet(new Bullet(caliber));
    }

    public Bullet removeBullet() {
        if (isEmpty()) {
            return null;
        }
        return bullets.pop();
    }
}
