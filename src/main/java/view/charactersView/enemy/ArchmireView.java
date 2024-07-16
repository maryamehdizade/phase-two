package view.charactersView.enemy;

import model.characterModel.enemy.ArchmireModel;
import model.model.GamePanelModel;
import view.drawable.Drawable;
import view.pages.GamePanel;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import static controller.Util.Util.addVector;
import static controller.Util.Util.multiplyVector;
import static controller.constants.EntityConstants.ARCH_SIZE;

public class ArchmireView implements Drawable {
    private Point2D loc;
    private String id;
    private int hp;
//    private int traceDuration = 2000;
    private ArrayList<Point2D > trace;
    private GamePanel mainPanel;

    public ArchmireView(Point2D loc, String id) {
        this.loc = loc;
        this.id = id;
        trace = new ArrayList<>();
    }

    @Override
    public void draw(Graphics g) {
        for (int i = 1; i < trace.size(); i++) {
            int alpha =trace.size()/i;
            alpha = Math.min(180, alpha);
            g.setColor(new Color(190, 11, 32, alpha));
            g.fillOval((int) (trace.get(trace.size() - i - 1).getX()), (int) (trace.get(trace.size() - i - 1).getY()), ARCH_SIZE,ARCH_SIZE);

        }

        g.setColor(new Color(157, 11, 32));
        g.fillOval((int) loc.getX(), (int) loc.getY(),ARCH_SIZE,ARCH_SIZE);
        g.setColor(Color.black);
        g.drawString(String.valueOf(hp), (int) (loc.getX() + ARCH_SIZE/2), (int) (loc.getY() + ARCH_SIZE/2));
    }
    public ArchmireView clone(GamePanel panel){
        ArchmireView archmireView = new ArchmireView(loc,id);
        archmireView.loc = addVector(addVector(mainPanel.getLocation(), loc),
                multiplyVector(panel.getLocation(), new Point2D.Double(-1,-1)));

        archmireView.setHp(hp);

        for (int i = 0; i < trace.size(); i++) {
            archmireView.trace.add(addVector(addVector(mainPanel.getLocation(), trace.get(i)),
                    multiplyVector(panel.getLocation(), new Point2D.Double(-1,-1))));
        }
        new Thread(() -> {
            while(true){
                archmireView.loc = addVector(addVector(mainPanel.getLocation(), loc),
                        multiplyVector(panel.getLocation(), new Point2D.Double(-1,-1)));
            }
        }).start();
        return archmireView;
    }

    public void setTrace(ArrayList<Point2D> trace) {
        this.trace = trace;
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
