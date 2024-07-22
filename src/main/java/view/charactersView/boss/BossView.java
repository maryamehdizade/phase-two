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
    public BossView(Image i,String id, Point2D l){
        this.id = id;
        loc = l;
        img = i;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(img, (int) loc.getX(), (int) loc.getY(), (int) SMILEY_DRAW_SIZE.getX(), (int) SMILEY_DRAW_SIZE.getY(), null);
        g.setColor(Color.black);
        g.drawString(String.valueOf(hp), (int) (loc.getX() + SMILEY_DRAW_SIZE.getX() / 2), (int) (loc.getY() + SMILEY_DRAW_SIZE.getY() / 2));

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
