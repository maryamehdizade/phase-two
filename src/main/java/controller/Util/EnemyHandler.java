package controller.Util;

import controller.DataBase;
import controller.Update;
import model.GamePanelModel;
import model.characterModel.enemy.*;
import model.movement.Movable;
import sound.Sound;
import view.pages.Game;
import view.pages.GamePanel;

import java.util.Random;

import static controller.Controller.createEnemyView;

public class EnemyHandler {
    private static DataBase dataBase;
    static boolean z = false;
    private static GamePanelModel panelModel;
    private static GamePanel panel;
    private static int bound;
    static Random random = new Random();

    public EnemyHandler(Update update) {
        dataBase = update.dataBase;
        panelModel = dataBase.getGamePanelModel();
        panel = update.panel;
        bound = update.bound;

    }

    public static void addingEnemies(){
        if(!z) {
            if (random.nextDouble(0, bound) < 1) {
                if ((panelModel.wave == 1 && panelModel.enemies <= 10) || (panelModel.wave == 2 && panelModel.enemies <= 15) ||
                        (panelModel.wave == 3 && panelModel.enemies <= 20)) {
                    Sound.sound().entrance();
                    Movable n;
                    if (Game.getGame().getPhase() == 0) {
                        if (panelModel.random.nextInt(0, 2) == 1) {
                            n = new TriangleModel();
                        } else {
                            n = new RectangleModel();
                        }
                    } else {
                        if (panelModel.random.nextInt(0, 2) == 1) {
                            n = new Omenoctmodel();
                        } else if (panelModel.random.nextInt(0, 2) == 1) {
                            n = new NecropickModel();
                            z = true;
                        } else {
                            n = new ArchmireModel();
                        }
                    }
                    panel.getDrawables().add(createEnemyView(n));
                    dataBase.movables.add(n);
                    panelModel.enemies++;
                }
                panelModel.start = true;
            }
        }
    }
}
