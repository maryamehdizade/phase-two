package model.characterModel.enemy;

import model.model.Enemy;
import model.movement.Collidable;
import model.movement.Movable;

import java.awt.geom.Point2D;
import java.util.UUID;

import static controller.Util.Util.*;
import static controller.constants.AttackConstants.WYRM_RANGED;
import static controller.constants.CollectableConstans.*;

public class WyrmModel extends Enemy implements Movable, Collidable {
    private Point2D loc;
    private int hp = 12;
    private String id;
    public double delta = Math.PI/200.0;
    private double xvelocity;
    private double yvelocity;
    private int speed = 2;
    public double currentAngel;

    public WyrmModel() {
        id = UUID.randomUUID().toString();
        createWrym();
    }
    private void createWrym(){
        move = true;
        collectables = wyrm;
        collectablesXp = wyrm_xp;
        rangedPower = WYRM_RANGED;

        loc = setEntityLoc();

    }
    public void findPlayer(Point2D loc){
        super.findPlayer(loc);
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

    public int getHp() {
        return hp;
    }

    @Override
    public int[] getyPoints() {
        return new int[0];
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean rigidBody() {
        return false;
    }

    @Override
    public boolean solid() {
        return false;
    }

    @Override
    public int move() {
        if(move) {
            if (distance(loc, playerModel.getLoc()) >= 250) {
                currentAngel = 0;
                m = Math.atan2((playerModel.getLocation().getY() - loc.getY()),
                        (playerModel.getLocation().getX() - loc.getX()));
                xvelocity = (Math.cos(m)) * speed;
                yvelocity = (Math.sin(m)) * speed;
                loc = new Point2D.Double(loc.getX() + xvelocity, loc.getY() + yvelocity);
            } else if (distance(loc, playerModel.getLoc()) <= 150) {
                currentAngel = 0;
                m = Math.atan2((playerModel.getLocation().getY() - loc.getY()),
                        (playerModel.getLocation().getX() - loc.getX()));
                xvelocity = (Math.cos(m)) * speed;
                yvelocity = (Math.sin(m)) * speed;
                loc = new Point2D.Double(loc.getX() - xvelocity, loc.getY() - yvelocity);
            } else {
                double dis = distance(playerModel.getLocation(), loc);
                xvelocity = 0;
                yvelocity = 0;
                if (currentAngel == 0) {
                    currentAngel = Math.atan2(playerModel.getLocation().getY() - loc.getY(),
                            loc.getX() - playerModel.getLocation().getX());
                }
                loc = new Point2D.Double(playerModel.getLocation().getX() + dis * Math.cos(currentAngel),
                        playerModel.getLocation().getY() - dis * Math.sin(currentAngel));
                currentAngel += delta;
            }
        }
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

    public double getXvelocity() {
        return xvelocity;
    }

    public double getYvelocity() {
        return yvelocity;
    }
}
