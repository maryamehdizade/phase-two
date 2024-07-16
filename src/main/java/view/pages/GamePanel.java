package view.pages;

import controller.listner.MyListner;
import sound.Sound;
import view.charactersView.PlayerView;
import view.drawable.Drawable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public  class GamePanel extends JPanel {
    public PlayerView playerView;
    private ArrayList<Drawable> drawables = new ArrayList<>();

    private boolean proteus;
    private boolean aceso;
    private boolean ares;
    private int wave;
    private int second;

    String id;
    Menu menu;
    String ability;

    public GamePanel(String id){

        this.id = id;
        Sound.sound().wave();

        setBackground(new Color(0, 0, 0));
        setFocusable(true);
        setLayout(null);
        requestFocus();

        menu = Menu.getMenu();
        for (String s :
                menu.skills.keySet()) {
            if(menu.skills.get(s)){
                ability = s;
                break;
            }
        }

        MyListner listener = new MyListner(this);

        addKeyListener(listener);
        addMouseListener(listener);

    }


    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.setColor(new Color(133, 186, 83));

        g.drawString("xp:" + playerView.getXp() + "          " + "hp:" + playerView.getHp()
                + "             " + second + "             wave:" + wave + "        skill tree:" + ability
                , 0, 20);

        for(Drawable d : drawables)d.draw(g);

        repaint();
    }







    public ArrayList<Drawable> getDrawables() {
        return drawables;
    }

    public void setWave(int wave) {
        this.wave = wave;
    }

    public void setSecond(int second) {
        this.second = second;
    }
}
