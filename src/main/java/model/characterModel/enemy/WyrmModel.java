package model.characterModel.enemy;

import java.awt.geom.Point2D;
import java.util.UUID;

public class WyrmModel {
    private Point2D loc;
    private int hp = 15;
    private String id;
    private double xvelocity;
    private double yvelocity;

    public WyrmModel() {
        id = UUID.randomUUID().toString();

    }
    private void createWrym(){

    }
}
