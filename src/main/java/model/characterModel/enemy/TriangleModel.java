package model.characterModel.enemy;

import model.characterModel.PlayerModel;
import model.movement.Movable;
import view.pages.GamePanel;

import java.awt.geom.Point2D;
import java.util.Random;
import java.util.UUID;

import static controller.Constant.TRI_SIZE;

public class TriangleModel implements Movable {

    private double x1, y1, x2, y2, x3, y3;
    private int[] xPoints;
    private int[] yPoints;
    private Point2D loc;
    private double speed = 1;
    private double dx;
    private double dy;
    private int hp = 15;
    private PlayerModel playerModel;
    private String id;
    private Random random = new Random();
    private GamePanel panel;
    private  double angle;
    private boolean impact;

    public TriangleModel(GamePanel panel) {
        this.panel = panel;
        this.playerModel = panel.playerModel;
        this.id = UUID.randomUUID().toString();

        createTriangle();
    }
    public void findPlayer(){
        double m = Math.atan2((playerModel.getLocation().getY() - loc.getY()),(playerModel.getLocation().getX() - loc.getX()));
        dx += ((Math.cos(m) * 2) * speed - dx)/80;
        dy += ((Math.sin(m) * 2) * speed - dy)/80;
    }
    @Override
    public int move() {
        if(!impact) {
            angle = Math.atan2((playerModel.getLocation().getY() - y1), (playerModel.getLocation().getX() - x1));
            dx = (Math.cos(angle) * 2) * speed;
            dy = (Math.sin(angle) * 2) * speed;
        }
        if(impact){
            findPlayer();
        }if(dx == (Math.cos(angle) * 2) * speed && dy == (Math.sin(angle) * 2) * speed){
            impact = false;
        }


        x1 += dx;
        y1 += dy;
        x2 += dx;
        y2 += dy;
        x3 += dx;
        y3 += dy;

        xPoints = new int[]{(int) x1, (int) x3, (int) x2};
        yPoints = new int[]{(int) y1, (int) y3, (int) y2};

        loc = new Point2D.Double((x1 + x2 + x3)/3, (y1 + y2 + y3)/3);

        return 0;
    }
    void createTriangle(){
        double x;
        double y;
        double r = Math.floor(Math.random()*2);
        if(r == 0) {
            x = random.nextDouble(20, 700);
            if(Math.floor(Math.random()*2) == 0){
                y = 30;
            }else{
                y = 500;
            }
        }else {
            y = random.nextDouble(0, 500);
            if(Math.floor(Math.random()*2) == 0){
                x =20;
            }else{
                x = 800;
            }

        }
        this.x1 = x;
        this.y1 = y;
        this.x2 = x + TRI_SIZE;
        this.y2 = y;
        this.x3 = x + TRI_SIZE/2.0;
        this.y3 = y + TRI_SIZE;

        xPoints = new int[]{(int) x1, (int) x3, (int) x2};
        yPoints = new int[]{(int) y1, (int) y3, (int) y2};

         angle = Math.atan2(playerModel.getLocation().getY() - y1, playerModel.getLocation().getX() - x1);
         dx = Math.cos(angle) * speed;
         dy = Math.sin(angle) * speed;

        loc = new Point2D.Double((x1 + x2 + x3)/3, (y1 + y2 + y3)/3);
    }

    @Override
    public void move(double velocity) {

    }

    public double getX1() {
        return x1;
    }

    public double getY1() {
        return y1;
    }

    public double getX2() {
        return x2;
    }

    public double getY2() {
        return y2;
    }

    public double getX3() {
        return x3;
    }

    public double getY3() {
        return y3;
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

    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
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
    public double getSpeed() {
        return speed;
    }

    @Override
    public Point2D getLoc() {
        return loc;
    }

    public int[] getxPoints() {
        return xPoints;
    }

    public int[] getyPoints() {
        return yPoints;
    }

    public void setImpact(boolean impact) {
        this.impact = impact;
    }
}
