package view.charactersView.enemy;

import view.drawable.Drawable;

import java.awt.*;
import java.awt.geom.Point2D;

import static controller.constants.EntityConstants.ARCH_SIZE;

public class ArchmireView implements Drawable {
    private Point2D loc;
    private String id;
    private int hp;
    private int traceDuration = 2000;

    public ArchmireView(Point2D loc, String id) {
        this.loc = loc;
        this.id = id;
    }

    @Override
    public void draw(Graphics g) {
        for (int i = 1; i < traceDuration/10; i++) {
            int alpha =(traceDuration/10)/i;
            alpha = Math.min(220, alpha);
            g.setColor(new Color(157, 11, 32, alpha));
            g.fillOval((int) (loc.getX() - i), (int) (loc.getY() - i), ARCH_SIZE,ARCH_SIZE);

        }

        g.setColor(new Color(122, 8, 25));
        g.fillOval((int) loc.getX(), (int) loc.getY(),ARCH_SIZE,ARCH_SIZE);
        g.setColor(Color.black);
        g.drawString(String.valueOf(hp), (int) (loc.getX() + ARCH_SIZE/2), (int) (loc.getY() + ARCH_SIZE/2));
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
