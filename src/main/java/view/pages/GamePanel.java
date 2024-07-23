package view.pages;

import controller.DataBase;
import controller.listner.MyListner;
import sound.Sound;
import view.charactersView.PlayerView;
import view.drawable.Drawable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public  class GamePanel extends JPanel {
    public PlayerView playerView;
    private ArrayList<Drawable> drawables = new ArrayList<>();

    private int wave;
    private int second;

    private String id;
    private Menu menu;
    private String ability;
    private String activeAbility = "";
    private String activeAbility2 = "";

    public GamePanel(String id){

        this.id = id;
        Sound.sound().wave();

        setBackground(new Color(0, 0, 0));
        setFocusable(true);
        setLayout(null);
        requestFocus();

        menu = Menu.getMenu();
        for (SkillTree.names s :
                menu.skills.keySet()) {
            if(menu.skills.get(s)){
                ability = String.valueOf(s);
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
        for (SkillTree.names n : DataBase.getDataBase().handler.skills.keySet()) {
            if(DataBase.getDataBase().handler.skills.get(n)){
                if(n != SkillTree.names.dolus) {
                    if (Objects.equals(activeAbility, "")) activeAbility = String.valueOf(n);
                    else activeAbility2 = String.valueOf(n);
                }
            }
        }
        g.drawString("xp:" + playerView.getXp() + "          " + "hp:" + playerView.getHp()
                + "             " + second + "             wave:" + wave + "        skill tree:" + ability +"    active skill:"
                        + activeAbility + " " + activeAbility2, 0, 20);
        activeAbility = "";
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
