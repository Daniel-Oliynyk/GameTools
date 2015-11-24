package gametools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class for managing and updating groups of sprites.
 */
public class Group {
    private final List<Sprite> elements;
    private boolean removeSprites;
    private Area moveableArea = Area.UNDEFINED_AREA;
    
    /**
     * Creates a blank group with no sprites.
     */
    public Group() {
        this(new ArrayList<>());
    }
    
    /**
     * Creates a copy of the specified group.
     * @param copy The group to copy contents and properties from.
     */
    public Group(Group copy) {
        elements = copy.elements;
        removeSprites = copy.removeSprites;
        moveableArea = copy.moveableArea;
    }
    
    /**
     * Creates a group from the passed in sprites.
     * @param elements The sprites the group should start with.
     */
    public Group(List<Sprite> elements) {
        this.elements = elements;
    }
    
    /**
     * Returns a single sprite at the specified position in the group.
     * @param i The index of the sprite.
     * @return The sprite at the specified position in the group.
     */
    public Sprite get(int i) {
        return elements.get(i);
    }
    
    /**
     * @return An array list containing all the sprites in order.
     */
    public List<Sprite> getAll() {
        return elements;
    }
    
    /**
     * Returns the first sprite (in the group's default order) that is colliding
     * with the specified object.
     * @param obj The object to check collision against.
     * @return The first sprite that is colliding with the specified object. 
     */
    public Sprite getFirstWithin(Area obj) {
        return getFirstWithin(obj, Area.Collision.TOUCH);
    }
    
    /**
     * Returns the first sprite (in the group's default order) that is colliding
     * with the object using the specified collision method.
     * @param obj The object to check collision against.
     * @param method The collision testing method to use.
     * @return The first sprite that is colliding with the specified object. 
     */
    public Sprite getFirstWithin(Area obj, Area.Collision method) {
        return getAllWithin(obj, method).get(0);
    }
    
    /**
     * Returns a group of all the sprites that are colliding with the specified object.
     * @param obj The object to check collision against.
     * @return A group containing all sprites that are touching the object.
     */
    public Group getGroupWithin(Area obj) {
        return getGroupWithin(obj, Area.Collision.TOUCH);
    }
    
    /**
     * Returns a group of all the sprites that are colliding with the specified object.
     * @param obj The object to check collision against.
     * @param method The collision testing method to use.
     * @return A group containing all sprites that are touching the object.
     */
    public Group getGroupWithin(Area obj, Area.Collision method) {
        return new Group(getAllWithin(obj, method));
    }
    
    /**
     * Returns all sprites from the group that are colliding with the specified
     * object as a new array list.
     * @param obj The object to check collision against.
     * @return An array list containing all sprites that are touching the object.
     */
    public List<Sprite> getAllWithin(Area obj) {
        return getAllWithin(obj, Area.Collision.TOUCH);
    }
    
    /**
     * Returns all sprites from the group that are colliding with the object
     * (using the specified collision method) as a new array list.
     * @param obj The object to check collision against.
     * @param method The collision testing method to use.
     * @return An array list containing all sprites that are touching the object.
     */
    public List<Sprite> getAllWithin(Area obj, Area.Collision method) {
        List<Sprite> colliding = new ArrayList<>();
        for (Sprite sprite : elements) if (sprite.isWithin(obj, method)) colliding.add(sprite);
        return colliding;
    }
    
    /**
     * @return The amount of elements contained in the group.
     */
    public int size() {
        return elements.size();
    }
    
    /**
     * @return True if the group is removing sprites outside their boundaries.
     */
    public boolean removeSprites() {
        return removeSprites;
    }
    
    /**
     * Adds a sprite to the end of the group.
     * @param sprite The sprite to add.
     */
    public void add(Sprite sprite) {
        elements.add(sprite);
    }
    
    /**
     * Adds all the passed in elements to the group.
     * @param sprites An array list of sprites to add to the group.
     */
    public void addAll(List<Sprite> sprites) {
        elements.addAll(sprites);
    }
    
    /**
     * Empties the group of all elements.
     */
    public void clear() {
        elements.clear();
    }
    
    /**
     * Removes the specified sprite from the group.
     * @param i The index of the sprite to remove.
     */
    public void remove(int i) {
        elements.remove(i);
    }
    
    /**
     * Removes the specified sprite from the group.
     * @param sprite The sprite to remove.
     */
    public void remove(Sprite sprite) {
        elements.remove(sprite);
    }
    
    /**
     * Removes all sprites that are colliding with the specified object.
     * @param obj The object to check collision against.
     */
    public void removeAllWithin(Area obj) {
        removeAllWithin(obj, Area.Collision.TOUCH);
    }
    
    /**
     * Removes all sprites that are colliding with the specified object using a
     * custom collision testing method.
     * @param obj The object to check collision against.
     * @param method The collision testing method to use.
     */
    public void removeAllWithin(Area obj, Area.Collision method) {
        removeAll(getAllWithin(obj, method));
    }
    
    /**
     * Removes all the sprites from the group that match the ones passed in.
     * @param sprites An group of sprites to remove.
     */
    public void removeAll(Group sprites) {
        elements.removeAll(sprites.getAll());
    }
    
    /**
     * Removes all the sprites from the group that match the ones passed in.
     * @param sprites An array list of sprites to remove from the group.
     */
    public void removeAll(List<Sprite> sprites) {
        elements.removeAll(sprites);
    }
    
    /**
     * @param remove Whether or not to remove sprites from the group if
     * they go outside their boundaries.
     */
    public void removeSprites(boolean remove) {
        removeSprites = remove;
    }
    
    /**
     * Defines the area the sprites can move inside. If removing sprites is turned on
     * and a sprite goes outside this area the sprite will automatically be removed.
     * @param area The area sprites are allowed to move within.
     */
    public void setRemoveArea(Area area) {
        moveableArea = area;
        removeSprites = true;
    }
    
    /**
     * Removes all sprites automatically as soon as they leave the screen.
     */
    public void removeWhenOffScreen() {
        removeSprites = true;
        moveableArea = new Area(Game.getArea());
    }
    
    /**
     * Turns all the sprites in the group in the specified direction.
     * @param rot The rotation constant for the direction the sprite should turn in.
     */
    public void turn(Sprite.Rotation rot) {
        for (Sprite sprite : elements) sprite.turn(rot);
    }
    
    /**
     * Rotates all the sprites in the group around the specified position.
     * @param mid The position around which to rotate.
     * @param rot The direction to rotate in.
     */
    public void rotate(Position mid, Sprite.Rotation rot) {
        for (Sprite sprite : elements) sprite.rotate(mid, rot);
    }
    
    /**
     * Translates all the sprites in the group.
     * @param trans The amount translate the group by.
     */
    public void translate(Position trans) {
        for (Sprite sprite : elements) sprite.translate(trans);
    }
    
    /**
     * Runs the draw method on every sprite and removes sprites as necessary.
     */
    public void drawAll() {
        for (Iterator<Sprite> it = elements.iterator(); it.hasNext();) {
            Sprite sprite = it.next();
            sprite.draw();
            if (removeSprites && moveableArea != Area.UNDEFINED_AREA)
                if (!sprite.isWithin(moveableArea)) it.remove();
        }
    }
}
