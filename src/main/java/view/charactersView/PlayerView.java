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
    private int levelUp;
    private int[] xPoints;
    private int[] yPoints;

    public PlayerView(String id, Point2D location) {
        this.location = location;
        this.id = id;
    }
    public void draw(Graphics g){
        g.setColor(Color.gray);
        g.drawOval((int) location.getX(), (int) location.getY(), (int) size, (int) size);
        if(levelUp > 0){
            xPoints = new int[levelUp];
            yPoints = new int[levelUp];
            double degree = 360.0/levelUp;
            double xloc = (location.getX() + size / 2);
            double yloc = (location.getY() + size / 2);
            int count = 0;
            for (double i = 0; i < 360.0; i += degree) {
                double x = Math.cos(degree) * size;
                double y = Math.sin(degree) * size;
                xPoints[count] = (int) x;
                yPoints[count] = (int) y;
                count ++;
                //todo:fix
                g.drawLine((int) xloc, (int) yloc, (int) (x + xloc), (int) (y + yloc));
            }

//            g.drawPolygon(new int[]{(int) location.getX(), (int) (location.getX() + size/2), (int) (location.getX() + size)},
//                    new int[]{(int) (location.getY() + size / 2), (int) (location.getY() - size/ 3), (int) (location.getY() +
//                            size /2)}, 3);
//            g.setColor(Color.black);
//            g.fillOval((int) ((int) location.getX() + 1.0), (int) ((int) location.getY() + 1.0),
//                    (int) ((int) size - 1.2), (int) ((int) size  - 1.2));
//
//            g.fillOval((int) (location.getX() + 3*size /10), (int) (location.getY() - size/10), (int) (2*size/5), (int) (2*size/5));

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

    public int getLevelUp() {
        return levelUp;
    }

    public void setLevelUp(int levelUp) {
        this.levelUp = levelUp;
    }

    public int[] getxPoints() {
        return xPoints;
    }

    public int[] getyPoints() {
        return yPoints;
    }
}
