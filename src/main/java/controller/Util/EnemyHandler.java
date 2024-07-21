package controller.Util;

import controller.DataBase;
import controller.Update;
import model.characterModel.enemy.boss.*;
import model.model.GamePanelModel;
import model.characterModel.enemy.*;
import model.movement.Movable;
import sound.Sound;
import view.charactersView.boss.BossView;
import view.charactersView.boss.lHandView;
import view.charactersView.boss.rHandView;
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
                update.d = true;
                BossModel n = new BossModel();

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
                BossView b = (BossView) createEnemyView(n);
                panel.getDrawables().add(b);
                dataBase.getGamePanelModel().movables.add(n);
//                panel.getDrawables().add(createEnemyView(n.p));
//                dataBase.getGamePanelModel().movables.add(n.p);

                rHandView r = (rHandView) createEnemyView(n.r);
                b.r = r;
                panel.getDrawables().add(r);
                dataBase.getGamePanelModel().movables.add(n.r);
                lHandView l = (lHandView) createEnemyView(n.l);
                b.l = l;
                panel.getDrawables().add(l);
                dataBase.getGamePanelModel().movables.add(n.l);
                dataBase.boss = n;

                panelModel.enemies ++;
            }
            panelModel.start = true;
        }
    }
}
