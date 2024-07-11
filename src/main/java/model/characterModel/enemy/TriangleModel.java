package model.characterModel.enemy;

import model.movement.Collidable;
import model.movement.Movable;

import java.awt.geom.Point2D;
import java.util.UUID;

import static controller.Util.Util.setEntityLoc;
import static controller.constants.CollectableConstans.*;
import static controller.constants.EntityConstants.TRI_SIZE;
import static controller.constants.AttackConstants.*;

public class TriangleModel extends Enemy implements Movable, Collidable {

    private double x1, y1, x2, y2, x3, y3;
    private int[] xPoints;
    private int[] yPoints;
    private Point2D loc;
    private int hp = 15;
    private String id;
    private boolean impact;

    public TriangleModel() {

        this.id = UUID.randomUUID().toString();

        createTriangle();
    }
    public void findPlayer(Point2D loc){
        super.findPlayer(loc);
    }

    @Override
    public boolean solid() {
        return true;
    }

    @Override
    public int move() {
        if(!impact) {
             m = Math.atan2((playerModel.getLocation().getY() - y1), (playerModel.getLocation().getX() - x1));
             xvelocity = (Math.cos( m) * 2) * speed;
             yvelocity = (Math.sin( m) * 2) * speed;
        }
        if(impact){
            findPlayer(loc);
        }if( xvelocity == (Math.cos( m) * 2) * speed &&  yvelocity == (Math.sin( m) * 2) * speed){
            impact = false;
        }


        x1 +=  xvelocity;
        y1 +=  yvelocity;
        x2 +=  xvelocity;
        y2 +=  yvelocity;
        x3 +=  xvelocity;
        y3 +=  yvelocity;

        xPoints = new int[]{(int) x1, (int) x3, (int) x2};
        yPoints = new int[]{(int) y1, (int) y3, (int) y2};

        loc = new Point2D.Double(x1, y1 );

        return 0;
    }
    void createTriangle(){
        collectables = tri;
        collectablesXp = tri_xp;
        meleePower = TRI_MELEE_POWER;
        double x;
        double y;

        Point2D startLoc = setEntityLoc();
        x = startLoc.getX();
        y = startLoc.getY();

        this.x1 = x;
        this.y1 = y;
        this.x2 = x + TRI_SIZE;
        this.y2 = y;
        this.x3 = x + TRI_SIZE/2.0;
        this.y3 = y + TRI_SIZE;

        xPoints = new int[]{(int) x1, (int) x3, (int) x2};
        yPoints = new int[]{(int) y1, (int) y3, (int) y2};

          m = Math.atan2(playerModel.getLocation().getY() - y1, playerModel.getLocation().getX() - x1);
          xvelocity = Math.cos( m) * speed;
          yvelocity = Math.sin( m) * speed;

        loc = new Point2D.Double((x1 + x2 + x3)/3, (y1 + y2 + y3)/3);
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
        return true;
    }

    @Override
    public void setPanelH(double panelH) {

    }

    public double getX1() {
        return x1;
    }

    public double getY1() {
        return y1;
    }

    public double getX2() {
        return x2;
    }

    public double getY2() {
        return y2;
    }

    public double getX3() {
        return x3;
    }

    public double getY3() {
        return y3;
    }

    public int getHp() {
        return hp;
    }

    public String getId() {
        return id;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public void setXvelocity(double xvelocity) {
         this.xvelocity = xvelocity;
    }

    @Override
    public void setYvelocity(double yvelocity) {
         this.yvelocity = yvelocity;
    }

    @Override
    public double getXvelocity() {
        return  xvelocity;
    }

    @Override
    public double getYvelocity() {
        return  yvelocity;
    }


    @Override
    public Point2D getLoc() {
        return loc;
    }

    public int[] getxPoints() {
        return xPoints;
    }

    public int[] getyPoints() {
        return yPoints;
    }

    public void setImpact(boolean impact) {
        this.impact = impact;
    }
}
