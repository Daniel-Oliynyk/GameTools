package gametools;

/**
 * Abstract class for adding custom script code to sprites.
 */
public abstract class Script {
    /**
     * An empty script to represent an undefined or unavailable script.
     */
    public static final Script UNDEFINED_SCRIPT = new Script() {
        @Override
        public void update() {}
    };
    
    /**
     * Method that runs right after the sprites own update method but before
     * the sprite is drawn.
     */
    public abstract void update();
}
