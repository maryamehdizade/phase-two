package controller.Util;

import controller.DataBase;
import controller.Update;
import model.characterModel.enemy.boss.*;
import model.model.GamePanelModel;
import model.characterModel.enemy.*;
import model.movement.Movable;
import sound.Sound;
import view.pages.Game;
import view.pages.GamePanel;

import java.util.Random;

import static controller.Controller.createEnemyView;

public class EnemyHandler {
    private static DataBase dataBase;
    private static GamePanelModel panelModel;
    private static GamePanel panel;
    private static int bound;
    static Random random = new Random();
    private static Update update;

    public EnemyHandler(Update update) {
        this.update = update;
        dataBase = update.dataBase;
        panelModel = dataBase.getGamePanelModel();
        panel = update.panel;
        bound = update.bound;

    }

    public static void addingEnemies(){
        if (random.nextDouble(0, bound) < 1) {
            if((panelModel.wave == 1 && panelModel.enemies <= 10) || (panelModel.wave == 2 && panelModel.enemies <= 15) ||
                    (panelModel.wave == 3 && panelModel.enemies <= 20)) {
                Sound.sound().entrance();

//                FinalBoss n = new FinalBoss();
                Lhand b = new Lhand();
                Rhand c= new Rhand();
                Phand p = new Phand();
                BossModel n = new BossModel();
//                FinalBoss boss = new FinalBoss(b, c, p, n);

//                if(Game.getGame().getPhase() == 0) {
//                    if (panelModel.random.nextInt(0, 2) == 1) {
//                        n = new TriangleModel();
//                    } else {
//                        n = new RectangleModel();
//                    }
//                }else{
//                    if (panelModel.random.nextInt(0, 2) == 1)
//                        n = new Omenoctmodel();
//                    else if (panelModel.random.nextInt(0, 2) == 1)
//                        n = new NecropickModel();
//                    else if (panelModel.random.nextInt(0, 2) == 1)
//                        n = new ArchmireModel();
//                    else if (panelModel.random.nextInt(0, 2) == 1)
//                        n = new WyrmModel();
//                    else n = new BarricadosModel();
//                }

                panel.getDrawables().add(createEnemyView(n));
                dataBase.getGamePanelModel().movables.add(n);
//                panel.getDrawables().add(createEnemyView(n.p));
//                dataBase.getGamePanelModel().movables.add(n.p);
//                panel.getDrawables().add(createEnemyView(n.r));
//                dataBase.getGamePanelModel().movables.add(n.r);
//                panel.getDrawables().add(createEnemyView(n.l));
//                dataBase.getGamePanelModel().movables.add(n.l);
                dataBase.boss = n;

                panelModel.enemies ++;
            }
            panelModel.start = true;
        }
    }
}
