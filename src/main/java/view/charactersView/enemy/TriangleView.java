package view.charactersView.enemy;

import view.drawable.Drawable;

import java.awt.*;
import java.awt.geom.Point2D;

import static controller.constants.EntityConstants.TRI_SIZE;

public class TriangleView implements Drawable {
    private double x1, y1, x2, y2, x3, y3;
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

    public TriangleView(String id, double x1, double y1, double x2, double y2, double x3, double y3) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
        this.id = id;
    }

    public void draw(Graphics g) {
        g.setColor(Color.yellow);
        g.drawPolygon(xPoints, yPoints, 3);
        g.drawString(String.valueOf(hp), ((xPoints[0] + xPoints[2] + xPoints[2]) / 3) - TRI_SIZE/5,
                ((yPoints[0] + yPoints[1] + yPoints[2]) / 3) +  TRI_SIZE/5);
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }

    public void setX3(double x3) {
        this.x3 = x3;
    }

    public void setY3(double y3) {
        this.y3 = y3;
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
