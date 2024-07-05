package model.characterModel.enemy;

import model.characterModel.PlayerModel;
import model.movement.Movable;
import view.pages.GamePanel;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.Random;
import java.util.UUID;

import static controller.Constant.OMENOCT_SIZE;
import static controller.Util.setEntityLoc;

public class Omenoctmodel extends Enemy implements Movable {
    private GamePanel panel;
    private double speed = 1;
    private int hp = 20;
    private Random random = new Random();
    private int[] xPoints;
    private int[] yPoints;
    private Point2D loc;
    private String id;
    private double m;
    private boolean impact;
    private JFrame frame;
    private Point2D destination;
    private double x, x2, x3, x4, x5, x6, x7, x8,y, y2,y3,y4,y5,y6,y7,y8;
    public Omenoctmodel(GamePanel panel){
        this.panel = panel;
        this.playerModel = panel.playerModel;
        frame = playerModel.getFrame();
        this.id = UUID.randomUUID().toString();
        createOmenoct();
    }
    private void createOmenoct(){
        Point2D startLoc = setEntityLoc();
        x = startLoc.getX();
        y = startLoc.getY();

        xPoints = new int[]{(int) (x + OMENOCT_SIZE/3), (int) (x + OMENOCT_SIZE * 2/3), (int) (x + OMENOCT_SIZE),
                (int) (x + OMENOCT_SIZE), (int) (x + OMENOCT_SIZE * 2 /3), (int) (x + OMENOCT_SIZE/3), (int) x, (int) x};
        yPoints = new int[]{(int) y, (int) y, (int) (y + OMENOCT_SIZE/3), (int) (y + OMENOCT_SIZE *2/3),
                (int) (y + OMENOCT_SIZE), (int) (y + OMENOCT_SIZE), (int) (y + OMENOCT_SIZE*2/3), (int) (y + OMENOCT_SIZE/3)};

        findNearestWall();

//        double m = Math.atan2((playerModel.getLocation().getY() - loc.getY()),(playerModel.getLocation().getX() - loc.getX()));
//        xvelocity += ((Math.cos(m) * 2) * speed - xvelocity)/80;
//        yvelocity += ((Math.sin(m) * 2) * speed - yvelocity)/80;

    }

    @Override
    public int move() {

        xPoints = new int[]{(int) (x + OMENOCT_SIZE/3), (int) (x + OMENOCT_SIZE * 2/3), (int) (x + OMENOCT_SIZE),
                (int) (x + OMENOCT_SIZE), (int) (x + OMENOCT_SIZE * 2 /3), (int) (x + OMENOCT_SIZE/3), (int) x, (int) x};
        yPoints = new int[]{(int) y, (int) y, (int) (y + OMENOCT_SIZE/3), (int) (y + OMENOCT_SIZE *2/3),
                (int) (y + OMENOCT_SIZE), (int) (y + OMENOCT_SIZE), (int) (y + OMENOCT_SIZE*2/3), (int) (y + OMENOCT_SIZE/3)};

        if(!impact) {
            m = Math.atan2((destination.getY() - loc.getY()), (destination.getX() - loc.getX()));
            xvelocity = (Math.cos(m) * 2) * speed;
            yvelocity = (Math.sin(m) * 2) * speed;
        }
        if(impact){
            findPlayer(loc);
        }if( xvelocity == (Math.cos(m) * 2) * speed &&  yvelocity == (Math.sin(m) * 2) * speed){
            impact = false;
        }
        return 0;
    }
    @Override
    public void findPlayer(Point2D loc) {
        m = Math.atan2((destination.getY() - loc.getY()),(destination.getX() - loc.getX()));
        xvelocity += ((Math.cos(m) * 2) * speed - xvelocity)/80;
        yvelocity += ((Math.sin(m) * 2) * speed - yvelocity)/80;
    }

    private void findNearestWall(){
        double xDis = - panel.getX() + playerModel.getLocation().getX();
        double xDis2 = panel.getWidth() - playerModel.getLocation().getX();
        double yDis = -panel.getY() + playerModel.getLocation().getY();
        double yDis2 = panel.getHeight() - playerModel.getLocation().getY();
        double y = Math.min(yDis2, yDis);
        double x = Math.min(xDis, xDis2);
        if(x > y)destination = new Point2D.Double(0, y);
        if(y > x)destination = new Point2D.Double(x, 0);

    }

    @Override
    public void move(double velocity) {

    }

    @Override
    public double getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public double getXvelocity() {
        return xvelocity;
    }

    @Override
    public void setXvelocity(double xvelocity) {
        this.xvelocity = xvelocity;
    }

    @Override
    public double getYvelocity() {
        return yvelocity;
    }

    @Override
    public void setYvelocity(double yvelocity) {
        this.yvelocity = yvelocity;
    }

    @Override
    public Point2D getLoc() {
        return loc;
    }



    @Override
    public void setImpact(boolean impact) {
        this.impact = impact;
    }

    @Override
    public int[] getxPoints() {
        return xPoints;
    }


    @Override
    public int[] getyPoints() {
        return yPoints;
    }



    public String getId() {
        return id;
    }

    @Override
    public int getHp() {
        return hp;
    }

    @Override
    public void setHp(int hp) {
        this.hp = hp;
    }
}
