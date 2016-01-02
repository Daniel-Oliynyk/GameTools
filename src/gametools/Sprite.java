package gametools;

import java.awt.image.BufferedImage;

/**
 * Used for adding movement and behavior for graphics.
 */
public class Sprite extends Graphic {
    /**
     * The predefined directions the sprite can move in.
     */
    public static enum Direction {
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
         * Clockwise rotation.
         */
        CLOCKWISE(1),
        /**
         * Counter clockwise rotation.
         */
        COUNTER_CLOCKWISE(-1);
        
        private final int mult;
        
        private Rotation(int mult) {
            this.mult = mult;
        }
        
        private double mult(double speed) {
            return mult * speed;
        }
    }
    private Script script = Script.UNDEFINED_SCRIPT;
    private double speed = 5, rotationSpeed = 0.05;
    private boolean relational;
    private Area movementArea = Area.UNDEFINED_AREA;
    
    /**
     * Creates a blank sprite without an image or position.
     */
    public Sprite() {
        super();
    }
    
    /**
     * Creates a sprite in the default location (the corner of the screen) with a custom image.
     * @param image The image for the sprite.
     */
    public Sprite(BufferedImage image) {
        super(image);
    }
    
    /**
     * Creates a sprite in the default location (the corner of the screen) with a custom animation.
     * @param animation The animation for the sprite.
     */
    public Sprite(Animation animation) {
        super(animation);
    }
    
    /**
     * Creates a sprite and copies over all the properties from the graphic.
     * @param graphic The graphic to copy the properties from.
     */
    public Sprite(Graphic graphic) {
        super(graphic);
    }
    
    /**
     * Creates a sprite at the specified coordinates with a custom image.
     * @param pos The position of the sprite.
     * @param image The image for the sprite.
     */
    public Sprite(Position pos, BufferedImage image) {
        super(pos, image);
    }
    
    /**
     * Creates a sprite at the specified coordinates with a custom animation.
     * @param pos The position of the sprite.
     * @param animation The animation for the sprite.
     */
    public Sprite(Position pos, Animation animation) {
        super(pos, animation);
    }
    
    /**
     * Creates an exact copy of the passed in sprite.
     * @param sprite The sprite to copy properties from.
     */
    public Sprite(Sprite sprite) {
        super(sprite);
        script = sprite.script;
        speed = sprite.speed;
        rotationSpeed = sprite.rotationSpeed;
        relational = sprite.relational;
        movementArea = sprite.movementArea;
    }
    
    /**
     * Returns the amount the sprite moves each frame.
     * @return The speed the sprite moves at.
     */
    public double getSpeed() {
        return speed;
    }
    
    /**
     * The amount the sprite rotates each frame.
     * @return The rotation speed of the sprite.
     */
    public double getRotationSpeed() {
        return rotationSpeed * 100;
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
     * Returns whether or not the sprite is moving using directions relative to its angle.
     * @return Whether or not the sprite is using relational movement.
     */
    public boolean getRelationalMovement() {
        return relational;
    }
    
    /**
     * Returns the current script acting on the sprite or an undefined script
     * if there is none.
     * @return The script the sprite is using.
     */
    public Script getScript() {
        return script;
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
     * Turns on or off relational movement, where a sprite's directions are
     * relational to where it is facing (its angle).
     * @param relational True to turn relational movement on, false to turn it off.
     */
    public void setRelationalMovement(boolean relational) {
        this.relational = relational;
    }
    
    /**
     * Sets a script for the sprite to use.
     * @param script A script to run every time the sprite is updated.
     */
    public void script(Script script) {
        this.script = script;
    }
    
    /**
     * Removes the script the sprite is using if it has any.
     */
    public void removeScript() {
        this.script = Script.UNDEFINED_SCRIPT;
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
    
    /**
     * Rotates the sprite around the specified position at the sprite's speed.
     * @param mid The position around which to rotate.
     * @param rot The direction to rotate in.
     */
    public void rotate(Position mid, Rotation rot) {
        rotate(mid, rot.mult(rotationSpeed));
    }
    
    /**
     * Rotates the sprite around the specified position at the passed in amount.
     * @param mid The position around which to rotate.
     * @param ang How far to rotate in radians.
     */
    public void rotate(Position mid, double ang) {
        Position newLoc = new Position(getCenter());
        newLoc.rotate(mid, ang);
        x = newLoc.x - (width / 2);
        y = newLoc.y - (height / 2);
        angle = Tools.fixAngle(angle + ang);
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
        setAngle(angle + rot.mult(rotationSpeed));
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
        ang = Tools.fixAngle(ang);
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
    }
    
    /**
     * Moves the sprite to the coordinates at the set speed.
     * @param pos The position where the sprite should move to.
     */
    public void moveTo(Position pos) {
        double ang = getCenter().angleTo(pos);
        double prev = speed;
        if (getCenter().dist(pos) < speed) speed = getCenter().dist(pos);
        moveAt(ang);
        speed = prev;
    }
    
    /**
     * Moves the sprite at one of the defined directions at a custom speed.
     * @param dir The direction the sprite should move to.
     * @param speed The speed at which to move at.
     */
    public void move(Direction dir, double speed) {
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
        double ang = (Math.PI / 4) * dir.rotation();
        if (relational) ang += angle;
        moveAt(ang);
    }
    
    @Override
    public void draw(UpdateType type) {
        if (type.update()) {
            update();
            if (this.script != Script.UNDEFINED_SCRIPT) script.update();
            if (movementArea != Area.UNDEFINED_AREA) {
                if (!isWithin(movementArea, Collision.INSIDE_X)) x = (x <= 0)? 0 : movementArea.width - width;
                if (!isWithin(movementArea, Collision.INSIDE_Y)) y = (y <= 0)? 0 : movementArea.height - height;
            }
        }
        super.draw(type);
    }
}