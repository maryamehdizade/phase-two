package model.movement;

import controller.DataBase;
import model.characterModel.BulletModel;
import model.characterModel.PlayerModel;
import model.characterModel.enemy.Enemy;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import static controller.Util.*;

public interface Collidable {
    default void collision(Movable m) {
//        ArrayList<Movable> movables = DataBase.getDataBase().movables;
        int power = DataBase.getDataBase().getGamePanelModel().getPower();
        Movable n = (Movable) this;
        if (m != n) {
            if (n.isCircular()) {
                if (m.isCircular()) {
                    if (n instanceof BulletModel) {
                        if (m instanceof Enemy) {
                            if (distance(centerLoc(n), centerLoc(m)) <= m.size() / 2.0 + n.size() / 2.0) {
                                //reduce m hp and remove bullet
                            }
                        }
                    } else if (!(m instanceof BulletModel)) {
                        if (m.collides() && n.collides()) {
                            if (distance(centerLoc(n), centerLoc(m)) <= m.size() / 2.0 + n.size() / 2.0) {
                                //impact
                            }
                        }
                    }
                } else {
                    // m is enemy or player model   n can be bullet model or round enemy
                    if (n instanceof BulletModel) {
                        if (m instanceof Enemy) {
                            Polygon t = new Polygon(m.getxPoints(), m.getyPoints(), m.getyPoints().length);
                            if (t.contains(centerLoc(n))) {
                                //impact and reduce m hp
                            }
                        }
                    } else {
                        for (int i = 0; i < m.getxPoints().length; i++) {
                            if (distance(centerLoc(n),
                                    new Point2D.Double(m.getxPoints()[i], m.getyPoints()[i])) <= n.size() / 2.0) {
                                //impact
                                if (m instanceof PlayerModel || n instanceof PlayerModel) {
                                    if (m.doesMeleeAtack()) {
                                        //reduce n hp
                                    }
                                }
                            }
                        }

                    }
                }
            } else {
                if (!m.isCircular()) {

                    if(m.collides() && n.collides()){
                        boolean impact = false;
                        Polygon p = new Polygon(n.getxPoints(), n.getyPoints(), n.getyPoints().length);
                        for (int i = 0; i < m.getyPoints().length; i++) {
                            if(p.contains(m.getxPoints()[i], m.getyPoints()[i])){
                                if(m instanceof PlayerModel || n instanceof PlayerModel){
                                    //reduce n hp
                                }
                                impact = true;
                            }
                        }
                        p = new Polygon(m.getxPoints(), m.getyPoints(), m.getyPoints().length);
                        for (int i = 0; i < n.getyPoints().length; i++) {
                            if(p.contains(n.getxPoints()[i], n.getyPoints()[i])){
                                if(m instanceof PlayerModel || n instanceof PlayerModel){
                                    //reduce m hp
                                }
                                impact = true;
                            }
                        }
                        if(impact){
                            //impact
                        }
                    }
                }
            }
        }
    }
}
