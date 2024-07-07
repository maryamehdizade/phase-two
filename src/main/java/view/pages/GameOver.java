package view.pages;

import controller.DataBase;
import controller.Update;

import javax.swing.*;
import java.awt.*;

public class GameOver extends JFrame {
    Update update;
    public GameOver(Update update){
        this.update = update;

        setSize(300, 200);
        setLocation(500,100);
        setResizable(false);
        setBackground(Color.black);
        setLayout(null);
        setUndecorated(true);
        setVisible(true);
        setSize(301,201);
        requestFocus();
        setFocusable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //addbuttons
        add();
    }
    private void add(){
        Menu.getMenu().setXp(Menu.getMenu().getXp() + update.panel.playerView.getXp());

        JLabel xp = new JLabel(String.valueOf(update.panel.playerView.getXp()));
        xp.setSize(100,70);
        xp.setLocation(150,70);
        xp.setBackground(Color.black);

        JButton button = new JButton("menu");
        button.setSize(200,50);
        button.setBackground(Color.gray);
        button.setLocation(50,140);
        button.addActionListener(e -> {
            startOver();
        });

        JLabel vic = new JLabel("victory!");
        vic.setLocation(100,10);
        vic.setSize(100,50);
        vic.setBackground(Color.black);

        if(update.dataBase.getGamePanelModel().victory){
            add(vic);
        }
        add(button);
        add(xp);
    }
    private void startOver(){
        dispose();
        Game.getGame().dispose();
        Game.getGame().remove();
        Update.getUpdate().remove();
        DataBase.getDataBase().remove();
        Menu.getMenu().setVisible(true);
    }
}

