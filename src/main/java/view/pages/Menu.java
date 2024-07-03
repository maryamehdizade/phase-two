package view.pages;

import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame {

    private JButton exit = new JButton("exit");
    private JButton skillTree = new JButton("skill tree");
    private JButton play = new JButton("play");
    private JButton setting = new JButton("setting");
    private JButton tutorial = new JButton("tutorial");
    public boolean a = false;
    public double aa = 0.1;
    public int bound = 300;

    private final int buttonWidth = 300;
    private final int buttonHieght = 70;
    private int xLoc = 200;
    private final Color color = Color.GRAY;
    Game game;
    boolean ares = false;
    boolean aceso = false;
    boolean proteus = false;
    private int xp;

    private JPanel panel = new JPanel();
    public Menu(){
        setSize(700,700);
        setLocation(300,20);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        requestFocus();

        this.add(panel);
        panel.setLayout(null);
        panel.setBackground(Color.black);

        addButtons();

    }
    private void addButtons(){
        exit.setSize(buttonWidth,buttonHieght);
        exit.setLocation(xLoc,400);
        exit.setBackground(color);

        play.setSize(buttonWidth,buttonHieght);
        play.setLocation(xLoc,100);
        play.setBackground(color);
        play.addActionListener(e -> {
            setVisible(false);
            try {
//                Minimize m = new Minimize(this);
//                if(a) {
                if(true){
                   game = new Game(this);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        skillTree.setSize(buttonWidth,buttonHieght);
        skillTree.setLocation(xLoc,200);
        skillTree.setBackground(color);
        skillTree.addActionListener(e -> {
            new SkillTree(this);
            setVisible(false);
        });

        tutorial.setSize(buttonWidth,buttonHieght);
        tutorial.setLocation(xLoc,300);
        tutorial.setBackground(color);

        setting.setSize(buttonWidth, buttonHieght);
        setting.setLocation(xLoc, 500);
        setting.setBackground(color);
        setting.addActionListener(e -> {
            setVisible(false);
            try {
                new Setting(this);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        panel.add(exit);
        panel.add(setting);
        panel.add(play);
        panel.add(skillTree);
        panel.add(tutorial);


    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }
}
