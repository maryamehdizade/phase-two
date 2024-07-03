package view.charactersView.enemy;

import java.awt.*;
import java.awt.geom.Point2D;

import static controller.Constant.RECT_SIZE;

public class RectangleView {
    private int hp;
    private Point2D loc;
    String id;

    public RectangleView(String id, Point2D loc) {
        this.id = id;
        this.loc = loc;
    }

    public void draw(Graphics g) {
        g.setColor(Color.green);
        g.drawRect((int) loc.getX(), (int) loc.getY(), RECT_SIZE, RECT_SIZE);
        g.drawString(String.valueOf(hp), (int) loc.getX()+ RECT_SIZE/3, (int) loc.getY() + RECT_SIZE*2/3);
    }


    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setLoc(Point2D loc) {
        this.loc = loc;
    }

    public String getId() {
        return id;
    }

    public int getHp() {
        return hp;
    }
}
