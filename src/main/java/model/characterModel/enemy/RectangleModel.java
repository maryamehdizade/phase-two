package model.characterModel.enemy;
import model.movement.Movable;
import view.pages.GamePanel;

import java.awt.geom.Point2D;
import java.util.Random;
import java.util.UUID;

import static controller.Constant.RECT_SIZE;
import static controller.Util.setEntityLoc;

public class RectangleModel extends Enemy  implements Movable {
    private int hp = 10;
    private GamePanel panel;
    private Random random = new Random();
    private int[] xPoints;
    private int[] yPoints;
    private Point2D loc;
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

        loc = setEntityLoc();

        xPoints = new int[]{(int) loc.getX(), (int) (loc.getX() + RECT_SIZE),(int) (loc.getX() + RECT_SIZE), (int) loc.getX()};
        yPoints = new int[]{(int) loc.getY(), (int) loc.getY(), (int) (loc.getY() + RECT_SIZE), (int) (loc.getY() + RECT_SIZE)};

        m = Math.atan2((playerModel.getLocation().getY() - loc.getY()),(playerModel.getLocation().getX() - loc.getX()));
         xvelocity = (Math.cos(m) * 2) * speed;
         yvelocity = (Math.sin(m) * 2) * speed;

    }

    @Override
    public int move() {

        if(!impact) {
            m = Math.atan2((playerModel.getLocation().getY() - loc.getY()), (playerModel.getLocation().getX() - loc.getX()));
             xvelocity = (Math.cos(m) * 2) * speed;
             yvelocity = (Math.sin(m) * 2) * speed;
        }

        if(impact){
            findPlayer(loc);
        }if( xvelocity == (Math.cos(m) * 2) * speed &&  yvelocity == (Math.sin(m) * 2) * speed){
            impact = false;
        }


        loc = new Point2D.Double(loc.getX() +  xvelocity, loc.getY() +  yvelocity);

        xPoints = new int[]{(int) loc.getX(), (int) (loc.getX() + RECT_SIZE),(int) (loc.getX() + RECT_SIZE), (int) loc.getX()};
        yPoints = new int[]{(int) loc.getY(), (int) loc.getY(), (int) (loc.getY() + RECT_SIZE), (int) (loc.getY() + RECT_SIZE)};


        return 0;
    }

    @Override
    public void findPlayer(Point2D loc) {
        super.findPlayer(loc);
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
         this.xvelocity = xvelocity;
    }

    @Override
    public void setYvelocity(double yvelocity) {
         this.yvelocity = yvelocity;
    }

    @Override
    public double getXvelocity() {
        return  xvelocity;
    }

    @Override
    public double getYvelocity() {
        return  yvelocity;
    }
    @Override
    public void move(double velocity) {

    }

}
