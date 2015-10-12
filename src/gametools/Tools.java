package gametools;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;

/**
 * A collection of methods to be used across the game.
 */
public class Tools {
    private static Class main;
    
    /**
     * Initializes the tools and sets up the root directory.
     * @param root The main class or any class in the root directory.
     */
    public static void initialize(Class root) {
        main = root;
    }
    
    /**
     * Imports any image stored within the project.
     * @param path The location of the image relative to the package of the project.
     * @return A buffered image of the file stored in the path or a blank image and an error.
     */
    public static BufferedImage loadImage(String path) {
        BufferedImage image;
        try {
            image = ImageIO.read(main.getResourceAsStream(path));
        }
        catch(IOException ex) {
            System.out.println(ex.toString());
            image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        }
        return image;
    }
    
    /**
     * Imports a range of images from a sprite sheet into an array. Images are loaded
     * in order from left to right and top to bottom.
     * @param path The location of the sprite sheet relative to the package of the project.
     * @param width The width of an individual sprite.
     * @param height The height of an individual sprite.
     * @param start The start of the range of sprite sheet images.
     * @param end The end of the range of sprite sheet images.
     * @return An array of buffered images that show each sprite.
     */
    public static BufferedImage[] loadSpriteSheet(String path, int width, int height, int start, int end) {
        BufferedImage[] full = loadSpriteSheet(path, width, height);
        return trimSpriteSheet(full, start, end);
    }
    
    /**
     * Imports all images from a sprite sheet into an array. Images are loaded in order
     * from left to right and top to bottom.
     * @param path The location of the sprite sheet relative to the package of the project.
     * @param width The width of an individual sprite.
     * @param height The height of an individual sprite.
     * @return An array of buffered images that show each sprite.
     */
    public static BufferedImage[] loadSpriteSheet(String path, int width, int height) {
        BufferedImage sheet = loadImage(path);
        int horizontal = (int) Math.floor(sheet.getWidth() / width);
        int vertical = (int) Math.floor(sheet.getHeight() / height);
        BufferedImage[] sprites = new BufferedImage[horizontal * vertical];
        int total = 0;
        for (int y = 0; y < vertical; y++) {
            for (int x = 0; x < horizontal; x++) {
                sprites[total] = sheet.getSubimage(x * width, y * height, width, height);
                total++;
            }
        }
        return sprites;
    }
    
    /**
     * Trims an array of sprites to a specific range.
     * @param full The full sprite sheet that needs to be trimmed.
     * @param start The start of the sprite sheet images.
     * @param end The end of the sprite sheet images.
     * @return An array of buffered images that show the trimmed selection.
     */
    public static BufferedImage[] trimSpriteSheet(BufferedImage[] full, int start, int end) {
        return Arrays.copyOfRange(full, start, end);
    }
    
    /**
     * Converts two coordinates to an item object containing those locations.
     * @param x1 The x position of the top left corner.
     * @param y1 The y position of the top left corner.
     * @param x2 The x position of the bottom right corner.
     * @param y2 The y position of the bottom right corner.
     * @return An item with the specified coordinates.
     */
    public static Item pointsToItem(int x1, int y1, int x2, int y2) {
        return new Item(x1, y1, x2 - x1, y2 - y1);
    }
}
