package view.charactersView;

import controller.model.Cerberus;
import view.drawable.Drawable;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.UUID;

import static controller.constants.EntityConstants.SMALL_BALL_SIZE;

public  class PlayerView implements Drawable {
    private int xp ;
    private int hp ;
    private final String id;
    private double size;
    private  Point2D  loc;
    private int[] xPoints = new int[0];
    private int[] yPoints = new int[0];
    private ArrayList<Cerberus> cerberus = new ArrayList<>();

    public PlayerView(String id, Point2D  loc) {
        this. loc =  loc;
        this.id = id;
    }
    public void draw(Graphics g){
        g.setColor(Color.gray);
        g.drawOval((int)  loc.getX(), (int)  loc.getY(), (int) size, (int) size);


        for (int i = 0; i < xPoints.length; i++) {
            g.drawLine((int) (loc.getX() + size/2), (int) (loc.getY()+size/2),xPoints[i],yPoints[i]);
        }
        for (Point2D p:cerberus) {
            g.fillOval((int) p.getX(), (int) p.getY(),SMALL_BALL_SIZE,SMALL_BALL_SIZE);
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

    public void setCerberus(ArrayList<Cerberus> cerberus) {
        this.cerberus = cerberus;
    }
}
