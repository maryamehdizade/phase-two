package model.model;

import model.characterModel.PlayerModel;

import java.awt.geom.Point2D;

public class Enemy {
    public PlayerModel playerModel = PlayerModel.getPlayer();
    public double xvelocity;
    public boolean move;
    public double yvelocity;
    public double counter;
    public int collectables;
    public int collectablesXp;
    public int meleePower;
    public int rangedPower;
    public double speed = 1;
    public double m;
    public boolean solid;
    public void findPlayer(Point2D loc){
        m = Math.atan2((playerModel.getLocation().getY() - loc.getY()),(playerModel.getLocation().getX() - loc.getX()));
        setVel();
    }
    void setVel(){
        xvelocity += ((Math.cos(m) * 2) * speed -  xvelocity)/80;
        yvelocity += ((Math.sin(m) * 2) * speed -  yvelocity)/80;
    }
}
