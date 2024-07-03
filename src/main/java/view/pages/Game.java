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


    public Game(Menu menu) throws Exception {
        this.menu = menu;

        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);
        setFocusable(false);
        setResizable(false);
        setSize(FRAME_DIMENSION);
        setLocationRelativeTo(null);
        setVisible(true);


        this.gamePanel = new GamePanel(this);
        panel = gamePanel;
        add(panel);

        sound = Sound.sound();
        sound.start();

    }

    public Menu getMenu() {
        return menu;
    }
}
