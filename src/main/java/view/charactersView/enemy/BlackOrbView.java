package view.charactersView.enemy;

import controller.model.BlackOrbCircles;
import model.movement.Movable;
import view.drawable.Drawable;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import static controller.Util.Util.centerLoc;

public class BlackOrbView implements Drawable {
    private Point2D loc;
    private String id;
    private int hp;
    private ArrayList<BlackOrbCircles> circles;

    public BlackOrbView(Point2D loc, String id,ArrayList<BlackOrbCircles> circles) {
        this.circles = circles;
        this.loc = loc;
        this.id = id;
    }

    public void setLoc(Point2D loc) {
        this.loc = loc;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(120, 10, 222));
        for (int i = 0; i < circles.size(); i++) {
            for (int j = i + 1; j <circles.size() ; j++) {
                BlackOrbCircles c = circles.get(i);
                BlackOrbCircles c1 = circles.get(j);

                int n = (int) (c.getLoc().getX() - c1.getLoc().getX())%11;
                int m = (int) (c.getLoc().getY() - c1.getLoc().getY())%11;

                int[] x = new int[]{(int) (centerLoc(c).getX() - m),
                        (int) (centerLoc(c).getX() + m),
                        (int) (centerLoc(c1).getX() + m),
                        (int) (centerLoc(c1).getX() -m)};
                int[] y = new int[]{(int) (centerLoc(c).getY() + n),
                        (int) (centerLoc(c).getY() - n),
                        (int) (centerLoc(c1).getY() - n) ,
                        (int) (centerLoc(c1).getY() +n)};
                g.fillPolygon(x,y,4);
            }
        }

        for (int i = 0;i < circles.size();i++) {
            g.setColor(Color.black);
            g.fillOval((int) circles.get(i).getLoc().getX(), (int) circles.get(i).getLoc().getY(), circles.get(i).size(), circles.get(i).size());
            g.setColor(new Color(120, 10, 222));
            g.drawOval((int) circles.get(i).getLoc().getX(), (int) circles.get(i).getLoc().getY(), circles.get(i).size(), circles.get(i).size());
            g.drawString(String.valueOf(circles.get(i).getHp()), (int) centerLoc(circles.get(i)).getX(),
                    (int) centerLoc( circles.get(i)).getY());
        }
        
    }

    public String getId() {
        return id;
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

    public void setCircles(ArrayList<BlackOrbCircles> circles) {
        this.circles = circles;
    }
}
