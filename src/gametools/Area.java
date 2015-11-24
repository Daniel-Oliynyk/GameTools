package gametools;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

/**
 * Used for basic positioning and collision.
 */
public class Area {
    //<editor-fold defaultstate="collapsed" desc="Enums and Constants">
    /**
     * An empty area to represent a non existent or undefined object.
     */
    public static final Area UNDEFINED_AREA = new Area(Position.UNDEFINED_POSITION, new Dimension(-1, -1));
    /**
     * The different methods for testing collision between two objects.
     */
    public static enum Collision {
        /**
         * Collision when the first object has any point within the second.
         */
        TOUCH,
        /**
         * Collision when the first object has any point within the second
         * horizontally, no matter the vertical position.
         */
        TOUCH_X,
        /**
         * Collision when the first object has any point within the second
         * vertically, no matter the horizontal position.
         */
        TOUCH_Y,
        /**
         * Collision when the first object is completely within the second.
         */
        INSIDE,
        /**
         * Collision when the first object is completely within the second
         * horizontally, no matter the vertical position.
         */
        INSIDE_X,
        /**
         * Collision when the first object is completely within the second
         * vertically, no matter the horizontal position.
         */
        INSIDE_Y;
    }
    /**
     * The corners of the object.
     */
    public static enum Corner {
        /**
         * Top left corner of the object.
         */
        TOP_LEFT,
        /**
         * Top right corner of the object.
         */
        TOP_RIGHT,
        /**
         * Bottom left corner of the object.
         */
        BOTTOM_LEFT,
        /**
         * Bottom right corner of the object.
         */
        BOTTOM_RIGHT;
    }
    private static final List<Collision> TOUCH_COLLISION = Arrays.asList(Collision.TOUCH, Collision.TOUCH_X, Collision.TOUCH_Y);
    private static final List<Collision> INSIDE_COLLISION = Arrays.asList(Collision.INSIDE, Collision.INSIDE_X, Collision.INSIDE_Y);
    //</editor-fold>
    /**
     * The precise x position of the object.
     */
    protected double x;
    /**
     * The precise y position of the object.
     */
    protected double y;
    int width, height;
    private boolean draggable, dragging;
    private Position offset = Position.UNDEFINED_POSITION;
    
    //<editor-fold defaultstate="collapsed" desc="Constructors, Getters and Setters">
    /**
     * Creates an area with the coordinates and dimensions of zero.
     */
    public Area() {
        this(new Position(), new Dimension());
    }
    
    /**
     * Creates an area on the specified position with dimensions of zero.
     * @param pos The position of the area.
     */
    public Area(Position pos) {
        this(pos, new Dimension());
    }
    
    /**
     * Creates an area with custom dimensions and coordinates of zero.
     * @param size The size of the area.
     */
    public Area(Dimension size) {
        this(new Position(), size);
    }
    
    /**
     * Creates an area and copies over the properties from the passed in object.
     * @param area The area to copy the properties from.
     */
    public Area(Area area) {
        this(area.getPosition(), area.getDimensions());
    }
    
    /**
     * Creates an area at the x and y with the specified dimensions.
     * @param pos The position of the area.
     * @param size The dimensions of the area.
     */
    public Area(Position pos, Dimension size) {
        x = pos.x;
        y = pos.y;
        width = size.width;
        height = size.height;
    }
    
    /**
     * @return The x position of the object.
     */
    public double getX() {
        return getPosition().x;
    }
    
    /**
     * @return The y position of the object.
     */
    public double getY() {
        return getPosition().y;
    }
    
    /**
     * Returns the width of the object. In sprites this is automatically
     * set to be the width of the sprite image.
     * @return The width of the object.
     */
    public int getWidth() {
        return getDimensions().width;
    }
    
    /**
     * Returns the height of the object. In sprites this is automatically
     * set to be the height of the sprite image.
     * @return The height of the object.
     */
    public int getHeight() {
        return getDimensions().height;
    }
    
    /**
     * @return A point representing the current position of the object.
     */
    public Position getPosition() {
        return new Position(x, y);
    }
    
    /**
     * @return A dimension representing the current width and height of the object.
     */
    public Dimension getDimensions() {
        return new Dimension(width, height);
    }
    
    /**
     * @return A point representing the center of the object.
     */
    public Position getCenter() {
        return new Position(x + (width / 2), y + (height / 2));
    }
    
    /**
     * Returns a position representing one of the corners of the object.
     * @param cor The corner to find the position of.
     * @return A position with the x and y of the specified corner or an undefined position.
     */
    public Position getCorner(Corner cor) {
        if (cor == Corner.TOP_LEFT) return new Position(x, y);
        else if (cor == Corner.TOP_RIGHT) return new Position(x + width, y);
        else if (cor == Corner.BOTTOM_LEFT) return new Position(x, y + height);
        else if (cor == Corner.BOTTOM_RIGHT) return new Position(x + width, y + height);
        else return Position.UNDEFINED_POSITION;
    }
    
    /**
     * Returns all four corners of the area.
     * @return An array containing the four corners.
     */
    public Position[] getAllCorners() {
        Position[] all = new Position[4];
        for (int i = 0; i < Corner.values().length; i++) all[i] = getCorner(Corner.values()[i]);
        return all;
    }
    
    /**
     * Whether or not the area can be dragged by the mouse.
     * @return True if the area is draggable.
     */
    public boolean isDraggable() {
        return draggable;
    }
    
    /**
     * Sets the x position of the object.
     * @param x The new x position.
     */
    public void setX(double x) {
        setPosition(new Position(x, y));
    }
    
    /**
     * Sets the y position of the object.
     * @param y The new y position.
     */
    public void setY(double y) {
        setPosition(new Position(x, y));
    }
    
    /**
     * Sets the x and y position of the object at once.
     * @param pos The new position for the object.
     */
    public void setPosition(Position pos) {
        x = pos.x;
        y = pos.y;
    }
    
    /**
     * Sets the width of the object.
     * @param width The new width.
     */
    public void setWidth(int width) {
        setDimensions(new Dimension(width, height));
    }
    
    /**
     * Sets the height of the object.
     * @param height The new height.
     */
    public void setHeight(int height) {
        setDimensions(new Dimension(width, height));
    }
    
    /**
     * Sets the width and height of the object.
     * @param size The new dimensions of the object.
     */
    public void setDimensions(Dimension size) {
        width = size.width;
        height = size.height;
    }
    
    /**
     * Copies over the position and dimensions from the specified area.
     * @param area The area whose properties should be copied over.
     */
    public void setArea(Area area) {
        setArea(area.getPosition(), area.getDimensions());
    }
    
    /**
     * Sets both the position and the dimensions of the object.
     * @param pos The new position.
     * @param size The new dimensions.
     */
    public void setArea(Position pos, Dimension size) {
        setDimensions(size);
        setPosition(pos);
    }
    
    /**
     * Turns object dragging on or off.
     * @param draggable Whether or not to allow the object to be dragged by the user.
     */
    public void setDraggable(boolean draggable) {
        if (!draggable) dragging = false;
        this.draggable = draggable;
    }
    
    /**
     * Centers the object around the specified coordinates.
     * @param pos The position of the center.
     */
    public void centerOn(Position pos) {
        x = pos.x - width / 2;
        y = pos.y - height / 2;
    }
    
    /**
     * Translates the object and adds the passed in values to the x and y.
     * @param trans The amount to add to the current x and y value.
     */
    public void translate(Position trans) {
        x = x + trans.x;
        y = y + trans.y;
    }
    
    /**
     * Returns a textual representation of the object.
     * @return A string representing the object.
     */
    @Override
    public String toString() {
        return getClass().getName() + "[x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + "]";
    }
    //</editor-fold>
    
    /**
     * Checks if a point is within the object.
     * @param pos The position to check collision against.
     * @return True if the specified position is within the object.
     */
    public boolean isWithin(Position pos) {
        return isWithin(new Area(pos, new Dimension()));
    }
    
    /**
     * Checks if the object is colliding with another object or area using rectangular
     * collision and touch collision as the collision testing method.
     * @param obj The object to test collision against.
     * @return True if the objects are in contact.
     */
    public boolean isWithin(Area obj) {
        return isWithin(obj, Collision.TOUCH);
    }
    
    /**
     * Checks if the object is colliding with another object or area using rectangular
     * collision and a custom collision flag.
     * @param obj The object to test collision against.
     * @param method The collision testing method to use.
     * @return True if the objects are in contact.
     */
    public boolean isWithin(Area obj, Collision method) {
        boolean horizontal, vertical;
        if (INSIDE_COLLISION.contains(method)) {
            horizontal = x > obj.x && x < obj.x + obj.width && x + width <= obj.x + obj.width;
            vertical = y > obj.y && y < obj.y + obj.height && y + height <= obj.y + obj.height;
        }
        else {
            horizontal = x + width > obj.x && x < obj.x + obj.width;
            vertical = y + height > obj.y && y < obj.y + obj.height;
        }
        if (method == Collision.TOUCH || method == Collision.INSIDE) return horizontal && vertical;
        else if (method == Collision.TOUCH_X || method == Collision.INSIDE_X) return horizontal;
        else if (method == Collision.TOUCH_Y || method == Collision.INSIDE_Y) return vertical;
        else return horizontal && vertical;
    }
    
    void updateDrag() {
        if (draggable && Game.mouseEngaged(MouseEvent.BUTTON1) && Game.mouseInside(this)) {
            dragging = true;
            offset = new Position(Game.getMousePosition().x - x, Game.getMousePosition().y - y);
        }
        if (dragging) {
            double offsetX = Game.getMousePosition().x - offset.x;
            double offsetY = Game.getMousePosition().y - offset.y;
            setPosition(new Position(offsetX, offsetY));
        }
        if (!Game.mousePressed()) dragging = false;
    }
    
    /**
     * Sets the position of the object, and then runs the draw method.
     * @param pos The new position of the object.
     */
    public void draw(Position pos) {
        setPosition(pos);
        boolean prev = draggable;
        draggable = false;
        draw();
        draggable = prev;
    }
    
    /**
     * Fills a rectangle on the current area using whatever color is currently set.
     */
    public void draw() {
        updateDrag();
        Game.painter().fillRect((int) x, (int) y, width, height);
    }
}
