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
    static Random random = new Random();
    private static Update update;

    public EnemyHandler(Update update) {
        this.update = update;
        dataBase = update.dataBase;
        panelModel = dataBase.getGamePanelModel();
        panel = update.panel;

    }

    public static void addingEnemies(){
        if (random.nextDouble(0, dataBase.bound) < 1) {
            Movable n = null;
            if (dataBase.wave != 6) {
                if (Game.getGame().getPhase() == 0) {
                    if ((panelModel.wave == 1 && panelModel.enemies <= 10) || (panelModel.wave == 2 && panelModel.enemies <= 15) ||
                            (panelModel.wave == 3 && panelModel.enemies <= 20)) {
                        switch ((int) (Math.random() * 2)) {
                            case 0 -> n = new TriangleModel();
                            case 1 -> n = new RectangleModel();
                        }
                    }
                } else {
                    switch ((int) (Math.random() * 6)) {
                        case 0 -> n = new Omenoctmodel();
                        case 1 -> n = new NecropickModel();
                        case 2 -> n = new ArchmireModel();
                        case 3 -> n = new WyrmModel();
                        case 4 -> {
                            if (dataBase.wave >= 3 && Math.random() <= 0.4) n = new BarricadosModel();
                        }
                        case 5 -> {
                            if (dataBase.wave >= 3) n = new BlackOrbModel();
                        }
                    }
                }
                if (n != null) {
                    panel.getDrawables().add(createEnemyView(n));
                    dataBase.getGamePanelModel().movables.add(n);
                    Sound.sound().entrance();
                }
            } else {
                if (!update.d) {
                    update.d = true;
                    n = new BossModel();
                    BossView b = (BossView) createEnemyView(n);
                    panel.getDrawables().add(b);
                    dataBase.getGamePanelModel().movables.add(n);

                    rHandView r = (rHandView) createEnemyView(((BossModel) n).r);
                    b.r = r;

                    panel.getDrawables().add(r);
                    dataBase.getGamePanelModel().movables.add(((BossModel) n).r);

                    lHandView l = (lHandView) createEnemyView(((BossModel) n).l);
                    b.l = l;

                    panel.getDrawables().add(l);
                    dataBase.getGamePanelModel().movables.add(((BossModel) n).l);
                    dataBase.getGamePanelModel().boss = (BossModel) n;
                }
            }
            panelModel.enemies++;
            panelModel.start = true;
        }
    }
}
