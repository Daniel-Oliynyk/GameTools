package gametools;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

/**
 * Used to animate game objects such as sprites.
 */
public class Animation {
    /**
     * Loop the animation non stop infinity times.
     */
    private static final int LOOP_INFINITY = -1;
    /**
     * An empty animation to represent a non existent or undefined object.
     */
    public static final Animation UNDEFINED_ANIMATION = new Animation(Tools.UNDEFINED_IMAGE);
    private final BufferedImage[] frames;
    private BufferedImage frameImage;
    private int frameNumber, speed, repeatNumber, repeatAmount;
    private boolean complete;
    
    /**
     * Creates an animation with just one still image.
     * @param image A buffered image for the animation.
     */
    public Animation(BufferedImage image) {
        this(new BufferedImage[]{image}, 2, LOOP_INFINITY);
    }
    
    /**
     * Creates an animation using each element of the passed in array for a frame.
     * @param frames An array of images that represent each frame of the animation.
     */
    public Animation(BufferedImage[] frames) {
        this(frames, 2, LOOP_INFINITY);
    }
    
    /**
     * Creates an animation using each element of the passed in array for a frame
     * and sets the animation speed.
     * @param frames An array of images that represent each frame of the animation.
     * @param speed The how many updates it should take for the animation.
     * to move to the next frame.
     */
    public Animation(BufferedImage[] frames, int speed) {
        this(frames, speed, LOOP_INFINITY);
    }
    
    /**
     * Creates an animation using each element of the passed in array for a frame
     * and sets the animation speed and repeat amount.
     * @param frames An array of images that represent each frame of the animation.
     * @param speed The how many updates it should take for the animation.
     * @param repeatAmount The amount of times the animation should repeat before completing.
     */
    public Animation(BufferedImage[] frames, int speed, int repeatAmount) {
        frameImage = frames[0];
        this.frames = frames;
        this.speed = speed;
        this.repeatAmount = repeatAmount;
    }
    
    /**
     * Returns the width of the animation. This is automatically
     * set to be the width of the animation frames.
     * @return The width of the sprite.
     */
    public int getWidth() {
        return frames[0].getWidth();
    }
    
    /**
     * Returns the height of the animation. This is automatically
     * set to be the height of the animation frames.
     * @return The height of the sprite.
     */
    public int getHeight() {
        return frames[0].getHeight();
    }
    
    /**
     * @return A dimension representing that contains both the width and height of the animation.
     */
    public Dimension getDimensions() {
        return new Dimension(frames[0].getWidth(), frames[0].getHeight());
    }
    
    /**
     * @return A buffered image of the current frame of the animation.
     */
    public BufferedImage getFrame() {
        return frameImage;
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
    public int getSize() {
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
        return (int) Math.floor(frameNumber / speed);
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
     * Sets how many frames it will take for the animation to move to the next image.
     * @param speed The speed of the animation.
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    /**
     * @param frameNumber The frame the animation should jump to.
     */
    public void setFrameNumber(int frameNumber) {
        this.frameNumber = frameNumber;
    }
    
    /**
     * @param repeatAmount Sets the amount of times the animation should repeat.
     */
    public void setRepeatAmount(int repeatAmount) {
        this.repeatAmount = repeatAmount;
    }
    
    /**
     * Updates the animation and moves it to the next frame if necessary.
     */
    public void update() {
        if (!complete) {
            frameNumber++;
            if (frameNumber + 1 >= frames.length * speed) {
                frameNumber = 0;
                repeatNumber++;
                if (repeatAmount != LOOP_INFINITY && repeatNumber >= repeatAmount) {
                    complete = true;
                    repeatNumber = 0;
                    repeatAmount = LOOP_INFINITY;
                }
            }
            int frame = (int) Math.floor(frameNumber / speed);
            frameImage = frames[frame];
        }
        else frameImage = frames[0];
    }
}
