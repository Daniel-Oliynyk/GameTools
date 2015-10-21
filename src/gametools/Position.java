package gametools;

/**
 * Used for positioning and basic math.
 */
public class Position {
    /**
     * The precise x position of the position.
     */
    public double x;
    /**
     * The precise y position of the position.
     */
    public double y;
    
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
        this.x = x;
        this.y = y;
    }
    
    /**
     * Calculates the distance between two positions using pythagorean theorem.
     * @param pos The second position to calculate distance to.
     * @return The distance between the current position and the one passed in.
     */
    public double dist(Position pos) {
        return Math.sqrt(Math.pow(pos.x - x, 2) + Math.pow(pos.y - y, 2));
    }
    
    public double angleTo(Position pos) {
        return Math.atan2(pos.y - y, pos.x - x);
    }
    
    /**
     * Rotates the point around the specified position.
     * @param center The center point to rotate around.
     * @param angle The amount of rotation to do (in radians).
     */
    public void rotate(Position center, double angle) {
        double current = angleTo(center);
        double dist = dist(center);
        x = center.x + Math.cos(current + angle) * dist;
        y = center.y + Math.sin(current + angle) * dist;
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
