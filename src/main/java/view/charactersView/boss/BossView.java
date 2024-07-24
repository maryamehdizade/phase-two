package view.charactersView.boss;

import model.characterModel.enemy.boss.BossModel;
import model.characterModel.enemy.boss.Lhand;
import model.characterModel.enemy.boss.Phand;
import model.characterModel.enemy.boss.Rhand;
import view.drawable.Drawable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static controller.constants.EntityConstants.FINALBOSS_AOE_SIZE;
import static controller.constants.EntityConstants.SMILEY_DRAW_SIZE;

public class BossView implements Drawable {
    private Image img;
    private int hp;
    private String id;
    public lHandView l;
    public rHandView r;
    public pHandView p;
    public ArrayList<Point2D> aoe = new ArrayList<>();
    private Point2D loc;
    private double size;
    public BossView(Image i,String id, Point2D l){
        this.id = id;
        loc = l;
        img = i;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(img, (int) loc.getX(), (int) loc.getY(), (int) size + 110, (int)size+110, null);
        g.setColor(Color.black);
        g.drawString(String.valueOf(hp), (int) (loc.getX() + size+110 / 2 ), (int) (loc.getY() + size+110 / 2));

        for (Point2D p : aoe) {
            g.setColor(Color.pink);
            g.fillOval((int) p.getX(), (int) p.getY(), FINALBOSS_AOE_SIZE, FINALBOSS_AOE_SIZE);
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

    }

    @Override
    public void setyPoints(int[] yPoints) {

    }

    @Override
    public void setXp(int xp) {

    }

    public void setImg(Image img) {
        this.img = img;
    }
}
