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

import static controller.constants.EntityConstants.SMILEY_DRAW_SIZE;

public class BossView implements Drawable {
    private Image img;
    private int hp;
    private String id;
    public lHandView l;
    public rHandView r;
    public pHandView p;
    private Point2D loc = new Point2D.Double();
    public BossView(Image i,String id, Point2D l){
        this.id = id;
        loc = l;
        img = i;
    }

    @Override
    public void draw(Graphics g) {
//        System.out.println(loc);
        g.drawImage(img,(int)loc.getX(),(int)loc.getY(),(int)SMILEY_DRAW_SIZE.getX(),(int)SMILEY_DRAW_SIZE.getY(),null);

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
