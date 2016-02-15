package gametools;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Used for basic positioning and collision.
 */
public class Area {
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
        TOUCH(null, Modifier.BOTH),
        /**
         * Collision when the first object has any point within the second
         * horizontally, no matter the vertical position.
         */
        TOUCH_X(TOUCH, Modifier.X),
        /**
         * Collision when the first object has any point within the second
         * vertically, no matter the horizontal position.
         */
        TOUCH_Y(TOUCH, Modifier.Y),
        /**
         * Collision when the first object is completely within the second.
         */
        INSIDE(null, Modifier.BOTH),
        /**
         * Collision when the first object is completely within the second
         * horizontally, no matter the vertical position.
         */
        INSIDE_X(INSIDE, Modifier.X),
        /**
         * Collision when the first object is completely within the second
         * vertically, no matter the horizontal position.
         */
        INSIDE_Y(INSIDE, Modifier.Y),
        /**
         * Collision when only the edges of the two objects are in contact.
         */
        EDGE(null, Modifier.BOTH),
        /**
         * Collision when only the edges of the two objects are in contact
         * horizontally, no matter the vertical position.
         */
        EDGE_X(EDGE, Modifier.X),
        /**
         * Collision when only the edges of the two objects are in contact
         * vertically, no matter the horizontal position.
         */
        EDGE_Y(EDGE, Modifier.Y);
        
        private static enum Modifier {
            X, Y, BOTH;
        }
        
        private final Collision parent;
        private final Modifier modifier;
        
        private Collision(Collision parent, Modifier type) {
            this.parent = parent;
            this.modifier = type;
        }
        
        private boolean isType(Collision type) {
            return (parent == null && this == type) || parent == type;
        }
        
        private boolean hasModifier(Modifier mod) {
            return modifier == mod;
        }
    }
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
        this(pos, new Dimension());
    }
    
    /**
     * Creates an area with custom dimensions and coordinates of zero.
     * @param width The width of the new area.
     * @param height The width of the new area.
     */
    public Area(int width, int height) {
        this(new Dimension(width, height));
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
        this(pos.x, pos.y, size.width, size.height);
    }
    
    /**
     * Creates an area at the x and y with the specified dimensions.
     * @param x The x position of the area.
     * @param y The y position of the area.
     * @param width The width of the area.
     * @param height The height of the area.
     */
    public Area(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    /**
     * Returns the x position of the object.
     * @return The x position of the object.
     */
    public double getX() {
        return getPosition().x;
    }
    
    /**
     * Returns the y position of the object.
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
     * Returns the current position.
     * @return A point representing the current position of the object.
     */
    public Position getPosition() {
        return new Position(x, y);
    }
    
    /**
     * Returns the size of the object.
     * @return A dimension representing the current width and height of the object.
     */
    public Dimension getDimensions() {
        return new Dimension(width, height);
    }
    
    /**
     * Returns the center of the object.
     * @return A point representing the center of the object.
     */
    public Position getCenter() {
        return new Position(x + width / 2, y + height / 2);
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
     * @param x The new x position.
     * @param y The new y position.
     */
    public void setPosition(double x, double y) {
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
     * @param width The new width.
     * @param height The new height.
     */
    public void setDimensions(int width, int height) {
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
     * Sets the position and size of the area.
     * @param x The new x position.
     * @param y The new y position.
     * @param width The new width.
     * @param height The new height.
     */
    public void setArea(double x, double y, int width, int height) {
        setArea(new Position(x, y), new Dimension(width, height));
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
     * Sets the object position to the center of the passed in object
     * @param obj The object to center on.
     */
    public void centerOn(Area obj) {
        centerOn(obj.getCenter());
    }
    
    /**
     * Centers the object around the specified coordinates.
     * @param x The x position of the center.
     * @param y The y position of the center.
     */
    public void centerOn(double x, double y) {
        centerOn(new Position(x, y));
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
     * @param hor The amount to shift the object horizontally (positive means to the right).
     * @param ver The amount to shift the object vertically (positive means to the down).
     */
    public void translate(double hor, double ver) {
        translate(new Position(hor, ver));
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
    
    /**
     * Checks if a point is within the object.
     * @param x The x position of the point.
     * @param y The y position of the point.
     * @return True if the specified position is within the object.
     */
    public boolean isWithin(double x, double y) {
        return isWithin(new Position(x, y));
    }
    
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
     * collision and touch collision as the collision detection method.
     * @param obj The object to test collision against.
     * @return True if the objects are in contact.
     */
    public boolean isWithin(Area obj) {
        return isWithin(obj, Collision.TOUCH);
    }
    
    /**
     * Checks if the object is colliding with another object or area using rectangular
     * collision and a custom collision method.
     * @param obj The object to test collision against.
     * @param method The collision detection method to use.
     * @return True if the objects are in contact.
     */
    public boolean isWithin(Area obj, Collision method) {
        boolean horizontal, vertical;
        if (method.isType(Collision.INSIDE)) {
            horizontal = x >= obj.x && x <= obj.x + obj.width && x + width <= obj.x + obj.width;
            vertical = y >= obj.y && y <= obj.y + obj.height && y + height <= obj.y + obj.height;
        }
        else if (method.isType(Collision.EDGE)) {
            horizontal = x == obj.x + obj.width || x + width == obj.x;
            vertical = y == obj.y + obj.height || y + height == obj.y;
        }
        else {
            horizontal = x + width > obj.x && x < obj.x + obj.width;
            vertical = y + height > obj.y && y < obj.y + obj.height;
        }
        if (method == Collision.EDGE) return horizontal || vertical;
        else if (method.hasModifier(Collision.Modifier.BOTH)) return horizontal && vertical;
        else if (method.hasModifier(Collision.Modifier.X)) return horizontal;
        else if (method.hasModifier(Collision.Modifier.Y)) return vertical;
        else return horizontal && vertical;
    }
    
    /**
     * Checks if the object is colliding with any of the elements in the group
     * using the default collision method.
     * @param sprites The array list of sprites to test collision against.
     * @return True if the object is colliding with at least one element in the group.
     */
    public boolean isWithin(List<Sprite> sprites) {
        return isWithin(new Group(sprites));
    }
    
    /**
     * Checks if the object is colliding with any of the elements in the group
     * using the default collision method.
     * @param sprites The group of sprites to test collision against.
     * @return True if the object is colliding with at least one element in the group.
     */
    public boolean isWithin(Group sprites) {
        return isWithin(sprites, Collision.TOUCH);
    }
    
    /**
     * Checks if the object is colliding with any of the elements in the group
     * using the default collision method.
     * @param sprites The array list of sprites to test collision against.
     * @param method The collision detection method to use.
     * @return True if the object is colliding with at least one element in the group.
     */
    public boolean isWithin(List<Sprite> sprites, Collision method) {
        return isWithin(new Group(sprites), method);
    }
    
    /**
     * Checks if the object is colliding with any of the elements in the group
     * using the default collision method.
     * @param sprites The group of sprites to test collision against.
     * @param method The collision detection method to use.
     * @return True if the object is colliding with at least one element in the group.
     */
    public boolean isWithin(Group sprites, Collision method) {
        return sprites.isWithin(this);
    }
    
    void updateDrag() {
        if (draggable && Game.mouseEngaged(MouseEvent.BUTTON1) && Game.mouseWithin(this) && !Game.isDragging()) {
            Game.setDragging(true);
            dragging = true;
            offset = new Position(Game.mousePosition().x - x, Game.mousePosition().y - y);
        }
        if (dragging) {
            double offsetX = Game.mousePosition().x - offset.x;
            double offsetY = Game.mousePosition().y - offset.y;
            setPosition(new Position(offsetX, offsetY));
        }
        if (!Game.mousePressed()) {
            if (dragging) Game.setDragging(false);
            dragging = false;
        }
    }
    
    /**
     * Fills a rectangle on the current area using whatever color is currently set.
     */
    public void draw() {
        updateDrag();
        Game.painter().fillRect((int) x, (int) y, width, height);
    }
}
