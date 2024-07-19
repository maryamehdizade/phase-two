package view.charactersView.boss;
import view.drawable.Drawable;
import java.awt.*;
import java.awt.geom.Point2D;
import static controller.constants.EntityConstants.P_HAND_SIZE;

public class pHandView implements Drawable {
    private Image img;
    private int hp;
    private String id;
    private Point2D loc;
    public pHandView(String id){
        this.id = id;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(img,(int)loc.getX(),(int)loc.getY(),(int) P_HAND_SIZE.getX(),(int)P_HAND_SIZE.getY(),null);

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
