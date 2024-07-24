package sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public final class Sound {
    private static Sound sound;

    public static Sound sound(){
        if(sound == null)sound = new Sound();
        return sound;
    }

    private Clip back;

    public Sound() {
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(
                    "C:\\Users\\EPSILON\\IdeaProjects\\sound\\Background.wav").getAbsoluteFile());

            back = AudioSystem.getClip();
            back.open(audioInputStream);
            back.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void start(){
        back.start();
    }
    public void stop(){
        back.stop();
    }
    public void wave() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(
                     "C:\\Users\\EPSILON\\IdeaProjects\\sound\\Wave.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();

            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void Victory() {
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(
                     "C:\\Users\\EPSILON\\IdeaProjects\\sound\\Victory.wav").getAbsoluteFile());

            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void Losing(){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(
                     "C:\\Users\\EPSILON\\IdeaProjects\\sound\\Losing.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void entrance() {
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(
                     "C:\\Users\\EPSILON\\IdeaProjects\\sound\\swing.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void death() {
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(
                     "C:\\Users\\EPSILON\\IdeaProjects\\sound\\interface.wav").getAbsoluteFile());

            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void injured() {
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(
                     "C:\\Users\\EPSILON\\IdeaProjects\\sound\\Injured.wav").getAbsoluteFile());

            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Clip getBack() {
        return back;
    }
}
