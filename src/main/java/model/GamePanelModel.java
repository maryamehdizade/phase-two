package model;

import model.characterModel.PlayerModel;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;
import java.util.UUID;

import static controller.constants.AttackConstants.EPSILON_POWER;
import static controller.constants.Constant.MIN_SIZE;
import static controller.constants.EntityConstants.BALL_SIZE;

public class GamePanelModel {

    String id;
    Point loc;
    public boolean victory = false;
    public Random random = new Random();
    public boolean wave1 = true;
    public int heal = 0;
    public boolean proteus,ares,aceso;
    public boolean start = false;
    public boolean wave2 = false;
    public boolean wave3 = false;
    public boolean empower = false;
    public int wave = 1;
    public int phase, second;

    public int enemies = 0;
    public int aresCount ;
    public int acesoCount ;
    public int proteusCount ;
    public int power;
    private Dimension dimension;
   public PlayerModel playerModel;

    public GamePanelModel(Point location, Dimension dimension) {
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
             getDimension().width -= 1;
            if( getLoc().getX() < 200)  getLoc().setLocation( getLoc().getX() + 0.5,  getLoc().getY() );
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
             getDimension().height -= 1;
            if( getLoc().getY() < 200)  getLoc().setLocation( getLoc().getX(),  getLoc().getY() + 0.5);
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
