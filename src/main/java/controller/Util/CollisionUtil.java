package controller.Util;

import controller.DataBase;
import controller.Update;
import controller.model.BlackOrbCircles;
import model.characterModel.enemy.boss.BossModel;
import model.characterModel.enemy.boss.Lhand;
import model.characterModel.enemy.boss.Phand;
import model.characterModel.enemy.boss.Rhand;
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
import java.util.ArrayList;

import static controller.Controller.createCollectableView;
import static controller.Util.Util.*;
import static controller.constants.Constant.FRAME_DIMENSION;
import static controller.constants.Constant.MAX_SIZE;
import static controller.constants.EntityConstants.FINALBOSS_AOE_SIZE;
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
        if((r instanceof Rhand && !((Rhand) r).vulnerable) ||(r instanceof Lhand && !((Lhand) r).vulnerable)
        ||(r instanceof BossModel && !((BossModel) r).vulnerable)) {
        }else{
            Sound.sound().injured();
            r.setHp(r.getHp() - panelModel.power);
            dataBase.successShoots++;
            checkDeath(r);
        }
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
    public static void laserDamage(Movable r,BlackOrbModel b){
        r.setHp(r.getHp() - b.laserPower);
        checkDeath(r);
    }
    public static void checkLeftOmenocts(){
        for (int i = 0; i < panelModel.movables.size(); i++) {
            Movable o = panelModel.movables.get(i);
            if(o instanceof Omenoctmodel && o.getLoc().getX() <= 0 &&o.getLoc().getX()>= -5){
                injured(o);
            }
        }
    }
    public static void checkRightOmenocts(){
        for (int i = 0; i < panelModel.movables.size(); i++) {
            Movable o = panelModel.movables.get(i);
            if(o instanceof Omenoctmodel && o.getLoc().getX() + OMENOCT_SIZE >= panel.getWidth() -5 &&o.getLoc().getX() + OMENOCT_SIZE <= panel.getWidth() +5){
                injured(o);
            }
        }
    }
    public static void checkDownOmenocts(){
        for (int i = 0; i < panelModel.movables.size(); i++) {
            Movable o = panelModel.movables.get(i);
            if(o instanceof Omenoctmodel && o.getLoc().getY() + OMENOCT_SIZE >= panel.getHeight()-5&&o.getLoc().getY() + OMENOCT_SIZE <= panel.getHeight()+5){
                injured(o);
            }
        }
    }
    public static void checkTopOmenocts(){
        for (int i = 0; i < panelModel.movables.size(); i++) {
            Movable o = panelModel.movables.get(i);
            if(o instanceof Omenoctmodel && o.getLoc().getY() <= 0&&o.getLoc().getY()>=-5){
                injured(o);
            }
        }
    }

    public static void entityDeath(Movable m) {
        Sound.sound().death();
        dataBase.killedEnemies++;

        panelModel.movables.remove(m);
        if (m instanceof BossModel) {
            panelModel.movables.remove(((BossModel) m).r);
            panelModel.movables.remove(((BossModel) m).l);
            panelModel.movables.remove(((BossModel) m).p);
            removeEntity(((BossModel) m).l);
            removeEntity(((BossModel) m).p);
            removeEntity(((BossModel) m).r);
            update.phaseTwoVic();
            
        } 
        else if (m instanceof Rhand) panelModel.boss.r = null;
        else if (m instanceof Lhand) panelModel.boss.l = null;
        else if (m instanceof Phand) panelModel.boss.p = null;
        if (m instanceof NecropickModel) ((NecropickModel) m).timer.stop();
        removeEntity(m);
        death(m);
    }
    public static void astrape(Movable m){
        if(dataBase.astrape)m.setHp(m.getHp() - 2);
    }
    public static void bossAoe(){
        for (int i = 0; i < panelModel.boss.aoe.size(); i++) {
            Point2D a = new Point2D.Double(panelModel.boss.aoe.get(i).getX() + FINALBOSS_AOE_SIZE/2,
                    panelModel.boss.aoe.get(i).getY() + FINALBOSS_AOE_SIZE/2);
            if(distance(centerLoc(panelModel.playerModel),a)<=
                    FINALBOSS_AOE_SIZE/2 + panelModel.playerModel.size/2.0){
                panelModel.playerModel.counter++;
                if(panelModel.playerModel.counter >= 50){
                    reduceHp(panelModel.boss);
                    panelModel.playerModel.counter = 0;
                }
            }

        }
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
        if(movable instanceof BossModel)panelModel.playerModel.setHp(
                panelModel.playerModel.getHp() + 250);
       else {
            Enemy e = (Enemy) movable;
            int n = e.collectables;
            for (int i = 0; i < n; i++) {
                CollectableModel c = new CollectableModel(addVector(movable.getLoc(), new Point2D.Double(i * 4, i * 4)));
                c.setCreator(e);
                dataBase.collectableModels.add(c);
                panel.getDrawables().add(createCollectableView(c));
            }
        }
    }
    public static void reduceHp( Enemy movable){
        int w = movable.meleePower;
        if(w == 0)w = 5;
        panelModel.playerModel.setHp( panelModel.playerModel.getHp() - w);
        checkDeath(panelModel.playerModel);
    }
    private static void checkDeath(Movable r){
        if (r.getHp() <= 0) {
            if(r instanceof PlayerModel)update.gameOver();
            else entityDeath(r);
        }
        if(r instanceof BlackOrbModel){
            if(((BlackOrbModel) r).getCircles().isEmpty())entityDeath(r);
        }
    }

    public static void reduceHp(EnemyBullets bullet){
        int w = bullet.creator.rangedPower;
        panelModel.playerModel.setHp(panelModel.playerModel.getHp() - w);
        if( panelModel.playerModel.getHp() <= 0){
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
        panelModel.movables.remove(bulletModel);
    }
    public static void removeEnemyBullet(EnemyBullets e){
        for (int i = 0; i < panel.getDrawables().size(); i++) {
            if(panel.getDrawables().get(i) instanceof EnemyBulletView)
                if (panel.getDrawables().get(i).getId().equals(e.getId())) {
                    panel.getDrawables().remove(i);
                    break;
                }
        }
        panelModel.movables.remove(e);
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
