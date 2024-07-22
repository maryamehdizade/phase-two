package model.characterModel.enemy.boss;
import model.model.Enemy;
import model.movement.Collidable;
import model.movement.Movable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import static controller.Util.Util.*;
import static controller.constants.ImageFiles.pHand;

public class Phand extends Enemy implements Movable, Collidable {
    private Point2D loc = new Point2D.Double();
    private String id;
    private File file;
    boolean occupied;
    public boolean vulnerable;
    private int hp;
    public Phand(){
        id = UUID.randomUUID().toString();
        file = pHand;
        speed = 3;
        create();
    }
    private void create(){
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean rigidBody() {
        return true;
    }

    @Override
    public boolean solid() {
        return false;
    }
    public boolean inPlace;
    Point2D target = new Point2D.Double(0,0);
    @Override
    public int move() {
        if(Objects.equals(target, new Point2D.Double(0,0))) {
            switch ((int) (Math.random() * 4)) {
                case 1 -> target = addVector(playerModel.getLocation(), new Point2D.Double(10, -150));
                case 2 -> target = addVector(playerModel.getLocation(), new Point2D.Double(10, 100));
                case 3 -> target = addVector(playerModel.getLocation(), new Point2D.Double(-150, 10));
                case 0 -> target = addVector(playerModel.getLocation(), new Point2D.Double(100, 10));
            }
        }
        m = Math.atan2((target.getY() - loc.getY()), (target.getX() - loc.getX()));
        xvelocity = (Math.cos(m) * 2) * speed;
        yvelocity = (Math.sin(m) * 2) * speed;
        loc = new Point2D.Double(loc.getX() +  xvelocity, loc.getY() +  yvelocity);
        if(distance(loc,target)<=10)inPlace = true;
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
        return 20;
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
