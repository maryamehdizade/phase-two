package view.charactersView.boss;

import view.drawable.Drawable;

import java.awt.*;
import java.awt.geom.Point2D;

import static controller.constants.EntityConstants.L_HAND_SIZE;
import static controller.constants.EntityConstants.SMILEY_DRAW_SIZE;

public class lHandView implements Drawable {
    private Image img;
    private int hp;
    private String id;
    private Point2D loc = new Point2D.Double();

    public lHandView(Image img, String id, Point2D l) {
        this.img = img;
        loc = l;
        this.id = id;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(img,(int)loc.getX(),(int)loc.getY(),(int)L_HAND_SIZE.getX(),(int)L_HAND_SIZE.getY(),null);
        g.drawString(String.valueOf(hp), (int) (loc.getX() +L_HAND_SIZE.getX()/2), (int) (loc.getY() + L_HAND_SIZE.getY()/2));
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

    }

    @Override
    public void setyPoints(int[] yPoints) {

    }

    @Override
    public void setXp(int xp) {

    }
}
