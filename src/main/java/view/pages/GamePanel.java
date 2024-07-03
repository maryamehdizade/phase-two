package view.pages;

import controller.Update;
import model.characterModel.BulletModel;
import model.characterModel.MovePlayer;
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

public  class GamePanel extends JPanel implements KeyListener, MouseListener {
    public PlayerModel playerModel;
    public PlayerView playerView;
    public MovePlayer movePlayer;
    private ArrayList<BulletView> bullets = new ArrayList<>();
    private ArrayList<RectangleModel> rectangleModels = new ArrayList<>();
    private ArrayList<RectangleView> rectangleView = new ArrayList<>();
    private ArrayList<BulletModel> bulletsModel = new ArrayList<>();
    private ArrayList<TriangleModel> triangleModels = new ArrayList<>();
    private ArrayList<TriangleView> triangleViews = new ArrayList<>();
    private ArrayList<CollectableView> collectableViews = new ArrayList<>();
    private ArrayList<CollectableModel> collectableModels = new ArrayList<>();
    private ArrayList<Movable> movables = new ArrayList<>();
    private Dimension dimension = new Dimension(700,700);
    private Point loc = new Point(100,20);
    protected boolean victory = false;
    public Random random = new Random();
    public boolean wave1 = true;
    public int heal = 0;
    public boolean start = false;
    public boolean wave2 = false;
    public boolean wave3 = false;
    public boolean empower = false;
    public int wave = 1;
    public int bound ;
    public int enemies = 0;
    public int aresCount ;
    public int acesoCount ;
    public int proteusCount ;
    private boolean proteus;
    public Game game;
    Update update;
    int power = 5;
    public int count = 0;

    public GamePanel(Game game){
            Sound.sound().wave();

        this.game = game;
        bound = this.game.menu.bound;

        setBackground(new Color(0, 0, 0));
        setFocusable(true);
        setLayout(null);
        requestFocus();
        addKeyListener(this);
        addMouseListener(this);
        setSize(dimension);


        playerModel = PlayerModel.getPlayer();
        playerView = createPlayerView(playerModel.getId());
        movePlayer = new MovePlayer(playerModel, this);
        movables.add(movePlayer);

        update = new Update(this);


    }

    public void Wave(){
        if(enemies >= 10 && wave1 && start && movables.size() == 1){
            try {
                Sound.sound().wave();
            } catch (Exception e) {
                e.printStackTrace();
            }
            wave++;
            enemies = 0;
            count = 0;

        } else if(enemies >= 15 && wave2 && movables.size() == 1){
            try {
                Sound.sound().wave();
            } catch (Exception e) {
                e.printStackTrace();
            }
            wave++;
            enemies = 0;
            count = 0;

        }else if(wave == 3 && movables.size() == 1 && enemies >= 20){
            victory = true;
        }
    }
    public void xmin(){
        if(dimension.width > MIN_SIZE.width) {
            dimension.width -= 1;
            if(loc.getX() < 200) loc.setLocation(loc.getX() + 0.5,loc.getY() );
        }
        if(playerModel.getLocation().getX() + BALL_SIZE > dimension.getWidth()){
            playerModel.setLocation(
                    new Point2D.Double(dimension.getWidth() - BALL_SIZE ,playerModel.getLocation().getY()));
        }else if(playerModel.getLocation().getX()  < 2){
            playerModel.setLocation(
                    new Point2D.Double(5,playerModel.getLocation().getY()));

        }

    }
    public void ymin(){
        if(dimension.height > MIN_SIZE.height) {
            dimension.height -= 1;
            if(loc.getY() < 200) loc.setLocation(loc.getX(),loc.getY() + 0.5);
        }
        if (playerModel.getLocation().getY() + BALL_SIZE> dimension.getHeight()) {
            playerModel.setLocation(
                    new Point2D.Double(playerModel.getLocation().getX(), dimension.getHeight() - BALL_SIZE - 5));
        }else if(playerModel.getLocation().getY() < 2){
            playerModel.setLocation(
                    new Point2D.Double(playerModel.getLocation().getX(),  5));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        playerView.draw(g);


        for (BulletView b : bullets) {
            b.draw(g);
        }
        for (RectangleView r : rectangleView) {
            r.draw(g);
        }
        for (TriangleView t : triangleViews) {
            t.draw(g);
        }
        for (CollectableView c :collectableViews) {
            c.draw(g);
        }

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
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_A) {
            movePlayer.setlForce(true);
        } else if (keyCode == KeyEvent.VK_D) {
            movePlayer.setrForce(true);
        } else if (keyCode == KeyEvent.VK_W) {
            movePlayer.setuForce(true);
        } else if (keyCode == KeyEvent.VK_S) {
            movePlayer.setdForce(true);
        }
        if(keyCode == KeyEvent.VK_SPACE){
            update.model.stop();
            update.view.stop();
            update.time.stop();
            new Store(this);
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_D) {
            movePlayer.setrForce(false);
            movePlayer.setR0Force(true);
        }
        if (keyCode == KeyEvent.VK_A) {
            movePlayer.setlForce(false);
            movePlayer.setL0Force(true);
        }
        if (keyCode == KeyEvent.VK_S) {
            movePlayer.setdForce(false);
            movePlayer.setD0Force(true);
        }
        if (keyCode == KeyEvent.VK_W) {
            movePlayer.setuForce(false);
            movePlayer.setU0Force(true);
        }
        if(keyCode == KeyEvent.VK_P){
            if(game.menu.proteus){
                if(proteusCount == 0) {
                    if(playerModel.getXp() >= 100) {
                        playerModel.setXp(playerModel.getXp() -100);
                        update.proteus = true;
                        proteusCount ++;
                        //todo:change to int
                        playerView.setLevelUp(true);
                        proteus = true;
                    }
                }
                //todo
            }
        }
        if(keyCode == KeyEvent.VK_C){
            if(game.menu.aceso){
                if(acesoCount == 0) {
                    if(playerModel.getXp() >= 100) {
                        playerModel.setXp(playerModel.getXp() -100);
                        update.aceso = true;
                        heal ++;
                        acesoCount ++;

                    }
                }
            }
        }
        if(keyCode == KeyEvent.VK_R){
            if(game.menu.ares){
                if(aresCount == 0) {
                    if(playerModel.getXp() >= 100) {
                        playerModel.setXp(playerModel.getXp() -100);
                        power += 2;
                        update.ares = true;
                        aresCount++;
                    }
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int targetX = e.getX();
        int targetY = e.getY();
        int n = 0;
        if(empower)n = 2;
        for (int i = -1; i < n; i++) {
            BulletModel model = new BulletModel(playerCenter(playerModel), targetX + i * n * 10,
                    targetY + i * n * 10, this);
            bulletsModel.add(model);
            bullets.add(createBulletView(model));
        }


    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public Dimension getDimension() {
        return dimension;
    }

    public ArrayList<BulletModel> getBulletsModel() {
        return bulletsModel;
    }

    public ArrayList<BulletView> getBullets() {
        return bullets;
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

    public ArrayList<RectangleModel> getRectangleModels() {
        return rectangleModels;
    }

    public ArrayList<RectangleView> getRectangleView() {
        return rectangleView;
    }

    public ArrayList<TriangleModel> getTriangleModels() {
        return triangleModels;
    }

    public ArrayList<TriangleView> getTriangleViews() {
        return triangleViews;
    }

    public ArrayList<CollectableView> getCollectableViews() {
        return collectableViews;
    }

    public ArrayList<CollectableModel> getCollectableModels() {
        return collectableModels;
    }

    public ArrayList<Movable> getMovables() {
        return movables;
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
}
