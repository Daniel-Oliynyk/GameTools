package gametools.gravity;

import gametools.Game;
import gametools.Group;

public abstract class GravityGame extends Game {
    private static final Group platforms = new Group(), objects = new Group();
    private static double defaultMultiplier = 1, defaultTerminalVelocity = 25;
    
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
}
