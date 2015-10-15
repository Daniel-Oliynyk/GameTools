package gametools;

/**
 * Used for positioning and basic math.
 */
public class Location {
    /**
     * The precise x position of the location.
     */
    public double x;
    /**
     * The precise y position of the location.
     */
    public double y;
    
    /**
     * Creates a location with the position of zero.
     */
    public Location() {
        this(0, 0);
    }
    
    /**
     * Creates a location on the specified coordinates.
     * @param x
     * @param y
     */
    public Location(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Calculates the distance between two points using pythagorean theorem.
     * @param location The second location to calculate distance to.
     * @return The distance between the current location and the one passed in.
     */
    public double dist(Location location) {
        return Math.sqrt(Math.pow(location.x - x, 2) + Math.pow(location.y - y, 2));
    }
    
    /**
     * Rotates the point around the specified location.
     * @param center The center point to rotate around.
     * @param angle The amount of rotation to do (in radians).
     */
    public void rotate(Location center, double angle) {
        double current = Math.atan2(center.y - y, center.x - x);
        double dist = dist(center);
        x = center.x + (Math.cos(current + angle) * dist);
        y = center.y + (Math.sin(current + angle) * dist);
    }
    
    public void draw(int radius) {
        Game.painter().fillOval((int) x - radius, (int) y - radius, radius, radius);
    }
}
