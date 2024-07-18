package controller.model;

import model.model.Enemy;
import model.movement.Collidable;
import model.movement.Movable;

import java.awt.geom.Point2D;
import java.util.UUID;

public class BlackOrbCircles extends Enemy implements Collidable, Movable {
    private int hp = 30;
    private Point2D loc;
    private String id;

    public BlackOrbCircles(Point2D loc) {
        this.loc = loc;
        id = UUID.randomUUID().toString();
        create();
    }
    private void create(){
    }

    @Override
    public boolean rigidBody() {
        return true;
    }

    @Override
    public boolean solid() {
        return true;
    }

    @Override
    public int move() {
        return 0;
    }

    @Override
    public void move(double velocity) {

    }

    @Override
    public void setPanelW(double panelW) {

    }

    @Override
    public boolean collides() {
        return true;
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

    }

    @Override
    public void setYvelocity(double yvelocity) {

    }

    @Override
    public double getXvelocity() {
        return 0;
    }

    @Override
    public double getYvelocity() {
        return 0;
    }

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
        this.hp = hp;
    }

    public void setLoc(Point2D loc) {
        this.loc = loc;
    }

    public int getHp() {
        return hp;
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
