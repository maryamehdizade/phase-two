package model.characterModel.enemy;

import model.movement.Collidable;
import model.movement.Movable;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.UUID;

import static controller.Util.Util.setEntityLoc;
import static controller.constants.AttackConstants.*;
import static controller.constants.EntityConstants.OMENOCT_SIZE;

public class Omenoctmodel extends Enemy implements Movable, Collidable {
    private double speed = 1;
    private int hp = 20;
    private int[] xPoints;
    private int[] yPoints;
    private Point2D loc;
    private String id;
    private double panelW, panelH;
    private boolean impact;
    private JFrame frame;
    private boolean inPlace;
    private boolean xWall;


    int n = 2;
    private Point2D destination = new Point2D.Double(0, 0);
    private double x, y;

    public Omenoctmodel() {
        frame = playerModel.getFrame();
        this.id = UUID.randomUUID().toString();
        createOmenoct();
    }

    private void createOmenoct() {
        collectables = 8;
        collectablesXp = 4;
        meleePower = OMENOCT_MELEE_POWER;
        rangedPower = OMENOCT_RANGED_POWER;
        Point2D startLoc = setEntityLoc();
        x = startLoc.getX();
        y = startLoc.getY();

        loc = new Point2D.Double(x, y);

        xPoints = new int[]{(int) (x + OMENOCT_SIZE / 3), (int) (x + OMENOCT_SIZE * 2 / 3), (int) (x + OMENOCT_SIZE),
                (int) (x + OMENOCT_SIZE), (int) (x + OMENOCT_SIZE * 2 / 3), (int) (x + OMENOCT_SIZE / 3), (int) x, (int) x};
        yPoints = new int[]{(int) y, (int) y, (int) (y + OMENOCT_SIZE / 3), (int) (y + OMENOCT_SIZE * 2 / 3),
                (int) (y + OMENOCT_SIZE), (int) (y + OMENOCT_SIZE), (int) (y + OMENOCT_SIZE * 2 / 3), (int) (y + OMENOCT_SIZE / 3)};

        findNearestWall();

    }

    private int count;

    @Override
    public boolean solid() {
        return false;
    }

    @Override
    public int move() {

        if (!impact) {
            if (!checkX() || !checkY()) {
                findNearestWall();
                findPlayer();
                System.out.println("hey");
            }

        }
        if (impact) {
            count++;
        }
        if (count >= 10) {
            impact = false;
        }


//        System.out.print(loc.getX() + "    " + loc.getY());
//        System.out.print("     d:  " + destination.getX() + "  " + destination.getY());
//        System.out.print("     playre:  " + playerModel.getLocation().getX() + "   " + playerModel.getLocation().getY());
//        System.out.println("      " + xvelocity + "    " + yvelocity);


        loc = new Point2D.Double(loc.getX() + xvelocity, loc.getY() + yvelocity);

        xPoints = new int[]{(int) loc.getX() + OMENOCT_SIZE / 3, (int) (loc.getX() + OMENOCT_SIZE * 2 / 3),
                (int) (loc.getX() + OMENOCT_SIZE), (int) (loc.getX() + OMENOCT_SIZE), (int) (loc.getX() + OMENOCT_SIZE * 2 / 3)
                , (int) (loc.getX() + OMENOCT_SIZE / 3), (int) (loc.getX()), (int) (loc.getX())};

        yPoints = new int[]{(int) loc.getY(), (int) loc.getY(), (int) loc.getY() + OMENOCT_SIZE / 3,
                (int) (loc.getY() + OMENOCT_SIZE * 2 / 3), (int) loc.getY() + OMENOCT_SIZE, (int) loc.getY() + OMENOCT_SIZE,
                (int) (loc.getY() + OMENOCT_SIZE * 2 / 3), (int) (loc.getY() + OMENOCT_SIZE / 3)};

//        if (loc.getX() > panelW) loc = new Point2D.Double(panelW - OMENOCT_SIZE, loc.getY());
//        else if (loc.getX() < 0) loc = new Point2D.Double(0, loc.getY());
//        if (loc.getY() > panelH) loc = new Point2D.Double(loc.getX(), panelH - OMENOCT_SIZE);
//        else if (loc.getY() < 0) loc = new Point2D.Double(loc.getX(), 0);

        return 0;
    }

    private boolean checkX() {
        boolean a = Math.abs((int) destination.getX() - (int) loc.getX()) < n;
        if (a) loc = new Point2D.Double(destination.getX(), loc.getY());
        return a;
    }

    private boolean checkY() {
        boolean a = Math.abs((int) destination.getY() - (int) loc.getY()) < n;
        if (a) loc = new Point2D.Double(loc.getX(), destination.getY());
        return a;
    }

    @Override
    void findPlayer(Point2D loc) {
        super.findPlayer(loc);
    }

    public void findPlayer() {
        m = Math.atan2((destination.getY() - loc.getY()), (destination.getX() - loc.getX()));
        xvelocity = (Math.cos(m) * 2) * speed;
        yvelocity = (Math.sin(m) * 2) * speed;

//        if(!checkY()) {
//            if ((int) destination.getY() >= 0 && (int) destination.getY() - panelH + OMENOCT_SIZE <= 0) {
//                if (checkX())
//                    verticalMovement();
//                else {
//                    if (loc.getY() <= n || loc.getY() + OMENOCT_SIZE >= panelW - n) {
//                        horizonMovement();
//                    } else verticalMovement();
//                }
//            }
//        }
//        else if(!checkX()) {
//
//            if ((int) destination.getX() >= 0 && (int) destination.getX() - panelW + OMENOCT_SIZE <= 0) {
////            if((int)destination.getY() < n || (int) destination.getY() - panelH + OMENOCT_SIZE > -n){
//                if (checkY())
//                    horizonMovement();
//                else {
//                    if (loc.getX() <= n || loc.getX() + OMENOCT_SIZE >= panelH - n)
//                        verticalMovement();
//                    else horizonMovement();
//
//                }
//            }
//        }
    }

    private void verticalMovement() {

        if ((int) loc.getY() > (int) destination.getY()) speed = -1;
        else if ((int) loc.getY() < (int) destination.getY()) speed = 1;
//        if(loc.getX() != destination.getX())speed *= -1;
        yvelocity = speed * n;
    }

    private void horizonMovement() {
        if ((int) loc.getX() > (int) destination.getX()) speed = -1;
        else if ((int) loc.getX() < (int) destination.getX()) speed = 1;
//    if(loc.getY()!= destination.getY()) speed *= -1;
        xvelocity = speed * n;

    }


    private void findNearestWall() {
        double xDis = loc.getX();
        double xDis2 = (panelW - loc.getX());
        double yDis = loc.getY();
        double yDis2 = (panelH - loc.getY());
        double y = Math.min(yDis2, yDis);
        double x = Math.min(xDis, xDis2);
        if (x >= y) {
            double yy = 0;
            if (xDis2 < xDis) yy = (panelH - OMENOCT_SIZE);
            destination = new Point2D.Double(x, yy);

            xWall = false;
        }else {
            double xx = 0;
            if (yDis2 < yDis) xx = (panelW - OMENOCT_SIZE);
            destination = new Point2D.Double(xx, y);
            xWall = true;
        }
    }

    @Override
    public void move(double velocity) {

    }

    @Override
    public void setPanelW(double panelW) {
        this.panelW = panelW;
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
        this.panelH = panelH;
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
