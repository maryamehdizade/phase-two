package model.characterModel;

import model.movement.Collidable;
import model.movement.Movable;
import view.pages.GamePanel;

import java.awt.geom.Point2D;
import java.util.UUID;

public class BulletModel implements Movable, Collidable {
    private double panelW,panelH;
    private Point2D loc;
    private double dx;
    private double dy;
    private double speed = 3;
    private String id;

    public BulletModel(Point2D loc, int targetX, int targetY) {


        id = UUID.randomUUID().toString();
        this.loc = loc;
        double angle = Math.atan2(targetY - loc.getX(), targetX - loc.getY());
        dx = (int) (Math.cos(angle) * speed);
        dy = (int) (Math.sin(angle) * speed);

    }
    public void findPlayer(){

    }

    @Override
    public void setImpact(boolean impact) {

    }

    @Override
    public int[] getxPoints() {
        return new int[0];
    }

    @Override
    public void setHp(int hp) {

    }

    @Override
    public int getHp() {
        return 0;
    }

    @Override
    public int[] getyPoints() {
        return new int[0];
    }

    @Override
    public boolean solid() {
        return false;
    }

    @Override
    public int move() {
        loc = new Point2D.Double(loc.getX() + dx, loc.getY() + dy);

        if(loc.getX() < 0)return 1;
        else if(loc.getX() > panelW)return 2;
        else if(loc.getY() < 0)return 3;
        else if(loc.getY() > panelH)return 4;
        return 0;
    }

    @Override
    public void move(double velocity) {

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



    public Point2D getLoc() {
        return loc;
    }

    public String getId() {
        return id;
    }

    public void setPanelW(double panelW) {
        this.panelW = panelW;
    }

    @Override
    public boolean collides() {
        return true;
    }

    @Override
    public boolean doesMeleeAtack() {
        return false;
    }

    public void setPanelH(double panelH) {
        this.panelH = panelH;
    }
}
