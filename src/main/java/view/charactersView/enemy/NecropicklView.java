package view.charactersView.enemy;

import view.drawable.Drawable;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Objects;

import static controller.constants.EntityConstants.NECROPICK_SIZE;

public class NecropicklView implements Drawable {
    private Point2D loc;
    private int hp;
    private String id;
    private int[] xPoints;
    private int[] yPoints;
    public boolean visible;
    public Point2D preLoc = new Point2D.Double();

    public NecropicklView(Point2D loc, String id) {
        this.loc = loc;
        this.id = id;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.gray);
        if(!visible && !Objects.equals(preLoc, new Point2D.Double(0, 0))){
            g.fillOval((int) preLoc.getX(), (int) preLoc.getY(), (int) NECROPICK_SIZE.getY(), (int) NECROPICK_SIZE.getX());
        }
        if(visible) {
            g.fillRect((int) loc.getX(), (int) loc.getY(), (int) NECROPICK_SIZE.getX(), (int) NECROPICK_SIZE.getY());
            g.fillRect((int) (loc.getX() + NECROPICK_SIZE.getX() / 2 - NECROPICK_SIZE.getY() / 2),
                    (int) loc.getY(), (int) NECROPICK_SIZE.getY(), (int) NECROPICK_SIZE.getX());
            g.setColor(Color.black);
            g.drawString(String.valueOf(hp), (int) (loc.getX() + NECROPICK_SIZE.getX() / 2 - NECROPICK_SIZE.getY() / 2),
                    (int) (loc.getY() + NECROPICK_SIZE.getY()));

        }
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
