package gametools.platforms;

import gametools.Animation;
import gametools.Graphic;
import gametools.Position;
import gametools.Sprite;
import java.awt.image.BufferedImage;

public class Mob extends Sprite {
    private double multiplier = 1, gravity;
    
    //<editor-fold defaultstate="collapsed" desc="Constructors">
    /* *
     * Creates a blank sprite without an image or position.
     */
    public Mob() {
        super();
    }
    
    /* *
     * Creates a sprite in the default location (the corner of the screen) with a custom image.
     * @param image The image for the sprite.
     */
    public Mob(BufferedImage image) {
        super(image);
    }
    
    /* *
     * Creates a sprite in the default location (the corner of the screen) with a custom animation.
     * @param animation The animation for the sprite.
     */
    public Mob(Animation animation) {
        super(animation);
    }
    
    /* *
     * Creates a sprite and copies over all the properties from the graphic.
     * @param graphic The graphic to copy the properties from.
     */
    public Mob(Graphic graphic) {
        super(graphic);
    }
    
    /* *
     * Creates a sprite at the specified coordinates with a custom image.
     * @param pos The position of the sprite.
     * @param image The image for the sprite.
     */
    public Mob(Position pos, BufferedImage image) {
        super(pos, image);
    }
    
    /* *
     * Creates a sprite at the specified coordinates with a custom animation.
     * @param pos The position of the sprite.
     * @param animation The animation for the sprite.
     */
    public Mob(Position pos, Animation animation) {
        super(pos, animation);
    }
    
    /* *
     * Creates an exact copy of the passed in sprite.
     * @param sprite The sprite to copy properties from.
     */
    public Mob(Sprite sprite) {
        super(sprite);
    }
    
    public Mob(Mob mob) {
        super(mob);
        gravity = mob.gravity;
    }
    //</editor-fold>
    
    public void setGravity(double gravity) {
        multiplier = gravity;
    }
}
