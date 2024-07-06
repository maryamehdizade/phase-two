package view.charactersView;

import view.drawable.Drawable;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.UUID;

import static controller.Constant.BALL_SIZE;

public  class PlayerView implements Drawable {
    private int xp ;
    private int hp ;
    private final String id;
    private double size;
    private  Point2D  loc;
    private int[] xPoints = new int[0];
    private int[] yPoints = new int[0];

    public PlayerView(Point2D  loc) {
        this. loc =  loc;
        id = UUID.randomUUID().toString();
    }
    public void draw(Graphics g){
        g.setColor(Color.gray);
        g.drawOval((int)  loc.getX(), (int)  loc.getY(), (int) size, (int) size);

        double xloc = ( loc.getX() + size / 2);
        double yloc = ( loc.getY() + size / 2);

        for (int i = 0; i < xPoints.length; i++) {
            g.drawLine((int) xloc, (int) yloc,xPoints[i],yPoints[i]);
        }

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
        return "";
    }

    public void setxPoints(int[] xPoints) {
        this.xPoints = xPoints;
    }

    public void setyPoints(int[] yPoints) {
        this.yPoints = yPoints;
    }

    public Point2D getLoc() {
        return loc;
    }

    @Override
    public void setLoc(Point2D loc) {
        this.loc = loc;
    }

    public void setSize(double size) {
        this.size = size;
    }
}
