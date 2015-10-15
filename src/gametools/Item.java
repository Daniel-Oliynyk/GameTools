package gametools;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Used for basic positioning and collision.
 */
public class Item {
    //<editor-fold defaultstate="collapsed" desc="Constants">
    /**
     * An empty item to represent a non existent or undefined object.
     */
    public static final Item UNDEFINED_ITEM = new Item(-1, -1, -1, -1);
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
    
    /**
     * Creates an item with the coordinates and dimensions of zero.
     */
    public Item() {
        this(0, 0, 0, 0);
    }
    
    /**
     * Creates an item on the specified location with dimensions of zero.
     * @param location The position of the item.
     */
    public Item(Location location) {
        this(location.x, location.y, 0, 0);
    }
    
    /**
     * Creates an item with custom dimensions and coordinates of zero.
     * @param size The size of the item.
     */
    public Item(Dimension size) {
        this(0, 0, size.width, size.height);
    }
    
    /**
     * Creates an item with custom dimensions and coordinates of zero.
     * @param width The width of the item.
     * @param height The height of the item.
     */
    public Item(int width, int height) {
        this(0, 0, width, height);
    }
    
    /**
     * Creates an item at the x and y with the specified dimensions.
     * @param location The position of the item.
     * @param size The dimensions of the item.
     */
    public Item(Location location, Dimension size) {
        this(location.x, location.y, size.width, size.height);
    }
    
    /**
     * Creates an item at the x and y with the specified dimensions.
     * @param x The x position of the item.
     * @param y The y position of the item.
     * @param width The width of the item.
     * @param height The height of the item.
     */
    public Item(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    /**
     * @return The x position of the object.
     */
    public double getX() {
        return getLocation().x;
    }
    
    /**
     * @return The y position of the object.
     */
    public double getY() {
        return getLocation().y;
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
    public Location getLocation() {
        return new Location(x, y);
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
    public Location getCenter() {
        return new Location(x + (width / 2), y + (height / 2));
    }
    
    /**
     * Sets the x position of the object.
     * @param x The new x position.
     */
    public final void setX(double x) {
        setLocation(x, y);
    }
    
    /**
     * Sets the y position of the object.
     * @param y The new y position.
     */
    public final void setY(double y) {
        setLocation(x, y);
    }
    
    /**
     * Sets the x and y position of the object using a location.
     * @param location The new location for the item.
     */
    public final void setLocation(Location location) {
        setLocation(location.x, location.y);
    }
    
    /**
     * Sets the x and y position of the object.
     * @param x The new x position.
     * @param y The new y position.
     */
    public final void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Checks if the object is colliding with another item or area using rectangular collision
     * and touch collision as the collision testing method.
     * @param item The object to test collision against.
     * @return True if the objects are in contact.
     */
    public boolean isWithin(Item item) {
        return isWithin(item, CL_TOUCH);
    }
    
    /**
     * Checks if the object is colliding with another item or area using rectangular
     * collision and a custom collision flag.
     * @param item The object to test collision against.
     * @param method The collision testing method to use.
     * @return True if the objects are in contact.
     */
    public boolean isWithin(Item item, int method) {
        boolean horizontal, vertical;
        if (CL_INSIDE_ALL.contains(method)) {
            horizontal = x > item.x && x < item.x + item.width && x + width <= item.x + width;
            vertical = y > item.y && y < item.y + item.height && y + height <= item.y + height;
        }
        else {
            horizontal = x + width > item.x && x < item.x + item.width;
            vertical = y + height > item.y && y < item.y + item.height;
        }
        if (method == CL_TOUCH || method == CL_INSIDE) return horizontal && vertical;
        else if (method == CL_TOUCH_X || method == CL_INSIDE_X) return horizontal;
        else if (method == CL_TOUCH_Y || method == CL_INSIDE_Y) return vertical;
        else return horizontal && vertical;
    }
    
    public void draw() {
        Game.painter().fillRect((int) x, (int) y, width, height);
    }
}
