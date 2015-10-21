package gametools;

/**
 * A terse class used to shorten positioning code.
 */
public class Position {
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
     * Creates a position on the specified coordinates.
     * @param x The new x value.
     * @param y The new y value.
     */
    public Position(double x, double y) {
        set(x, y);
    }
    
    /**
     * @return The precise x location of the position.
     */
    public final double x() {
        return getX();
    }
    
    /**
     * @return The precise x location of the position.
     */
    public final double getX() {
        return x;
    }
    
    /**
     * @return The precise y location of the position.
     */
    public final double y() {
        return getY();
    }
    
    /**
     * @return The precise y location of the position.
     */
    public final double getY() {
        return y;
    }
    
    /**
     * Sets the x value of the position.
     * @param x The new x value.
     */
    public final void x(double x) {
        setX(x);
    }
    
    /**
     * Sets the x value of the position.
     * @param x The new x value.
     */
    public final void setX(double x) {
        set(x, y);
    }
    
    /**
     * Sets the y value of the position.
     * @param y The new x value.
     */
    public final void y(double y) {
        setY(y);
    }
    
    /**
     * Sets the y value of the position.
     * @param y The new x value.
     */
    public final void setY(double y) {
        set(x, y);
    }
    
    /**
     * Increments the x value by the specified amount.
     * @param ix The amount to increment the x by.
     */
    public final void ix(double ix) {
        incrementX(ix);
    }
    
    /**
     * Increments the x value by the specified amount.
     * @param ix The amount to increment the x by.
     */
    public final void incrementX(double ix) {
        set(x + ix, y);
    }
    
    /**
     * Increments the y value by the specified amount.
     * @param iy The amount to increment the y by.
     */
    public final void iy(double iy) {
        incrementY(iy);
    }
    
    /**
     * Increments the y value by the specified amount.
     * @param iy The amount to increment the y by.
     */
    public final void incrementY(double iy) {
        set(x, y + iy);
    }
    
    /**
     * Sets both the x and y at once.
     * @param x The new x value.
     * @param y The new y value.
     */
    public final void set(double x, double y) {
        setPosition(x, y);
    }
    
    /**
     * Sets both the x and y at once.
     * @param x The new x value.
     * @param y The new y value.
     */
    public final void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Translates the position and adds the passed in values to the x and y.
     * @param tx The amount to add to the current x value.
     * @param ty The amount to add to the current y value.
     */
    public final void trans(double tx, double ty) {
        translate(tx, ty);
    }
    
    /**
     * Translates the position and adds the passed in values to the x and y.
     * @param tx The amount to add to the current x value.
     * @param ty The amount to add to the current y value.
     */
    public final void translate(double tx, double ty) {
        x = x + tx;
        y = y + ty;
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
