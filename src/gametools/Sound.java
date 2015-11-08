package gametools;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * An easy to use class for sound playback that is a wrapper for the java clip class.
 */
public class Sound {
    private Clip clip;
    private FloatControl volumeControl;
    private float volume;
    private boolean failed, pause = true, loop;
    
    /**
     * Creates a sound and opens the file at the passed in path.<br>
     * <b>Note</b>: This class only has support for certain encodings of wav files.
     * @param path The location of the sound file relative to the package of the project.
     */
    public Sound(String path) {
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(Tools.root.getResourceAsStream(path)));
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        }
        catch (Exception ex) {
            System.err.println("There were errors loading the sound '" + path + "':");
            System.err.println(ex.toString());
            failed = true;
        }
    }

    /**
     * Returns the relative volume of the sound, where zero is the default volume,
     * negative numbers mean quieter and positive numbers mean louder than the default.
     * @return
     */
    public float getVolume() {
        return volume;
    }
    
    /**
     * Plays the full sound once.
     */
    public void play() {
        if (!failed) {
            clip.stop();
            reset();
            pause = false;
            loop = false;
            clip.start();
        }
    }
    
    /**
     * Loops the sound infinitely.
     */
    public void loop() {
        if (!failed) {
            clip.stop();
            reset();
            pause = false;
            loop = true;
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
    
    /**
     * Pauses or resumes the sound.
     * @param pause Whether to pause the sound or to resume it from pause.
     */
    public void pause(boolean pause) {
        if (!failed && this.pause != pause) {
            if (pause) clip.stop();
            else {
                if (loop) clip.loop(Clip.LOOP_CONTINUOUSLY);
                else clip.start();
            }
            this.pause = pause;
        }
    }
    
    /**
     * Sets the sound back to the start without interrupting playback.
     */
    public void reset() {
        if (!failed) clip.setFramePosition(0);
    }
    
    /**
     * Sets the relative volume of the sound, where zero is the default volume,
     * negative numbers mean quieter and positive numbers mean louder than the default.
     * @param vol The new relative volume of the sound.
     */
    public void setVolume(float vol) {
        if (vol > volumeControl.getMaximum()) volumeControl.setValue(volumeControl.getMaximum());
        else if (vol < volumeControl.getMinimum()) volumeControl.setValue(volumeControl.getMinimum());
        else volumeControl.setValue(vol);
        volume = vol;
    }
    
    /**
     * @return True if the sound is not paused and is currently playing.
     */
    public boolean isPlaying() {
        if (!failed) return !pause && clip.isActive();
        else return false;
    }
    
    /**
     * Returns the clip the sound is using for more advanced operations.<br>
     * <b>Note</b>: Some changes to the clip may cause unexpected behavior
     * in the sound class.
     * @return
     */
    public Clip getClip() {
        return clip;
    }
}
