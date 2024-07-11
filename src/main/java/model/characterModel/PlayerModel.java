package model.characterModel;

import model.movement.Collidable;
import model.movement.Movable;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.UUID;

import static controller.Util.Util.addVector;
import static controller.constants.EntityConstants.BALL_SIZE;


public final class PlayerModel implements Movable, Collidable {
    
    private double panelW,panelH;
    public int shoots;
    public int successShoots;
    private int xp = 0;
    private int hp = 100;
    public double size = 20;
    private Point2D location;
    private double speed;
    private int impactTime = 50;
    private int count;
    private boolean impact;
    private double xvelocity = 0;
    private double yvelocity = 0;
    private boolean dForce = false;
    private boolean uForce = false;
    private boolean lForce = false;
    private boolean rForce = false;
    private boolean r0Force = false;
    private boolean d0Force = false;
    private boolean u0Force = false;
    private boolean l0Force = false;
    private int[] xPoints = new int[0];
    private int[] yPoints = new int[0];
    private int levelUp = 0;
    private static PlayerModel player;
    private JPanel panel;
    private String id;

    public static PlayerModel getPlayer() {
        if(player == null){
            player = new PlayerModel(new Point2D.Double(350,350));
        }
        return player;
    }

    public static void defuse() {
        player = null;
    }

    public String getId() {
        return id;
    }

    public PlayerModel(Point2D location) {
        id = UUID.randomUUID().toString();
        this.location = location;
    }


    @Override
    public boolean solid() {
        return false;
    }

    @Override
    public int move() {
        if(impact){
             setLocation(new Point2D.Double( getLocation().getX() - xvelocity,
                     getLocation().getY() - yvelocity));
            count ++;
        }if(count == impactTime){
            impact = false;
            count = 0;
        }
        return 0;
    }
    public void setPoints() {
        if (levelUp > 0) {
            xPoints = new int[levelUp];
            yPoints = new int[levelUp];
            double degree = 360.0 / levelUp;
            double xloc = (location.getX() + size / 2);
            double yloc = (location.getY() + size / 2);
            int count = 0;
            for (double i = 0; i < 359.0; i += degree) {

                double x = Math.cos(i) * size;
                double y = Math.sin(i) * size;
                xPoints[count] = (int) (x + xloc);
                yPoints[count] = (int) (y + yloc);
                count++;

            }
        }
    }

    @Override
    public void move(double velocity) {
        if (dForce || uForce || u0Force || d0Force) {
            if ( getLocation().getY() > 0 &&
                     getLocation().getY() + BALL_SIZE <=  panelH) {
                 setLocation(addVector( getLocation(), new Point2D.Double(0, velocity)));
            }
            if ( getLocation().getY() + BALL_SIZE >  panelH) {
                 setLocation(
                        new Point2D.Double( getLocation().getX(),  panelH - BALL_SIZE - 7));
                yvelocity = 0;
            } else if ( getLocation().getY() < 2) {
                 setLocation(
                        new Point2D.Double( getLocation().getX(), 7));
                yvelocity = 0;
            }
            impact = false;
        }
        if (rForce || lForce || l0Force || r0Force) {
            if ( getLocation().getX() > 0 &&
                     getLocation().getX() + BALL_SIZE <=  panelW) {
                 setLocation(addVector( getLocation(), new Point2D.Double(velocity, 0)));
            }
            if ( getLocation().getX() + BALL_SIZE >  panelW) {
                 setLocation(
                        new Point2D.Double( panelW - BALL_SIZE - 7,  getLocation().getY()));
                xvelocity = 0;
            } else if ( getLocation().getX() < 2) {
                setLocation(
                        new Point2D.Double(7,  getLocation().getY()));
                xvelocity = 0;

            }
            impact = false;
        }
    }


    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public Point2D getLoc() {
        return location;
    }

    @Override
    public void setImpact(boolean impact) {
        this.impact = impact;
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
        return xvelocity;
    }
@Override
    public double getYvelocity() {
        return yvelocity;
    }

    @Override
    public int[] getxPoints() {
        return xPoints;
    }
    public void setLocation(Point2D location) {
        this.location = location;
    }

    public Point2D getLocation() {
        return location;
    }
    public int getXp() {
        return xp;
    }
    @Override
    public int getHp() {
        return hp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    @Override
    public void setHp(int hp) {
        this.hp = hp;
    }

    @Override
    public int[] getyPoints() {
        return yPoints;
    }

    public boolean isdForce() {
        return dForce;
    }

    public void setdForce(boolean dForce) {
        this.dForce = dForce;
    }

    public boolean isuForce() {
        return uForce;
    }

    public void setuForce(boolean uForce) {
        this.uForce = uForce;
    }

    public boolean islForce() {
        return lForce;
    }

    public void setlForce(boolean lForce) {
        this.lForce = lForce;
    }

    public boolean isrForce() {
        return rForce;
    }

    public void setrForce(boolean rForce) {
        this.rForce = rForce;
    }

    public boolean isR0Force() {
        return r0Force;
    }

    public void setR0Force(boolean r0Force) {
        this.r0Force = r0Force;
    }

    public boolean isD0Force() {
        return d0Force;
    }

    public void setD0Force(boolean d0Force) {
        this.d0Force = d0Force;
    }

    public boolean isU0Force() {
        return u0Force;
    }

    public void setU0Force(boolean u0Force) {
        this.u0Force = u0Force;
    }

    public boolean isL0Force() {
        return l0Force;
    }

    public void setL0Force(boolean l0Force) {
        this.l0Force = l0Force;
    }

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

    public void setPanelH(double panelH) {
        this.panelH = panelH;
    }

    public int getLevelUp() {
        return levelUp;
    }

    public void setLevelUp(int levelUp) {
        this.levelUp = levelUp;
    }
}
