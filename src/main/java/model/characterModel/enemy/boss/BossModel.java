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

import static controller.constants.ImageFiles.smiley;

public class BossModel extends Enemy implements Movable, Collidable {

    private Point2D loc;
    private String id;

    private File file;
    private int xvelocity;
    private int yvelocity;
    private int hp = 300;
    public  Lhand l;
    public  Rhand r;
    public Phand p;
    public boolean vulnerable;

    public BossModel() {
        id = UUID.randomUUID().toString();
        speed = 2;
        file = smiley;
        create();
    }
    private void create(){
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        loc = new Point2D.Double(500,-200);
        l = new Lhand(this);
        r = new Rhand(this);
    }

    public boolean inPlace;
    @Override
    public int move() {
        if(!inPlace) {
            if (loc.getY() < 75) {
                moveDown();
            } else {
                inPlace = true;
            }
        }
        return 0;
    }
    private void moveDown(){
        loc = new Point2D.Double(loc.getX(), loc.getY() + speed);
        squeeze();
    }
    public void squeeze(){
        r.moveDown();
        l.moveDown();
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

    public void setImage(Image i) {
        image = i;
    }
}
