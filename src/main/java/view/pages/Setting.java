package view.pages;

import sound.Sound;

import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.*;

public class Setting extends JFrame {

    private JSlider sound;
    private JSlider sensitivity;
    private JSlider level;


    public Setting(Menu m){

        setSize(700,700);
        setLocation(300,20);
        setLayout(null);
        setBackground(Color.black);
        setLocationRelativeTo(null);
        setVisible(true);

        sound = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        sound.setLocation(300,100);
        sound.setSize(200,100);
        sound.addChangeListener(e -> {
            try {
                adjustVolume();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        sensitivity = new JSlider(JSlider.HORIZONTAL, 1, 3, 1);
        sensitivity.setLocation(300,250);
        sensitivity.setSize(200,100);
        sensitivity.addChangeListener(e -> {
            m.aa = sensitivity.getValue()/10.0;
        });


        level = new JSlider(JSlider.HORIZONTAL, 1, 2, 1);
        level.setLocation(300,400);
        level.setSize(200,100);
        level.addChangeListener(e -> {
            if(level.getValue() ==1)m.bound = 300;
            else m.bound = 250;
        });

        JLabel sound1 = new JLabel("sound");
        sound1.setLocation(100,100);
        sound1.setSize(200,100);

        JLabel sense = new JLabel("sensitivity");
        sense.setLocation(100,250);
        sense.setSize(200,100);

        JLabel level1 = new JLabel("level");
        level1.setLocation(100,400);
        level1.setSize(200,100);

        JButton menu = new JButton("menu");
        menu.setSize(400,50);
        menu.setLocation(100,500);
        menu.addActionListener(e -> {
            try {
                m.setVisible(true);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            dispose();
        });

        add(menu);
        add(sense);
        add(sound1);
        add(sensitivity);
        add(sound);
        add(level1);
        add(level);

    }

    private void adjustVolume() throws Exception {

        int volume = sound.getValue();
        FloatControl gainControl = (FloatControl) Sound.sound().getBack().getControl(FloatControl.Type.MASTER_GAIN);
        float gain = (float) volume / sound.getMaximum();
        float dB = gainControl.getMinimum() + gain * (gainControl.getMaximum() - gainControl.getMinimum());
        gainControl.setValue(dB);

    }
}
