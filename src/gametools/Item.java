package gametools;

import java.awt.Dimension;
import java.awt.Point;

/**
 * A class used for basic positioning and collision.
 */
public class Item {
    
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
     * The height of the object (usually the width of the object's associated
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
     * Creates an item with custom dimensions and coordinates of zero.
     * @param width The width of the item.
     * @param height The height of the item.
     */
    public Item(int width, int height) {
        this(0, 0, width, height);
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
    public int getX() {
        return (int) x;
    }
    
    /**
     * @return The y position of the object.
     */
    public int getY() {
        return (int) y;
    }
    
    /**
     * Returns the width of the object. In sprites this is automatically
     * set to be the width of the sprite image.
     * @return The width of the object.
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Returns the height of the object. In sprites this is automatically
     * set to be the height of the sprite image.
     * @return The height of the object.
     */
    public int getHeight() {
        return height;
    }
    /**
     * @return A point representing the current position of the object.
     */
    public Point getPosition() {
        return new Point((int) x, (int) y);
    }
    
    /**
     * @return A dimension representing the current width and height of the object.
     */
    public Dimension getDimensions() {
        return new Dimension(width, height);
    }
    
    /**
     * Sets the x position of the object.
     * @param x The new x position.
     */
    public final void setX(int x) {
        this.x = x;
    }
    
    /**
     * Sets the y position of the object.
     * @param y The new y position.
     */
    public final void setY(int y) {
        this.y = y;
    }
    
    /**
     * Sets the x and y position of the object.
     * @param x The new x position.
     * @param y The new y position.
     */
    public final void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Checks if the object is completely within (no points outside) a specified object or area.
     * @param item The object to test collision against.
     * @return True if the item is inside the specified item.
     */
    public boolean isInside(Item item) {
        int ix = (int) item.x + width;
        int iy = (int) item.y + height;
        return isCollidingWith(new Item(ix, iy, item.width - (width * 2), item.height - (height * 2)));
    }
    
    /**
     * Checks if the object is colliding with another item or area using rectangular collision.
     * @param item The object to test collision against.
     * @return True if the objects are in contact.
     */
    public boolean isCollidingWith(Item item) {
        boolean horizontal = x + width > item.x && x < item.x + item.width;
        boolean vertical = y + height > item.y && y < item.y + item.height;
        return horizontal && vertical;
    }
}
