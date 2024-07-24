package view.pages;

import controller.DataBase;
import controller.Update;
import javax.swing.*;
import java.awt.*;

public class GameOver extends JFrame {
    Update update;
    private int h = 40;
    private int w = 300;
    private int xLoc = 50;
    private int yLoc = 10;
    public GameOver(Update update){
        this.update = update;

        setSize(400, 300);
        setLocation(500,100);
        setResizable(false);
        setBackground(Color.black);
        setLayout(null);
        setUndecorated(true);
        setVisible(true);
        setSize(401,301);
        requestFocus();
        setFocusable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //addbuttons
        add();
    }
    private void add(){
        Menu.getMenu().setXp(Menu.getMenu().getXp() + update.panel.playerView.getXp());

        JLabel xp = new JLabel("xp:"+ update.panel.playerView.getXp());
        xp.setSize(w,h);
        xp.setLocation(xLoc,yLoc + h);
        xp.setBackground(Color.black);

        JLabel bullets = new JLabel("bullets:" + update.dataBase.shoots);
        bullets.setSize(w,h);
        bullets.setLocation(xLoc,yLoc + 4*h);
        bullets.setBackground(Color.black);

        JLabel Sbullets = new JLabel("successful shoots:" + update.dataBase.successShoots );
        Sbullets.setSize(w,h);
        Sbullets.setLocation(xLoc,yLoc + 3*h);
        Sbullets.setBackground(Color.black);

        JLabel kills = new JLabel("kills:" + update.dataBase.killedEnemies);
        kills.setSize(w,h);
        kills.setLocation(xLoc,yLoc + 2*h);
        kills.setBackground(Color.black);


        JButton button = new JButton("menu");
        button.setSize(w,h);
        button.setBackground(Color.gray);
        button.setLocation(xLoc,yLoc + 5*h);
        button.addActionListener(e -> {
            startOver();
        });

        JLabel vic = new JLabel("victory!");
        vic.setLocation(xLoc,yLoc);
        vic.setSize(w,h);
        vic.setBackground(Color.black);

        if(update.dataBase.getGamePanelModel().victory){
            add(vic);
        }
        add(button);
        add(xp);
        add(bullets);
        add(kills);
        add(Sbullets);
    }
    private void startOver(){
        Game.getGame().dispose();
        Game.remove();
        Update.setUpdate(null);
        DataBase.remove(null);
        Menu.getMenu().setVisible(true);
        dispose();
    }

}

