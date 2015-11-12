package gametools;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Manages game input, the game window and the output displayed.
 */
public abstract class Game {
    private static int fps = 60, width = 800, height = 800, mouseX, mouseY;
    private static Graphics2D graphics;
    private static HashSet<Integer> keys = new HashSet<>(), mouse = new HashSet<>(),
            prevKeys = new HashSet<>(), prevMouse = new HashSet<>();
    private static final JFrame frame = new JFrame();
    private static BufferedImage screen, background = Tools.UNDEFINED_IMAGE;
    
    /**
     * Initializes the game and runs the window method.
     */
    public Game() {
        window();
    }
    
    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    /**
     * @return The width of the screen.
     */
    public static int getWidth() {
        return getDimensions().width;
    }
    
    /**
     * @return The height of the screen.
     */
    public static int getHeight() {
        return getDimensions().height;
    }
    
    /**
     * @return A dimension of the screen size.
     */
    public static Dimension getDimensions() {
        return new Dimension(width, height);
    }
    
    /**
     * @return An area of the screen size.
     */
    public static Area getArea() {
        return new Area(new Dimension(width, height));
    }
    
    /**
     * The 2D graphics object is referenced here. It can be used for drawing
     * basic shapes, lines, images and text.
     * @return The 2D graphics object that should be used for displaying all
     * graphical output in the game.
     */
    public static Graphics2D painter() {
        return graphics;
    }
    
    /**
     * @return The full mouse coordinates stored within a position.
     */
    public static Position getMousePosition() {
        return new Position(mouseX, mouseY);
    }
    
    /**
     * @return The center of the screen.
     */
    public static Position getCenter() {
        return getArea().getCenter();
    }
    
    /**
     * @return The rough FPS the game is running at.
     */
    public static int getFPS() {
        return fps;
    }
    
    /**
     * @return The current background image set for the game.
     */
    public static BufferedImage getBackground() {
        return background;
    }
    
    /**
     * @return The title of the game window.
     */
    public static String getTitle() {
        return frame.getTitle();
    }
    
    /**
     * Sets the title to  display at the top of the game window. The default is no title.
     * @param title The new title for the game window.
     */
    protected void setTitle(String title) {
        frame.setTitle(title);
    }
    
    /**
     * Sets the background to a solid color.
     * @param color The color the background should be.
     */
    protected void setBackground(Color color) {
        background = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D back = background.createGraphics();
        back.setColor(color);
        back.fillRect(0, 0, width, height);
    }
    
    /**
     * Sets an image to a background. If the image is smaller than the screen size
     * then it will be tiled automatically.
     * @param image The image or tile to be used for the background.
     */
    protected void setBackground(BufferedImage image) {
        background = image;
    }
    
    private void fixBackground() {
        if (background == Tools.UNDEFINED_IMAGE) setBackground(Color.BLACK);
        else if (background.getWidth() != width || background.getHeight() != height) {
            BufferedImage newBackground = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D back = newBackground.createGraphics();
            for (int x = 0; x < newBackground.getWidth(); x += background.getWidth())
                for (int y = 0; y < newBackground.getHeight(); y += background.getHeight())
                    back.drawImage(background, x, y, null);
            background = newBackground.getSubimage(0, 0, width, height);
        }
    }
    
    /**
     * Sets the rough amount of frames the game should display every second. The default is 60.
     * @param fps The FPS the game should run at.
     */
    protected void setFPS(int fps) {
        Game.fps = fps;
    }
    
    /**
     * Sets the width of the game.<br>
     * <b>Note</b>: This function will not work after the create function is executed.
     * @param width The width of the game.
     */
    protected void setWidth(int width) {
        setDimensions(new Dimension(width, height));
    }
    
    /**
     * Sets the height of the game.<br>
     * <b>Note</b>: This function will not work after the create function is executed.
     * @param height The height of the game.
     */
    protected void setHeight(int height) {
        setDimensions(new Dimension(width, height));
    }
    
    /**
     * Sets both the width and height of the game.<br>
     * <b>Note</b>: This function will not work after the create function is executed.
     * @param size The dimensions of the game.
     */
    protected void setDimensions(Dimension size) {
        width = (int) size.getWidth();
        height = (int) size.getHeight();
    }
    //</editor-fold>
    
    /**
     * Checks if any mouse button was just pressed down.
     * @return True if any mouse button was just pressed.
     */
    public static boolean mouseEngaged() {
        for (Integer button : mouse) if (!prevMouse.contains(button)) return true;
        return false;
    }
    
    /**
     * Checks if the specified mouse button was just pressed down.
     * @param button An integer representing the mouse button pressed down
     * (use the mouse event class to find the right integer).
     * @return True if the specified mouse button was just pressed.
     */
    public static boolean mouseEngaged(int button) {
        return mouse.contains(button) && !prevMouse.contains(button);
    }
    
    /**
     * Checks if any mouse button was just released.
     * @return True if any mouse button was just released.
     */
    public static boolean mouseReleased() {
        for (Integer button : prevMouse) if (!mouse.contains(button)) return true;
        return false;
    }
    
    /**
     * Checks if the specified mouse button was just released.
     * @param button An integer representing the mouse button released
     * (use the mouse event class to find the right integer).
     * @return True if the specified mouse button was just released.
     */
    public static boolean mouseReleased(int button) {
        return prevMouse.contains(button) && !mouse.contains(button);
    }
    
    /**
     * @return True if any button on the mouse is currently pressed.
     */
    public static boolean mousePressed() {
        return !mouse.isEmpty();
    }
    
    /**
     * Used to determine whether a button on the mouse is currently pressed by the user.
     * @param button An integer representing the mouse button being searched for
     * (use the mouse event class to find the right integer).
     * @return True if one of the mouse buttons being pressed down matches the input.
     */
    public static boolean mousePressed(int button) {
        return mouse.contains(button);
    }
    
    /**
     * Checks if the mouse is currently inside an object.
     * @param obj The object to check collision with the mouse.
     * @return True if the mouse is completely inside the specified object.
     */
    public static boolean mouseInside(Area obj) {
        return getMousePosition().isInside(obj);
    }
    
    /**
     * Checks if any button on the keyboard was just pressed down.
     * @return True if any button on the keyboard was just pressed.
     */
    public static boolean keyEngaged() {
        for (Integer key : keys) if (!prevKeys.contains(key)) return true;
        return false;
    }
    
    /**
     * Checks if the specified key was just pressed down.
     * @param key An integer representing the key pressed down
     * (use the key event class to find the right integer).
     * @return True if the specified key was just pressed.
     */
    public static boolean keyEngaged(int key) {
        return keys.contains(key) && !prevKeys.contains(key);
    }
    
    /**
     * Checks if any button on the keyboard was just released.
     * @return True if any button on the keyboard was just released.
     */
    public static boolean keyReleased() {
        for (Integer key : prevKeys) if (!keys.contains(key)) return true;
        return false;
    }
    
    /**
     * Checks if the specified key was just released.
     * @param key An integer representing the key released
     * (use the key event class to find the right integer).
     * @return True if the specified key was just released.
     */
    public static boolean keyReleased(int key) {
        return prevKeys.contains(key) && !keys.contains(key);
    }
    
    /**
     * @return True if any button on the keyboard is currently pressed.
     */
    public static boolean keyPressed() {
        return !keys.isEmpty();
    }
    
    /**
     * Used to determine whether a key is currently pressed by the user.
     * @param key An integer representing the key being searched for
     * (use the key event class to find the right integer).
     * @return True if one of the keys being pressed down matches the input.
     */
    public static boolean keyPressed(int key) {
        return keys.contains(key);
    }
    
    /**
     * Initializes the game and creates the window. Calls the setup method
     * after creating the window and then loops the run method at the specified FPS.<br>
     * <b>Note</b>: This function should only be executed in the main method and only once.
     */
    protected void create() {
        screen = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        graphics = screen.createGraphics();
        fixBackground();
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(keyControl);
        frame.addWindowListener(windowControl);
        frame.setResizable(false);
        
        JPanel panel = new JPanel();
        panel.add(new JLabel(new ImageIcon(screen)));
        panel.addMouseMotionListener(moveControl);
        panel.addMouseListener(clickControl);
        frame.add(panel);
        frame.setVisible(true);
        frame.setSize(width + 7, height + 34);
        frame.setLocationRelativeTo(null);
        
        setup();
        double timeStart = System.nanoTime();
        while (true) {
            if (System.nanoTime() - timeStart > 1000000000 / fps) {
                fixBackground();
                graphics.drawImage(background, 0, 0, null);
                timeStart = System.nanoTime();
                run();
                prevKeys = new HashSet<>(keys);
                prevMouse = new HashSet<>(mouse);
                frame.repaint();
            }
        }
    }
    
    /**
     * Method for creating window and adjusting game settings.
     */
    protected abstract void window();
    
    /**
     * Method that runs only once for initializing objects and running setup code.
     */
    protected abstract void setup();
    
    /**
     * Method that updates at the specified FPS (the default is 60) that should
     * contain most the main game code.
     */
    protected abstract void run();
    
    //<editor-fold defaultstate="collapsed" desc="Input Adapters">
    private final KeyAdapter keyControl = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent key) {
            keys.add(key.getKeyCode());
        }
        
        @Override
        public void keyReleased(KeyEvent key) {
            keys.remove(key.getKeyCode());
        }
    };

    private final MouseMotionAdapter moveControl = new MouseMotionAdapter() {
        @Override
        public void mouseDragged(MouseEvent me) {
            mouseX = me.getX() - 1;
            mouseY = me.getY() - 5;
        }
        
        @Override
        public void mouseMoved(MouseEvent me) {
            mouseX = me.getX() - 1;
            mouseY = me.getY() - 5;
        }
    };

    private final MouseAdapter clickControl = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent me) {
            mouse.add(me.getButton());
        }
        
        @Override
        public void mouseReleased(MouseEvent me) {
            mouse.remove(me.getButton());
        }
    };
    
    private final WindowAdapter windowControl = new WindowAdapter() {
        @Override
        public void windowIconified(WindowEvent we) {
            keys = new HashSet<>();
            mouse = new HashSet<>();
        }
        
        @Override
        public void windowLostFocus(WindowEvent we) {
            keys = new HashSet<>();
            mouse = new HashSet<>();
        }
        
        @Override
        public void windowDeactivated(WindowEvent we) {
            keys = new HashSet<>();
            mouse = new HashSet<>();
        }
    };
    //</editor-fold>
}