package view.charactersView.boss;

import view.drawable.Drawable;

import java.awt.*;
import java.awt.geom.Point2D;

import static controller.constants.EntityConstants.*;

public class rHandView implements Drawable {
    private Image img;
    private int hp;
    private String id;
    private Point2D loc = new Point2D.Double();
    public rHandView(Image image,String id,Point2D loc){
        this.img = image;
        this.loc = loc;
        this.id = id;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(img,(int)loc.getX(),(int)loc.getY(),(int)R_HAND_SIZE.getX(),(int)R_HAND_SIZE.getY(),null);
        g.drawString(String.valueOf(hp), (int) (loc.getX() +R_HAND_SIZE.getX()/2), (int) (loc.getY() + R_HAND_SIZE.getY()/2));
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
