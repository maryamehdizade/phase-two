package view.charactersView.enemy;

import view.drawable.Drawable;

import java.awt.*;
import java.awt.geom.Point2D;

import static controller.constants.EntityConstants.WRYM_SIZE;

public class WyrmView implements Drawable {
    private Point2D loc;
    private int hp;
    private String id;

    public WyrmView(String id, Point2D loc) {
        this.id = id;
        this.loc = loc;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(23,56,90));
        g.drawOval((int) loc.getX(), (int) loc.getY(),WRYM_SIZE,WRYM_SIZE);
        g.drawOval((int) (loc.getX()+WRYM_SIZE/3), (int) (loc.getY() + WRYM_SIZE/5),
                WRYM_SIZE/2,WRYM_SIZE/2);
        g.drawString(String.valueOf(hp), (int) (loc.getX() + WRYM_SIZE/3 + 5),
                (int) (loc.getY() + WRYM_SIZE/5 + 5));
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
        this.hp = hp;
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
