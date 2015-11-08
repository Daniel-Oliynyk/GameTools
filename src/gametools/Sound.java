package gametools;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
    private Clip clip;
    private FloatControl volumeControl;
    private float volume;
    private boolean failed, pause = true, loop;
    
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

    public float getVolume() {
        return volume;
    }
    
    public void play() {
        if (!failed) {
            clip.stop();
            reset();
            pause = false;
            loop = false;
            clip.start();
        }
    }
    
    public void loop() {
        if (!failed) {
            clip.stop();
            reset();
            pause = false;
            loop = true;
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
    
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
    
    public void reset() {
        if (!failed) clip.setFramePosition(0);
    }
    
    public void setVolume(float vol) {
        volumeControl.setValue(vol);
        volume = vol;
    }
    
    public boolean isPlaying() {
        if (!failed) return !pause && clip.isActive();
        else return false;
    }
    
    public Clip getClip() {
        return clip;
    }
}
