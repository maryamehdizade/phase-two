package view.drawable;

import java.awt.*;
import java.awt.geom.Point2D;

public interface Drawable {
    public void draw(Graphics g);
    String getId();
    void setLoc(Point2D loc);
    void setHp(int hp);
    void setxPoints(int[] xPoints);
    void setyPoints(int[] yPoints);
//    void setSize(double size);
    void setXp(int xp);
}
