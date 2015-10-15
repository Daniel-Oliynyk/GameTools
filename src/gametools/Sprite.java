package gametools;

import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.List;

/**
 * Used to represent game objects or characters. Contains methods for movement, animation and collision.
 */
public class Sprite extends Item {
    //<editor-fold defaultstate="collapsed" desc="Constants">
    /**
     * No movement or movement that does not follow a pre-defined direction.
     */
    public static final int DR_UNDEFINED = -1;
    /**
     * Movement in the east (right) direction.
     */
    public static final int DR_EAST = 0;
    /**
     * Diagonal movement in the south east (downward and rightward) direction.
     */
    public static final int DR_SOUTHEAST = 1;
    /**
     * Movement in the south (downward) direction.
     */
    public static final int DR_SOUTH = 2;
    /**
     * Diagonal movement in the south west (downward and leftward) direction.
     */
    public static final int DR_SOUTHWEST = 3;
    /**
     * Movement in the west (left) direction.
     */
    public static final int DR_WEST = 4;
    /**
     * Diagonal movement in the north west (upward and leftward) direction.
     */
    public static final int DR_NORTHWEST = 5;
    /**
     * Movement in the north (upward) direction.
     */
    public static final int DR_NORTH = 6;
    /**
     * Diagonal movement in the north east (upward and rightward) direction.
     */
    public static final int DR_NORTHEAST = 7;
    /**
     * Top left corner.
     */
    public static final int CR_TOPLEFT = 0;
    /**
     * Top right corner.
     */
    public static final int CR_TOPRIGHT = 1;
    /**
     * Bottom left corner.
     */
    public static final int CR_BOTTOMLEFT = 2;
    /**
     * Bottom right corner.
     */
    public static final int CR_BOTTOMRIGHT = 3;
    private static final List<Integer> DR_ALL = Arrays.asList(DR_EAST, DR_SOUTHEAST, DR_SOUTH,
            DR_SOUTHWEST, DR_WEST, DR_NORTHWEST, DR_NORTH, DR_NORTHEAST);
    //</editor-fold>
    private int speed = 10, lastDirection, moveDirection;
    private double rotation, moveAngle;
    private boolean moved, anglularMovement, directionalMovement;
    private Animation animation, previous = Animation.UNDEFINED_ANIMATION;
    private Item movementArea = Item.UNDEFINED_ITEM;
    private Location[] corners;
    
    /**
     * Creates a blank placeholder sprite without an image or animation.<br>
     * <b>Note</b>: This sprite may cause errors if it is attempted to be drawn
     * without an image or animation first set.
     */
    public Sprite() {
        super();
        Location none = new Location();
        corners = new Location[]{none, none, none, none};
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
     * @param location The position of the sprite.
     * @param animation The image for the sprite.
     */
    public Sprite(Location location, Animation animation) {
        this(location.x, location.y, animation);
    }
    
    /**
     * Creates a sprite at the specified coordinates with a custom animation.
     * @param x The x position of the sprite.
     * @param y The x position of the sprite.
     * @param animation The image for the sprite.
     */
    public Sprite(double x, double y, Animation animation) {
        super(x, y, animation.getWidth(), animation.getHeight());
        corners = new Location[4];
        corners[0] = new Location(x, y);
        corners[1] = new Location(x + animation.getWidth(), y);
        corners[2] = new Location(x, y + animation.getHeight());
        corners[3] = new Location(x + animation.getWidth(), y + animation.getHeight());
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
     * Sets the speed the sprite will move at when using its move methods.
     * @param speed The amount of pixels the sprite will move each frame.
     */
    public final void setSpeed(int speed) {
        this.speed = speed;
    }
    
    public void setAngle(double angle) {
        rotation = angle;
        double nx = 0, ny = 0, outerX = 0, outerY = 0;
        int i = 0;
        boolean flag = true;
        for (Location corner : corners) {
            if (i == 0) corner.set(x, y);
            else if (i == 1) corner.set(x + width, y);
            else if (i == 2) corner.set(x, y + height);
            else if (i == 3) corner.set(x + width, y + height);
            i++;
            corner.rotate(getCenter(), angle);
            if (corner.x < nx || flag) nx = corner.x;
            if (corner.y < ny || flag) ny = corner.y;
            if (corner.x > outerX || flag) outerX = corner.x;
            if (corner.y > outerY || flag) outerY = corner.y;
            flag = false;
        }
        Tools.pti(nx, ny, outerX, outerY).draw();
//        x = nx;
//        y = ny;
//        width = (int) (outerX - x);
//        height = (int) (outerY - y);
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
        lastDirection = DR_UNDEFINED;
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
        if (DR_ALL.contains(direction)) {
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
        if (DR_ALL.contains(direction)) {
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
        moveDirection = DR_UNDEFINED;
        moveAngle = 0;
    }
    
    /**
     * @return True if the mouse is within the sprite.
     */
    public boolean mouseWithin() {
        return isWithin(new Item(Game.getMouseX(), Game.getMouseY(), 0, 0));
    }
    
    /**
     * Draws the sprite and updates its animation.
     */
    @Override
    public void draw() {
        double prevX = x, prevY = y;
        if (directionalMovement) moveTo(moveDirection);
        else if (anglularMovement) moveAt(moveAngle);
        if (!moved) lastDirection = DR_UNDEFINED;
        moved = false;
        if (movementArea != Item.UNDEFINED_ITEM) {
            if (!isWithin(movementArea, CL_INSIDE_X)) x = prevX;
            if (!isWithin(movementArea, CL_INSIDE_Y)) y = prevY;
        }
        animation.update();
        AffineTransform at = new AffineTransform();
        at.rotate(rotation, getCenter().x, getCenter().y);
        at.translate(x, y);
        Game.painter().drawImage(animation.getFrame(), at, null);
        getCenter().draw(5);
        for (Location corner : corners) corner.draw(10);
    }
}