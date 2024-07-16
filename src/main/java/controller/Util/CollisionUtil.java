package controller.Util;

import controller.DataBase;
import controller.Update;
import model.model.GamePanelModel;
import model.characterModel.BulletModel;
import model.characterModel.PlayerModel;
import model.characterModel.enemy.*;
import model.model.Enemy;
import model.movement.Movable;
import sound.Sound;
import view.charactersView.BulletView;
import view.charactersView.enemy.EnemyBulletView;
import view.pages.GamePanel;

import java.awt.*;
import java.awt.geom.Point2D;

import static controller.Controller.createCollectableView;
import static controller.Util.Util.addVector;
import static controller.constants.Constant.FRAME_DIMENSION;
import static controller.constants.Constant.MAX_SIZE;
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
        checkDeath(r);
    }
    public static void aoeDamage(Movable r, ArchmireModel a){
        if(!(r instanceof PlayerModel))Sound.sound().injured();
        r.setHp(r.getHp() - a.getAoe());
        checkDeath(r);
    }
    public static void drownDamage(Movable r, ArchmireModel a){
        if(!(r instanceof PlayerModel))Sound.sound().injured();
        r.setHp(r.getHp() - a.getDrown());
        checkDeath(r);
    }
    public static void checkLeftOmenocts(){
        for (int i = 0; i < dataBase.getGamePanelModel().movables.size(); i++) {
            Movable o = dataBase.getGamePanelModel().movables.get(i);
            if(o instanceof Omenoctmodel && o.getLoc().getX() <= 0 &&o.getLoc().getX()>= -5){
                injured(o);
            }
        }
    }
    public static void checkRightOmenocts(){
        for (int i = 0; i < dataBase.getGamePanelModel().movables.size(); i++) {
            Movable o = dataBase.getGamePanelModel().movables.get(i);
            if(o instanceof Omenoctmodel && o.getLoc().getX() + OMENOCT_SIZE >= panel.getWidth() -5 &&o.getLoc().getX() + OMENOCT_SIZE <= panel.getWidth() +5){
                injured(o);
            }
        }
    }
    public static void checkDownOmenocts(){
        for (int i = 0; i < dataBase.getGamePanelModel().movables.size(); i++) {
            Movable o = dataBase.getGamePanelModel().movables.get(i);
            if(o instanceof Omenoctmodel && o.getLoc().getY() + OMENOCT_SIZE >= panel.getHeight()-5&&o.getLoc().getY() + OMENOCT_SIZE <= panel.getHeight()+5){
                injured(o);
            }
        }
    }
    public static void checkTopOmenocts(){
        for (int i = 0; i < dataBase.getGamePanelModel().movables.size(); i++) {
            Movable o = dataBase.getGamePanelModel().movables.get(i);
            if(o instanceof Omenoctmodel && o.getLoc().getY() <= 0&&o.getLoc().getY()>=-5){
                injured(o);
            }
        }
    }

    private static void entityDeath(Movable m) {
        Sound.sound().death();
        dataBase.getGamePanelModel().movables.remove(m);
        if(m instanceof NecropickModel)((NecropickModel) m).timer.stop();
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
            CollectableModel c = new CollectableModel(addVector(movable.getLoc(), new Point2D.Double(i*4, i *4)));
            c.setCreator(e);
            dataBase.collectableModels.add(c);
            panel.getDrawables().add(createCollectableView(c));
        }
    }
    public static void reduceHp( Enemy movable){
        int w = movable.meleePower;
        dataBase.getGamePanelModel().playerModel.setHp( dataBase.getGamePanelModel().playerModel.getHp() - w);
        checkDeath(dataBase.getGamePanelModel().playerModel);
    }
    private static void checkDeath(Movable r){
        if (r.getHp() <= 0) {
            if(r instanceof PlayerModel)update.gameOver();
            else entityDeath(r);
        }
    }

    public static void reduceHp(EnemyBullets bullet){
        int w = bullet.creator.rangedPower;
        dataBase.getGamePanelModel().playerModel.setHp(dataBase.getGamePanelModel().playerModel.getHp() - w);
        if( dataBase.getGamePanelModel().playerModel.getHp() <= 0){
            update.gameOver();
        }
    }

    public static void removeBullet(BulletModel bulletModel){
        for (int j = 0; j < panel.getDrawables().size(); j++) {
            if(panel.getDrawables().get(j) instanceof BulletView)
                if (panel.getDrawables().get(j).getId().equals(bulletModel.getId())) {
                    panel.getDrawables().remove(j);
                    break;
                }
        }
        dataBase.getGamePanelModel().movables.remove(bulletModel);
    }
    public static void removeEnemyBullet(EnemyBullets e){
        for (int i = 0; i < panel.getDrawables().size(); i++) {
            if(panel.getDrawables().get(i) instanceof EnemyBulletView)
                if (panel.getDrawables().get(i).getId().equals(e.getId())) {
                    panel.getDrawables().remove(i);
                    break;
                }
        }
        dataBase.enemyBullets.remove(e);
    }

    private static final int n = 20;
    public static void moveLeft(){
        if((panelModel.getLoc().getX() - n) >=0 && (panelModel.getDimension().getWidth() + n / 2.0) <= MAX_SIZE.getWidth()) {
            panelModel.setLoc(new Point((int) (panelModel.getLoc().getX() - n), (int) panelModel.getLoc().getY()));
            panelModel.setDimension(new Dimension((int) (panelModel.getDimension().getWidth() + n / 2),
                    (int) panelModel.getDimension().getHeight()));
        }

    }
    public static void moveRight(){
        if((panelModel.getLoc().getX() + n/2 + panelModel.getDimension().getWidth()) <= FRAME_DIMENSION.getWidth() &&
                (panelModel.getDimension().getWidth() + n) <=MAX_SIZE.getWidth()) {
            panelModel.setLoc(new Point((int) (panelModel.getLoc().getX() + n / 2), (int) panelModel.getLoc().getY()));
            panelModel.setDimension(new Dimension((int) (panelModel.getDimension().getWidth() + n),
                    (int) panelModel.getDimension().getHeight()));
        }
    }
    public static void moveUp(){
        if(panelModel.getLoc().getY()- n >= 0 && panelModel.getDimension().getHeight()+ n/2 <= MAX_SIZE.getHeight()) {
            panelModel.setLoc(new Point((int) panelModel.getLoc().getX(), (int) (panelModel.getLoc().getY() - n)));
            panelModel.setDimension(new Dimension((int) panelModel.getDimension().getWidth(),
                    (int) (panelModel.getDimension().getHeight() + n / 2)));
        }

    }
    public static void moveDown(){
        if(panelModel.getLoc().getY() +  n/2 + panelModel.getDimension().getHeight() <= FRAME_DIMENSION.getHeight() && panelModel.getDimension().getHeight()+ n<= MAX_SIZE.getHeight()) {
            panelModel.setLoc(new Point((int) panelModel.getLoc().getX(), (int) (panelModel.getLoc().getY() + n / 2)));
            panelModel.setDimension(new Dimension((int) panelModel.getDimension().getWidth(),
                    (int) (panelModel.getDimension().getHeight() + n)));
        }
    }

}
