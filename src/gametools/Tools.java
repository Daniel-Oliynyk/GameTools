package gametools;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * A collection of methods to be used across the game.
 */
public class Tools {
    /**
     * An empty image to represent a non existent image or an image that failed to load.
     */
    public static final BufferedImage UNDEFINED_IMAGE = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    /**
     * An undefined sprite sheet or a sprite sheet that could not be created due to load errors.
     */
    public static final BufferedImage[] UNDEFINED_SPRITE_SHEET = new BufferedImage[]{UNDEFINED_IMAGE};
    /**
     * A class situated in the root of the project to help with creating relative paths.
     */
    private static Class root;
    
    /**
     * Initializes the tools and sets up the root directory.
     * @param root The main class or any class in the root directory.
     */
    public static void initialize(Class root) {
        Tools.root = root;
    }
    
    public static Class getRoot() {
        return root;
    }
    
    /**
     * Imports any image stored within the project.
     * @param path The location of the image relative to the package of the project.
     * @return A buffered image of the file stored in the path or a blank image and an error.
     */
    public static BufferedImage loadImage(String path) {
        BufferedImage image;
        try {
            image = ImageIO.read(root.getResourceAsStream(path));
        }
        catch(Exception ex) {
            System.err.println("There were errors loading the image '" + path + "':");
            System.err.println(ex.toString());
            image = UNDEFINED_IMAGE;
        }
        return image;
    }
    
    /**
     * Imports a range of images from a sprite sheet into an array. Images are loaded
     * in order from left to right and top to bottom.
     * @param path The location of the sprite sheet relative to the package of the project.
     * @param size The size of an individual sprite.
     * @param start The start of the range of sprite sheet images.
     * @param end The end of the range of sprite sheet images.
     * @return An array of buffered images that show each sprite.
     */
    public static BufferedImage[] loadSpriteSheet(String path, Dimension size, int start, int end) {
        BufferedImage[] full = loadSpriteSheet(path, size);
        if (full != UNDEFINED_SPRITE_SHEET) return trimSpriteSheet(full, start, end);
        else return UNDEFINED_SPRITE_SHEET;
    }
    
    /**
     * Imports all images from a sprite sheet into an array. Images are loaded in order
     * from left to right and top to bottom.
     * @param path The location of the sprite sheet relative to the package of the project.
     * @param size The size of an individual sprite.
     * @return An array of buffered images that show each sprite.
     */
    public static BufferedImage[] loadSpriteSheet(String path, Dimension size) {
        BufferedImage sheet = loadImage(path);
        if (sheet != UNDEFINED_IMAGE) {
            int horizontal = (int) Math.floor(sheet.getWidth() / size.width);
            int vertical = (int) Math.floor(sheet.getHeight() / size.height);
            BufferedImage[] sprites = new BufferedImage[horizontal * vertical];
            int total = 0;
            for (int y = 0; y < vertical; y++) {
                for (int x = 0; x < horizontal; x++) {
                    sprites[total] = sheet.getSubimage(x * size.width, y * size.height, size.width, size.height);
                    total++;
                }
            }
            return sprites;
        }
        else {
            System.err.println("There was an error generating a spritesheet from the image '" + path + "'");
            return UNDEFINED_SPRITE_SHEET;
        }
    }
    
    /**
     * Trims an array of sprites to a specific range.
     * @param full The full sprite sheet that needs to be trimmed.
     * @param start The start of the sprite sheet images.
     * @param end The end of the sprite sheet images.
     * @return An array of buffered images that show the trimmed selection.
     */
    public static BufferedImage[] trimSpriteSheet(BufferedImage[] full, int start, int end) {
        if (full != UNDEFINED_SPRITE_SHEET) return Arrays.copyOfRange(full, start, end);
        else return UNDEFINED_SPRITE_SHEET;
    }
    
    /**
     * Converts two points to an area object going through those positions.
     * @param tl The position of the top left corner.
     * @param br The position of the bottom right corner.
     * @return An area with the specified coordinates.
     */
    public static Area ar(Position tl, Position br) {
        return new Area(tl, new Dimension((int) (tl.x - br.x), (int) (tl.y - br.y)));
    }
    
    /**
     * The short form notation for creating an area.
     * @param pos The position of the top left corner.
     * @param size The width and height of the area.
     * @return An area with the specified coordinates and size.
     */
    public static Area ar(Position pos, Dimension size) {
        return new Area(pos, size);
    }
    
    /**
     * The short form notation for creating a position.
     * @param x The x location of the point.
     * @param y The y location of the point.
     * @return A position that contains the x and y.
     */
    public static Position pt(double x, double y) {
        return new Position(x, y);
    }
    
    /**
     * The short form notation for creating a dimension.
     * @param width The width of the dimension.
     * @param height The height of the dimension.
     * @return A dimension with the passed in width and height.
     */
    public static Dimension dm(int width, int height) {
        return new Dimension(width, height);
    }
    
    public static void messageDialog(String prompt) {
        JOptionPane.showMessageDialog(null, prompt);
    }
    
    public static String inputDialog(String prompt) {
        return JOptionPane.showInputDialog(prompt);
    }
}
