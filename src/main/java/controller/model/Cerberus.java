package controller.model;

import javax.swing.*;
import java.awt.geom.Point2D;

public class Cerberus extends Point2D.Double {
    private boolean inRest;
    private double x;
    private double y;
    int restTime;
    Timer t;
    boolean started = true;

    public Cerberus(double x, double y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    @Override
    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean isInRest() {
        return inRest;
    }

    public void setInRest(boolean inRest) {
        this.inRest = inRest;
        if(inRest){
            started = false;
            t = new Timer(1000,e -> {
                restTime++;
                if(restTime >= 15){
                    t.stop();
                    this.inRest = false;
                    restTime = 0;
                    started = true;
                }
            });
            t.start();
        }

    }
}
