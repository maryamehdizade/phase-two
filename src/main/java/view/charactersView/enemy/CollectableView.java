package view.charactersView.enemy;

import view.drawable.Drawable;

import java.awt.*;
import java.awt.geom.Point2D;

import static controller.constants.EntityConstants.COLLECTABLE_SIZE;

public class CollectableView implements Drawable {
    private Point2D loc;
    String id;

    public CollectableView(String ID, Point2D loc) {
        id = ID;
        this.loc = loc;
    }

    public void draw(Graphics g){
        g.setColor(Color.white);
        g.drawOval((int) loc.getX(), (int) loc.getY(), COLLECTABLE_SIZE, COLLECTABLE_SIZE);
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
