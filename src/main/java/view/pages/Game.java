package view.pages;



import sound.Sound;

import javax.swing.*;
import java.awt.*;

import static controller.Constant.FRAME_DIMENSION;


public  class Game extends JFrame {
    JPanel panel ;
    int y = 2;
    GamePanel gamePanel ;
    private Sound sound;
    protected Menu menu;


    public Game(Menu menu){
        this.menu = menu;

        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);
        setFocusable(false);
        setResizable(false);
        setSize(FRAME_DIMENSION);
        setLocationRelativeTo(null);
        setVisible(true);


        try {
            this.gamePanel = new GamePanel(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        panel = gamePanel;
        add(panel);

        try {
            sound = Sound.sound();
        } catch (Exception e) {
            e.printStackTrace();
        }
        sound.start();

    }

    public Menu getMenu() {
        return menu;
    }
}
