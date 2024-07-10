package model.movement;

import controller.DataBase;
import model.characterModel.BulletModel;
import model.characterModel.PlayerModel;
import model.characterModel.enemy.RectangleModel;
import model.characterModel.enemy.TriangleModel;

import java.awt.geom.Point2D;

import static controller.Util.Util.*;

public class Impact {
    private static DataBase dataBase = DataBase.getDataBase();
    private static int impactV = 4;
    public static void impact(Point2D point, double r){
        for (int i = 0; i < dataBase.movables.size(); i ++) {
            Movable m =  dataBase.movables.get(i);
            if (!(m instanceof BulletModel)) {
//                System.out.println(m.getLoc() + m.getId());
                double x = 0;
                double y = 0;
                if (m instanceof RectangleModel) {
                    x = Math.abs(rectCenter((RectangleModel) m).getX() - point.getX());
                    y = Math.abs(rectCenter((RectangleModel) m).getY() - point.getY());
                } else if (m instanceof TriangleModel) {
                    x = Math.abs(m.getLoc().getX() - point.getX());
                    y = Math.abs(m.getLoc().getY() - point.getY());
                } else if (m instanceof PlayerModel) {
                    x = Math.abs(playerCenter(((PlayerModel) m)).getX() - point.getX());
                    y = Math.abs(playerCenter(((PlayerModel) m)).getY() - point.getY());
                }
                if (x <= r && y <= r) {
                    double speed = distance(x, y, point.getX(), point.getY()) / 500;
                    m.setImpact(true);
                    setSpeed(point, m, impactV / speed);
                }
            }
        }
    }
    private static void setSpeed(Point2D point, Movable m, double impactV){
        double a = -point.getY() + m.getLoc().getY();
        double b = -point.getX() + m.getLoc().getX();

        if(b != 0){
            double angel = Math.atan(a/b);
            if(b < 0){
                m.setXvelocity(-impactV*Math.cos(angel));
                if(a < 0)m.setYvelocity(-impactV*Math.sin(angel));
                else m.setYvelocity(impactV*Math.sin(angel));
            }
            else{
                m.setXvelocity(impactV*Math.cos(angel));
                if(a < 0) m.setYvelocity(impactV*Math.sin(angel));
                else m.setYvelocity(-impactV*Math.sin(angel));
            }

        }else if(a >= 0){
            m.setYvelocity(impactV);
        }else if(a < 0){
            m.setYvelocity(-impactV);
        }
    }

}
