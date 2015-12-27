package gametools;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
     * @return An array list containing all the sprites in order.
     */
    public List<Sprite> getAll() {
        return elements;
    }
    
    public boolean isWithin(Position pos) {
        return isWithin(new Area(pos, new Dimension(1, 1)));
    }
    
    public boolean isWithin(Area obj) {
        return isWithin(obj, Area.Collision.TOUCH);
    }
    
    public boolean isWithin(Group sprites) {
        return getAllWithin(sprites, Area.Collision.TOUCH).size() > 0;
    }
    
    public boolean isWithin(Sprite... sprites) {
        return isWithin(Arrays.asList(sprites), Area.Collision.TOUCH);
    }
    
    public boolean isWithin(List<Sprite> sprites) {
        return getAllWithin(sprites, Area.Collision.TOUCH).size() > 0;
    }
    
    public boolean isWithin(Area obj, Area.Collision method) {
        return getAllWithin(obj, method).size() > 0;
    }
    
    public boolean isWithin(Group sprites, Area.Collision method) {
        return getAllWithin(sprites, method).size() > 0;
    }
    
    public boolean isWithin(Area.Collision method, Sprite... sprites) {
        return isWithin(Arrays.asList(sprites), method);
    }
    
    public boolean isWithin(List<Sprite> sprites, Area.Collision method) {
        return getAllWithin(sprites, method).size() > 0;
    }
    
    public List<Sprite> getAllWithin(Sprite... sprites) {
        return getAllWithin(Arrays.asList(sprites));
    }
    
    public List<Sprite> getAllWithin(Group sprites) {
        return getAllWithin(sprites.getAll());
    }
    
    public List<Sprite> getAllWithin(List<Sprite> sprites) {
        return getAllWithin(sprites, Area.Collision.TOUCH);
    }
    
    public List<Sprite> getAllWithin(Area.Collision method, Sprite... sprites) {
        return getAllWithin(Arrays.asList(sprites), method);
    }
    
    public List<Sprite> getAllWithin(Group sprites, Area.Collision method) {
        return getAllWithin(sprites.getAll(), method);
    }
    
    public List<Sprite> getAllWithin(List<Sprite> sprites, Area.Collision method) {
        List<Sprite> all = new ArrayList<>();
        for (Sprite sprite : sprites) all.addAll(getAllWithin(sprite, method));
        Set<Sprite> noDuplicates = new LinkedHashSet<>(all);
        all.clear();
        all.addAll(noDuplicates);
        return all;
    }
    
    public List<Sprite> getAllWithin(Position pos) {
        return getAllWithin(new Area(pos, new Dimension(1, 1)));
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
    
    public void add(int i, Sprite sprite) {
        elements.add(i, sprite);
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
     * Removes all the sprites from the group that match the ones passed in.
     * @param sprites An group of sprites to remove.
     */
    public void remove(Group sprites) {
        elements.removeAll(sprites.getAll());
    }
    
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
    
    public void script(Script script) {
        for (Sprite sprite : elements) sprite.script(script);
    }
    
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
            sprite.draw(Graphic.UpdateType.UPDATE_ONLY);
            if (removeSprites && moveableArea != Area.UNDEFINED_AREA)
                if (!sprite.isWithin(moveableArea)) it.remove();
        }
        Collections.reverse(elements);
        for (Iterator<Sprite> it = elements.iterator(); it.hasNext();) it.next().draw(Graphic.UpdateType.DRAW_ONLY);
        Collections.reverse(elements);
    }
}
