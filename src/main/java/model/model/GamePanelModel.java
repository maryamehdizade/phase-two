package model.model;

import model.characterModel.PlayerModel;
import model.characterModel.enemy.boss.BossModel;
import model.movement.Movable;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import static controller.constants.AttackConstants.EPSILON_POWER;
import static controller.constants.Constant.MIN_SIZE;
import static controller.constants.EntityConstants.BALL_SIZE;

public class GamePanelModel {

    String id;
    Point loc;
    public boolean victory = false;
    public boolean wave1 = true;
    public int heal = 0;
    public boolean start = false;
    public boolean wave2 = false;
    public boolean wave3 = false;
    public boolean empower = false;
    public int wave = 1;


    public int enemies = 0;

    public int skillCount ;
    public BossModel boss;
    public int power;
    public boolean isometric;
    public boolean rigidBody;
    private Dimension dimension;
    public PlayerModel playerModel;
    public ArrayList<Movable> movables;
    public double shrinkageSpeed = 1;

    public GamePanelModel(Point location, Dimension dimension, boolean rigidBody, boolean isometric) {
        movables = new ArrayList<>();
        playerModel = PlayerModel.getPlayer();
        movables.add(playerModel);

        this.rigidBody = rigidBody;
        this.isometric= isometric;
        power = EPSILON_POWER;
        this.dimension = dimension;
        this.id = UUID.randomUUID().toString();
        this.loc = location;


    }
    public void shrinkage(){
        xmin();
        ymin();
    }
    public void xmin(){
        if( getDimension().width > MIN_SIZE.width) {
             getDimension().width -= (int) shrinkageSpeed;
            if( getLoc().getX() < MIN_SIZE.getWidth())  getLoc().setLocation( getLoc().getX() + shrinkageSpeed/2,  getLoc().getY() );
        }
        if( playerModel.getLocation().getX() + BALL_SIZE >  getDimension().getWidth()){
            playerModel.setLocation(
                    new Point2D.Double( getDimension().getWidth() - BALL_SIZE , playerModel.getLocation().getY()));
        }else if( playerModel.getLocation().getX()  < 2){
            playerModel.setLocation(
                    new Point2D.Double(5, playerModel.getLocation().getY()));

        }

    }
    public void ymin(){
        if( getDimension().height > MIN_SIZE.height) {
             getDimension().height -= (int) shrinkageSpeed;
            if( getLoc().getY() < MIN_SIZE.getHeight())  getLoc().setLocation( getLoc().getX(),  getLoc().getY() + shrinkageSpeed/2);
        }
        if ( playerModel.getLocation().getY() + BALL_SIZE>  getDimension().getHeight()) {
            playerModel.setLocation(
                    new Point2D.Double( playerModel.getLocation().getX(),  getDimension().getHeight() - BALL_SIZE - 5));
        }else if( playerModel.getLocation().getY() < 2){
            playerModel.setLocation(
                    new Point2D.Double( playerModel.getLocation().getX(),  5));
        }
    }

    public String getId() {
        return id;
    }

    public Point getLoc() {
        return loc;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setLoc(Point loc) {
        this.loc = loc;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}
