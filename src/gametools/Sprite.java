package gametools;

import java.awt.geom.AffineTransform;

/**
 * Used to represent game objects or characters. Contains methods for movement, animation and collision.
 */
public class Sprite extends Area {
    //<editor-fold defaultstate="collapsed" desc="Enums">
    /**
     * The predefined directions the sprite can move in.
     */
    public static enum Direction {
        /**
         * No movement or movement that does not follow a pre-defined direction.
         */
        UNDEFINED(-1),
        /**
         * Movement in the east (right) direction.
         */
        EAST(0),
        /**
         * Diagonal movement in the south east (downward and rightward) direction.
         */
        SOUTH_EAST(1),
        /**
         * Movement in the south (downward) direction.
         */
        SOUTH(2),
        /**
         * Diagonal movement in the south west (downward and leftward) direction.
         */
        SOUTH_WEST(3),
        /**
         * Movement in the west (left) direction.
         */
        WEST(4),
        /**
         * Diagonal movement in the north west (upward and leftward) direction.
         */
        NORTH_WEST(5),
        /**
         * Movement in the north (upward) direction.
         */
        NORTH(6),
        /**
         * Diagonal movement in the north east (upward and rightward) direction.
         */
        NORTH_EAST(7);
        
        private final int rotation;
        
        private Direction(int rotation) {
            this.rotation = rotation;
        }
        
        private int rotation() {
            return rotation;
        }
    }
    /**
     * The directions in which the sprite can rotate.
     */
    public static enum Rotation {
        /**
         * No movement or movement that does not follow a pre-defined direction.
         */
        UNDEFINED,
        /**
         * Clockwise rotation.
         */
        CLOCKWISE,
        /**
         * Counter clockwise rotation.
         */
        COUNTER_CLOCKWISE;
    }
    //</editor-fold>
    private Direction lastDirection, moveDirection;
    private double angle, moveAngle, speed = 5, rotationSpeed = 0.05;
    private boolean moved, anglularMovement, directionalMovement, relationalMovement;
    private Animation animation, previous = Animation.UNDEFINED_ANIMATION;
    private Area movementArea = Area.UNDEFINED_AREA;
    
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
     * @param animation The animation for the sprite.
     */
    public Sprite(Animation animation) {
        this(new Position(), animation);
    }
    
    /**
     * Creates a sprite and copies over the properties from the passed in object.
     * @param sprite The sprite to copy the properties from.
     */
    public Sprite(Sprite sprite) {
        this(sprite.getPosition(), sprite.animation);
        speed = sprite.speed;
        angle = sprite.angle;
        moveDirection = sprite.moveDirection;
        moveAngle = sprite.moveAngle;
        movementArea = sprite.movementArea;
        anglularMovement = sprite.anglularMovement;
        directionalMovement = sprite.directionalMovement;
    }
    
    /**
     * Creates a sprite at the specified coordinates with a custom animation.
     * @param pos The position of the sprite.
     * @param animation The image for the sprite.
     */
    public Sprite(Position pos, Animation animation) {
        super(pos, animation.getDimensions());
        this.animation = animation;
    }
    
    /**
     * @return The speed the sprite moves at.
     */
    public double getSpeed() {
        return speed;
    }
    
    /**
     * @return The rotation speed of the sprite.
     */
    public double getRotationSpeed() {
        return rotationSpeed * 100;
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
     * @return Whether or not the sprite is moving using directions relative to its angle.
     */
    public boolean getRelationalMovement() {
        return relationalMovement;
    }
    
    /**
     * Returns the sprite converted to an area object.
     * @return An area that represents the sprite.
     */
    public Area getArea() {
        return new Area(getPosition(), getDimensions());
    }
    
    /**
     * Returns the full area of the rotated image.
     * @return An area that completely covers the rotated image.
     */
    public Area getImageArea() {
        Area image = new Area(getArea());
        Position[] corners = getAllCorners();
        Position far = new Position();
        boolean flag = true;
        for (Position corner : corners) {
            corner.rotate(getCenter(), angle);
            if (corner.x < image.x || flag) image.x = corner.x;
            if (corner.y < image.y || flag) image.y = corner.y;
            if (corner.x > far.x || flag) far.x(corner.x);
            if (corner.y > far.y || flag) far.y(corner.y);
            flag = false;
        }
        image.width = (int) (far.x - image.x);
        image.height = (int) (far.y - image.y);
        return image;
    }
    
    /**
     * Returns the direction the sprite was moved in the previous or
     * current frame. Returns an undefined direction if the sprite was not recently
     * moved or was moved in a direction not defined by one of the constants.
     * @return The last direction the sprite was moved.
     */
    public Direction getLastDirection() {
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
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    
    /**
     * Sets the speed the sprite will rotate at. The default is ten.
     * @param speed The speed the sprite will rotate at.
     */
    public void setRotationSpeed(double speed) {
        rotationSpeed = speed / 200;
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
        angle = Position.fixAngle(ang);
    }
    
    /**
     * Turns on or off relational movement, where a sprite's directions are
     * relational to where it is facing (its angle).
     * @param relational True to turn relational movement on, false to turn it off.
     */
    public void setRelationalMovement(boolean relational) {
        relationalMovement = relational;
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
    
    public void rotate(Position mid, Rotation rot) {
        if (rot == Rotation.CLOCKWISE) rotate(mid, rotationSpeed);
        else if (rot == Rotation.COUNTER_CLOCKWISE) rotate(mid, -rotationSpeed);
    }
    
    public void rotate(Position mid, double ang) {
        Position newLoc = new Position(getCenter());
        newLoc.rotate(mid, ang);
        x = newLoc.x - (width / 2);
        y = newLoc.y - (height / 2);
        face(mid);
    }
    
    /**
     * Sets the angle of the sprite to face the center of the specified object.
     * @param obj The object the sprite should face.
     */
    public void face(Area obj) {
        face(obj.getCenter());
    }
    
    /**
     * Sets the angle of the sprite to face the specified position.
     * @param pos The position the sprite should face.
     */
    public void face(Position pos) {
        setAngle(getCenter().angleTo(pos));
    }
    
    /**
     * Turns the sprite in the specified direction using the sprite's rotation speed.
     * @param rot The rotation constant for the direction the sprite should turn in.
     */
    public void turn(Rotation rot) {
        if (rot == Rotation.CLOCKWISE) setAngle(angle + rotationSpeed);
        else if (rot == Rotation.COUNTER_CLOCKWISE) setAngle(angle - rotationSpeed);
    }
    
    /**
     * Turns the sprite until it faces the specified object.
     * @param obj The object the sprite should turn towards.
     */
    public void turnTo(Area obj) {
        turnTo(obj.getCenter());
    }
    
    /**
     * Turns the sprite until it faces the specified position.
     * @param pos The position the sprite should turn towards.
     */
    public void turnTo(Position pos) {
        turnTo(getCenter().angleTo(pos));
    }
    
    /**
     * Turns the sprite until it reaches the specified angle.
     * @param ang The angle the sprite should turn towards.
     */
    public void turnTo(double ang) {
        ang = Position.fixAngle(ang);
        double dif = ang - angle;
        Rotation rot;
        if (Math.abs(dif) > Math.PI) {
            if (dif > 0) rot = Rotation.COUNTER_CLOCKWISE;
            else rot = Rotation.CLOCKWISE;
        }
        else {
            if (dif > 0) rot = Rotation.CLOCKWISE;
            else rot = Rotation.COUNTER_CLOCKWISE;
        }
        if (Math.abs(dif) > rotationSpeed) turn(rot);
        else setAngle(ang);
    }
    
    /**
     * Moves the sprite at the passed in angle at the set speed.
     * @param ang A double of the angle in radians.
     */
    public void moveAt(double ang) {
        x += Math.cos(ang) * speed;
        y += Math.sin(ang) * speed;
        lastDirection = Direction.UNDEFINED;
    }
    
    /**
     * Moves the sprite to the coordinates at the set speed.
     * @param pos The position where the sprite should move to.
     */
    public void moveTo(Position pos) {
        double ang = getCenter().angleTo(pos);
        moveAt(ang);
    }
    
    /**
     * Moves the sprite at one of the defined directions at a custom speed.
     * @param dir The direction the sprite should move to.
     * @param speed The speed at which to move at.
     */
    public void move(Direction dir, int speed) {
        double prev = this.speed;
        this.speed = speed;
        move(dir);
        this.speed = prev;
    }
    
    /**
     * Moves the sprite at one of the defined directions at the set speed.
     * @param dir The direction the sprite should move to.
     */
    public void move(Direction dir) {
        moved = true;
        double ang = (Math.PI / 4) * dir.rotation();
        if (relationalMovement) ang += angle;
        moveAt(ang);
        lastDirection = dir;
    }
    
    /**
     * Moves the sprite constantly to the specified position until it gets
     * removed or it goes outside its movement area.
     * @param pos The location to move constantly to.
     */
    public void moveConstantlyTo(Position pos) {
        double ang = getCenter().angleTo(pos);
        moveConstantlyAt(ang);
    }
    
    /**
     * Moves the sprite constantly in the specified direction until it gets
     * removed or it goes outside its movement area.
     * @param dir The direction the sprite should move to.
     */
    public void moveConstantlyTo(Direction dir) {
        directionalMovement = true;
        anglularMovement = false;
        moveDirection = dir;
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
        moveDirection = Direction.UNDEFINED;
        moveAngle = 0;
    }
    
    /**
     * An empty method that runs before the draw method and should be overridden for custom code.
     */
    protected void update() {
        //Do nothing.
    }
    
    /**
     * Draws the sprite and updates its animation.<br>
     * <b>Note</b>: Overriding this method may cause unpredictable behavior for the sprite.
     */
    @Override
    public void draw() {
        update();
        if (directionalMovement) move(moveDirection);
        else if (anglularMovement) moveAt(moveAngle);
        if (!moved) lastDirection = Direction.UNDEFINED;
        moved = false;
        if (movementArea != Area.UNDEFINED_AREA) {
            if (!isWithin(movementArea, Collision.INSIDE_X)) x = (x <= 0)? 0 : movementArea.width - width;
            if (!isWithin(movementArea, Collision.INSIDE_Y)) y = (y <= 0)? 0 : movementArea.height - height;
        }
        animation.update();
        AffineTransform at = new AffineTransform();
        at.rotate(angle, getCenter().x, getCenter().y);
        at.translate(x, y);
        Game.painter().drawImage(animation.getFrame(), at, null);
    }
}