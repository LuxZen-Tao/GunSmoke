package uk.gnosisstudios.MidnightCaliber.sim;


import java.util.Stack;

public class Magazine {
    private int capacity;
    private Stack<Bullet> bullets;
    private String caliber;

    public Magazine(int capacity, String caliber) {
        this.capacity = capacity;
        this.caliber = caliber;
        this.bullets = new Stack<>();
    }

    public void loadBullet(Bullet bullet) {
        if (bullets.size() < capacity && bullet.caliber.equals(this.caliber)) {
            bullets.push(bullet);
        } else {
            System.out.println("Cannot load: Magazine full or caliber mismatch!");
        }
    }

    public Bullet popBullet() {
        return bullets.isEmpty() ? null : bullets.pop();
    }

    public boolean isFull() { return bullets.size() == capacity; }
    public boolean isEmpty() { return bullets.isEmpty(); }
    public String getCaliber() { return caliber; }
}