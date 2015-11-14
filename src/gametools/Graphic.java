package gametools;

import java.awt.Dimension;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Graphic extends Area {
    double angle;
    Animation animation = Animation.UNDEFINED_ANIMATION, previous = animation;
    
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
        setDimensions(new Dimension(width, height));
    }
    
    @Override
    public void setHeight(int height) {
        setDimensions(new Dimension(width, height));
    }
    
    @Override
    public void setDimensions(Dimension size) {
        setArea(getPosition(), size);
    }
    
    @Override
    public void setArea(Area area) {
        setArea(area.getPosition(), area.getDimensions());
    }
    
    @Override
    public void setArea(Position pos, Dimension size) {
        animation.setDimensions(size);
        x = pos.x;
        y = pos.y;
        width = size.width;
        height = size.height;
    }
    
    public void setImage(BufferedImage image) {
        setAnimation(new Animation(image));
    }
    
    public void setAnimation(Animation animation) {
        double prev = angle;
        setAngle(0);
        width = animation.getWidth();
        height = animation.getHeight();
        previous = this.animation;
        this.animation = animation;
        setAngle(prev);
    }
    
    /**
     * Rotates the object around its center. Fix here
     * @param ang The new angle of the object.
     */
    public void setAngle(double ang) {
        Position truePos = new Position();
        truePos.x = getCenter().x - (animation.getWidth() / 2);
        truePos.y = getCenter().y - (animation.getHeight() / 2);
        Area rotated = new Area();
        Position[] corners = new Area(truePos, animation.getDimensions()).getAllCorners();
        boolean flag = true;
        for (Position corner : corners) {
            corner.rotate(getCenter(), ang);
            if (corner.x < rotated.x || flag) rotated.x = (int) corner.x;
            if (corner.y < rotated.y || flag) rotated.y = (int) corner.y;
            flag = false;
        }
        rotated.width = (int) ((getCenter().x - rotated.x)) * 2;
        rotated.height = (int) ((getCenter().y - rotated.y)) * 2;
        rotated.centerOn(getCenter());
        angle = Position.fixAngle(ang);
        setArea(rotated);
    }

    @Override
    public void draw() {
        animation.update();
        double trueX = getCenter().x - (animation.getWidth() / 2);
        double trueY = getCenter().y - (animation.getHeight() / 2);
        AffineTransform at = new AffineTransform();
        at.rotate(angle, getCenter().x, getCenter().y);
        at.translate(trueX, trueY);
        Game.painter().drawImage(animation.getFrame(), at, null);
    }
}
