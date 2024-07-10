package model.movement.Util;

import controller.DataBase;
import controller.Update;
import model.GamePanelModel;
import model.characterModel.BulletModel;
import model.characterModel.enemy.CollectableModel;
import model.characterModel.enemy.Enemy;
import model.characterModel.enemy.RectangleModel;
import model.characterModel.enemy.TriangleModel;
import model.movement.Movable;
import sound.Sound;
import view.charactersView.BulletView;
import view.pages.GamePanel;

import java.awt.geom.Point2D;

import static controller.Controller.createCollectableView;
import static controller.Util.Util.addVector;
import static controller.constants.AttackConstants.*;

public class CollisionUtil {
    private static GamePanel panel;
    private static GamePanelModel panelModel;
    private static DataBase dataBase;
    static Update update;
    public CollisionUtil(){
        update = Update.getUpdate();
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
            CollectableModel c = new CollectableModel(addVector(movable.getLoc(), new Point2D.Double(i*10, i *10)));
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
}
