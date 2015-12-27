package gametools.gravity;

import gametools.Animation;
import gametools.Area;
import gametools.Graphic;
import gametools.Position;
import gametools.Sprite;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

public class Mass extends Sprite {
    private double multiplier, gravity, terminalVelocity, boost;
    private boolean onGround;
    
    //<editor-fold defaultstate="collapsed" desc="Constructors">
    /* *
     * Creates a blank sprite without an image or position.
     */
    public Mass() {
        super();
        multiplier = GravityGame.getDefaultGravity() / 100;
        terminalVelocity = GravityGame.getDefaultTerminalVelocity();
    }
    
    /* *
     * Creates a sprite in the default location (the corner of the screen) with a custom image.
     * @param image The image for the sprite.
     */
    public Mass(BufferedImage image) {
        super(image);
        multiplier = GravityGame.getDefaultGravity() / 100;
        terminalVelocity = GravityGame.getDefaultTerminalVelocity();
    }
    
    /* *
     * Creates a sprite in the default location (the corner of the screen) with a custom animation.
     * @param animation The animation for the sprite.
     */
    public Mass(Animation animation) {
        super(animation);
        multiplier = GravityGame.getDefaultGravity() / 100;
        terminalVelocity = GravityGame.getDefaultTerminalVelocity();
    }
    
    /* *
     * Creates a sprite and copies over all the properties from the graphic.
     * @param graphic The graphic to copy the properties from.
     */
    public Mass(Graphic graphic) {
        super(graphic);
        multiplier = GravityGame.getDefaultGravity() / 100;
        terminalVelocity = GravityGame.getDefaultTerminalVelocity();
    }
    
    /* *
     * Creates a sprite at the specified coordinates with a custom image.
     * @param pos The position of the sprite.
     * @param image The image for the sprite.
     */
    public Mass(Position pos, BufferedImage image) {
        super(pos, image);
        multiplier = GravityGame.getDefaultGravity() / 100;
        terminalVelocity = GravityGame.getDefaultTerminalVelocity();
    }
    
    /* *
     * Creates a sprite at the specified coordinates with a custom animation.
     * @param pos The position of the sprite.
     * @param animation The animation for the sprite.
     */
    public Mass(Position pos, Animation animation) {
        super(pos, animation);
        multiplier = GravityGame.getDefaultGravity() / 100;
        terminalVelocity = GravityGame.getDefaultTerminalVelocity();
    }
    
    /* *
     * Creates an exact copy of the passed in sprite.
     * @param sprite The sprite to copy properties from.
     */
    public Mass(Sprite sprite) {
        super(sprite);
        multiplier = GravityGame.getDefaultGravity() / 100;
        terminalVelocity = GravityGame.getDefaultTerminalVelocity();
    }
    
    public Mass(Mass mob) {
        super(mob);
        multiplier = mob.multiplier;
        gravity = mob.gravity;
        terminalVelocity = mob.terminalVelocity;
    }
    //</editor-fold>
    
    public double getGravity() {
        return multiplier * 100;
    }
    
    public double getTerminalVelocity() {
        return terminalVelocity;
    }
    
    public void setGravity(double gravity) {
        multiplier = gravity / 100;
    }
    
    public void setTerminalVelocity(double velocity) {
        terminalVelocity = velocity;
    }
    
    public void jump(double speed) {
        boost = Math.abs(speed);
        gravity = 0;
    }
    
    public void stopJump() {
        boost = 0;
        gravity = 0;
    }
    
    public boolean isJumping() {
        return boost != 0;
    }
    
    public boolean isOnGround() {
        return onGround;
    }
    
    @Override
    public void draw(UpdateType type) {
        if (type.update()) {
            onGround = false;
            if (gravity >= boost) {
                int height = (int) Math.ceil(multiplier);
                move(Direction.NORTH, boost);
                loop:
                for (int i = 0; i < gravity / multiplier; i++) {
                    move(Direction.SOUTH, multiplier);
                    Area mobBottom = new Area(new Position(x, y + getHeight() - height), new Dimension(getWidth(), height));
                    for (Sprite platform : GravityGame.platforms().getAll()) {
                        Area platBottom = new Area(platform.getPosition(), new Dimension(platform.getWidth(), height));
                        if (mobBottom.isWithin(platBottom)) {
                            y = platform.getY() - getHeight();
                            gravity = 0;
                            boost = 0;
                            onGround = true;
                            break loop;
                        }
                    }
                }
            }
            else move(Direction.NORTH, boost - gravity);
            gravity += multiplier;
            if (gravity > terminalVelocity + boost) gravity = terminalVelocity + boost;
        }
        super.draw(type);
    }
}
