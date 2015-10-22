package gametools;

import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.List;

/**
 * Used to represent game objects or characters. Contains methods for movement, animation and collision.
 */
public class Sprite extends Area {
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
    public static final int DR_SOUTH_EAST = 1;
    /**
     * Movement in the south (downward) direction.
     */
    public static final int DR_SOUTH = 2;
    /**
     * Diagonal movement in the south west (downward and leftward) direction.
     */
    public static final int DR_SOUTH_WEST = 3;
    /**
     * Movement in the west (left) direction.
     */
    public static final int DR_WEST = 4;
    /**
     * Diagonal movement in the north west (upward and leftward) direction.
     */
    public static final int DR_NORTH_WEST = 5;
    /**
     * Movement in the north (upward) direction.
     */
    public static final int DR_NORTH = 6;
    /**
     * Diagonal movement in the north east (upward and rightward) direction.
     */
    public static final int DR_NORTH_EAST = 7;
    /**
     * Clockwise sprite rotation.
     */
    public static final int RT_CLOCK_WISE = 0;
    /**
     * Counter-clockwise sprite rotation.
     */
    public static final int RT_COUNTER_CLOCK_WISE = 0;
    private static final List<Integer> DR_ALL = Arrays.asList(DR_EAST, DR_SOUTH_EAST, DR_SOUTH,
            DR_SOUTH_WEST, DR_WEST, DR_NORTH_WEST, DR_NORTH, DR_NORTH_EAST);
    //</editor-fold>
    private int speed = 5, lastDirection, moveDirection;
    private double prevX, prevY, angle, moveAngle;
    private boolean moved, anglularMovement, directionalMovement;
    private Animation animation, previous = Animation.UNDEFINED_ANIMATION;
    private Area movementArea = Area.UNDEFINED_AREA, spriteArea = new Area();
    
    //<editor-fold defaultstate="collapsed" desc="Constructors, Getters and Setters">
    /**
     * Creates a blank placeholder sprite without an image or animation.<br>
     * <b>Note</b>: This sprite may cause errors if it is attempted to be drawn
     * without an image or animation first set.
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
     * @param pos The position of the sprite.
     * @param animation The image for the sprite.
     */
    public Sprite(Position pos, Animation animation) {
        this(pos.x(), pos.y(), animation);
    }
    
    public Sprite(Sprite sprite) {
        this(sprite.x, sprite.y, sprite.animation);
        angle = sprite.angle;
    }
    
    /**
     * Creates a sprite at the specified coordinates with a custom animation.
     * @param x The x position of the sprite.
     * @param y The x position of the sprite.
     * @param animation The image for the sprite.
     */
    public Sprite(double x, double y, Animation animation) {
        super(x, y, animation.getWidth(), animation.getHeight());
        spriteArea = new Area(x, y, width, height);
        prevX = x;
        prevY = y;
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
    public Area getMovementArea() {
        return movementArea;
    }
    
    /**
     * @return The current angle of the sprite.
     */
    public double getAngle() {
        return angle;
    }
    
    /**
     * Returns the sprite converted to an area object.
     * @return An area that represents the sprite.
     */
    public Area getArea() {
        return new Area(getPosition(), getDimensions());
    }
    
    /**
     * Returns the full area of the rotated sprite.
     * @return An area that completely covers the rotated sprite.
     */
    public Area getImageArea() {
        adjustImageArea();
        return spriteArea;
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
    public void setSpeed(int speed) {
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
     * Rotates the sprite around its center.
     * @param ang The new angle of the sprite.
     */
    public void setAngle(double ang) {
        angle = ang;
    }
    
    /**
     * Defines the area the sprite can move inside. A sprite will not be able
     * to go outside this area using the movement commands however the sprite can
     * still get outside this area using the setters for its x and y.
     * @param area The area inside which the sprite can move unrestrictedly.
     */
    public void lockMovementArea(Area area) {
        movementArea = area;
    }
    //</editor-fold>
    
    /**
     * Moves the sprite at the passed in angle at the set speed.
     * @param ang A double of the angle in radians.
     */
    public void moveAt(double ang) {
        this.x += Math.cos(ang) * speed;
        this.y += Math.sin(ang) * speed;
        lastDirection = DR_UNDEFINED;
    }
    
    /**
     * Moves the sprite to the coordinates at the set speed.
     * @param pos The position where the sprite should move to.
     */
    public void moveTo(Position pos) {
        double ang = Math.atan2(pos.y - y, pos.x - x);
        moveAt(ang);
    }
    
    /**
     * Moves the sprite at one of the defined directions at a custom speed.
     * @param direction One of the direction constants that represents where
     * the sprite should move to.
     * @param speed The speed at which to move at.
     */
    public void moveTo(int direction, int speed) {
        int prev = this.speed;
        this.speed = speed;
        moveTo(direction);
        this.speed = prev;
    }
    
    /**
     * Moves the sprite at one of the defined directions at the set speed.
     * @param direction One of the direction constants that represents where
     * the sprite should move to.
     */
    public void moveTo(int direction) {
        if (DR_ALL.contains(direction)) {
            moved = true;
            double ang = (Math.PI / 4) * direction;
            moveAt(ang);
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
     * @param ang The angle the sprite should move at in radians.
     */
    public void moveConstantlyAt(double ang) {
        anglularMovement = true;
        directionalMovement = false;
        moveAngle = ang;
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
    
    private void adjustImageArea() {
        spriteArea = new Area(x, y, width, height);
        Position[] corners = new Position[4];
        corners[0] = new Position(x, y);
        corners[1] = new Position(x + width, y);
        corners[2] = new Position(x, y + height);
        corners[3] = new Position(x + width, y + height);
        Position farPos = new Position();
        boolean flag = true;
        for (Position corner : corners) {
            corner.rotate(getCenter(), angle);
            if (corner.x() < spriteArea.x || flag) spriteArea.x = corner.x();
            if (corner.y() < spriteArea.y || flag) spriteArea.y = corner.y();
            if (corner.x() > farPos.x() || flag) farPos.x(corner.x());
            if (corner.y() > farPos.y() || flag) farPos.y(corner.y());
            flag = false;
        }
        spriteArea.width = (int) (farPos.x() - spriteArea.x);
        spriteArea.height = (int) (farPos.y() - spriteArea.y);
    }
    
    /**
     * @return True if the mouse is within the sprite.
     */
    public boolean mouseWithin() {
        return isWithin(new Area(Game.getMouseX(), Game.getMouseY(), 0, 0));
    }
    
    /**
     * An empty method that runs before the draw method and should be overridden for custom code.
     */
    protected void update() {
        //Do nothing
    }
    
    /**
     * Draws the sprite and updates its animation.
     */
    @Override
    public final void draw() {
        update();
        if (directionalMovement) moveTo(moveDirection);
        else if (anglularMovement) moveAt(moveAngle);
        if (!moved) lastDirection = DR_UNDEFINED;
        moved = false;
        if (movementArea != Area.UNDEFINED_AREA) {
            if (!isWithin(movementArea, CL_INSIDE_X)) x = prevX;
            if (!isWithin(movementArea, CL_INSIDE_Y)) y = prevY;
        }
        animation.update();
        AffineTransform at = new AffineTransform();
        at.rotate(angle, getCenter().x(), getCenter().y());
        at.translate(x, y);
        Game.painter().drawImage(animation.getFrame(), at, null);
        prevX = x;
        prevY = y;
    }
}