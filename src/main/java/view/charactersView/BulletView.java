package view.charactersView;

import view.pages.GamePanel;

import java.awt.*;
import java.awt.geom.Point2D;

import static controller.Constant.BULLET_SIZE;

public class BulletView {
    private Point2D loc;
    private double dx;
    private double dy;
    private String id;

    GamePanel panel;

    public BulletView(String id, Point2D loc, double dx, double dy, GamePanel panel) {

        this.panel = panel;
        this.id = id;
        this.loc = loc;
        this.dx = dx;
        this.dy = dy;

    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval((int)loc.getX(), (int)loc.getY(), BULLET_SIZE, BULLET_SIZE);
    }

    public void setLoc(Point2D loc) {
        this.loc = loc;
    }

    public String getId() {
        return id;
    }
}
