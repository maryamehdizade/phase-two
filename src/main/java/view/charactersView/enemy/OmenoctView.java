package view.charactersView.enemy;

import view.drawable.Drawable;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Arrays;

public class OmenoctView implements Drawable {

    private int hp;
    private Point2D loc;
    String id;
    private int[] xPoints = new int[8];
    private int[] yPoints = new int[8];

    public OmenoctView(String id, Point2D loc) {
        this.id = id;
        this.loc = loc;
    }


    @Override
    public void draw(Graphics g) {



        g.setColor(new Color(115, 10, 10));
        g.fillPolygon(xPoints,yPoints,8);

        g.setColor(Color.black);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.drawString(String.valueOf(hp), xPoints[0] , yPoints[3]);
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
this.xPoints = xPoints;
    }

    @Override
    public void setyPoints(int[] yPoints) {
this.yPoints = yPoints;
    }


    @Override
    public void setXp(int xp) {
    }
}
