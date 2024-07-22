package model.characterModel.enemy.boss;

import model.model.Enemy;
import model.movement.Collidable;
import model.movement.Movable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static controller.Util.BossHandler.attacks;
import static controller.Util.CollisionUtil.reduceHp;
import static controller.Util.Util.*;
import static controller.constants.EntityConstants.R_HAND_SIZE;
import static controller.constants.ImageFiles.lHand;
import static controller.constants.ImageFiles.rHand;

public class Rhand extends Enemy implements Movable, Collidable {
    private Point2D loc = new Point2D.Double();
    private String id;

    private File file;
    public boolean vulnerable;
    private int hp = 100;
    private BossModel head;
    public Rhand(BossModel b){
        head = b;
        id = UUID.randomUUID().toString();
        file = rHand;
        rangedPower = 5;
        speed = 2;
        create();
    }
    private void create(){
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        loc = new Point2D.Double(head.getLoc().getX() - R_HAND_SIZE.getX() - 100,head.getLoc().getY() + 100);
    }
    public void moveDown(){
        loc = new Point2D.Double(loc.getX(), loc.getY() + speed);

    }
    private boolean slap;

    public void setSlap(boolean slap) {
        this.slap = slap;
    }

    public boolean isSlap() {
        return slap;
    }

    public void slap(){
        speed = 3;
        m = Math.atan2(playerModel.getLocation().getY() - 70 - loc.getY(), (playerModel.getLocation().getX()  - loc.getX()));
        xvelocity = (Math.cos(m) * 2) * speed;
        yvelocity = (Math.sin(m) * 2) * speed;
        loc = new Point2D.Double(loc.getX() +  xvelocity, loc.getY() +  yvelocity);
        if(distance(centerLoc(playerModel),centerLoc(this)) <= this.size()/2.0  +playerModel.size()/2.0){
            attacks.remove(Attacks.Slap);slap = false;
            reduceHp(this);
        }
        speed = 2;
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
        m = Math.atan2(playerModel.getLocation().getY() - 70 - loc.getY(), (playerModel.getLocation().getX() - 200  - loc.getX()));
        xvelocity = (Math.cos(m) * 2) * speed;
        yvelocity = (Math.sin(m) * 2) * speed;
        loc = new Point2D.Double(loc.getX() +  xvelocity, loc.getY() +  yvelocity);
        if(distance(addVector(playerModel.getLoc(),new Point2D.Double(-200,-70)),centerLoc(this)) <= 50)attacks.remove(Attacks.squeeze);

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

    public void setLoc(Point2D loc) {
        this.loc = loc;
    }
}
