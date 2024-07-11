package model.movement.Util;

import controller.DataBase;
import controller.Update;
import model.GamePanelModel;
import model.characterModel.BulletModel;
import model.characterModel.enemy.*;
import model.movement.Movable;
import sound.Sound;
import view.charactersView.BulletView;
import view.pages.GamePanel;

import java.awt.*;
import java.awt.geom.Point2D;

import static controller.Controller.createCollectableView;
import static controller.Util.Util.addVector;
import static controller.constants.AttackConstants.*;
import static controller.constants.EntityConstants.OMENOCT_SIZE;

public class CollisionUtil {
    private static GamePanel panel;
    private static GamePanelModel panelModel;
    private static DataBase dataBase;
    static Update update;
    public CollisionUtil(Update update){
        this.update = update;
        this.panel = update.panel;
        dataBase = DataBase.getDataBase();
        panelModel = dataBase.getGamePanelModel();

    }
    public static void injured(Movable r){
        Sound.sound().injured();
        r.setHp(r.getHp() - panelModel.power);
        if (r.getHp() <= 0) {
            entityDeath(r);
        }
    }
    public static void checkLeftOmenocts(){
        for (int i = 0; i < dataBase.movables.size(); i++) {
            Movable o = dataBase.movables.get(i);
            if(o instanceof Omenoctmodel && o.getLoc().getX() < 1){
                injured(o);
            }
        }
    }
    public static void checkRightOmenocts(){
        for (int i = 0; i < dataBase.movables.size(); i++) {
            Movable o = dataBase.movables.get(i);
            if(o instanceof Omenoctmodel && o.getLoc().getX() + OMENOCT_SIZE + 5 > panel.getWidth()){
                injured(o);
            }
        }
    }
    public static void checkDownOmenocts(){
        for (int i = 0; i < dataBase.movables.size(); i++) {
            Movable o = dataBase.movables.get(i);
            if(o instanceof Omenoctmodel && o.getLoc().getY() + OMENOCT_SIZE + 5 > panel.getHeight()){
                injured(o);
            }
        }
    }
    public static void checkTopOmenocts(){
        for (int i = 0; i < dataBase.movables.size(); i++) {
            Movable o = dataBase.movables.get(i);
            if(o instanceof Omenoctmodel && o.getLoc().getY() < 1){
                injured(o);
            }
        }
    }

    private static void entityDeath(Movable m) {
        Sound.sound().death();
        dataBase.movables.remove(m);
        removeEntity(m);
        death(m);
    }
    private static void removeEntity(Movable m){
        for (int i = 0; i < panel.getDrawables().size(); i++) {
            if(panel.getDrawables().get(i).getId().equals(m.getId())){
                panel.getDrawables().remove(i);
                break;
            }
        }
    }
    private static void death(Movable movable){
        Enemy e = (Enemy) movable;
        int n = e.collectables;
        for (int i = 0; i < n; i++) {
            CollectableModel c = new CollectableModel(addVector(movable.getLoc(), new Point2D.Double(i*6, i *6)));
            c.setCreator(e);
            dataBase.collectableModels.add(c);
            panel.getDrawables().add(createCollectableView(c));
        }
    }
    public static void reduceHp( Enemy movable){
        int w = movable.meleePower;
        dataBase.playerModel.setHp( dataBase.playerModel.getHp() - w);
        if( dataBase.playerModel.getHp() <= 0){
            update.gameOver();
        }
    }
    public static void removeBullet(int i) {

//        panel.getDrawables().remove(i);
        for (int j = 0; j < panel.getDrawables().size(); j++) {
            if(panel.getDrawables().get(j) instanceof BulletView)
                if (panel.getDrawables().get(j).getId().equals(dataBase.movables.get(i).getId())) {
                    panel.getDrawables().remove(j);
                    break;
                }
        }
        dataBase.movables.remove(i);
    }
    public static void removeBullet(BulletModel bulletModel){
        for (int i = 0; i <  dataBase.movables.size(); i++) {
            if(dataBase.movables.get(i) instanceof BulletModel)
                if( dataBase.movables.get(i).getId().equals(bulletModel.getId()))
                    removeBullet(i);
        }
    }

    private static final int n = 20;
    public static void moveLeft(){
        panelModel.setLoc(new Point((int) (panelModel.getLoc().getX() - n), (int) panelModel.getLoc().getY()));
        panelModel.setDimension(new Dimension((int) (panelModel.getDimension().getWidth() + n/2),
                (int) panelModel.getDimension().getHeight()));

    }
    public static void moveRight(){
        panelModel.setLoc(new Point((int) (panelModel.getLoc().getX() + n/2), (int) panelModel.getLoc().getY()));
        panelModel.setDimension (new Dimension((int) (panelModel.getDimension().getWidth() + n),
                (int) panelModel.getDimension().getHeight()));
    }
    public static void moveUp(){
        panelModel.setLoc(new Point((int) panelModel.getLoc().getX(), (int) (panelModel.getLoc().getY()- n)));
        panelModel.setDimension (new Dimension((int) panelModel.getDimension().getWidth(),
                (int) (panelModel.getDimension().getHeight()+ n/2)));

    }
    public static void moveDown(){
        panelModel.setLoc(new Point((int) panelModel.getLoc().getX(), (int) (panelModel.getLoc().getY() +  n/2)));
        panelModel.setDimension (new Dimension((int) panelModel.getDimension().getWidth(),
                (int) (panelModel.getDimension().getHeight()+ n)));
    }

}
