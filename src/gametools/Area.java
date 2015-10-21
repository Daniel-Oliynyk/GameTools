package gametools;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Used for basic positioning and collision.
 */
public class Area {
    //<editor-fold defaultstate="collapsed" desc="Constants">
    /**
     * An empty area to represent a non existent or undefined object.
     */
    public static final Area UNDEFINED_AREA = new Area(-1, -1, -1, -1);
    /**
     * Collision when the first object has any point within the second.
     */
    public static final int CL_TOUCH = 0;
    /**
     * Collision when the first object has any point within the second
     * horizontally, no matter the vertical position.
     */
    public static final int CL_TOUCH_X = 1;
    /**
     * Collision when the first object has any point within the second
     * vertically, no matter the horizontal position.
     */
    public static final int CL_TOUCH_Y = 2;
    /**
     * Collision when the first object is completely within the second.
     */
    public static final int CL_INSIDE = 3;
    /**
     * Collision when the first object is completely within the second
     * horizontally, no matter the vertical position.
     */
    public static final int CL_INSIDE_X = 4;
    /**
     * Collision when the first object is completely within the second
     * vertically, no matter the horizontal position.
     */
    public static final int CL_INSIDE_Y = 5;
    private static final List<Integer> CL_TOUCH_ALL = Arrays.asList(CL_TOUCH, CL_TOUCH_X, CL_TOUCH_Y);
    private static final List<Integer> CL_INSIDE_ALL = Arrays.asList(CL_INSIDE, CL_INSIDE_X, CL_INSIDE_Y);
    private static final List<Integer> CL_ALL = new ArrayList<Integer>(){{addAll(CL_TOUCH_ALL); addAll(CL_TOUCH_ALL);}};
    //</editor-fold>
    /**
     * The precise x position of the object.
     */
    protected double x;
    /**
     * The precise y position of the object.
     */
    protected double y;
    /**
     * The width of the object (usually the width of the object's associated
     * image if it exists).
     */
    protected int width;
    /**
     * The height of the object (usually the height of the object's associated
     * image if it exists).
     */
    protected int height;
    
    //<editor-fold defaultstate="collapsed" desc="Constructors, Getters and Setters">
    /**
     * Creates an area with the coordinates and dimensions of zero.
     */
    public Area() {
        this(0, 0, 0, 0);
    }
    
    /**
     * Creates an area on the specified position with dimensions of zero.
     * @param pos The position of the area.
     */
    public Area(Position pos) {
        this(pos.x(), pos.y(), 0, 0);
    }
    
    /**
     * Creates an area with custom dimensions and coordinates of zero.
     * @param size The size of the area.
     */
    public Area(Dimension size) {
        this(0, 0, size.width, size.height);
    }
    
    /**
     * Creates an area with custom dimensions and coordinates of zero.
     * @param width The width of the area.
     * @param height The height of the area.
     */
    public Area(int width, int height) {
        this(0, 0, width, height);
    }
    
    /**
     * Creates an area at the x and y with the specified dimensions.
     * @param pos The position of the area.
     * @param size The dimensions of the area.
     */
    public Area(Position pos, Dimension size) {
        this(pos.x(), pos.y(), size.width, size.height);
    }
    
    /**
     * Creates an area at the x and y with the specified dimensions.
     * @param x The x position of the area.
     * @param y The y position of the area.
     * @param width The width of the area.
     * @param height The height of the area.
     */
    public Area(double x, double y, int width, int height) {
        setPosition(x, y);
        setDimensions(width, height);
    }
    
    /**
     * @return The x position of the object.
     */
    public final double getX() {
        return getPosition().x();
    }
    
    /**
     * @return The y position of the object.
     */
    public final double getY() {
        return getPosition().y();
    }
    
    /**
     * Returns the width of the object. In sprites this is automatically
     * set to be the width of the sprite image.
     * @return The width of the object.
     */
    public final int getWidth() {
        return getDimensions().width;
    }
    
    /**
     * Returns the height of the object. In sprites this is automatically
     * set to be the height of the sprite image.
     * @return The height of the object.
     */
    public final int getHeight() {
        return getDimensions().height;
    }
    
    /**
     * @return A point representing the current position of the object.
     */
    public final Position getPosition() {
        return new Position(x, y);
    }
    
    /**
     * @return A dimension representing the current width and height of the object.
     */
    public final Dimension getDimensions() {
        return new Dimension(width, height);
    }
    
    /**
     * @return A point representing the center of the object.
     */
    public final Position getCenter() {
        return new Position(x + (width / 2), y + (height / 2));
    }
    
    /**
     * Sets the x position of the object.
     * @param x The new x position.
     */
    public final void setX(double x) {
        setPosition(x, y);
    }
    
    /**
     * Sets the y position of the object.
     * @param y The new y position.
     */
    public final void setY(double y) {
        setPosition(x, y);
    }
    
    /**
     * Sets the x and y position of the object at once.
     * @param pos The new position for the object.
     */
    public final void setPosition(Position pos) {
        setPosition(pos.x(), pos.y());
    }
    
    /**
     * Sets the x and y position of the object.
     * @param x The new x position.
     * @param y The new y position.
     */
    public final void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Sets the height and width of the object.
     * @param width The new width.
     * @param height The new height.
     */
    public final void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    /**
     * Centers the object around the specified coordinates.
     * @param pos The position of the center.
     */
    public final void centerOn(Position pos) {
        centerOn(pos.x(), pos.y());
    }
    
    /**
     * Centers the object around the specified coordinates.
     * @param x The x position of the center.
     * @param y The y position of the center.
     */
    public final void centerOn(double x, double y) {
        this.x = x - width / 2;
        this.y = y - height / 2;
    }
    
//</editor-fold>
    /**
     * Checks if the object is colliding with another object or area using rectangular
     * collision and touch collision as the collision testing method.
     * @param obj The object to test collision against.
     * @return True if the objects are in contact.
     */
    public boolean isWithin(Area obj) {
        return isWithin(obj, CL_TOUCH);
    }
    
    /**
     * Checks if the object is colliding with another object or area using rectangular
     * collision and a custom collision flag.
     * @param obj The object to test collision against.
     * @param method The collision testing method to use.
     * @return True if the objects are in contact.
     */
    public boolean isWithin(Area obj, int method) {
        boolean horizontal, vertical;
        if (CL_INSIDE_ALL.contains(method)) {
            horizontal = x > obj.x && x < obj.x + obj.width && x + width <= obj.x + width;
            vertical = y > obj.y && y < obj.y + obj.height && y + height <= obj.y + height;
        }
        else {
            horizontal = x + width > obj.x && x < obj.x + obj.width;
            vertical = y + height > obj.y && y < obj.y + obj.height;
        }
        if (method == CL_TOUCH || method == CL_INSIDE) return horizontal && vertical;
        else if (method == CL_TOUCH_X || method == CL_INSIDE_X) return horizontal;
        else if (method == CL_TOUCH_Y || method == CL_INSIDE_Y) return vertical;
        else return horizontal && vertical;
    }
    
    /**
     * Fills a rectangle at the current area using whatever color is currently set.
     */
    public void draw() {
        Game.painter().fillRect((int) x, (int) y, width, height);
    }
}
