package gametools;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    public Group(Sprite... elements) {
        this(Arrays.asList(elements));
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
     * Returns all the elements in the group.
     * @return An array list containing all the sprites in order.
     */
    public List<Sprite> getAll() {
        return elements;
    }
    
    /**
     * Checks if the passed in position is within at least one element in the group.
     * @param x The x of the point to test collision against.
     * @param y The y of the point to test collision against.
     * @return True if at least one element is within the point.
     */
    public boolean isWithin(double x, double y) {
        return isWithin(new Position(x, y));
    }
    
    /**
     * Checks if the passed in position is within at least one element in the group.
     * @param pos The position to test collision against.
     * @return True if at least one element is within the position.
     */
    public boolean isWithin(Position pos) {
        return isWithin(new Area(pos, new Dimension()));
    }
    
    /**
     * Checks if at least one element is colliding with the passed in object.
     * @param obj The object to test collision against.
     * @return True if at least one element is within the object.
     */
    public boolean isWithin(Area obj) {
        return isWithin(obj, Area.Collision.TOUCH);
    }
    
    /**
     * Checks if any two sprites collide between the two groups.
     * @param sprites The group to test collision against.
     * @return True if any two elements between the groups collide.
     */
    public boolean isWithin(Group sprites) {
        return isWithin(sprites.getAll());
    }
    
    /**
     * Checks if any of the passed in sprites collide with the group elements.
     * @param sprites The sprites to test collision against.
     * @return True if any elements collide.
     */
    public boolean isWithin(Sprite... sprites) {
        return isWithin(Arrays.asList(sprites), Area.Collision.TOUCH);
    }
    
    /**
     * Checks if any two sprites collide between the two groups.
     * @param sprites The array list to test collision against.
     * @return True if any two elements between the groups collide.
     */
    public boolean isWithin(List<Sprite> sprites) {
        return isWithin(sprites, Area.Collision.TOUCH);
    }
    
    /**
     * Checks if at least one element is colliding with the passed in object.
     * @param obj The object to test collision against.
     * @param method The collision detection method to use.
     * @return True if at least one element is within the object.
     */
    public boolean isWithin(Area obj, Area.Collision method) {
        for (Sprite current : elements)
            if (current.isWithin(obj, method)) return true;
        return false;
    }
    
    /**
     * Checks if any two sprites collide between the two groups.
     * @param sprites The group to test collision against.
     * @param method The collision detection method to use.
     * @return True if any two elements between the groups collide.
     */
    public boolean isWithin(Group sprites, Area.Collision method) {
        return isWithin(sprites.getAll(), method);
    }
    
    /**
     * Checks if any of the passed in sprites collide with the group elements.
     * @param method The collision detection method to use.
     * @param sprites The sprites to test collision against.
     * @return True if any elements collide.
     */
    public boolean isWithin(Area.Collision method, Sprite... sprites) {
        return isWithin(Arrays.asList(sprites), method);
    }
    
    /**
     * Checks if any two sprites collide between the two groups.
     * @param sprites The array list to test collision against.
     * @param method The collision detection method to use.
     * @return True if any two elements between the groups collide.
     */
    public boolean isWithin(List<Sprite> sprites, Area.Collision method) {
        for (Sprite current : elements)
            for (Sprite sprite : sprites)
                if (current.isWithin(sprite, method)) return true;
        return false;
    }
    
    /**
     * Returns an array list of all elements from the group that collide with
     * the passed in sprites.
     * @param sprites The sprites to test collision against.
     * @return An array list containing all the colliding sprites.
     */
    public List<Sprite> getAllWithin(Sprite... sprites) {
        return getAllWithin(Arrays.asList(sprites));
    }
    
    /**
     * Returns an array list of all elements from the group that collide with
     * elements from the passed in group.
     * @param sprites The group to test collision against.
     * @return An array list containing all the colliding sprites.
     */
    public List<Sprite> getAllWithin(Group sprites) {
        return getAllWithin(sprites.getAll());
    }
    
    /**
     * Returns an array list of all elements from the group that collide with
     * elements from the passed in array list.
     * @param sprites The array list to test collision against.
     * @return An array list containing all the colliding sprites.
     */
    public List<Sprite> getAllWithin(List<Sprite> sprites) {
        return getAllWithin(sprites, Area.Collision.TOUCH);
    }
    
    /**
     * Returns an array list of all elements from the group that collide with
     * the passed in sprites.
     * @param method The collision detection method to use.
     * @param sprites The sprites to test collision against.
     * @return An array list containing all the colliding sprites.
     */
    public List<Sprite> getAllWithin(Area.Collision method, Sprite... sprites) {
        return getAllWithin(Arrays.asList(sprites), method);
    }
    
    /**
     * Returns an array list of all elements from the group that collide with
     * elements from the passed in group.
     * @param sprites The group to test collision against.
     * @param method The collision detection method to use.
     * @return An array list containing all the colliding sprites.
     */
    public List<Sprite> getAllWithin(Group sprites, Area.Collision method) {
        return getAllWithin(sprites.getAll(), method);
    }
    
    /**
     * Returns an array list of all elements from the group that collide with
     * elements from the passed in array list.
     * @param sprites The array list to test collision against.
     * @param method The collision detection method to use.
     * @return An array list containing all the colliding sprites.
     */
    public List<Sprite> getAllWithin(List<Sprite> sprites, Area.Collision method) {
        List<Sprite> results = new ArrayList<>();
        for (Sprite element : elements) {
            for (Sprite sprite : sprites) {
                if (element.isWithin(sprite, method)) {
                    results.add(element);
                    break;
                }
            }
        }
        return results;
    }
    
    /**
     * Returns all sprites from the group that are colliding with the specified
     * position as a new array list.
     * @param x The x of the point to test collision against.
     * @param y The y of the point to test collision against.
     * @return An array list containing all sprites that are within the position.
     */
    public List<Sprite> getAllWithin(double x, double y) {
        return getAllWithin(new Position(x, y));
    }
    
    /**
     * Returns all sprites from the group that are colliding with the specified
     * position as a new array list.
     * @param pos The position to check collision against.
     * @return An array list containing all sprites that are within the position.
     */
    public List<Sprite> getAllWithin(Position pos) {
        return getAllWithin(new Area(pos, new Dimension()));
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
     * @param method The collision detection method to use.
     * @return An array list containing all sprites that are touching the object.
     */
    public List<Sprite> getAllWithin(Area obj, Area.Collision method) {
        List<Sprite> colliding = new ArrayList<>();
        for (Sprite sprite : elements) if (sprite.isWithin(obj, method)) colliding.add(sprite);
        return colliding;
    }
    
    /**
     * Returns the size of the group.
     * @return The amount of elements contained in the group.
     */
    public int size() {
        return elements.size();
    }
    
    /**
     * Returns whether or not the group is removing sprites outside their boundaries.
     * @return True if the group is removing sprites.
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
     * Adds a sprite at the specified index.
     * @param i The position to insert the sprite in.
     * @param sprite The sprite to add.
     */
    public void add(int i, Sprite sprite) {
        elements.add(i, sprite);
    }
    
    /**
     * Adds all the passed in elements to the group.
     * @param sprites A group of sprites to add to the group.
     */
    public void add(Group sprites) {
        add(sprites.getAll());
    }
    
    /**
     * Adds all the passed in elements to the group.
     * @param sprites The sprites to add to the group.
     */
    public void add(Sprite... sprites) {
        add(Arrays.asList(sprites));
    }
    
    /**
     * Adds all the passed in elements to the group.
     * @param sprites An array list of sprites to add to the group.
     */
    public void add(List<Sprite> sprites) {
        elements.addAll(sprites);
    }
    
    /**
     * Empties the group of all elements.
     * @param safe If true, immediately removes all elements, which can cause
     * errors if list if currently being iterated upon. If false, sets the remove
     * tag to true on all elements causing them to be removed safely in the next iteration.
     */
    public void clear(boolean safe) {
        if (!safe) elements.clear();
        else for (Sprite sprite : elements) sprite.remove(true);
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
     * Removes all the sprites from the group that match the ones passed in.
     * @param sprites An group of sprites to remove.
     */
    public void remove(Group sprites) {
        elements.removeAll(sprites.getAll());
    }
    
    /**
     * Removes all the passed in sprites.
     * @param sprites The sprites to remove.
     */
    public void remove(Sprite... sprites) {
        remove(Arrays.asList(sprites));
    }
    
    /**
     * Removes all the sprites from the group that match the ones passed in.
     * @param sprites An array list of sprites to remove from the group.
     */
    public void remove(List<Sprite> sprites) {
        elements.removeAll(sprites);
    }
    
    /**
     * Sets whether or not to remove sprites from the group if they go outside their boundaries.
     * @param remove Whether or not to remove sprites from the group.
     */
    public void removeWhenOutsideBounds(boolean remove) {
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
     * Adds a custom script to each sprite.
     * @param script The script to add to the sprites.
     */
    public void script(Script script) {
        for (Sprite sprite : elements) sprite.script(script);
    }
    
    /**
     * Removes the script from each sprite.
     */
    public void removeScript() {
        for (Sprite sprite : elements) sprite.removeScript();
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
     * @param x The x of the point around which to rotate.
     * @param y The y of the point around which to rotate.
     * @param rot The direction to rotate in.
     */
    public void rotate(double x, double y, Sprite.Rotation rot) {
        rotate(new Position(x, y), rot);
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
     * @param hor The amount to shift the group horizontally (positive means to the right).
     * @param ver The amount to shift the group vertically (positive means to the down).
     */
    public void translate(double hor, double ver) {
        translate(new Position(hor, ver));
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
            sprite.draw(Graphic.UpdateType.UPDATE_ONLY);
            if (sprite.markedForRemoval()) it.remove();
            else if (removeSprites && moveableArea != Area.UNDEFINED_AREA && !sprite.isWithin(moveableArea)) it.remove();
        }
        Collections.reverse(elements);
        for (Iterator<Sprite> it = elements.iterator(); it.hasNext();) it.next().draw(Graphic.UpdateType.DRAW_ONLY);
        Collections.reverse(elements);
    }
}
