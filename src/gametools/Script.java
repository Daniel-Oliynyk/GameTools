package gametools;

/**
 * Abstract class for adding custom script code to sprites.
 */
public abstract class Script {
    /**
     * An empty script to represent an undefined or unavailable script.
     */
    public static final Script UNDEFINED_SCRIPT = new Script(new Sprite()) {
        @Override
        public void update() {}
    };
    private final Sprite sprite;

    /**
     * Creates a script that will act on the passed in sprite.
     * @param sprite A pointer to the sprite the script should act on.
     */
    public Script(Sprite sprite) {
        this.sprite = sprite;
    }
    
    /**
     * Returns a pointer to the script's sprite.
     * @return The sprite being scripted.
     */
    public final Sprite sprite() {
        return sprite;
    }
    
    /**
     * Method that runs right after the sprites own update method but before
     * the sprite is drawn.
     */
    public abstract void update();
}
