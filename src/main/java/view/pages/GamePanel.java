package view.pages;

import controller.SkillTreeHandler;
import controller.Update;
import controller.listner.MyListner;
import sound.Sound;
import view.charactersView.PlayerView;
import view.drawable.Drawable;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

public  class GamePanel extends JPanel {
    public SkillTreeHandler handler;
    public PlayerView playerView;
    private ArrayList<Drawable> drawables = new ArrayList<>();
    public boolean victory = false;
    public Random random = new Random();
    public boolean wave1 = true;
    public int heal = 0;
    public boolean start = false;
    public boolean wave2 = false;
    public boolean wave3 = false;
    public boolean empower = false;
    public int wave = 1;
    public int phase, second;
    public int bound ;
    public int enemies = 0;
    public int aresCount ;
    public int acesoCount ;
    public int proteusCount ;
    private boolean proteus;
    private boolean aceso;
    private boolean ares;
//    public Game game;


    int power = 5;
    public int count = 0;
    String id;

    public GamePanel(String id){

        this.id = id;
        Sound.sound().wave();
//        this.game = Game.getGame();
        bound = Menu.getMenu().bound;

        setBackground(new Color(0, 0, 0));
        setFocusable(true);
        setLayout(null);
        requestFocus();


        handler = new SkillTreeHandler(this);
        MyListner listner = new MyListner(this);

        addKeyListener(listner);
        addMouseListener(listner);

    }


    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        for(Drawable d : drawables)d.draw(g);


        g.setColor(new Color(133, 186, 83));
        String ability = "";
        if(aceso)ability = "aceso";
        if(ares)ability = "ares";
        if(proteus)ability = "proteus";

        g.drawString("xp:" + playerView.getXp() + "          " + "hp:" + playerView.getHp()
                + "             " + second + "             wave:" + wave + "        skill tree:" + ability
                , 0, 20);

        repaint();
        // TODO
    }



    public boolean isVictory() {
        return victory;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public boolean isProteus() {
        return proteus;
    }

    public void setProteus(boolean proteus) {
        this.proteus = proteus;
    }

    public ArrayList<Drawable> getDrawables() {
        return drawables;
    }
}
