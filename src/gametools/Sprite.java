package gametools;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;

/**
 * Used to represent game objects or characters. Contains methods for movement, animation and collision.
 */
public class Sprite extends Item {
    //<editor-fold defaultstate="collapsed" desc="Constants">
    /**
     * Movement in the east (right) direction.
     */
    public static final int EAST = 0;
    /**
     * Diagonal movement in the south east (downward and rightward) direction.
     */
    public static final int SOUTHEAST = 1;
    /**
     * Movement in the south (downward) direction.
     */
    public static final int SOUTH = 2;
    /**
     * Diagonal movement in the south west (downward and leftward) direction.
     */
    public static final int SOUTHWEST = 3;
    /**
     * Movement in the west (left) direction.
     */
    public static final int WEST = 4;
    /**
     * Diagonal movement in the north west (upward and leftward) direction.
     */
    public static final int NORTHWEST = 5;
    /**
     * Movement in the north (upward) direction.
     */
    public static final int NORTH = 6;
    /**
     * Diagonal movement in the north east (upward and rightward) direction.
     */
    public static final int NORTHEAST = 7;
    /**
     * No movement or movement that does not follow a pre-defined direction.
     */
    public static final int UNDEFINED_DIRECTION = -1;
    private static final List<Integer> ALL_DIRECTIONS = Arrays.asList(EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST, NORTH, NORTHEAST);
    //</editor-fold>
    private int speed = 10, lastDirection, moveDirection;
    private double moveAngle;
    private boolean moved, anglularMovement, directionalMovement;
    private Animation animation, previous = Animation.UNDEFINED_ANIMATION;
    private Item movementArea = Item.UNDEFINED_ITEM;
    
    /**
     * Creates a blank placeholder sprite without an image or animation.<br>
     * <b>Note</b>: This sprite will cause an error if it is attempted to be
     * drawn without an image or animation first set.
     */
    public Sprite() {
        super();
    }
    
    /**
     * Creates a sprite in the default location (the corner of the screen) with a custom animation.
     * @param animation The image for the sprite.
     */
    public Sprite(Animation animation) {
        this(0, 0, animation);
    }
    
    /**
     * Creates a sprite at the specified coordinates with a custom animation.
     * @param x The x position of the sprite.
     * @param y The x position of the sprite.
     * @param animation The image for the sprite.
     */
    public Sprite(int x, int y, Animation animation) {
        super(x, y, animation.getWidth(), animation.getHeight());
        this.animation = animation;
    }
    
    /**
     * @return The speed the sprite moves at.
     */
    public int getSpeed() {
        return speed;
    }
    
    /**
     * @return The current animation on the sprite.
     */
    public Animation getAnimation() {
        return animation;
    }

    /**
     * Returns the area in which the sprite is allowed to move with the movement commands.
     * @return The area in which the sprite is allowed to move within or the constant
     * for an undefined area if none is currently set.
     */
    public Item getMovementArea() {
        return movementArea;
    }
    
    /**
     * Returns the direction the sprite was moved in the previous or
     * current frame. Returns -1 (undefined direction) if the sprite was not recently
     * moved or was moved in a direction not defined by one of the constants.
     * @return The last direction the sprite was moved. 
     */
    public int getLastDirection() {
        return lastDirection;
    }
    
    /**
     * @return The previous animation of the sprite, or the constant for an undefined
     * animation if no previous animation was set.
     */
    public Animation getPreviousAnimation() {
        return previous;
    }
    
    /**
     * Sets the x and y position of the sprite using a point.
     * @param position A point representing the new position.
     */
    public final void setPosition(Point position) {
        x = position.x;
        y = position.y;
    }
    
    /**
     * Sets the speed the sprite will move at when using its move methods.
     * @param speed The amount of pixels the sprite will move each frame.
     */
    public final void setSpeed(int speed) {
        this.speed = speed;
    }
    
    /**
     * Assigns a new animation to the sprite.
     * @param animation A new animation for the sprite.
     */
    public void setAnimation(Animation animation) {
        previous = this.animation;
        this.animation = animation;
    }
    
    /**
     * Defines the area the sprite can move inside. A sprite will not be able
     * to go outside this area using the movement commands however the sprite can
     * still get outside this area using the setters for its x and y.
     * @param area The area inside which the sprite can move unrestrictedly.
     */
    public void lockMovementArea(Item area) {
        movementArea = area;
    }
    
    /**
     * Moves the sprite at the passed in angle at the set speed.
     * @param angle A double of the angle in radians.
     */
    public void moveAt(double angle) {
        this.x += Math.cos(angle) * speed;
        this.y += Math.sin(angle) * speed;
        lastDirection = UNDEFINED_DIRECTION;
    }
    
    /**
     * Moves the sprite to the coordinates at the set speed.
     * @param x The x position where the sprite should move to.
     * @param y The y position where the sprite should move to.
     */
    public void moveTo(int x, int y) {
        double angle = Math.atan2(y - this.y, x - this.x);
        moveAt(angle);
    }
    
    /**
     * Moves the sprite at one of the defined directions at the set speed.
     * @param direction One of the direction constants that represents where
     * the sprite should move to.
     */
    public void moveTo(int direction) {
        if (ALL_DIRECTIONS.contains(direction)) {
            moved = true;
            double angle = (Math.PI / 4) * direction;
            moveAt(angle);
        }
        lastDirection = direction;
    }
    
    /**
     * Moves the sprite constantly in the specified direction until it gets
     * removed or it goes outside its movement area.
     * @param direction One of the direction constants that represents where
     * the sprite should move to.
     */
    public void moveConstantlyTo(int direction) {
        if (ALL_DIRECTIONS.contains(direction)) {
            directionalMovement = true;
            anglularMovement = false;
            moveDirection = direction;
        }
    }
    
    /**
     * Moves the sprite constantly at the specified angle until it gets
     * removed or it goes outside its movement area.
     * @param angle The angle the sprite should move at in radians.
     */
    public void moveConstantlyAt(double angle) {
        anglularMovement = true;
        directionalMovement = false;
        moveAngle = angle;
    }
    
    /**
     * Disables the sprite from moving constantly if it was set to do so in a different method.
     */
    public void stopConstantlyMoving() {
        directionalMovement = false;
        anglularMovement = false;
        moveDirection = UNDEFINED_DIRECTION;
        moveAngle = 0;
    }
    
    /**
     * @return True if the mouse is within the sprite.
     */
    public boolean mouseWithin() {
        return isCollidingWith(new Item(Game.getMouseX(), Game.getMouseY(), 0, 0));
    }
    
    /**
     * Draws the sprite and updates its animation.
     */
    public void draw() {
        Point previousLocation = getPosition();
        if (directionalMovement) moveTo(moveDirection);
        else if (anglularMovement) moveAt(moveAngle);
        if (!moved) lastDirection = UNDEFINED_DIRECTION;
        moved = false;
        if (movementArea != Item.UNDEFINED_ITEM && !isInside(movementArea)) setPosition(previousLocation);
        animation.update();
        Game.painter().drawImage(animation.getFrame(), (int) x, (int) y, null);
    }
}