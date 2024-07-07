package model.characterModel.enemy;

import model.characterModel.PlayerModel;

import java.awt.geom.Point2D;

public class Enemy {
    PlayerModel playerModel = PlayerModel.getPlayer();
    double xvelocity;
    double yvelocity;
    double speed = 1;
    double m;
    void findPlayer(Point2D loc){
        m = Math.atan2((playerModel.getLocation().getY() - loc.getY()),(playerModel.getLocation().getX() - loc.getX()));
        setVel();
    }
    void setVel(){
        xvelocity += ((Math.cos(m) * 2) * speed -  xvelocity)/80;
        yvelocity += ((Math.sin(m) * 2) * speed -  yvelocity)/80;
    }
}