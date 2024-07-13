package model.characterModel.enemy;

import model.movement.Collidable;
import model.movement.Movable;

import java.awt.geom.Point2D;
import java.util.UUID;

public class EnemyBullets implements Movable, Collidable {


    private double xvelocity;
    private double yvelocity;
    private String id;
    private boolean impact;
    private Point2D loc;
    private Point2D target;
    private double m;
    private int speed = 1;
    public Enemy creator;
    public boolean rigidBody;


    public EnemyBullets(Point2D loc, Point2D target, Enemy creator, boolean rigidBody) {
        this.rigidBody = rigidBody;
        this.loc = loc;
        this.target = target;
        this.creator = creator;
        id = UUID.randomUUID().toString();
        m = Math.atan2(target.getY() - loc.getY(), target.getX() - loc.getX());
    }

    @Override
    public boolean rigidBody() {
        return rigidBody;
    }

    @Override
    public boolean solid() {
        return false;
    }

    @Override
    public int move() {

        xvelocity = (Math.cos(m) ) * speed/2;
        yvelocity = (Math.sin(m) ) * speed/2;

        loc = new Point2D.Double(loc.getX() + xvelocity, loc.getY() + yvelocity);
        return 0;
    }

    @Override
    public void move(double velocity) {

    }

    public Point2D getTarget() {
        return target;
    }

    public void setTarget(Point2D target) {
        this.target = target;
    }

    @Override
    public void setPanelW(double panelW) {

    }

    @Override
    public boolean collides() {
        return false;
    }

    @Override
    public boolean doesMeleeAtack() {
        return false;
    }

    @Override
    public void setPanelH(double panelH) {

    }

    @Override
    public void setSpeed(double speed) {

    }

    @Override
    public void setXvelocity(double xvelocity) {
this.xvelocity = xvelocity;
    }

    @Override
    public void setYvelocity(double yvelocity) {
this.yvelocity = yvelocity;
    }

    @Override
    public double getXvelocity() {
        return xvelocity;
    }

    @Override
    public double getYvelocity() {
        return yvelocity;
    }

    @Override
    public Point2D getLoc() {
        return loc;
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
    public String getId() {
        return id;
    }
}
