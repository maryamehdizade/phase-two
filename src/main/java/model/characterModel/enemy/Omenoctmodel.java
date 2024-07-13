package model.characterModel.enemy;
import model.movement.Collidable;
import model.movement.Movable;
import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.Random;
import java.util.UUID;
import static controller.Util.Util.setEntityLoc;
import static controller.constants.AttackConstants.*;
import static controller.constants.CollectableConstans.*;
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
    private JPanel panel;
    private boolean inPlace;
    private boolean xWall;
    Random random;

    int n = 1;
    private Point2D destination = new Point2D.Double(0, 0);
    private double x, y;

    public Omenoctmodel() {
        this.id = UUID.randomUUID().toString();
        createOmenoct();
    }

    private void createOmenoct() {
        collectables = omenoct;
        collectablesXp = omenoct_xp;
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
    public boolean rigidBody() {
        return false;
    }

    @Override
    public boolean solid() {
        return false;
    }

    @Override
    public int move() {
        findNearestWall();

        if (!impact) {
            if (!checkX() || !checkY()) {
                findNearestWall();
                findPlayer();
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

        return 0;
    }

    private boolean checkX() {
        //        if (a) loc = new Point2D.Double(destination.getX(), loc.getY());
        return Math.abs((int) destination.getX() - (int) loc.getX()) < n;
    }

    private boolean checkY() {
        //        if (a) loc = new Point2D.Double(loc.getX(), destination.getY());
        return Math.abs((int) destination.getY() - (int) loc.getY()) < n;
    }

    @Override
    void findPlayer(Point2D loc) {
        super.findPlayer(loc);
    }

    public void findPlayer() {
        m = Math.atan2((destination.getY() - loc.getY()), (destination.getX() - loc.getX()));
        xvelocity = (Math.cos(m) ) * speed;
        yvelocity = (Math.sin(m) ) * speed;

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
            if (yDis2 < yDis) yy = (panelH - OMENOCT_SIZE);
            destination = new Point2D.Double(x, yy);

            xWall = false;
        }else {
            double xx = 0;
            if (xDis2 < xDis) xx = (panelW - OMENOCT_SIZE);
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
