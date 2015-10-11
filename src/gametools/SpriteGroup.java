package gametools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class for managing and updating groups of sprites.
 */
public class SpriteGroup {
    private final List<Sprite> elements = new ArrayList();
    private boolean remove;
    private Item moveableArea;
    
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
     * @return The amount of elements contained in the group.
     */
    public int size() {
        return elements.size();
    }
    
    /**
     * @return True if the group is removing sprites outside their boundaries.
     */
    public boolean removeSprites() {
        return remove;
    }
    
    /**
     * Adds a sprite to the end of the group.
     * @param sprite The sprite to add.
     */
    public void add(Sprite sprite) {
        elements.add(sprite);
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
     * @param remove Whether or not to remove sprites from the group if
     * they go outside their boundaries.
     */
    public void removeSprites(boolean remove) {
        this.remove = remove;
    }
    
    /**
     * Defines the area the sprites can move inside. If removing sprites is turned on
     * and a sprite goes outside this area the sprite will automatically be removed.
     * @param x1 The x position of the top left corner.
     * @param y1 The y position of the top left corner.
     * @param x2 The x position of the bottom right corner.
     * @param y2 The y position of the bottom right corner.
     */
    public void setRemoveArea(int x1, int y1, int x2, int y2) {
        setRemoveArea(new Item(x1, y1, x2 - x1, y2 - y1));
    }
    
    /**
     * Defines the area the sprites can move inside. If removing sprites is turned on
     * and a sprite goes outside this area the sprite will automatically be removed.
     * @param area The area sprites are allowed to move within.
     */
    public void setRemoveArea(Item area) {
        moveableArea = area;
        remove = true;
    }
    
    /**
     * Removes all sprites automatically as soon as they leave the screen.
     */
    public void removeWhenOffScreen() {
        remove = true;
        moveableArea = new Item(0, 0, Game.getWidth(), Game.getHeight());
    }
    
    /**
     * Runs the draw method on every sprite and removes sprites as necessary.
     */
    public void drawAll() {
        for (Iterator<Sprite> it = elements.iterator(); it.hasNext();) {
            Sprite sprite = it.next();
            sprite.draw();
            if (remove && !sprite.isWithin(moveableArea)) it.remove();
        }
    }
}
