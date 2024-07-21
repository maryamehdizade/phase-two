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

import static controller.constants.EntityConstants.L_HAND_SIZE;
import static controller.constants.EntityConstants.SMILEY_SIZE;
import static controller.constants.ImageFiles.lHand;

public class Lhand extends Enemy implements Movable, Collidable {
    private Point2D loc= new Point2D.Double();
    private String id;
    private File file;
    private int xvelocity;
    private int yvelocity;
    public boolean vulnerable;
    private int hp = 100;
    private BossModel head;
    public Lhand(BossModel head){
        id = UUID.randomUUID().toString();
        file = lHand;
        rangedPower = 5;
        speed = 2;
        this.head = head;
        create();
    }
    private void create(){
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        loc = new Point2D.Double(head.getLoc().getX() + L_HAND_SIZE.getX()+SMILEY_SIZE + 100,head.getLoc().getY() + 100);
    }
    public void moveDown(){
        loc = new Point2D.Double(loc.getX(), loc.getY() + speed);

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

    public void setLoc(Point2D loc) {
        this.loc = loc;
    }
}
