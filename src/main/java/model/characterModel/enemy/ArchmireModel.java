package model.characterModel.enemy;
import model.model.Enemy;
import model.model.GamePanelModel;
import model.movement.Collidable;
import model.movement.Movable;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.UUID;
import static controller.Util.Util.*;
import static controller.constants.AttackConstants.*;
import static controller.constants.CollectableConstans.*;
public class ArchmireModel extends Enemy implements Movable, Collidable {
    private int hp = 30;
    private String id;
    private Point2D loc;
    private int speed = 1;
    private double xvelocity;
    private double yvelocity;
    private int drown;
    private int aoe;
    public GamePanelModel mainPanel;
    public ArchmireModel() {
        id = UUID.randomUUID().toString();
        createArch();
    }
    private void createArch(){
        collectables = arch;
        collectablesXp = arch_xp;
        aoe = ARC_AOE;
        drown = DROWN;
        loc = setEntityLoc2();
    }
    public int getHp() {
        return hp;
    }
    @Override
    public int[] getyPoints() {return new int[0];}
    public void setHp(int hp) {
        this.hp = hp;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    private ArrayList<Point2D> trace = new ArrayList<>();
    @Override
    public boolean rigidBody() {return false;}

    @Override
    public boolean solid() {
        return false;
    }


    @Override
    public int move() {
        if(move) {
            m = Math.atan2((playerModel.getLoc().getY() - loc.getY()), (playerModel.getLoc().getX() - loc.getX()));
            xvelocity = (Math.cos(m)) * speed;
            yvelocity = (Math.sin(m)) * speed;
            loc = new Point2D.Double(loc.getX() + xvelocity, loc.getY() + yvelocity);
            trace.add(loc);
            if (trace.size() > 200) trace.remove(0);
        }
        return 0;}
    @Override
    public void move(double velocity) {}
    @Override
    public void setPanelW(double panelW) {}
    @Override
    public boolean collides() {return false;}
    @Override
    public boolean doesMeleeAtack() {return false;}
    @Override
    public void setPanelH(double panelH) {}
    @Override
    public void setSpeed(double speed) {}
    @Override
    public void setXvelocity(double xvelocity) {}
    @Override
    public void setYvelocity(double yvelocity) {}
    @Override
    public double getXvelocity() {return 0;}
    @Override
    public double getYvelocity() {return 0;}
    public Point2D getLoc() {
        return loc;
    }
    @Override
    public void setImpact(boolean impact) {}
    @Override
    public int[] getxPoints() {return new int[0];}
    public void setLoc(Point2D loc) {
        this.loc = loc;
    }
    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public void setXvelocity(int xvelocity) {
        this.xvelocity = xvelocity;
    }
    public void setYvelocity(int yvelocity) {
        this.yvelocity = yvelocity;
    }
    public int getDrown() {
        return drown;
    }
    public int getAoe() {
        return aoe;
    }

    public ArrayList<Point2D> getTrace() {
        return trace;
    }
}