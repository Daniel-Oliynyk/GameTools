package gametools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
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
    private static Random rand = new Random();
    
    /**
     * Initializes the tools and sets up the root directory.
     * @param root The main class or any class in the root directory.
     */
    public static void initialize(Class root) {
        Tools.root = root;
    }
    
    static Class getRoot() {
        return root;
    }
    
    /**
     * Converts negatives and angles past a full rotation into a more readable format.
     * @param ang The angle to fix.
     * @return The same angle but represented non negatively and smaller than two PI.
     */
    public static double fixAngle(double ang) {
        double fixed = ang % (Math.PI * 2);
        if (fixed < 0) fixed = (Math.PI * 2) + fixed;
        return fixed;
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
        catch (Exception ex) {
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
     * The short form notation for creating an area.
     * @param size The width and height of the area.
     * @return An area with the specified coordinates and size.
     */
    public static Area ar(Dimension size) {
        return new Area(size);
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
    
    public static Group grp(List<Sprite> sprites) {
        return new Group(sprites);
    }
    
    /**
     * Creates a text pop up for the user with the passed in text.
     * @param prompt The text to display inside the pop up.
     */
    public static void messageDialog(String prompt) {
        JOptionPane.showMessageDialog(null, prompt);
    }
    
    /**
     * Creates an input prompt with the passed in text.
     * @param prompt The text to display inside the pop up.
     * @return The data the user inputted.
     */
    public static String inputDialog(String prompt) {
        return JOptionPane.showInputDialog(prompt);
    }
    
    /**
     * Generates a completely random position that is greater than zero.
     * @return A position with random coordinates.
     */
    public static Position randomPosition() {
        return new Position(rand.nextInt(), rand.nextInt());
    }
    
    /**
     * Generates a random position within the passed in area.
     * @param area The area within which the random position will be.
     * @return A random position within the passed in area.
     */
    public static Position randomPosition(Area area) {
        double x = rand.nextInt((int) area.width) + area.x;
        double y = rand.nextInt((int) area.height) + area.y;
        return new Position(x, y);
    }
    
    /**
     * Generates a button graphic with a custom color and centered text.
     * @param back The background color of the button.
     * @param fore The text color of the button.
     * @param text The string to draw in one line on the button.
     * @param bounds The position and size of the button.
     * @return A button graphics with the passed in properties.
     */
    public static Graphic generateButton(Color back, Color fore, String text, Area bounds) {
        BufferedImage button = new BufferedImage(bounds.width, bounds.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = button.createGraphics();
        Color end = (back == Color.WHITE)? Color.LIGHT_GRAY : back;
        GradientPaint grad = new GradientPaint(0, -bounds.height, Color.WHITE, 0, bounds.height, end);
        graphics.setPaint(grad);
        graphics.fillRoundRect(0, 0, bounds.width, bounds.height, 7, 7);
        graphics.setColor(Color.BLACK);
        graphics.setStroke(new BasicStroke(2));
        graphics.drawRoundRect(1, 1, bounds.width - 2, bounds.height - 2, 5, 5);
        FontMetrics font = graphics.getFontMetrics();
        Rectangle2D rect = font.getStringBounds(text, graphics);
        int x = (int) ((bounds.width - rect.getWidth()) / 2);
        int y = (int) ((bounds.height - rect.getHeight()) / 2) + font.getAscent();
        graphics.setColor(fore);
        graphics.drawString(text, x, y);
        return new Graphic(bounds.getPosition(), new Animation(button));
    }
}
