package model.characterModel.enemy.boss;

import model.movement.Collidable;
import model.movement.Movable;

import java.awt.geom.Point2D;
import java.util.UUID;

import static controller.constants.ImageFiles.lHand;

public class Lhand extends FinalBoss implements Movable, Collidable {
    private Point2D loc;
    private String id;

    private int hp;
    public Lhand(){
        id = UUID.randomUUID().toString();
        file = lHand;
    }
    @Override
    public boolean rigidBody() {
        return true;
    }

    @Override
    public boolean solid() {
        return false;
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
        this.yvelocity = yvelocity;
    }

    @Override
    public void setYvelocity(double yvelocity) {
        this.xvelocity = xvelocity;
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
        this.hp = hp;
    }

    @Override
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
