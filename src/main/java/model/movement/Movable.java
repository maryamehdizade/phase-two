package model.movement;

import model.characterModel.BulletModel;
import model.characterModel.PlayerModel;
import model.characterModel.enemy.*;

import java.awt.geom.Point2D;

import static controller.constants.EntityConstants.*;

public interface Movable {
    boolean rigidBody();
    boolean solid();
    int move();

    void move(double velocity);


    void setPanelW(double panelW);
    default boolean isCircular(){
        return getxPoints().length == 0;
    }
    default int size(){
        switch (this) {
            case PlayerModel playerModel -> {
                return (int) playerModel.size;
            }
            case BulletModel model -> {
                return BULLET_SIZE;
            }
            case RectangleModel rectangleModel -> {
                return RECT_SIZE;
            }
            case TriangleModel triangleModel -> {
                return TRI_SIZE;
            }
            case Omenoctmodel omenoctmodel -> {
                return OMENOCT_SIZE;
            }
            case NecropickModel necropickModel -> {
                return (int) NECROPICK_SIZE.getX();
            }
            case ArchmireModel archmireModel ->{
                return ARCH_SIZE;
            }
            case WyrmModel wyrmModel ->{
                return WRYM_SIZE;
            }
            default -> {
                return 0;
            }
        }

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
