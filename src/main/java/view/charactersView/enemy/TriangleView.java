package view.charactersView.enemy;

import java.awt.*;

import static controller.Constant.TRI_SIZE;

public class TriangleView {
    private double x1, y1, x2, y2, x3, y3;
    private int hp;
    private String id;

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
        int[] xPoints = {(int) x1, (int) x2, (int) x3};
        int[] yPoints = {(int) y1, (int) y2, (int) y3};
        g.setColor(Color.yellow);
        g.drawPolygon(xPoints, yPoints, 3);
        g.drawString(String.valueOf(hp), (int) ((x1 + x2 + x3) / 3) - TRI_SIZE/5, (int) ((y1 + y2 + y3) / 3) +  TRI_SIZE/5);
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

}
