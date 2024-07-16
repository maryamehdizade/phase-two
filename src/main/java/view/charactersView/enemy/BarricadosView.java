package view.charactersView.enemy;

import view.drawable.Drawable;

import java.awt.*;
import java.awt.geom.Point2D;

import static controller.constants.EntityConstants.BAR_SIZE;

public class BarricadosView implements Drawable {
    private Point2D loc;

    private String id;
    private int hp;

    public BarricadosView(String id, Point2D loc) {
        this.loc = loc;
        this.id = id;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect((int) loc.getX(), (int) loc.getY(),BAR_SIZE,BAR_SIZE);
        g.setColor(new Color(234, 105, 117));
        g.fillRect((int) loc.getX() + 5, (int) loc.getY() + 5,BAR_SIZE - 10,BAR_SIZE -10);

    }

    public String getId() {
        return id;
    }

    public void setLoc(Point2D loc) {
        this.loc = loc;
    }

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
