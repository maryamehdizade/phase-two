package model.characterModel.enemy;

import model.model.Enemy;
import model.movement.Collidable;
import model.movement.Movable;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.UUID;

import static controller.Util.Util.setLoc;
import static controller.constants.EntityConstants.BAR_SIZE;

public class BarricadosModel extends Enemy implements Movable, Collidable {
    private Point2D loc;
    private int hp = 1000;
    private String id;
    public int sec;
    public Timer timer;
    private int[] xPoints = new int[4];
    private int[] yPoints = new int[4];

    public BarricadosModel() {
        id = UUID.randomUUID().toString();
        createdBarricados();
    }
    private void createdBarricados(){
        loc = setLoc();
        xPoints = new int[]{(int) loc.getX(), (int) (loc.getX()+BAR_SIZE), (int) (loc.getX()+BAR_SIZE),(int) loc.getX()};
        yPoints = new int[]{(int) loc.getY(), (int) loc.getY(), (int) (loc.getY()+BAR_SIZE),
                (int) (loc.getY()+BAR_SIZE)};
        timer = new Timer(1000, e -> sec++);
        timer.start();
    }
    public Point2D getLoc() {
        return loc;
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

    @Override
    public void setImpact(boolean impact) {

    }

    @Override
    public int[] getxPoints() {
        return xPoints;
    }

    @Override
    public void setHp(int hp) {
    }

    @Override
    public int getHp() {
        return 100;
    }

    @Override
    public int[] getyPoints() {
        return yPoints;
    }

    @Override
    public String getId() {
        return id;
    }
}
