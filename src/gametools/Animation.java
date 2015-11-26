package gametools;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * Used to animate game objects such as sprites.
 */
public class Animation {
    /**
     * Loop the animation non stop infinity times.
     */
    public static final int LOOP_CONTINUOUSLY = -1;
    /**
     * An empty animation to represent a non existent or undefined object.
     */
    public static final Animation UNDEFINED_ANIMATION = new Animation(Tools.UNDEFINED_IMAGE);
    
    private final BufferedImage[] frames, original;
    private int counter, frame, speed, repeatNumber, repeatAmount;
    private boolean complete, paused;
    
    /**
     * Creates an animation with just one still image.
     * @param image A buffered image for the animation.
     */
    public Animation(BufferedImage image) {
        this(new BufferedImage[]{image}, 2, LOOP_CONTINUOUSLY);
    }
    
    /**
     * Creates an animation using each element of the passed in array for a frame.
     * @param frames An array of images that represent each frame of the animation.
     */
    public Animation(BufferedImage[] frames) {
        this(frames, 8, LOOP_CONTINUOUSLY);
    }
    
    /**
     * Creates an animation using each element of the passed in array for a frame
     * and sets the animation speed.
     * @param frames An array of images that represent each frame of the animation.
     * @param speed The how many updates it should take for the animation.
     * to move to the next frame.
     */
    public Animation(BufferedImage[] frames, int speed) {
        this(frames, speed, LOOP_CONTINUOUSLY);
    }
    
    /**
     * Creates an animation and copies over the properties from the passed in object.
     * @param animation The animation to copy the properties from.
     */
    public Animation(Animation animation) {
        original = animation.original.clone();
        frames = animation.frames.clone();
        speed = animation.speed;
        counter = animation.counter;
        frame = animation.frame;
        repeatAmount = animation.repeatAmount;
        repeatNumber = animation.repeatNumber;
        complete = animation.complete;
        paused = animation.paused;
    }
    
    /**
     * Creates an animation using each element of the passed in array for a frame
     * and sets the animation speed and repeat amount.
     * @param frames An array of images that represent each frame of the animation.
     * @param speed The how many updates it should take for the animation.
     * @param repeatAmount The amount of times the animation should repeat before completing.
     */
    public Animation(BufferedImage[] frames, int speed, int repeatAmount) {
        original = frames.clone();
        this.frames = frames.clone();
        this.speed = speed;
        this.repeatAmount = repeatAmount;
    }
    
    /**
     * Returns the width of the animation. This is automatically
     * set to be the width of the animation frames.
     * @return The width of the sprite.
     */
    public int getWidth() {
        return getDimensions().width;
    }
    
    /**
     * Returns the height of the animation. This is automatically
     * set to be the height of the animation frames.
     * @return The height of the sprite.
     */
    public int getHeight() {
        return getDimensions().height;
    }
    
    /**
     * @return A dimension representing that contains both the width and height of the animation.
     */
    public Dimension getDimensions() {
        if (frames != Tools.UNDEFINED_SPRITE_SHEET) return new Dimension(frames[0].getWidth(), frames[0].getHeight());
        else return new Dimension();
    }
    
    /**
     * @return A buffered image of the current frame of the animation.
     */
    public BufferedImage getFrame() {
        return frames[frame];
    }
    
    /**
     * @return A array of the buffered images for each frame.
     */
    public BufferedImage[] getAllFrames() {
        return frames;
    }
    
    /**
     * @return The amount of frames in the animation.
     */
    public int getLength() {
        return frames.length;
    }
    
    /**
     * @return The amount of updates it will take to move to the next frame of the animation.
     */
    public int getSpeed() {
        return speed;
    }
    
    /**
     * @return The current frame the animation is at.
     */
    public int getFrameNumber() {
        return (int) Math.floor(frame / speed);
    }
    
    /**
     * @return The amount of times the animation has already replayed.
     */
    public int getRepeatNumber() {
        return repeatNumber;
    }
    
    /**
     * @return The amount of times the animation will repeat before stopping.
     */
    public int getRepeatAmount() {
        return repeatAmount;
    }
    
    /**
     * @return True is the animation has completed.
     */
    public boolean isComplete() {
        return complete;
    }
    
    /**
     * @return True if the animation is currently paused.
     */
    public boolean isPaused() {
        return paused;
    }
    
    /**
     * Sets how many frames it will take for the animation to move to the next image.
     * @param speed The speed of the animation.
     */
    public void setSpeed(int speed) {
        this.speed = speed;
        this.counter = frame * speed;
    }
    
    /**
     * @param frameNumber The frame the animation should jump to.
     */
    public void setFrameNumber(int frameNumber) {
        this.frame = frameNumber;
        this.counter = frame * speed;
    }
    
    /**
     * @param repeatAmount Sets the amount of times the animation should repeat.
     */
    public void setRepeatAmount(int repeatAmount) {
        this.repeatAmount = repeatAmount;
    }
    
    /**
     * Pauses or resumes the animation.
     * @param pause Whether to pause or resume to animation.
     */
    public void pause(boolean pause) {
        paused = pause;
    }
    
    /**
     * Scales the animation to match the new width.
     * @param width The new width.
     */
    protected void setWidth(int width) {
        setDimensions(new Dimension(width, getHeight()));
    }
    
    /**
     * Scales the animation to match the new width.
     * @param height The new height.
     */
    protected void setHeight(int height) {
        setDimensions(new Dimension(getWidth(), height));
    }
    
    /**
     * Scales the animation to match the new width and height.
     * @param size The new size of the animation frames.
     */
    protected void setDimensions(Dimension size) {
        for (int i = 0; i < original.length; i++) {
            BufferedImage scaledImage = new BufferedImage(size.width, size.height,
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics2D = scaledImage.createGraphics();
            graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graphics2D.drawImage(original[i], 0, 0, size.width, size.height, null);
            graphics2D.dispose();
            frames[i] = scaledImage;
        }
    }
    
    /**
     * Resets and restarts the animations.
     */
    public void reset() {
        frame = 0;
        repeatNumber = 0;
        complete = false;
    }
    
    /**
     * Updates the animation and moves it to the next frame if necessary.
     */
    public void update() {
        if (!complete) {
            if (!paused) counter++;
            if (counter + 1 >= frames.length * speed) {
                counter = 0;
                repeatNumber++;
                if (repeatAmount != LOOP_CONTINUOUSLY && repeatNumber >= repeatAmount) {
                    complete = true;
                    repeatNumber = 0;
                    repeatAmount = LOOP_CONTINUOUSLY;
                }
            }
            frame = (int) Math.floor(counter / speed);
        }
        else {
            counter = 0;
            frame = 0;
        }
    }
}
