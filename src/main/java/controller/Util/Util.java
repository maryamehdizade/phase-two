package controller.Util;

import controller.model.BlackOrbCircles;
import model.characterModel.enemy.BlackOrbModel;
import model.model.GamePanelModel;
import model.characterModel.BulletModel;
import model.characterModel.PlayerModel;
import model.characterModel.enemy.EnemyBullets;
import model.characterModel.enemy.RectangleModel;
import model.characterModel.enemy.TriangleModel;
import model.movement.Movable;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

import static controller.constants.Constant.*;
import static controller.constants.EntityConstants.*;

public class Util {
    public static GamePanelModel model;
    public static Polygon getPolygon(BlackOrbModel b, int i, int j) {
        BlackOrbCircles c = b.getCircles().get(i);
        BlackOrbCircles c1 = b.getCircles().get(j);
        int n = (int) (c.getLoc().getX() - c1.getLoc().getX())%11;
        int m = (int) (c.getLoc().getY() - c1.getLoc().getY())%11;

        int[] x = new int[]{(int) (centerLoc(c).getX() - m),
                (int) (centerLoc(c).getX() + m),
                (int) (centerLoc(c1).getX() + m),
                (int) (centerLoc(c1).getX() -m)};
        int[] y = new int[]{(int) (centerLoc(c).getY() + n),
                (int) (centerLoc(c).getY() - n),
                (int) (centerLoc(c1).getY() - n) ,
                (int) (centerLoc(c1).getY() +n)};
        return new Polygon(x,y,4);
    }

    public Util(GamePanelModel model) {
        this.model = model;
    }

    public static Point2D setEntityLoc(){
        double x;
        double y;
        double r = Math.floor(Math.random()*2);
        Random random = new Random();
        if(r == 0) {
            x = random.nextDouble(model.getLoc().getX(),model.getDimension().getWidth());
            if(Math.floor(Math.random()*2) == 0){
                y = 0 ;
            }else{
                y =model.getDimension().getHeight();
            }
        }else {
            y = random.nextDouble(0, model.getDimension().getHeight());
            if(Math.floor(Math.random()*2) == 0){
                x = 0;
            }else{
                x = model.getDimension().getWidth();
            }

        }
        return new Point2D.Double(x, y);
    }
    public static Point2D setEntityLoc2(){
        double x;
        double y;
        double r = Math.floor(Math.random()*2);
        Random random = new Random();
        if(r == 0) {
            x = random.nextDouble(-model.getLoc().getX(),FRAME_DIMENSION.getWidth() - model.getLoc().getX());
            if(Math.floor(Math.random()*2) == 0){
                y = 0 ;
            }else{
                y =500;
            }
        }else {
            y = random.nextDouble(-model.getLoc().getY(),FRAME_DIMENSION.getHeight() - model.getLoc().getY());
            if(Math.floor(Math.random()*2) == 0){
                x = 0;
            }else{
                x = 800;
            }

        }
        return new Point2D.Double(x, y);
    }
    public static Point2D setLoc(){
        Random random = new Random();
        return new Point2D.Double(random.nextDouble(120,model.getDimension().getWidth() - 150)
                ,random.nextDouble(120,model.getDimension().getHeight() - 150));
    }
    public static boolean bulletIsOutSideOfFrame(EnemyBullets e, GamePanelModel panel){
    return e.getLoc().getX() + panel.getLoc().getX() <= 10 ||
            e.getLoc().getY() + panel.getLoc().getY() >= FRAME_DIMENSION.getHeight() - 10;
    }
    public static boolean bulletIsOutSideOfPanel(EnemyBullets e, GamePanelModel panel){
        return e.getLoc().getX() <= 0 || e.getLoc().getY()>= panel.getDimension().getHeight() ||
                e.getLoc().getX() >= panel.getDimension().getWidth() || e.getLoc().getY() >= panel.getDimension().getHeight();
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

    public static Point2D bulletCenter(BulletModel t) {
        return new Point2D.Double(t.getLoc().getX() + BULLET_SIZE/2.0, t.getLoc().getY() + BULLET_SIZE/2.0);
    }
    public static Point2D centerLoc(Movable movable){
        return new Point2D.Double(movable.getLoc().getX() + movable.size()/2.0, movable.getLoc().getY() + movable.size()/2.0);
    }
    public static Point2D multiplyVector(Point2D a, Point2D b){
        return new Point2D.Double(a.getX()*b.getX(), a.getY()*b.getY());
    }
}
