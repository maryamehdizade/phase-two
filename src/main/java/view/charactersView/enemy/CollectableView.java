package view.charactersView.enemy;

import java.awt.*;
import java.awt.geom.Point2D;

import static controller.Constant.COLLECTABLE_SIZE;

public class CollectableView {
    private Point2D loc;
    String id;

    public CollectableView(String ID, Point2D loc) {
        this.loc = loc;
    }

    public void draw(Graphics g){
        g.setColor(Color.white);
        g.drawOval((int) loc.getX(), (int) loc.getY(), COLLECTABLE_SIZE, COLLECTABLE_SIZE);
    }
}
