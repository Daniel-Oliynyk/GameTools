package gametools.gravity;

import gametools.Game;
import gametools.Group;

public abstract class GravityGame extends Game {
    private static final Group platforms = new Group(), objects = new Group();
    private static double defaultMultiplier = 1, defaultTerminalVelocity = 25, scanDistance = 4;
    
    public static Group platforms() {
        return platforms;
    }
    
    public static Group characters() {
        return objects;
    }
    
    public static double getDefaultGravity() {
        return defaultMultiplier * 100;
    }
    
    public static double getDefaultTerminalVelocity() {
        return defaultTerminalVelocity;
    }
    
    public static void setDefaultGravity(double gravity) {
        defaultMultiplier = gravity / 100;
    }
    
    public static void setDefaultTerminalVelocity(double velocity) {
        defaultTerminalVelocity = velocity;
    }

    /**
     * Returns the amount of pixels traveled before scanning for collision.
     * @return The distance before checking collision.
     */
    public static double getScanDistance() {
        return scanDistance;
    }

    /**
     * Sets the amount of pixels the object can fall before collision is checked again.
     * Setting this value too small will result in poor performance, and setting it too
     * high results in inaccurate collision and in some cases even clipping through platforms.
     * @param scanDistance The amount of pixels to travel before checking collision.
     */
    public static void setScanDistance(double scanDistance) {
        GravityGame.scanDistance = scanDistance;
    }
}
