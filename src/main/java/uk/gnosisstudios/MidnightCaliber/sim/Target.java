package uk.gnosisstudios.MidnightCaliber.sim;

public abstract class Target {
    protected int health;
    protected boolean isVisible = true;

    public abstract void onHit(int damage); // Unique reaction to being shot

    public void setVisible(boolean visible) { this.isVisible = visible; }
    public boolean isVisible() { return isVisible; }
}
