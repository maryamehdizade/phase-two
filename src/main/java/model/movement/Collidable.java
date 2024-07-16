package model.movement;

import model.characterModel.BulletModel;
import model.characterModel.PlayerModel;
import model.characterModel.enemy.ArchmireModel;
import model.model.Enemy;
import model.characterModel.enemy.EnemyBullets;
import model.characterModel.enemy.WyrmModel;

import java.awt.*;
import java.awt.geom.Point2D;

import static controller.Util.Util.*;
import static controller.constants.EntityConstants.ARCH_SIZE;
import static controller.constants.ImpactConstants.IMPACT_RANGE;
import static model.movement.Impact.impact;
import static controller.Util.CollisionUtil.*;

public interface Collidable {
    default void collision(Movable m) {
        Movable n = (Movable) this;
        if (m instanceof ArchmireModel) {
            if ( m!=n) {
                if (distance(centerLoc(n), centerLoc(m)) <= Math.abs(n.size()/2)) {
                    if(n instanceof Enemy) {
                        Enemy e = (Enemy) n;
                        e.counter += 0.1;

                        if (e.counter % 10 >= 0 && e.counter % 10 < 0.1) {
                            drownDamage(n, (ArchmireModel) m);
                        }
                        if (distance(centerLoc(n), centerLoc(m)) > Math.abs(n.size() / 2))
                            e.counter = 0;
                    }
                    else if(n instanceof PlayerModel){
                        PlayerModel e = (PlayerModel) n;
                        e.counter += 0.1;

                        if (e.counter % 10 >= 0 && e.counter % 10 < 0.1) {
                            drownDamage(n, (ArchmireModel) m);
                        }
                        if (distance(centerLoc(n), centerLoc(m)) > Math.abs(n.size() / 2))
                            e.counter = 0;
                    }
                }else if(n instanceof BulletModel){
                    if (distance(centerLoc(n), centerLoc(m)) <= m.size() / 2.0 + n.size() / 2.0) {

                        //reduce m hp and remove bullet
                        removeBullet((BulletModel) n);
                        injured(m);
                        impact(centerLoc(n),IMPACT_RANGE);
                    }
                }
                else {
                    for (int i = 0; i < ((ArchmireModel) m).getTrace().size(); i += m.size()/2) {
                        boolean a = distance(addVector(((ArchmireModel) m).getTrace().get(i), new Point2D.Double(m.size() / 2,
                                        m.size() / 2)), centerLoc(n)) <= Math.abs(n.size()/2);

                        if (a) {
                            if(n instanceof Enemy) {
                                Enemy e = (Enemy) n;
                                e.counter += 0.1;
                                if (e.counter % 10 >= 0 && e.counter % 10 < 0.1) {
                                    aoeDamage(n, (ArchmireModel) m);
                                }
                                if (distance(addVector(((ArchmireModel) m).getTrace().get(i), new Point2D.Double(ARCH_SIZE / 2,
                                                ARCH_SIZE / 2)), centerLoc(n)) > Math.abs(n.size() / 2))
                                    e.counter = 0;
                            }else{
                                PlayerModel e = (PlayerModel) n;
                                e.counter += 0.1;
                                if (e.counter % 10 >= 0 && e.counter % 10 < 0.1) {
                                    aoeDamage(n, (ArchmireModel) m);
                                }
                                if (distance(addVector(((ArchmireModel) m).getTrace().get(i), new Point2D.Double(ARCH_SIZE / 2,
                                                ARCH_SIZE / 2)), centerLoc(n)) > Math.abs(n.size() / 2))
                                    e.counter = 0;
                            }
                        }
                    }
                }
            }
        } else {
            if (m != n) {
                if (n.isCircular()) {
                    if (m.isCircular()) {
                        if (n instanceof BulletModel) {
                            if (m instanceof Enemy) {
                                if (distance(centerLoc(n), centerLoc(m)) <= m.size() / 2.0 + n.size() / 2.0) {

                                    //reduce m hp and remove bullet
                                    removeBullet((BulletModel) n);
                                    injured(m);
                                    impact(collisionPoint(centerLoc(m), centerLoc(n)),IMPACT_RANGE);
                                }
                            }
                        } else if (!(m instanceof BulletModel)) {
                            if (m.collides() && n.collides()) {
                                if (distance(centerLoc(n), centerLoc(m)) <= m.size() / 2.0 + n.size() / 2.0) {
                                    //impact

                                    impact(collisionPoint(centerLoc(m), centerLoc(n)), IMPACT_RANGE);
                                }
                            }
                        }
                    } else {
                        if (n instanceof BulletModel) {
                            if (m instanceof Enemy) {
                                Polygon t = new Polygon(m.getxPoints(), m.getyPoints(), m.getyPoints().length);
                                if (t.contains(centerLoc(n))) {
                                    //impact and reduce m hp

                                    removeBullet((BulletModel) n);
                                    injured(m);
                                    impact(centerLoc(n), IMPACT_RANGE);

                                }
                            }
                        } else {
                            for (int i = 0; i < m.getxPoints().length; i++) {
                                if (distance(centerLoc(n),
                                        new Point2D.Double(m.getxPoints()[i], m.getyPoints()[i])) <= n.size() / 2.0) {
                                    if (m instanceof PlayerModel || n instanceof PlayerModel) {
                                        if (m.doesMeleeAtack()) {
                                            //reduce n hp
                                            if (n instanceof PlayerModel) reduceHp((Enemy) m);
                                            else if(!(n instanceof WyrmModel))injured(n);
                                        }
                                    }
                                    impact(new Point2D.Double(m.getxPoints()[i], m.getyPoints()[i]), IMPACT_RANGE);
                                } else if (distance(centerLoc(m), centerLoc(n)) <= m.size() / 2.0 + n.size() / 2.0) {
                                    impact(collisionPoint(centerLoc(m), centerLoc(n)), IMPACT_RANGE);
                                }
                            }

                        }
                    }
                } else {
                    if (!m.isCircular()) {

                        if (m.collides() && n.collides()) {
                            boolean impact = false;
                            Point2D collisionPoint = new Point2D.Double(0, 0);
                            if (!(n instanceof PlayerModel)) {
                                Polygon p = new Polygon(n.getxPoints(), n.getyPoints(), n.getyPoints().length);
                                for (int i = 0; i < m.getyPoints().length; i++) {
                                    if (p.contains(m.getxPoints()[i], m.getyPoints()[i])) {
                                        if (m instanceof PlayerModel) {
                                            //reduce n hp
                                            if(!(n instanceof WyrmModel))injured(n);
                                        }
                                        impact = true;
                                        collisionPoint = new Point2D.Double(m.getxPoints()[i], m.getyPoints()[i]);
                                    }
                                }
                            } else {
                                for (int i = 0; i < m.getyPoints().length; i++) {
                                    if (distance(centerLoc(n), new Point2D.Double(m.getxPoints()[i], m.getyPoints()[i])) <= ((PlayerModel) n).size / 2.0) {
                                        //reduce n hp
                                        reduceHp((Enemy) m);
                                        impact = true;
                                        collisionPoint = new Point2D.Double(m.getxPoints()[i], m.getyPoints()[i]);
                                    } else if (distance(centerLoc(m), centerLoc(n)) <= m.size() / 2.0 + ((PlayerModel) n).size / 2.0) {
                                        impact = true;
                                        collisionPoint = collisionPoint(centerLoc(m), centerLoc(n));
                                    }

                                }
                            }
                            if (!(m instanceof PlayerModel)) {
                                Polygon p = new Polygon(m.getxPoints(), m.getyPoints(), m.getyPoints().length);
                                for (int i = 0; i < n.getyPoints().length; i++) {
                                    if (p.contains(n.getxPoints()[i], n.getyPoints()[i])) {
                                        if (n instanceof PlayerModel) {
                                            //reduce m hp
                                            if(!(m instanceof WyrmModel))injured(m);
                                        }
                                        impact = true;
                                        collisionPoint = new Point2D.Double(n.getxPoints()[i], n.getyPoints()[i]);
                                    }
                                }
                            } else {
                                for (int i = 0; i < n.getyPoints().length; i++) {
                                    if (distance(centerLoc(m), new Point2D.Double(n.getxPoints()[i], n.getyPoints()[i])) <= m.size() / 2.0) {
                                        reduceHp((Enemy) n);
                                        impact = true;
                                        collisionPoint = new Point2D.Double(n.getxPoints()[i], n.getyPoints()[i]);
                                    } else if (distance(centerLoc(m), centerLoc(n)) <= m.size() / 2.0 + n.size() / 2.0) {
                                        impact = true;
                                        collisionPoint = collisionPoint(centerLoc(m), centerLoc(n));
                                    }


                                }
                            }
                            if (impact) {
                                impact(collisionPoint, IMPACT_RANGE);
                            }
                        }
                    }
                }
            }

            if (n instanceof EnemyBullets && m instanceof PlayerModel) {
                if (m.isCircular()) {
                    if (distance(centerLoc(m), centerLoc(n)) <= m.size() + n.size()) {
                        removeEnemyBullet((EnemyBullets) n);
                        reduceHp((EnemyBullets) n);
                        impact(centerLoc(n),IMPACT_RANGE);
                    }
                }
            }
        }
    }

}
