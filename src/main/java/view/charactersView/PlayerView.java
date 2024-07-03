package view.charactersView;

import java.awt.*;
import java.awt.geom.Point2D;

import static controller.Constant.BALL_SIZE;

public  class PlayerView {
    private int xp ;
    private int hp ;
    private final String id;
    public double size;
    private  Point2D location;
    private boolean levelUp;

    public PlayerView(String id, Point2D location) {
        this.location = location;
        this.id = id;
    }
    public void draw(Graphics g){
        g.setColor(Color.gray);
        g.drawOval((int) location.getX(), (int) location.getY(), (int) size, (int) size);
        if(levelUp){
            g.drawPolygon(new int[]{(int) location.getX(), (int) (location.getX() + size/2), (int) (location.getX() + size)},
                    new int[]{(int) (location.getY() + size / 2), (int) (location.getY() - size/ 3), (int) (location.getY() +
                            size /2)}, 3);
            g.setColor(Color.black);
            g.fillOval((int) ((int) location.getX() + 1.0), (int) ((int) location.getY() + 1.0),
                    (int) ((int) size - 1.2), (int) ((int) size  - 1.2));

            g.fillOval((int) (location.getX() + 3*size /10), (int) (location.getY() - size/10), (int) (2*size/5), (int) (2*size/5));
        }
    }

    public  Point2D getLocation() {
        return new Point2D.Double(location.getX() + BALL_SIZE/2.0,location.getY() + BALL_SIZE/2.0);
    }

    public void setLocation(Point2D location) {
        this.location = location;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public String getId() {
        return id;
    }

    public boolean isLevelUp() {
        return levelUp;
    }

    public void setLevelUp(boolean levelUp) {
        this.levelUp = levelUp;
    }
}
