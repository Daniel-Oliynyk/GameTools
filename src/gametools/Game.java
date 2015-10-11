package gametools;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
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
    private static int fps = 60, width = 800, height = 800;
    private static Graphics2D graphics;
    
    private static HashSet<Integer> keys = new HashSet<>();
    private static int mouseButton, mouseX, mouseY;
    
    private static Color backgroundColor = Color.BLACK;
    private static final JFrame frame = new JFrame();
    private BufferedImage screen;
    
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
     * @return The height of the screen.
     */
    public static Dimension getDimensions() {
        return new Dimension(width, height);
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
     * @return The x position of the mouse within the game window.
     */
    public static int getMouseX() {
        return getMousePosition().x;
    }
    
    /**
     * @return The y position of the mouse within the game window.
     */
    public static int getMouseY() {
        return getMousePosition().y;
    }
    
    /**
     * @return The full mouse coordinates stored within a point.
     */
    public static Point getMousePosition() {
        return new Point(mouseX, mouseY);
    }
    
    /**
     * @return The background color that the screen is repainted to every frame.
     */
    public static Color getBackgroundColor() {
        return backgroundColor;
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
     * Sets the color that the screen is repainted to every frame. The default is black.
     * @param color The color that the screen is repainted to every frame. The default is black.
     */
    protected void setBackground(Color color) {
        backgroundColor = color;
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
        setDimensions(width, height);
    }
    
    /**
     * Sets the height of the game.<br>
     * <b>Note</b>: This function will not work after the create function is executed.
     * @param height The height of the game.
     */
    protected void setHeight(int height) {
        setDimensions(width, height);
    }
    
    /**
     * Sets both the width and height of the game.<br>
     * <b>Note</b>: This function will not work after the create function is executed.
     * @param width The width of the game.
     * @param height The height of the game.
     */
    protected void setDimensions(int width, int height) {
        setDimensions(new Dimension(width, height));
    }
    
    /**
     * Sets both the width and height of the game.<br>
     * <b>Note</b>: This function will not work after the create function is executed.
     * @param dimensions The dimensions of the game.
     */
    protected void setDimensions(Dimension dimensions) {
        width = (int) dimensions.getWidth();
        height = (int) dimensions.getHeight();
    }
    //</editor-fold>
    
    /**
     * @return True if any button on the mouse is currently pressed.
     */
    public static boolean mousePressed() {
        return mouseButton != MouseEvent.NOBUTTON;
    }
    
    /**
     * Used to determine whether a button on the mouse is currently pressed by the user.
     * @param button An integer representing the button on the mouse button
     * being searched for (use the mouse event class to find the right integer).
     * @return True if one of the mouse buttons being pressed down matches the input.
     */
    public static boolean mousePressed(int button) {
        return mouseButton == button;
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
                graphics.setColor(backgroundColor);
                graphics.fillRect(0, 0, width, height);
                timeStart = System.nanoTime();
                run();
                frame.repaint();
            }
        }
    }
    
    /**
     * Method for creating window and adjusting game settings.
     */
    public abstract void window();
    
    /**
     * Method that runs only once for initializing objects and running setup code.
     */
    public abstract void setup();
    
    /**
     * Method that updates at the specified FPS (the default is 60) that should
     * contain most the main game code.
     */
    public abstract void run();
    
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
            mouseX = me.getX();
            mouseY = me.getY() - 3;
        }
        
        @Override
        public void mouseMoved(MouseEvent me) {
            mouseX = me.getX();
            mouseY = me.getY() - 3;
        }
    };

    private final MouseAdapter clickControl = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent me) {
            mouseButton = me.getButton();
        }
        
        @Override
        public void mouseReleased(MouseEvent me) {
            mouseButton = MouseEvent.NOBUTTON;
        }
    };
    
    private final WindowAdapter windowControl = new WindowAdapter() {
        @Override
        public void windowIconified(WindowEvent we) {
            keys = new HashSet<>();
            mouseButton = MouseEvent.NOBUTTON;
        }
        
        @Override
        public void windowLostFocus(WindowEvent we) {
            keys = new HashSet<>();
            mouseButton = MouseEvent.NOBUTTON;
        }
        
        @Override
        public void windowDeactivated(WindowEvent we) {
            keys = new HashSet<>();
            mouseButton = MouseEvent.NOBUTTON;
        }
    };
    //</editor-fold>
}