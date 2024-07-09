package view.charactersView.enemy;

import view.drawable.Drawable;

import java.awt.*;
import java.awt.geom.Point2D;

import static controller.constants.EntityConstants.TRI_SIZE;

public class TriangleView implements Drawable {

    private int hp;
    private String id;
    private int[] xPoints = new int[3];
    private int[] yPoints = new int[3];


    public void setxPoints(int[] xPoints) {
        this.xPoints = xPoints;
    }

    public void setyPoints(int[] yPoints) {
        this.yPoints = yPoints;
    }


    @Override
    public void setXp(int xp) {

    }

    public TriangleView(String id) {
        this.id = id;
    }

    public void draw(Graphics g) {
        g.setColor(Color.yellow);
        g.drawPolygon(xPoints, yPoints, 3);
        g.drawString(String.valueOf(hp), ((xPoints[0] + xPoints[2] + xPoints[2]) / 3) - TRI_SIZE/5,
                ((yPoints[0] + yPoints[1] + yPoints[2]) / 3) +  TRI_SIZE/5);
    }


    public void setHp(int hp) {
        this.hp = hp;
    }

    public String getId() {
        return id;
    }

    @Override
    public void setLoc(Point2D loc) {

    }

}
