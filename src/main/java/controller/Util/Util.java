package controller.Util;

import model.characterModel.BulletModel;
import model.characterModel.PlayerModel;
import model.characterModel.enemy.EnemyBullets;
import model.characterModel.enemy.RectangleModel;
import model.characterModel.enemy.TriangleModel;
import model.movement.Movable;
import view.pages.GamePanel;

import java.awt.geom.Point2D;
import java.util.Random;

import static controller.constants.Constant.*;
import static controller.constants.EntityConstants.*;

public class Util {
    public static Point2D setEntityLoc(){
        double x;
        double y;
        double r = Math.floor(Math.random()*2);
        Random random = new Random();
        if(r == 0) {
            x = random.nextDouble(20,500);
            if(Math.floor(Math.random()*2) == 0){
                y = 0 ;
            }else{
                y =500;
            }
        }else {
            y = random.nextDouble(20, 500);
            if(Math.floor(Math.random()*2) == 0){
                x = 0;
            }else{
                x = 800;
            }

        }
        return new Point2D.Double(x, y);
    }
    // collisions
    public static boolean bulletIsOutSideOfFrame(EnemyBullets e, GamePanel panel){
    return e.getLoc().getX() + panel.getLocation().getX() <= 10 ||
            e.getLoc().getY() + panel.getLocation().getY() >= FRAME_DIMENSION.getHeight() - 10;
    }
    public static Point2D collisionPoint(Point2D a, Point2D b){
        return new Point2D.Double((a.getX() + b.getX())/2.0, (a.getY() + b.getY())/2.0);
    }

    public static Point2D addVector(Point2D a, Point2D b) {
        return new Point2D.Double(a.getX() + b.getX(), a.getY() + b.getY());
    }
    public static double distance(double x , double y, double x1 , double y1){
        return Math.pow(Math.pow((x - x1), 2) + Math.pow((y - y1), 2), 0.5);
    }

    public static double distance(Point2D a, Point2D b){
        return Math.pow(Math.pow((a.getX() - b.getX()), 2) + Math.pow((a.getY() - b.getY()), 2), 0.5);
    }

    public static Point2D playerCenter(PlayerModel playerModel) {
        return new Point2D.Double(playerModel.getLocation().getX() + BALL_SIZE / 2.0, playerModel.getLocation().getY() + BALL_SIZE / 2.0);
    }

    public static Point2D rectCenter(RectangleModel rectangleModel) {
        return new Point2D.Double(rectangleModel.getLoc().getX() + RECT_SIZE / 2.0, rectangleModel.getLoc().getY() + RECT_SIZE / 2.0);
    }
    public static Point2D triangleCenter(TriangleModel t) {
        return new Point2D.Double((t.getX1() + t.getX2() + t.getX3())/3.0 , (t.getY1() + t.getY2() + t.getY3())/3.0);
    }
    public static Point2D bulletCenter(BulletModel t) {
        return new Point2D.Double(t.getLoc().getX() + BULLET_SIZE/2.0, t.getLoc().getY() + BULLET_SIZE/2.0);
    }
    public static Point2D centerLoc(Movable movable){
        return new Point2D.Double(movable.getLoc().getX() + movable.size()/2.0, movable.getLoc().getY() + movable.size()/2.0);
    }
}
