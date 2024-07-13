package model.characterModel.enemy;

import model.model.Enemy;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.UUID;

public class CollectableModel {
    private Enemy creator;


    private Point2D loc;
    String id;
    private int second = 0;
    public Timer timer ;


    public CollectableModel(Point2D loc) {
        this.loc = loc;
        this.id = UUID.randomUUID().toString();
        timer = new Timer(1000,e -> second++);
        timer.start();
    }

    public Point2D getLoc() {
        return loc;
    }

    public String getId() {
        return id;
    }

    public int getSecond() {
        return second;
    }
    public Enemy getCreator() {
        return creator;
    }

    public void setCreator(Enemy creator) {
        this.creator = creator;
    }

}
