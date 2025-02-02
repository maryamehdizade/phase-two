package view.pages;



import controller.Update;
import sound.Sound;

import javax.swing.*;
import java.awt.*;

import static controller.constants.Constant.FRAME_DIMENSION;


public  class Game extends JFrame {
    private static Game game;

    public static Game getGame() {
        if(game == null){
            System.out.println("creating game");
            game = new Game();
        }
        return game;
    }
    public static void remove(){
        game = null;
    }

    JPanel panel ;
    int y = 2;
    private Sound sound;
    private int phase = 1 ;


    private Game() {

        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);
        setFocusable(false);
        setResizable(false);
        setSize(FRAME_DIMENSION);
        setLocationRelativeTo(null);
        setVisible(true);


        panel = Update.getUpdate().panel;
        add(panel);

        sound = Sound.sound();
        sound.start();

    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public int getPhase() {
        return phase;
    }
}
