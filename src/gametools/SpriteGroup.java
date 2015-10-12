package gametools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class for managing and updating groups of sprites.
 */
public class SpriteGroup {
    private final List<Sprite> elements;
    private boolean removeSprites;
    private Item moveableArea;
    
    /**
     * Creates a blank sprite group.
     */
    public SpriteGroup() {
        elements = new ArrayList<>();
    }
    
    /**
     * Creates a sprite group and fills it with the passed in sprites.
     * @param elements The sprites the group should start with.
     */
    public SpriteGroup(List<Sprite> elements) {
        this.elements = elements;
    }
    
    /**
     * Returns a single sprite from the group at the specified reference.
     * @param i The index of the sprite.
     * @return The sprite at the specified position in the group.
     */
    public Sprite get(int i) {
        return elements.get(i);
    }
    
    /**
     * @return An array list containing all sprites in order.
     */
    public List<Sprite> getAll() {
        return elements;
    }
    
    /**
     * Returns the first sprite (in the group's default order) that is completely
     * inside the specified object.
     * @param item The object to check collision against.
     * @return The first sprite that is completely within the specified object. 
     */
    public Sprite getFirstInside(Item item) {
        return getAllInside(item).get(0);
    }
    
    /**
     * Returns all sprites from the group that are completely inside the specified
     * object as a new sprite group.
     * @param item The object to check collision against.
     * @return A sprite group containing all sprites that are inside the object.
     */
    public SpriteGroup getGroupInside(Item item) {
        return new SpriteGroup(getAllInside(item));
    }
    
    /**
     * Returns all sprites from the group that are completely inside the specified
     * object as a new array list.
     * @param item The object to check collision against.
     * @return An array list containing all sprites that are inside the object.
     */
    public List<Sprite> getAllInside(Item item) {
        List<Sprite> colliding = new ArrayList<>();
        for (Sprite sprite : elements) if (sprite.isInside(item)) colliding.add(sprite);
        return colliding;
    }
    
    /**
     * Returns the first sprite (in the group's default order) that is colliding
     * with the specified object.
     * @param item The object to check collision against.
     * @return The first sprite that is colliding with the specified object. 
     */
    public Sprite getFirstCollidingWith(Item item) {
        return getAllCollidingWith(item).get(0);
    }
    
    /**
     * Returns all sprites from the group that are colliding with the specified
     * object as a new sprite group.
     * @param item The object to check collision against.
     * @return A sprite group containing all sprites that are touching the object.
     */
    public SpriteGroup getGroupCollidingWith(Item item) {
        return new SpriteGroup(getAllCollidingWith(item));
    }
    
    /**
     * Returns all sprites from the group that are colliding with the specified
     * object as a new array list.
     * @param item The object to check collision against.
     * @return An array list containing all sprites that are touching the object.
     */
    public List<Sprite> getAllCollidingWith(Item item) {
        List<Sprite> colliding = new ArrayList<>();
        for (Sprite sprite : elements) if (sprite.isCollidingWith(item)) colliding.add(sprite);
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
     * Removes all sprites that are completely inside the specified item.
     * @param item The item to check collision against.
     */
    public void removeAllInside(Item item) {
        removeAll(getAllInside(item));
    }
    
    /**
     * Removes all sprites that are colliding with the specified item.
     * @param item The item to check collision against.
     */
    public void removeAllCollidingWith(Item item) {
        removeAll(getAllCollidingWith(item));
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
    public void setRemoveArea(Item area) {
        moveableArea = area;
        removeSprites = true;
    }
    
    /**
     * Removes all sprites automatically as soon as they leave the screen.
     */
    public void removeWhenOffScreen() {
        removeSprites = true;
        moveableArea = new Item(0, 0, Game.getWidth(), Game.getHeight());
    }
    
    /**
     * Runs the draw method on every sprite and removes sprites as necessary.
     */
    public void drawAll() {
        for (Iterator<Sprite> it = elements.iterator(); it.hasNext();) {
            Sprite sprite = it.next();
            sprite.draw();
            if (removeSprites && !sprite.isCollidingWith(moveableArea)) it.remove();
        }
    }
}
