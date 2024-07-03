package model.characterModel;

import model.movement.Movable;
import view.pages.GamePanel;

import java.awt.geom.Point2D;

import static controller.Constant.BALL_SIZE;
import static controller.Util.addVector;

public class MovePlayer implements Movable {
    public PlayerModel playerModel;
    private Point2D loc;
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
    private GamePanel panel;
    private double speed;
    private boolean impact;

    public MovePlayer(PlayerModel playerModel, GamePanel panel) {
        this.playerModel = playerModel;
        this.panel = panel;
        loc = playerModel.getLocation();
    }
    void updateLoc(){
        loc = playerModel.getLocation();
    }

    int count;
    @Override
    public int move() {
        if(impact){
            playerModel.setLocation(new Point2D.Double(playerModel.getLocation().getX() - xvelocity,
                    playerModel.getLocation().getY() - yvelocity));
            count ++;
        }if(count == 50){
            impact = false;
            count = 0;
        }
        return 0;
    }

    @Override
    public void move(double velocity) {
        if(!panel.isVictory()) {
            if (dForce || uForce || u0Force || d0Force) {
                if (playerModel.getLocation().getY() > 0 &&
                        playerModel.getLocation().getY() + BALL_SIZE <= panel.getHeight()) {
                    playerModel.setLocation(addVector(playerModel.getLocation(), new Point2D.Double(0, velocity)));
                }
                if (playerModel.getLocation().getY() + BALL_SIZE > panel.getHeight()) {
                    playerModel.setLocation(
                            new Point2D.Double(playerModel.getLocation().getX(), panel.getHeight() - BALL_SIZE - 7));
                    yvelocity = 0;
                } else if (playerModel.getLocation().getY() < 2) {
                    playerModel.setLocation(
                            new Point2D.Double(playerModel.getLocation().getX(), 7));
                    yvelocity = 0;
                }
                updateLoc();
                impact = false;
            }
            if (rForce || lForce || l0Force || r0Force) {
                if (playerModel.getLocation().getX() > 0 &&
                        playerModel.getLocation().getX() + BALL_SIZE <= panel.getWidth()) {
                    playerModel.setLocation(addVector(playerModel.getLocation(), new Point2D.Double(velocity, 0)));
                }
                if (playerModel.getLocation().getX() + BALL_SIZE > panel.getWidth()) {
                    playerModel.setLocation(
                            new Point2D.Double(panel.getWidth() - BALL_SIZE - 7, playerModel.getLocation().getY()));
                    xvelocity = 0;
                } else if (playerModel.getLocation().getX() < 2) {
                    playerModel.setLocation(
                            new Point2D.Double(7, playerModel.getLocation().getY()));
                    xvelocity = 0;

                }
                updateLoc();
                impact = false;
            }
        }
    }

    @Override
    public double getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public Point2D getLoc() {
        return loc;
    }

    @Override
    public void findPlayer() {

    }

    @Override
    public void setImpact(boolean impact) {
        this.impact = impact;
    }


    @Override
    public int[] getxPoints() {
        return new int[0];
    }

    @Override
    public void setHp(int hp) {

    }

    @Override
    public int getHp() {
        return 0;
    }

    @Override
    public int[] getyPoints() {
        return new int[0];
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
    public void setXvelocity(double xvelocity) {
        this.xvelocity = xvelocity;
    }

    public void setYvelocity(double yvelocity) {
        this.yvelocity = yvelocity;
    }


    public double getXvelocity() {
        return xvelocity;
    }

    public double getYvelocity() {
        return yvelocity;
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

}
