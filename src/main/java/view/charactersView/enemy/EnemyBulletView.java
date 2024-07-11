package view.charactersView.enemy;

import view.drawable.Drawable;

import java.awt.*;
import java.awt.geom.Point2D;

import static controller.constants.EntityConstants.ENEMY_BULLET_SIZE;

public class EnemyBulletView implements Drawable {
    private String id;
    private Point2D loc;

    public EnemyBulletView(String id, Point2D loc) {
        this.id = id;
        this.loc = loc;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(86, 10, 10));
        g.fillOval((int) loc.getX(), (int) loc.getY(),ENEMY_BULLET_SIZE,ENEMY_BULLET_SIZE);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setLoc(Point2D loc) {
this.loc = loc;
    }

    @Override
    public void setHp(int hp) {

    }

    @Override
    public void setxPoints(int[] xPoints) {

    }

    @Override
    public void setyPoints(int[] yPoints) {

    }

    @Override
    public void setXp(int xp) {

    }
}
