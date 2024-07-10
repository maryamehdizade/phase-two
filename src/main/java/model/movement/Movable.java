package model.movement;

import model.characterModel.BulletModel;
import model.characterModel.PlayerModel;
import model.characterModel.enemy.Omenoctmodel;
import model.characterModel.enemy.RectangleModel;
import model.characterModel.enemy.TriangleModel;

import java.awt.geom.Point2D;

import static controller.constants.EntityConstants.*;

public interface Movable {
    boolean solid();
    int move();

    void move(double velocity);


    void setPanelW(double panelW);
    default boolean isCircular(){
        return getxPoints().length == 0;
    }
    default int size(){
        if(this instanceof PlayerModel)return (int) ((PlayerModel) this).size;
        else if(this instanceof BulletModel)return BULLET_SIZE;
        else if(this instanceof RectangleModel)return RECT_SIZE;
        else if( this instanceof TriangleModel)return TRI_SIZE;
        else if(this instanceof Omenoctmodel)return OMENOCT_SIZE;
        return 0;
    }
    boolean collides();
    boolean doesMeleeAtack();
    void setPanelH(double panelH);

    void setSpeed(double speed);

    void setXvelocity(double xvelocity);

    void setYvelocity(double yvelocity);


    double getXvelocity();

    double getYvelocity();

    Point2D getLoc();



    void setImpact(boolean impact);

    int[] getxPoints();

    void setHp(int hp);

    int getHp();

    int[] getyPoints();
    String getId();
}
