package model.characterModel.enemy;

import controller.Util.Util;
import controller.model.BlackOrbCircles;
import model.model.Enemy;
import model.movement.Movable;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.UUID;

import static controller.constants.CollectableConstans.orb;
import static controller.constants.CollectableConstans.orb_xp;
import static controller.constants.EntityConstants.ORB_SIZE;

public class BlackOrbModel extends Enemy implements Movable{
    public int laserPower = 12;
    private Point2D loc;
    private int hp;
    private String id;
    private ArrayList<BlackOrbCircles> circles;

    public BlackOrbModel() {
        circles = new ArrayList<>();
        id = UUID.randomUUID().toString();
        create();
    }
    private void create(){
        collectables= orb;
        collectablesXp = orb_xp;
        loc = Util.setLoc();
        new Thread(()->{
            circles.add(new BlackOrbCircles(
                    new Point2D.Double(loc.getX()+ORB_SIZE/4.0,loc.getY() + ORB_SIZE)));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            circles.add(new BlackOrbCircles(
                    new Point2D.Double(loc.getX()+ORB_SIZE/2.0,loc.getY())));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            circles.add(new BlackOrbCircles(
                    new Point2D.Double(loc.getX()+3*ORB_SIZE/4.0,loc.getY()+ORB_SIZE)));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            circles.add(new BlackOrbCircles(
                    new Point2D.Double(loc.getX(),loc.getY() + ORB_SIZE/3.0)));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            circles.add(new BlackOrbCircles(
                    new Point2D.Double(loc.getX() + ORB_SIZE,loc.getY() + ORB_SIZE/3.0)));

        }).start();
    }

    public ArrayList<BlackOrbCircles> getCircles() {
        return circles;
    }


    @Override
    public boolean rigidBody() {
        return false;
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

    }

    @Override
    public int getHp() {
        return 0;
    }

    @Override
    public int[] getyPoints() {
        return new int[0];
    }


    public String getId() {
        return id;
    }

    public void setLoc(Point2D loc) {
        this.loc = loc;
    }
}
