package uk.gnosisstudios.MidnightCaliber.sim;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private IntegerProperty health = new SimpleIntegerProperty(100);
    private int lives = 3;
    private PlayerState currentState = PlayerState.IN_COVER;

    private Gun currentGun;
    private List<Magazine> vest = new ArrayList<>(); // Your "inventory"

    public Player(String name) {
        this.name = name;
    }

    // --- Duck & Peek System ---
    public void duck() {
        this.currentState = PlayerState.IN_COVER;
        System.out.println(name + " is safe behind cover.");
    }

    public void peek() {
        this.currentState = PlayerState.AIMING;
        System.out.println(name + " is popping out to shoot!");
    }

    // --- Combat Logic ---
    public void pullTrigger() {
        if (currentState == PlayerState.IN_COVER) {
            System.out.println("Can't shoot while in cover!");
            return;
        }

        if (currentGun != null) {
            currentGun.shoot();
        } else {
            System.out.println("No gun equipped!");
        }
    }

    public void reloadGun() {
        // Simple logic: find the first mag in the vest that matches the gun
        for (Magazine mag : vest) {
            if (mag.getCaliber().equals(currentGun.getRequiredCaliber())) {
                Magazine oldMag = currentGun.ejectMag();
                if (oldMag != null) vest.add(oldMag); // Keep the empty mag to refill later

                currentGun.reload(mag);
                vest.remove(mag);
                return;
            }
        }
        System.out.println("No matching ammo in vest!");
    }

    // --- Getters and Setters ---
    public void takeDamage(int amount) {
        if (currentState == PlayerState.AIMING) {
            health.set(health.get() - amount);
            if (health.get() <= 0) {
                lives--;
                health.set(100);
                System.out.println("Life lost! Remaining: " + lives);
            }
        } else {
            System.out.println("Blocked! Damage taken while in cover: 0");
        }
    }

    public IntegerProperty healthProperty() { return health; }
    public void setGun(Gun gun) { this.currentGun = gun; }
}
