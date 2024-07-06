package model.characterModel.enemy;

import model.movement.Movable;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.UUID;

import static controller.Util.setEntityLoc;
import static controller.constants.EntityConstants.OMENOCT_SIZE;

public class Omenoctmodel extends Enemy implements Movable {
    private double speed = 1;
    private int hp = 20;
    private int[] xPoints;
    private int[] yPoints;
    private Point2D loc;
    private String id;
    private double panelW, panelH, panelX, panelY;
    private boolean impact;
    private JFrame frame;
    private boolean xWall;
    private Point2D destination;
    private double x, x2, x3, x4, x5, x6, x7, x8,y, y2,y3,y4,y5,y6,y7,y8;
    public Omenoctmodel(){
        frame = playerModel.getFrame();
        this.id = UUID.randomUUID().toString();
        createOmenoct();
    }
    private void createOmenoct(){
        Point2D startLoc = setEntityLoc();
        x = startLoc.getX();
        y = startLoc.getY();

        loc = new Point2D.Double(x + OMENOCT_SIZE/2.0, y + OMENOCT_SIZE/2.0);

        xPoints = new int[]{(int) (x + OMENOCT_SIZE/3), (int) (x + OMENOCT_SIZE * 2/3), (int) (x + OMENOCT_SIZE),
                (int) (x + OMENOCT_SIZE), (int) (x + OMENOCT_SIZE * 2 /3), (int) (x + OMENOCT_SIZE/3), (int) x, (int) x};
        yPoints = new int[]{(int) y, (int) y, (int) (y + OMENOCT_SIZE/3), (int) (y + OMENOCT_SIZE *2/3),
                (int) (y + OMENOCT_SIZE), (int) (y + OMENOCT_SIZE), (int) (y + OMENOCT_SIZE*2/3), (int) (y + OMENOCT_SIZE/3)};

        findNearestWall();

//        double m = Math.atan2((playerModel.getLocation().getY() - loc.getY()),(playerModel.getLocation().getX() - loc.getX()));
//        xvelocity += ((Math.cos(m) * 2) * speed - xvelocity)/80;
//        yvelocity += ((Math.sin(m) * 2) * speed - yvelocity)/80;

    }

    @Override
    public int move() {

        if(!impact) {
            findPlayer(loc);
        }
        if(impact){
            findNearestWall();
        }if( xvelocity == (Math.cos(m) * 2) * speed &&  yvelocity == (Math.sin(m) * 2) * speed){
            impact = false;
        }

        loc = new Point2D.Double(loc.getX() +  xvelocity, loc.getY() +  yvelocity);

        xPoints = new int[]{(int) loc.getX() - OMENOCT_SIZE, (int) (loc.getX() + OMENOCT_SIZE/6),
                (int) (loc.getX() + OMENOCT_SIZE/2), (int) (loc.getX() + OMENOCT_SIZE/2),  (int) (loc.getX() + OMENOCT_SIZE/6)
        , (int) (loc.getX() - OMENOCT_SIZE/6), (int) (loc.getX() - OMENOCT_SIZE/2), (int) (loc.getX() - OMENOCT_SIZE/2)};

        yPoints = new int[]{(int) loc.getY() - OMENOCT_SIZE/2, (int) loc.getY() - OMENOCT_SIZE/2, (int)loc.getY() - OMENOCT_SIZE/6,
                (int) (loc.getY() + OMENOCT_SIZE/6) ,(int) loc.getY() + OMENOCT_SIZE/2, (int) loc.getY() + OMENOCT_SIZE/2 ,
                (int) (loc.getY() + OMENOCT_SIZE/6),  (int) (loc.getY() - OMENOCT_SIZE/6)};

        return 0;
    }
    private boolean checkX(){
        return destination.getX() == loc.getX() - OMENOCT_SIZE/2 || destination.getX() == loc.getX() + OMENOCT_SIZE/2;
    }
    private boolean checkY(){
        return destination.getY() == loc.getY() - OMENOCT_SIZE/2 || destination.getY() == loc.getY() + OMENOCT_SIZE/2;
    }
    @Override
    public void findPlayer(Point2D loc) {
        if (destination.getY() != 0 && destination.getY() != panelH) {
            if (checkX())
                verticalMovement();
            else {
                if (loc.getX() - OMENOCT_SIZE/2.0 <= 2 || loc.getX() + OMENOCT_SIZE/2.0 >= panelW - 2) {
                    horizonMovement();
                } else verticalMovement();
            }
        } else if (destination.getX() != 0 && destination.getX() != panelW) {
            if (checkY())
                horizonMovement();
            else {
                if (loc.getX() - OMENOCT_SIZE/2<= 2 || loc.getX() + OMENOCT_SIZE/2 >= panelH - 2)
                    verticalMovement();
                else horizonMovement();

            }
        }
    }
    private void verticalMovement(){
        if(loc.getY() > destination.getY())speed = -1;
        else speed = 1;
//        if(loc.getX() != destination.getX())speed *= -1;
        xvelocity = 0;
        yvelocity = speed * 2;
    }
    private void horizonMovement(){
    if(loc.getX() > destination.getX())speed = -1;
    else speed = 1;
//    if(loc.getY()!= destination.getY()) speed *= -1;
    xvelocity = speed * 2;
    yvelocity = 0;
}
    @Override
    void setVel() {

    }

    private void findNearestWall(){
        double xDis = - panelX + playerModel.getLocation().getX();
        double xDis2 = panelW - playerModel.getLocation().getX();
        double yDis = -panelY + playerModel.getLocation().getY();
        double yDis2 = panelH - playerModel.getLocation().getY();
        double y = Math.min(yDis2, yDis);
        double x = Math.min(xDis, xDis2);
        if(x > y){
            double yy = 0;
            if(xDis2 < xDis)yy = panelH;
            destination = new Point2D.Double( playerModel.getLocation().getX(), yy);
            xWall = false;
        }
        if(y > x){
            double yy = 0;
            if(yDis2 < yDis)yy = panelW;
             destination = new Point2D.Double(yy, playerModel.getLocation().getY());
            xWall = true;
        }

        findPlayer(loc);
    }

    @Override
    public void move(double velocity) {

    }

    @Override
    public void setPanelW(double panelW) {

    }

    @Override
    public void setPanelH(double panelH) {

    }

    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public double getXvelocity() {
        return xvelocity;
    }

    @Override
    public void setXvelocity(double xvelocity) {
        this.xvelocity = xvelocity;
    }

    @Override
    public double getYvelocity() {
        return yvelocity;
    }

    @Override
    public void setYvelocity(double yvelocity) {
        this.yvelocity = yvelocity;
    }

    @Override
    public Point2D getLoc() {
        return loc;
    }



    @Override
    public void setImpact(boolean impact) {
        this.impact = impact;
    }

    @Override
    public int[] getxPoints() {
        return xPoints;
    }


    @Override
    public int[] getyPoints() {
        return yPoints;
    }



    public String getId() {
        return id;
    }

    @Override
    public int getHp() {
        return hp;
    }

    @Override
    public void setHp(int hp) {
        this.hp = hp;
    }
}
