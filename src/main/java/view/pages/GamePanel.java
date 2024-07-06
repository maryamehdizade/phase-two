package view.pages;

import controller.SkillTreeHandler;
import controller.Update;
import controller.Update2;
import controller.listner.MyListner;
import model.characterModel.BulletModel;
import model.characterModel.PlayerModel;
import model.characterModel.enemy.CollectableModel;
import model.characterModel.enemy.RectangleModel;
import model.characterModel.enemy.TriangleModel;
import model.movement.Movable;
import sound.Sound;
import view.charactersView.BulletView;
import view.charactersView.PlayerView;
import view.charactersView.enemy.CollectableView;
import view.charactersView.enemy.RectangleView;
import view.charactersView.enemy.TriangleView;
import view.drawable.Drawable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

import static controller.Constant.BALL_SIZE;
import static controller.Constant.MIN_SIZE;
import static controller.Controller.createBulletView;
import static controller.Controller.createPlayerView;
import static controller.Util.playerCenter;

public  class GamePanel extends JPanel {
    public SkillTreeHandler handler;
    public PlayerView playerView;
    private ArrayList<Drawable> drawables = new ArrayList<>();
    private Dimension dimension = new Dimension(700,700);
    private Point loc = new Point(100,20);
    public boolean victory = false;
    public Random random = new Random();
    public boolean wave1 = true;
    public int heal = 0;
    public boolean start = false;
    public boolean wave2 = false;
    public boolean wave3 = false;
    public boolean empower = false;
    public int wave = 1;
    public int phase;
    public int bound ;
    public int enemies = 0;
    public int aresCount ;
    public int acesoCount ;
    public int proteusCount ;
    private boolean proteus;
    public Game game;
    public Update update;
    int power = 5;
    public int count = 0;

    public GamePanel(Game game, int phase) {
        this.phase = phase;
        Sound.sound().wave();

        this.game = game;
        bound = this.game.menu.bound;

        setBackground(new Color(0, 0, 0));
        setFocusable(true);
        setLayout(null);
        requestFocus();
        setSize(dimension);


        handler = new SkillTreeHandler(this);
        update = new Update(this);
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
        if(game.menu.aceso)ability = "aceso";
        if(game.menu.ares)ability = "ares";
        if(game.menu.proteus)ability = "proteus";

        g.drawString("xp:" + playerView.getXp() + "          " + "hp:" + playerView.getHp()
                + "             " + (int)update.getSecond()+ "             wave:" + wave + "        skill tree:" + ability
                , game.getLocation().x - 273, game.getBounds().y + 20);


        repaint();
        // TODO
    }


    public Dimension getDimension() {
        return dimension;
    }


    public Point getLoc() {
        return loc;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public void setLoc(Point loc) {
        this.loc = loc;
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
