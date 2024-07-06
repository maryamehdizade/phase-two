package controller.listner;

import controller.DataBase;
import controller.Update;
import model.characterModel.BulletModel;
import view.pages.GamePanel;
import view.pages.Menu;
import view.pages.Store;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static controller.Controller.createBulletView;
import static controller.Util.playerCenter;

public class MyListner implements KeyListener, MouseListener {
    DataBase dataBase;
    GamePanel panel;
//    Update update = Update.getUpdate();
    
    public MyListner(GamePanel panel){
        this.panel = panel;
        dataBase = DataBase.getDataBase();
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_A) {
            dataBase.playerModel.setlForce(true);
        } else if (keyCode == KeyEvent.VK_D) {
             dataBase.playerModel.setrForce(true);
        } else if (keyCode == KeyEvent.VK_W) {
             dataBase.playerModel.setuForce(true);
        } else if (keyCode == KeyEvent.VK_S) {
             dataBase.playerModel.setdForce(true);
        }
        if(keyCode == KeyEvent.VK_SPACE){
//            update.model.stop();
//            update.view.stop();
//            update.time.stop();
            new Store(panel);
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_D) {
             dataBase.playerModel.setrForce(false);
             dataBase.playerModel.setR0Force(true);
        }
        if (keyCode == KeyEvent.VK_A) {
             dataBase.playerModel.setlForce(false);
             dataBase.playerModel.setL0Force(true);
        }
        if (keyCode == KeyEvent.VK_S) {
             dataBase.playerModel.setdForce(false);
             dataBase.playerModel.setD0Force(true);
        }
        if (keyCode == KeyEvent.VK_W) {
             dataBase.playerModel.setuForce(false);
             dataBase.playerModel.setU0Force(true);
        }
        if(keyCode == KeyEvent.VK_P){
            if(Menu.getMenu().proteus){
                if(panel.proteusCount == 0) {
                    if( dataBase.playerModel.getXp() >= 100) {
                         dataBase.playerModel.setXp( dataBase.playerModel.getXp() -100);
                        panel.handler.proteus = true;
                        panel.proteusCount ++;
                         dataBase.playerModel.setLevelUp( dataBase.playerModel.getLevelUp() + 1);
                        panel.setProteus(true);
                    }
                }
//                todo
            }
        }
        if(keyCode == KeyEvent.VK_C){
            if(Menu.getMenu().aceso){
                if(panel.acesoCount == 0) {
                    if( dataBase.playerModel.getXp() >= 100) {
                         dataBase.playerModel.setXp( dataBase.playerModel.getXp() -100);
                        panel.handler.aceso = true;
                        panel.heal ++;
                        panel.acesoCount ++;

                    }
                }
            }
        }
        if(keyCode == KeyEvent.VK_R){
            if(Menu.getMenu().ares){
                if(panel.aresCount == 0) {
                    if( dataBase.playerModel.getXp() >= 100) {
                         dataBase.playerModel.setXp( dataBase.playerModel.getXp() -100);
                        panel.setPower(panel.getPower() + 2);
                        panel.handler.ares = true;
                        panel.aresCount++;
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
        if(panel.empower)n = 2;
        for (int i = -1; i < n; i++) {
            BulletModel model = new BulletModel(playerCenter( dataBase.playerModel), targetX + i * n * 10,
                    targetY + i * n * 10);
            dataBase.movables.add(model);
            panel.getDrawables().add(createBulletView(model));
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
