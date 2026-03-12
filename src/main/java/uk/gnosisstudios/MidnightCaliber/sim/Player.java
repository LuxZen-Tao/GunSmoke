package uk.gnosisstudios.MidnightCaliber.sim;

public class Player {
    private final String name;
    private int health = 100;
    private int lives = 3;
    private PlayerState currentState = PlayerState.IN_COVER;
    private Gun equippedGun;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getLives() {
        return lives;
    }

    public PlayerState getCurrentState() {
        return currentState;
    }

    public Gun getEquippedGun() {
        return equippedGun;
    }

    public void duck() {
        currentState = PlayerState.IN_COVER;
    }

    public void peek() {
        currentState = PlayerState.AIMING;
    }

    public void equipGun(Gun gun) {
        this.equippedGun = gun;
    }

    // compile-compat alias for existing controller usage
    public void setGun(Gun gun) {
        equipGun(gun);
    }

    public boolean reloadGun(Magazine magazine) {
        if (equippedGun == null || magazine == null) {
            return false;
        }

        Magazine old = equippedGun.ejectMagazine();
        equippedGun.insertMagazine(magazine);
        if (equippedGun.getMagazine() == magazine) {
            return true;
        }

        if (old != null) {
            equippedGun.insertMagazine(old);
        }
        return false;
    }

    public ShotResult pullTrigger(Target target) {
        if (equippedGun == null || currentState == PlayerState.IN_COVER) {
            return new ShotResult(false, false, true, 0, 0);
        }
        return equippedGun.shoot(target);
    }

    public void takeDamage(int amount) {
        if (amount <= 0 || lives <= 0) {
            return;
        }

        int applied = currentState == PlayerState.IN_COVER ? (int) Math.ceil(amount * 0.25) : amount;
        health = Math.max(0, health - applied);

        if (health == 0) {
            lives--;
            health = lives > 0 ? 100 : 0;
        }
    }

    public boolean isAlive() {
        return lives > 0;
    }
}
