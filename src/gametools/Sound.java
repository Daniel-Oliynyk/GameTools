package gametools;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    public static final int LOOP_CONTINUOUSLY = Clip.LOOP_CONTINUOUSLY;
    private Clip clip;
    private boolean failed;
    
    public Sound(String path) {
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(Tools.root.getResourceAsStream(path)));
        }
        catch (Exception ex) {
            System.err.println("There were errors loading the sound '" + path + "':");
            System.err.println(ex.toString());
            failed = true;
        }
    }
    
    public void play(int loop) {
        if (!failed && loop != 0) {
            reset();
            if (loop > 0) loop--;
            clip.loop(loop);
        }
    }
    
    public void pause(boolean pause) {
        if (!failed) {
            if (pause) clip.stop();
            else clip.start();
        }
    }
    
    public void reset() {
        if (!failed) clip.setFramePosition(0);
    }
    
    public boolean isPlaying() {
        if (!failed) return clip.isActive();
        else return false;
    }

    public Clip getClip() {
        return clip;
    }
}
