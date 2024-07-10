package view.pages;

import controller.Update;

import javax.swing.*;
import java.awt.*;

public class Store extends JFrame  {
    private GamePanel panel;
    private JButton empower = new JButton("Empower : 75xp");
    private JButton heal = new JButton("Apollo Heal : 50xp");
    private JButton banish = new JButton("Hephaestus : 100xp");
    private JButton slaughter = new JButton("slaughter : 200xp");
    private JButton slumber = new JButton("slumber : 150xp");
    private JButton dismay = new JButton("dismay: 120xp");
    private Dimension size = new Dimension(220, 100);
    Color color = new Color(100,0,0);
    private JPanel main = new JPanel();
    int x1 = 20;
    int x2 = size.width + x1 + x1;
    int y1 = 15;
    int y2 = size.height + y1 + y1;
    int y3 = size.height + y2 + y1;
    Update update = Update.getUpdate();

    private int font = 22;

    public Store(GamePanel panel) {

        update.model.stop();
        update.view.stop();
        update.time.stop();

        this.panel = panel;

        setSize(500, 500);
        setResizable(false);
        setBackground(Color.black);
        setUndecorated(true);
        setVisible(true);
        setSize(501, 501);
        requestFocus();
        setFocusable(false);

        this.add(main);
        main.setLayout(null);
        main.setBackground(Color.black);

        addButtons();

    }
    private void addButtons(){

        slaughter.setSize(size);
        slaughter.setLocation(x2, y3);
        slaughter.setBackground(color);
        slaughter.setFont(new Font("TimesRoman", Font.PLAIN, font));
        slaughter.addActionListener(e -> {
            if(panel. playerView.getXp() >= 200){
                panel. playerView.setXp(panel. playerView.getXp() - 200);
                update.dataBase.setXp(panel.playerView.getXp());
            }
        });

        slumber.setSize(size);
        slumber.setLocation(x2, y2);
        slumber.setBackground(color);
        slumber.setFont(new Font("TimesRoman", Font.PLAIN, font));
        slumber.addActionListener(e -> {
            if(panel. playerView.getXp() >= 150){
                panel. playerView.setXp(panel. playerView.getXp() - 150);
                update.dataBase.setXp(panel.playerView.getXp());
            }
        });

        dismay.setSize(size);
        dismay.setLocation(x2, y1);
        dismay.setBackground(color);
        dismay.setFont(new Font("TimesRoman", Font.PLAIN, font));
        dismay.addActionListener(e -> {
            if(panel. playerView.getXp() >= 120){
                panel. playerView.setXp(panel. playerView.getXp() - 120);
                update.dataBase.setXp(panel.playerView.getXp());
            }
        });




        banish.setSize(size);
        banish.setLocation(x1, y3);
        banish.setBackground(color);
        banish.setFont(new Font("TimesRoman", Font.PLAIN, 22));
        banish.addActionListener(e -> {
            if(panel. playerView.getXp() >= 100){
                panel. playerView.setXp(panel. playerView.getXp() - 100);
                update.dataBase.setXp(panel.playerView.getXp());
                update.impact(panel.playerView.getLoc(), 200);

            }
            start();
        });

        empower.setSize(size);
        empower.setLocation(x1, y2);
        empower.setBackground(color);
        empower.setFont(new Font("TimesRoman", Font.PLAIN, 22));
        empower.addActionListener(e -> {
            if(panel. playerView.getXp() >= 75){
                panel. playerView.setXp(panel. playerView.getXp() - 75);
                update.dataBase.setXp(panel.playerView.getXp());
                update.dataBase.getGamePanelModel().empower = true;
            }
            start();
        });


        heal.setSize(size);
        heal.setLocation(x1, y1);
        heal.setBackground(color);
        heal.setFont(new Font("TimesRoman", Font.PLAIN, 22));
        heal.addActionListener(e -> {
            if(panel.playerView.getXp() >= 50){
                panel. playerView.setXp(panel. playerView.getXp() - 50);
                update.dataBase.setXp(panel.playerView.getXp());
                panel. playerView.setHp(panel. playerView.getHp() + 10);
                update.dataBase.setHp(panel.playerView.getHp());
            }
            start();
        });

        JButton play = new JButton("back");
        play.setSize(size);
        play.setBackground(color);
        play.setLocation(140, y3 + size.height + y1);
        play.setFont(new Font("TimesRoman", Font.PLAIN, font));
        play.addActionListener(e -> {
            start();
        });


        main.add(dismay);
        main.add(slaughter);
        main.add(slumber);
        main.add(heal);
        main.add(empower);
        main.add(banish);
        main.add(play);
        main.setBackground(Color.black);

    }
    private void start(){
        dispose();
        update.view.start();
        update.model.start();
        update.time.start();
    }

}
