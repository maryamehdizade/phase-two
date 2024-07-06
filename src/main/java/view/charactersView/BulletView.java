package view.charactersView;

import view.drawable.Drawable;

import java.awt.*;
import java.awt.geom.Point2D;

import static controller.constants.EntityConstants.BULLET_SIZE;


public class BulletView implements Drawable {
    private Point2D loc;
    private String id;


    public BulletView(String id, Point2D loc) {

        this.id = id;
        this.loc = loc;

    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval((int)loc.getX(), (int)loc.getY(), BULLET_SIZE, BULLET_SIZE);
    }

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

    public String getId() {
        return id;
    }

}
