package model.characterModel.enemy;

import model.characterModel.PlayerModel;
import model.movement.Movable;
import view.pages.GamePanel;

import java.awt.geom.Point2D;
import java.util.Random;
import java.util.UUID;

import static controller.Constant.RECT_SIZE;

public class RectangleModel extends java.awt.Rectangle implements Movable {
    private int hp = 10;
    private PlayerModel playerModel;

    private double speed = 1;
    private GamePanel panel;
    private Random random = new Random();
    private int[] xPoints;
    private int[] yPoints;
    private Point2D loc;
    private double dx;
    private double dy;
    String id;
    private boolean impact;
    private double m;

    public RectangleModel(GamePanel panel) {
        this.panel = panel;
        this.playerModel = panel.playerModel;
        this.id = UUID.randomUUID().toString();
        createRecs();
    }
    public void createRecs(){
        double x;
        double y;
        double r = Math.floor(Math.random()*2);
        if(r == 0) {
            x = random.nextDouble(20,500);
            if(Math.floor(Math.random()*2) == 0){
                y = 20 ;
            }else{
                y =500;
            }
        }else {
            y = random.nextDouble(20, 500);
            if(Math.floor(Math.random()*2) == 0){
                x = 20;
            }else{
                x = 800;
            }

        }

        loc = new Point2D.Double(x,y);

        xPoints = new int[]{(int) loc.getX(), (int) (loc.getX() + RECT_SIZE),(int) (loc.getX() + RECT_SIZE), (int) loc.getX()};
        yPoints = new int[]{(int) loc.getY(), (int) loc.getY(), (int) (loc.getY() + RECT_SIZE), (int) (loc.getY() + RECT_SIZE)};

        m = Math.atan2((playerModel.getLocation().getY() - loc.getY()),(playerModel.getLocation().getX() - loc.getX()));
        dx = (Math.cos(m) * 2) * speed;
        dy = (Math.sin(m) * 2) * speed;

    }

    @Override
    public int move() {

        if(!impact) {
            m = Math.atan2((playerModel.getLocation().getY() - loc.getY()), (playerModel.getLocation().getX() - loc.getX()));
            dx = (Math.cos(m) * 2) * speed;
            dy = (Math.sin(m) * 2) * speed;
        }

        if(impact){
            findPlayer();
        }if(dx == (Math.cos(m) * 2) * speed && dy == (Math.sin(m) * 2) * speed){
            impact = false;
        }


        loc = new Point2D.Double(loc.getX() + dx, loc.getY() + dy);

        xPoints = new int[]{(int) loc.getX(), (int) (loc.getX() + RECT_SIZE),(int) (loc.getX() + RECT_SIZE), (int) loc.getX()};
        yPoints = new int[]{(int) loc.getY(), (int) loc.getY(), (int) (loc.getY() + RECT_SIZE), (int) (loc.getY() + RECT_SIZE)};


        return 0;
    }

    @Override
    public void findPlayer() {
        double m = Math.atan2((playerModel.getLocation().getY() - loc.getY()),(playerModel.getLocation().getX() - loc.getX()));
        dx += ((Math.cos(m) * 2) * speed - dx)/80;
        dy += ((Math.sin(m) * 2) * speed - dy)/80;
    }

    @Override
    public void setImpact(boolean impact) {
        this.impact = impact;
    }

    public Point2D getLoc() {
        return loc;
    }

    public int getHp() {
        return hp;
    }

    public String getId() {
        return id;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int[] getxPoints() {
        return xPoints;
    }

    public int[] getyPoints() {
        return yPoints;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }
    public void setXvelocity(double xvelocity) {
        dx = xvelocity;
    }

    @Override
    public void setYvelocity(double yvelocity) {
        dy = yvelocity;
    }

    @Override
    public double getXvelocity() {
        return dx;
    }

    @Override
    public double getYvelocity() {
        return dy;
    }
    @Override
    public void move(double velocity) {

    }

}
