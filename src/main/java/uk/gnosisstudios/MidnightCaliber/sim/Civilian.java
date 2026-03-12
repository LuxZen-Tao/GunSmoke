package uk.gnosisstudios.MidnightCaliber.sim;

public class Civilian extends Target {
    @Override
    public void onHit(int damage) {
        System.out.println("DANGER: Civilian hit! Penalty to score applied.");
        this.setVisible(false); // They run away
    }
}


