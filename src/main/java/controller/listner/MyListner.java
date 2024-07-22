package controller.listner;

import controller.DataBase;
import model.characterModel.BulletModel;
import model.characterModel.PlayerModel;
import model.characterModel.enemy.EnemyBullets;
import model.characterModel.enemy.boss.BossModel;
import model.characterModel.enemy.boss.Lhand;
import model.characterModel.enemy.boss.Phand;
import model.characterModel.enemy.boss.Rhand;
import model.movement.Movable;
import view.pages.GamePanel;
import view.pages.Menu;
import view.pages.SkillTree;
import view.pages.Store;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

import static controller.Controller.createBulletView;
import static controller.Util.Util.*;
import static model.movement.Impact.impact;

public class MyListner implements KeyListener, MouseListener {
    DataBase dataBase;
    GamePanel panel;

    public MyListner(GamePanel panel){
        this.panel = panel;
        dataBase = DataBase.getDataBase();
    }
    public static int v = 0;
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode() + v;
        if(v!= 0) {
            if (keyCode == 69) keyCode--;
            if (keyCode == 91) keyCode = 83;
            if (keyCode == 72) keyCode = 65;
        }
        if (keyCode == KeyEvent.VK_A) {
            dataBase.getGamePanelModel().playerModel.setlForce(true);
        } else if (keyCode == KeyEvent.VK_D) {
             dataBase.getGamePanelModel().playerModel.setrForce(true);
        } else if (keyCode == KeyEvent.VK_W) {
             dataBase.getGamePanelModel().playerModel.setuForce(true);
        } else if (keyCode == KeyEvent.VK_S) {
             dataBase.getGamePanelModel().playerModel.setdForce(true);
        }
        if(keyCode == KeyEvent.VK_SPACE){
            new Store(panel);
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_D) {
             dataBase.getGamePanelModel().playerModel.setrForce(false);
             dataBase.getGamePanelModel().playerModel.setR0Force(true);
        }
        if (keyCode == KeyEvent.VK_A) {
             dataBase.getGamePanelModel().playerModel.setlForce(false);
             dataBase.getGamePanelModel().playerModel.setL0Force(true);
        }
        if (keyCode == KeyEvent.VK_S) {
             dataBase.getGamePanelModel().playerModel.setdForce(false);
             dataBase.getGamePanelModel().playerModel.setD0Force(true);
        }
        if (keyCode == KeyEvent.VK_W) {
             dataBase.getGamePanelModel().playerModel.setuForce(false);
             dataBase.getGamePanelModel().playerModel.setU0Force(true);
        }
        if(keyCode == KeyEvent.VK_P){
//            if(Menu.getMenu().skills.get(String.valueOf(SkillTree.names.proteus))){
//                if(dataBase.getGamePanelModel().proteusCount == 0) {
//                    if( dataBase.getGamePanelModel().playerModel.getXp() >= 100) {
                         dataBase.getGamePanelModel().playerModel.setXp( dataBase.getGamePanelModel().playerModel.getXp() -100);
                        dataBase.handler.proteus = true;
                        dataBase.getGamePanelModel().proteusCount ++;
                         dataBase.getGamePanelModel().playerModel.setLevelUp( dataBase.getGamePanelModel().playerModel.getLevelUp() + 1);

//                    }
//                }
//            }
        }
        if(keyCode == KeyEvent.VK_C){
            if(Menu.getMenu().skills.get(String.valueOf(SkillTree.names.aceso))){
                if(dataBase.getGamePanelModel().acesoCount == 0) {
                    if( dataBase.getGamePanelModel().playerModel.getXp() >= 100) {
                         dataBase.getGamePanelModel().playerModel.setXp( dataBase.getGamePanelModel().playerModel.getXp() -100);
                        dataBase.handler.aceso = true;
                        dataBase.getGamePanelModel().heal ++;
                        dataBase.getGamePanelModel().acesoCount ++;
                    }
                }
            }else if(Menu.getMenu().skills.get(String.valueOf(SkillTree.names.aceso))){

            }else if(Menu.getMenu().skills.get(String.valueOf(SkillTree.names.aceso))){

            }
        }
        if(keyCode == KeyEvent.VK_R){
            if(Menu.getMenu().skills.get(String.valueOf(SkillTree.names.ares))){
                if(dataBase.getGamePanelModel().aresCount == 0) {
                    if( dataBase.getGamePanelModel().playerModel.getXp() >= 100) {
                         dataBase.getGamePanelModel().playerModel.setXp( dataBase.getGamePanelModel().playerModel.getXp() -100);
                        dataBase.getGamePanelModel().setPower(dataBase.getGamePanelModel().getPower() + 2);
                        dataBase.handler.ares = true;
                        dataBase.getGamePanelModel().aresCount++;
                    }
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int targetX = e.getX();
        int targetY = e.getY();
        int n = 0;
        if(dataBase.getGamePanelModel().empower)n = 2;
        for (int i = -1; i < n; i++) {
            BulletModel model = new BulletModel(playerCenter( dataBase.getGamePanelModel().playerModel), targetX + i * n * 10,
                    targetY + i * n * 10);
            dataBase.getGamePanelModel().movables.add(model);
            panel.getDrawables().add(createBulletView(model));
        }
        for (Movable m : DataBase.getDataBase().getGamePanelModel().movables) {
            if(m instanceof Phand || m instanceof Rhand|| m instanceof Lhand|| m instanceof BossModel){
                m.move(3);
            }
        }


    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
