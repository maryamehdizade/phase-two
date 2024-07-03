package model.movement;

import java.awt.geom.Point2D;

public interface Movable {
    int move();

    void move(double velocity);

    double getSpeed();

    void setSpeed(double speed);

    void setXvelocity(double xvelocity);

    void setYvelocity(double yvelocity);


    double getXvelocity();

    double getYvelocity();

    Point2D getLoc();

    void findPlayer();

    void setImpact(boolean impact);

    int[] getxPoints();

    void setHp(int hp);

    int getHp();

    int[] getyPoints();
}
