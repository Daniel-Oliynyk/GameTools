package gametools;

/**
 * A terse class used to shorten positioning code.
 */
public class Position {
    /**
     * An empty position to represent a non existent or undefined point.
     */
    public static final Position UNDEFINED_POSITION = new Position(-1, -1);
    /**
     * The precise x location of the position.
     */
    protected double x;
    /**
     * The precise y location of the position.
     */
    protected double y;
    
    //<editor-fold defaultstate="collapsed" desc="Constructors, Getters and Setters">
    /**
     * Creates the object with the position of zero.
     */
    public Position() {
        this(0, 0);
    }
    
    /**
     * Creates a position and then copies over the coordinates from the specified position.
     * @param pos The position to copy coordinates from.
     */
    public Position(Position pos) {
        this(pos.x, pos.y);
    }
    
    /**
     * Creates a position on the specified coordinates.
     * @param x The x location.
     * @param y The y location.
     */
    public Position(double x, double y) {
        set(x, y);
    }
    
    /**
     * @return The precise x location of the position.
     */
    public double x() {
        return getX();
    }
    
    /**
     * @return The precise x location of the position.
     */
    public double getX() {
        return x;
    }
    
    /**
     * @return The precise y location of the position.
     */
    public double y() {
        return getY();
    }
    
    /**
     * @return The precise y location of the position.
     */
    public double getY() {
        return y;
    }
    
    /**
     * Sets the x value of the position.
     * @param x The new x value.
     */
    public void x(double x) {
        setX(x);
    }
    
    /**
     * Sets the x value of the position.
     * @param x The new x value.
     */
    public void setX(double x) {
        set(x, y);
    }
    
    /**
     * Sets the y value of the position.
     * @param y The new x value.
     */
    public void y(double y) {
        setY(y);
    }
    
    /**
     * Sets the y value of the position.
     * @param y The new x value.
     */
    public void setY(double y) {
        set(x, y);
    }
    
    /**
     * Increments the x value by the specified amount.
     * @param ix The amount to increment the x by.
     */
    public void ix(double ix) {
        incrementX(ix);
    }
    
    /**
     * Increments the x value by the specified amount.
     * @param ix The amount to increment the x by.
     */
    public void incrementX(double ix) {
        set(x + ix, y);
    }
    
    /**
     * Increments the y value by the specified amount.
     * @param iy The amount to increment the y by.
     */
    public void iy(double iy) {
        incrementY(iy);
    }
    
    /**
     * Increments the y value by the specified amount.
     * @param iy The amount to increment the y by.
     */
    public void incrementY(double iy) {
        set(x, y + iy);
    }
    
    /**
     * Copies over the x and and y from the specified position.
     * @param pos The position to copy coordinates from.
     */
    public void set(Position pos) {
        set(pos.x, pos.y);
    }
    
    /**
     * Sets both the x and y at once.
     * @param x The new x value.
     * @param y The new y value.
     */
    public void set(double x, double y) {
        setPosition(x, y);
    }
    
    /**
     * Copies over the x and and y from the specified position.
     * @param pos The position to copy coordinates from.
     */
    public void setPosition(Position pos) {
        setPosition(pos.x, pos.y);
    }
    
    /**
     * Sets both the x and y at once.
     * @param x The new x value.
     * @param y The new y value.
     */
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Translates the position and adds the passed in values to the x and y.
     * @param trans The amount to add to the current x and y value.
     */
    public void trans(Position trans) {
        translate(trans);
    }
    
    /**
     * Translates the position and adds the passed in values to the x and y.
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
        return getClass().getName() + "[x=" + x + ", y=" + y + "]";
    }
    //</editor-fold>
    
    /**
     * Calculates the distance between two positions using pythagorean theorem.
     * @param pos The second position to calculate distance to.
     * @return The distance between the current position and the one passed in.
     */
    public double dist(Position pos) {
        return Math.sqrt(Math.pow(pos.x - x, 2) + Math.pow(pos.y - y, 2));
    }
    
    /**
     * Uses trigonometry to calculate the angle to the specified position.
     * @param pos The position to calculate the angle to.
     * @return The angle to the specified position in radians.
     */
    public double angleTo(Position pos) {
        return Math.atan2(pos.y - y, pos.x - x);
    }
    
    /**
     * Checks if the point is inside the specified object.
     * @param obj The object to check collision against.
     * @return True if the point is completely inside the specified object.
     */
    public boolean isInside(Area obj) {
        return obj.isWithin(this);
    }
    
    /**
     * Rotates the point around the specified position.
     * @param mid The center point to rotate around.
     * @param ang The amount of rotation to do (in radians).
     */
    public void rotate(Position mid, double ang) {
        double cur = angleTo(mid);
        double dist = dist(mid);
        x = mid.x + Math.cos(cur + ang) * dist;
        y = mid.y + Math.sin(cur + ang) * dist;
    }
    
    /**
     * Fills a circle with its center at the current position using whatever
     * color is currently set.
     * @param rad The radius of the circle to draw.
     */
    public void draw(int rad) {
        Game.painter().fillOval((int) x - rad, (int) y - rad, rad * 2, rad * 2);
    }
}
