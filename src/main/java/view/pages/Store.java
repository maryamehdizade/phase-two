package view.pages;

import javax.swing.*;
import java.awt.*;

import static controller.Util.playerCenter;

public class Store extends JFrame  {
    private GamePanel panel;
    private JButton empower = new JButton("Empower : 75xp");
    private JButton heal = new JButton("Apollo Heal : 50xp");
    private JButton banish = new JButton("Hephaestus : 100xp");
    private Dimension size = new Dimension(280,30);
    Color color = new Color(100,0,0);
    private JPanel main = new JPanel();

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

        banish.setSize(size);
        banish.setLocation(10, 130);
        banish.setBackground(color);
        banish.setFont(new Font("TimesRoman", Font.PLAIN, 22));
        banish.addActionListener(e -> {
            if(panel.playerModel.getXp() >= 100){
                panel.playerModel.setXp(panel.playerModel.getXp() - 100);
                panel.update.impact(playerCenter(panel.playerModel), 200);
            }
            start();
        });

        empower.setSize(size);
        empower.setLocation(10, 80);
        empower.setBackground(color);
        empower.setFont(new Font("TimesRoman", Font.PLAIN, 22));
        empower.addActionListener(e -> {
            if(panel.playerModel.getXp() >= 75){
                panel.playerModel.setXp(panel.playerModel.getXp() - 75);
                panel.empower = true;
            }
            start();
        });


        heal.setSize(size);
        heal.setLocation(10, 30);
        heal.setBackground(color);
        heal.setFont(new Font("TimesRoman", Font.PLAIN, 22));
        heal.addActionListener(e -> {
            if(panel.playerModel.getXp() >= 50){
                panel.playerModel.setXp(panel.playerModel.getXp() - 50);
                panel.playerModel.setHp(panel.playerModel.getHp() + 10);
            }
            start();
        });

        JButton play = new JButton("back");
        play.setSize(size);
        play.setBackground(color);
        play.setLocation(10,170);
        play.addActionListener(e -> {
            start();
        });


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

    public static void main(String[] args) throws Exception {
        new Store(new GamePanel(new Game(new Menu())));
    }

}
