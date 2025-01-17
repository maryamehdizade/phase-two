package model.characterModel.enemy.boss;
import model.model.Enemy;
import model.movement.Collidable;
import model.movement.Movable;
import static controller.constants.Constant.PANEL_DIMENSION;
import static controller.constants.EntityConstants.*;
import static controller.listner.MyListner.v;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import static controller.Util.BossHandler.attacks;
import static controller.Util.Util.*;
import static controller.constants.ImageFiles.smiley;
import static model.movement.Impact.impact;

public class BossModel extends Enemy implements Movable, Collidable {

    private Point2D loc;
    private String id;
    public ArrayList<Point2D> aoe = new ArrayList<>();
    private File file;
    private int xvelocity;
    private int yvelocity;
    private int hp = FINALBOSS_HP - 295;
    public  Lhand l;
    private double size = SMILEY_SIZE;
    public  Rhand r;
    public Phand p;
    public int vomitCount;
    public int quackCount;
    public int projectileCount;
    public int rapidFire;
    public int squeeze;
    public boolean vulnerable;

    public BossModel() {
        id = UUID.randomUUID().toString();
        speed = 2;
        rangedPower = 5;
        file = smiley;
        create();
    }
    private void create(){
        move = true;
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
    public void slap(){
        if(l!= null)if(l.isSlap())l.slap();
        if(r!= null)if(r.isSlap())r.slap();
        if(p!= null)if(p.isSlap())p.slap();
    }
    @Override
    public int move() {
        if(move) {
            if (!inPlace) {
                if (loc.getY() < 75) {
                    moveDown();
                } else {
                    inPlace = true;
                }
            }
            if (!attacks.contains(Attacks.projectile)) {
                if (loc.getX() < 5) loc = new Point2D.Double(loc.getX() + speed, loc.getY());
                if (loc.getY() < 5) loc = new Point2D.Double(loc.getX(), loc.getY() + speed);
                if (loc.getX() > PANEL_DIMENSION.getWidth() - 200)
                    loc = new Point2D.Double(loc.getX() - speed, loc.getY());
                if (loc.getY() > PANEL_DIMENSION.getHeight() - 200)
                    loc = new Point2D.Double(loc.getX(), loc.getY() - speed);
            }
        }
        return 0;
    }
    private void moveDown(){
        loc = new Point2D.Double(loc.getX(), loc.getY() + speed);
        r.moveDown();
        l.moveDown();
    }
    public void squeeze(){
        r.move();
        l.move();
        squeeze++;
    }
    public void powerPunch(){
        if(!p.inPlace)p.move();
        else {
            impact(collisionPoint(p.getLoc(),playerModel.getLoc()),100);
            p.inPlace = false;
            p.target = new Point2D.Double(0,0);
            toggleOccupation();
            attacks.remove(Attacks.powerPunch);
        }
    }
    
    public void quake(){
        if(!p.inPlace)p.move(4);
        else {
//            impact(p.getLoc(),50);
            p.inPlace = false;
            toggleOccupation();
            v = 4;
            quakeCounter();
        }
    }
    Timer counter;
    void quakeCounter(){
        counter = new Timer(1000,e->{
            quackCount++;
            if(quackCount >= 8){
                v = 0;
                quackCount = 0;
                attacks.remove(Attacks.Quake);
                counter.stop();
            }
        });
        counter.start();
    }
    private double delta = Math.PI/200.0;
    public double angle;
    private double dis;
    public void projectile(){
        dis = distance(loc,playerModel.getLoc());
        if(angle == 0) {
            angle = Math.atan2(playerModel.getLocation().getY() - loc.getY(),
                    loc.getX() - playerModel.getLocation().getX());

        }
        projectileCount++;
        move(angle);
    }
    public void vomit(){
        if(aoe.size() <= 6)aoe.add(setLoc());
        vomitCount++;
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
        if(move) {
            loc = new Point2D.Double(playerModel.getLocation().getX() + dis * Math.cos(angle),
                    playerModel.getLocation().getY() - dis * Math.sin(angle));
            if (dis < 100) dis++;
            angle += delta;
        }
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

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
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
    public boolean hasTwoHands(){
        return l!=null && r!=null;
    }
    public boolean hasPunchHand(){
        if(p!=null) return !p.occupied;
        return false;
    }
    public void reset(){
        aoe = new ArrayList<>();
        vomitCount = 0;
    }
    public void toggleOccupation(){
        p.occupied = !p.occupied;
    }
}
