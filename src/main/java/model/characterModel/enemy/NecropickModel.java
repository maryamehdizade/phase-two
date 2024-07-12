package model.characterModel.enemy;

import model.characterModel.PlayerModel;
import model.movement.Collidable;
import model.movement.Movable;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.Random;
import java.util.UUID;

import static controller.Util.Util.centerLoc;
import static controller.Util.Util.distance;
import static controller.constants.CollectableConstans.*;
import static controller.constants.AttackConstants.NECRO_RANGED;
import static controller.constants.EntityConstants.NECROPICK_SIZE;

public class NecropickModel extends Enemy implements Movable, Collidable {
    private int hp = 10;
    public int sec;
    private int[] xPoints = new int[4];
    private int[] yPoints = new int[4];
    public boolean collides = true;
    private Point2D loc;
    private String id;
    public Timer timer;
    public int bullets;
    public boolean visible = true;

    public NecropickModel() {
        id = UUID.randomUUID().toString();
        createNecro();
        timer = new Timer(1000, e -> {
            sec++;
        });
        timer.start();
    }
    private void createNecro(){
        rangedPower = NECRO_RANGED;
        collectables = necro;
        collectablesXp = necro_xp;

        findPlayer(loc);
    }
    Random random = new Random();
    @Override
    public void findPlayer(Point2D loc) {
        double x;
        double y;
        double r = 200;

        while (true) {
            x = random.nextDouble(PlayerModel.getPlayer().getLoc().getX() - r, PlayerModel.getPlayer().getLoc().getX() + r);
            y = random.nextDouble(PlayerModel.getPlayer().getLoc().getY() - r, PlayerModel.getPlayer().getLoc().getY() + r);

            if (distance(new Point2D.Double(x, y), PlayerModel.getPlayer().getLoc()) >= 70) {
                this.loc = new Point2D.Double(x, y);

                xPoints = new int[]{(int) this.loc.getX(), (int) (this.loc.getX() + NECROPICK_SIZE.getX()),
                        (int) this.loc.getX(), (int) (this.loc.getX() + NECROPICK_SIZE.getX())};
                yPoints = new int[]{(int) this.loc.getY(), (int) this.loc.getY(),
                        (int) (this.loc.getY() + NECROPICK_SIZE.getX()), (int) (this.loc.getY() + NECROPICK_SIZE.getX())};
                break;
            }
        }
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public int getHp() {
        return hp;
    }

    @Override
    public int[] getxPoints() {
        return xPoints;
    }

    @Override
    public int[] getyPoints() {
        return yPoints;
    }

    @Override
    public Point2D getLoc() {
        return loc;
    }

    @Override
    public void setImpact(boolean impact) {

    }

    @Override
    public boolean rigidBody() {
        return true;
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
        return collides;
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
        return xvelocity;
    }

    @Override
    public double getYvelocity() {
        return yvelocity;
    }


    @Override
    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setxPoints(int[] xPoints) {
        this.xPoints = xPoints;
    }

    public void setyPoints(int[] yPoints) {
        this.yPoints = yPoints;
    }
}
