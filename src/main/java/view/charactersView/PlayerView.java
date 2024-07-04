package view.charactersView;

import java.awt.*;
import java.awt.geom.Point2D;

import static controller.Constant.BALL_SIZE;

public  class PlayerView {
    private int xp ;
    private int hp ;
    private final String id;
    public double size;
    private  Point2D location;
    private int[] xPoints = new int[0];
    private int[] yPoints = new int[0];

    public PlayerView(String id, Point2D location) {
        this.location = location;
        this.id = id;
    }
    public void draw(Graphics g){
        g.setColor(Color.gray);
        g.drawOval((int) location.getX(), (int) location.getY(), (int) size, (int) size);

        double xloc = (location.getX() + size / 2);
        double yloc = (location.getY() + size / 2);

        for (int i = 0; i < xPoints.length; i++) {
            g.drawLine((int) xloc, (int) yloc,xPoints[i],yPoints[i]);
        }

    }
    public void setLocation(Point2D location) {
        this.location = location;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public String getId() {
        return id;
    }

    public void setxPoints(int[] xPoints) {
        this.xPoints = xPoints;
    }

    public void setyPoints(int[] yPoints) {
        this.yPoints = yPoints;
    }
}
