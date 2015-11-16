package gametools;

import java.awt.Dimension;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Combines the area and animation classes into one object.
 */
public class Graphic extends Area {
    double angle;
    Animation animation = Animation.UNDEFINED_ANIMATION, previous = animation;
    
    /**
     * Creates a blank graphic without an image or position.
     */
    public Graphic() {
        super();
    }
    
    public Graphic(BufferedImage image) {
        this(new Position(), new Animation(image));
    }
    
    public Graphic(Animation animation) {
        this(new Position(), animation);
    }
    
    public Graphic(Graphic graphic) {
        this(graphic.getPosition(), graphic.animation);
        angle = graphic.angle;
    }
    
    public Graphic(Position pos, Animation animation) {
        super(pos, animation.getDimensions());
        this.animation = animation;
    }
    
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
        if (size.width != animation.getWidth() || size.height != animation.getHeight()) {
            Animation prev = previous;
            animation.setDimensions(size);
            setAnimation(animation);
            previous = prev;
        }
    }
    
    public void setImage(BufferedImage image) {
        setAnimation(new Animation(image));
    }
    
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
        angle = Position.fixAngle(ang);
    }

    /**
     * An empty method that runs before the draw method and should be overridden for custom code.
     */
    protected void update() {}
    
    @Override
    public void draw() {
        updateDrag();
        animation.update();
        AffineTransform at = new AffineTransform();
        at.rotate(angle, getCenter().x, getCenter().y);
        double trueX = getCenter().x - (animation.getWidth() / 2);
        double trueY = getCenter().y - (animation.getHeight() / 2);
        at.translate(trueX, trueY);
        Game.painter().drawImage(animation.getFrame(), at, null);
    }
}
