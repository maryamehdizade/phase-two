package view.pages;

import javax.swing.*;
import java.awt.*;

import static controller.Util.playerCenter;

public class Store extends JFrame  {
    private GamePanel panel;
    private JButton empower = new JButton("Empower : 75xp");
    private JButton heal = new JButton("Apollo Heal : 50xp");
    private JButton banish = new JButton("Hephaestus : 100xp");
    private JButton slaughter = new JButton("slaughter : 200xp");
    private JButton slumber = new JButton("slumber : 150xp");
    private JButton dismay = new JButton("dismay: 120xp");
    private Dimension size = new Dimension(280,30);
    Color color = new Color(100,0,0);
    private JPanel main = new JPanel();
    int x1 = 20;
    int x2 = size.width + x1 + x1;
    int y1 = 15;
    int y2 = size.height + y1 + y1;
    int y3 = size.height + y2 + y1;

    private int font = 22;

    public Store(GamePanel panel) {
        this.panel = panel;

        setSize(500, 500);
        setLocation((int) ((panel.getDimension().getWidth())), (int) (panel.getDimension().getHeight() / 2));
        setResizable(false);
        setBackground(Color.black);
        setUndecorated(true);
        setVisible(true);
        setSize(301,201);
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
                panel.update.dataBase.setXp(panel.playerView.getXp());
            }
        });

        slumber.setSize(size);
        slumber.setLocation(x2, y2);
        slumber.setBackground(color);
        slumber.setFont(new Font("TimesRoman", Font.PLAIN, font));
        slumber.addActionListener(e -> {
            if(panel. playerView.getXp() >= 150){
                panel. playerView.setXp(panel. playerView.getXp() - 150);
                panel.update.dataBase.setXp(panel.playerView.getXp());
            }
        });

        dismay.setSize(size);
        dismay.setLocation(x2, y1);
        dismay.setBackground(color);
        dismay.setFont(new Font("TimesRoman", Font.PLAIN, font));
        dismay.addActionListener(e -> {
            if(panel. playerView.getXp() >= 120){
                panel. playerView.setXp(panel. playerView.getXp() - 120);
                panel.update.dataBase.setXp(panel.playerView.getXp());
            }
        });




        banish.setSize(size);
        banish.setLocation(10, 130);
        banish.setBackground(color);
        banish.setFont(new Font("TimesRoman", Font.PLAIN, 22));
        banish.addActionListener(e -> {
            if(panel. playerView.getXp() >= 100){
                panel. playerView.setXp(panel. playerView.getXp() - 100);
                panel.update.dataBase.setXp(panel.playerView.getXp());
                panel.update.impact(panel.playerView.getLoc(), 200);

            }
            start();
        });

        empower.setSize(size);
        empower.setLocation(10, 80);
        empower.setBackground(color);
        empower.setFont(new Font("TimesRoman", Font.PLAIN, 22));
        empower.addActionListener(e -> {
            if(panel. playerView.getXp() >= 75){
                panel. playerView.setXp(panel. playerView.getXp() - 75);
                panel.update.dataBase.setXp(panel.playerView.getXp());
                panel.empower = true;
            }
            start();
        });


        heal.setSize(size);
        heal.setLocation(10, 30);
        heal.setBackground(color);
        heal.setFont(new Font("TimesRoman", Font.PLAIN, 22));
        heal.addActionListener(e -> {
            if(panel.playerView.getXp() >= 50){
                panel. playerView.setXp(panel. playerView.getXp() - 50);
                panel.update.dataBase.setXp(panel.playerView.getXp());
                panel. playerView.setHp(panel. playerView.getHp() + 10);
                panel.update.dataBase.setHp(panel.playerView.getHp());
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
        panel.update.view.start();
        panel.update.model.start();
    }

}
