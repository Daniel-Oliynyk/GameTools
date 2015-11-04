package gametools;

import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    public static final int LOOP_CONTINUOUSLY = Clip.LOOP_CONTINUOUSLY;
    private Clip clip;
    
    public Sound(String path) {
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(Tools.root.getResourceAsStream(path)));
        }
        catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
            System.err.println("There were errors loading the sound '" + path + "':");
            System.err.println(ex.toString());
        }
    }
    
    public void play() {
        clip.start();
    }
    
    public void loop(int loop) {
        clip.loop(loop);
    }
    
}
