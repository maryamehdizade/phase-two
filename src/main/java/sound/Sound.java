package sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.nio.file.Paths;

public final class Sound {
    private static Sound sound;

    public static Sound sound() throws Exception {
        if(sound == null)sound = new Sound();
        return sound;
    }

    private Clip back;

    public Sound() throws Exception{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(
                    Paths.get("").toAbsolutePath() + "/src/main/java/sound/Background.wav").getAbsoluteFile());
            back = AudioSystem.getClip();
            back.open(audioInputStream);
            back.loop(Clip.LOOP_CONTINUOUSLY);


    }
    public void start(){
        back.start();
    }
    public void stop(){
        back.stop();
    }
    public void wave() throws Exception {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(
                Paths.get("").toAbsolutePath() + "/src/main/java/sound/Wave.wav").getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    }
    public void Victory() throws Exception{
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(
                Paths.get("").toAbsolutePath() + "/src/main/java/sound/Victory.wav").getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    }
    public void Losing() throws Exception{
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(
                Paths.get("").toAbsolutePath() + "/src/main/java/sound/Losing.wav").getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    }
    public void entrance() throws Exception{
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(
                Paths.get("").toAbsolutePath() + "/src/main/java/sound/swing.wav").getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    }
    public void death() throws Exception{
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(
                Paths.get("").toAbsolutePath() + "/src/main/java/sound/interface.wav").getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    }
    public void injured() throws Exception{
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(
                Paths.get("").toAbsolutePath() + "/src/main/java/sound/Injured.wav").getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    }

    public Clip getBack() {
        return back;
    }
}
