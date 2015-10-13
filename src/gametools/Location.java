package gametools;

public class Location {
    public double x, y;
    
    public Location() {
        this(0, 0);
    }
    
    public Location(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public double dist(Location loc) {
        return Math.sqrt(Math.pow(loc.x - x, 2) + Math.pow(loc.y - y, 2));
    }
    
    public void rotate(Location center, double angle) {
        double current = Math.atan2(center.y - y, center.x - center.x);
        x = Math.cos(current + angle) * dist(center);
        y = Math.sin(current + angle) * dist(center);
    }
}
