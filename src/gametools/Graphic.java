package gametools;

import java.awt.Dimension;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Combines the area and animation classes into one object.
 */
public class Graphic extends Area {
    /**
     * The different ways of drawing the object.
     */
    public static enum UpdateType {
        /**
         * Does not update the object or draw it.
         */
        NONE(false, false),
        /**
         * Only updates the object.
         */
        UPDATE_ONLY(true, false),
        /**
         * Only draws the object.
         */
        DRAW_ONLY(false, true),
        /**
         * First updates the object, and then draws it.
         */
        UPDATE_DRAW(true, true);
        
        private final boolean update, draw;
        
        private UpdateType(boolean update, boolean draw) {
            this.update = update;
            this.draw = draw;
        }
        
        /**
         * Whether or not this update type requires updating.
         * @return True if the update type requires updating.
         */
        public boolean update() {
            return update;
        }
        
        /**
         * Whether or not this update type requires drawing.
         * @return True if the update type requires drawing.
         */
        public boolean draw() {
            return draw;
        }
    }
    double angle;
    Animation animation = Animation.UNDEFINED_ANIMATION, previous = animation;
    
    /**
     * Creates a blank graphic without an image or position.
     */
    public Graphic() {
        super();
    }
    
    /**
     * Creates a graphic in the default location (the corner of the screen) with a custom image.
     * @param image The image for the graphic.
     */
    public Graphic(BufferedImage image) {
        this(new Position(), new Animation(image));
    }
    
    /**
     * Creates a graphic in the default location (the corner of the screen) with a custom animation.
     * @param animation The animation for the graphic.
     */
    public Graphic(Animation animation) {
        this(new Position(), animation);
    }
    
    /**
     * Creates an exact copy of the passed in graphic.
     * @param graphic The graphic to copy properties from.
     */
    public Graphic(Graphic graphic) {
        this(graphic.getPosition(), graphic.animation);
        angle = graphic.angle;
    }
    
    /**
     * Creates a graphic at the specified coordinates with a custom image.
     * @param pos The position of the graphic.
     * @param image The image for the graphic.
     */
    public Graphic(Position pos, BufferedImage image) {
        super(pos, new Dimension(image.getWidth(), image.getHeight()));
        this.animation = new Animation(image);
    }
    
    /**
     * Creates a graphic at the specified coordinates with a custom animation.
     * @param pos The position of the graphic.
     * @param animation The animation for the graphic.
     */
    public Graphic(Position pos, Animation animation) {
        super(pos, animation.getDimensions());
        this.animation = animation;
    }
    
    /**
     * Returns the current image representing the object.
     * @return A buffered image representing the current state of the object.
     */
    public BufferedImage getImage() {
        return animation.getFrame();
    }
    
    /**
     * @return The current animation on the object.
     */
    public Animation getAnimation() {
        return animation;
    }
    
    /**
     * Returns the previous animation used by the object.
     * @return The previous animation or an undefined animation if there was none.
     */
    public Animation getPreviousAnimation() {
        return new Animation(previous);
    }
    
    /**
     * @return The current angle of the object.
     */
    public double getAngle() {
        return angle;
    }
    
    @Override
    public void setWidth(int width) {
        setDimensions(new Dimension(width, animation.getHeight()));
    }
    
    @Override
    public void setHeight(int height) {
        setDimensions(new Dimension(animation.getWidth(), height));
    }
    
    @Override
    public void setDimensions(Dimension size) {
        if (size.width != animation.getWidth() && size.height != animation.getHeight()) {
            Animation prev = previous;
            animation.setDimensions(size);
            setAnimation(animation);
            previous = prev;
        }
    }
    
    /**
     * Sets a new one image animation for the object.
     * @param image The image for the object.
     */
    public void setImage(BufferedImage image) {
        setAnimation(new Animation(image));
    }
    
    /**
     * Sets the animation for the object.
     * @param animation The animation for the object.
     */
    public void setAnimation(Animation animation) {
        double prev = angle;
        setAngle(0);
        previous = this.animation;
        this.animation = animation;
        width = animation.getWidth();
        height = animation.getHeight();
        setAngle(prev);
    }
    
    /**
     * Rotates the object around its center. Fix here
     * @param ang The new angle of the object.
     */
    public void setAngle(double ang) {
        if (ang == 0) {
            x = getCenter().x - (animation.getWidth() / 2);
            y = getCenter().y - (animation.getHeight() / 2);
            width = animation.getWidth();
            height = animation.getHeight();
        }
        else {
            Position trueCen = getCenter();
            Position truePos = new Position();
            Position rotated = new Position();
            truePos.x = trueCen.x - (animation.getWidth() / 2);
            truePos.y = trueCen.y - (animation.getHeight() / 2);
            Position[] corners = new Area(truePos, animation.getDimensions()).getAllCorners();
            boolean flag = true;
            for (Position corner : corners) {
                corner.rotate(trueCen, ang);
                if (corner.x < rotated.x || flag) rotated.x = (int) corner.x;
                if (corner.y < rotated.y || flag) rotated.y = (int) corner.y;
                flag = false;
            }
            width = (int) ((getCenter().x - rotated.x) * 2);
            height = (int) ((getCenter().y - rotated.y) * 2);
            centerOn(trueCen);
        }
        angle = Tools.fixAngle(ang);
    }

    /**
     * An empty method that runs before the draw method and should be overridden for custom code.
     */
    protected void update() {}
    
    /**
     * Draws the object and updates its animation.<br>
     * <b>Note</b>: Overriding this method may cause unpredictable behavior for the object.
     */
    @Override
    public void draw() {
        draw(UpdateType.UPDATE_DRAW);
    }
    
    /**
     * Draws the object and updates its animation.<br>
     * <b>Note</b>: Overriding this method may cause unpredictable behavior for the object.
     * @param type The way the object should be updated.
     */
    public void draw(UpdateType type) {
        if (type.update()) {
            updateDrag();
            animation.update();
        }
        if (type.draw()) {
            AffineTransform at = new AffineTransform();
            at.rotate(angle, getCenter().x, getCenter().y);
            double trueX = getCenter().x - (animation.getWidth() / 2);
            double trueY = getCenter().y - (animation.getHeight() / 2);
            at.translate(trueX, trueY);
            Game.painter().drawImage(animation.getFrame(), at, null);
        }
    }
}
