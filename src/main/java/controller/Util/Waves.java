package controller.Util;

import controller.DataBase;
import controller.Update;
import model.model.GamePanelModel;
import sound.Sound;

import static controller.constants.Constant.FINAL_WAVE;

public class Waves {
    private static GamePanelModel panelModel;
    private static DataBase dataBase;
    private static Update update;
    public static int bound;
    private static int count= 0;
    private static int waveEnemy;

    public Waves(Update update) {
        this.update = update;
        dataBase = update.dataBase;
        panelModel = dataBase.getGamePanelModel();
        bound = dataBase.bound;
        dataBase.second = 0;
        count = 0;
        panelModel.enemies = 0;
    }
    public static void wave(){
        if(dataBase.wave < FINAL_WAVE) {
            if (dataBase.killedEnemies >= dataBase.wave * 2 + 5 + waveEnemy) {
                update.d = true;
                if(dataBase.waveTime%10 == 0&&dataBase.bound>5)dataBase.bound -= 3;
                if (panelModel.movables.size() == 1) {
                    dataBase.wave++;
                    dataBase.waveTime = 0;
                    update.d = false;
                    waveEnemy = dataBase.killedEnemies;
                }
            }
        }
    }

    public static void Wave() {
        if (panelModel.enemies >= 10 && panelModel.wave1 && panelModel.start && dataBase.getGamePanelModel().movables.size() == 1) {

            waveChange();

        } else if (panelModel.enemies >= 15 && panelModel.wave2 && dataBase.getGamePanelModel().movables.size() == 1) {
            waveChange();

        } else if (panelModel.wave == 3 && dataBase.getGamePanelModel().movables.size() == 1 && panelModel.enemies >= 20) {
            panelModel.victory = true;
        }
    }
    public static void waveChange(){
        Sound.sound().wave();
        panelModel.wave++;
        panelModel.enemies = 0;
        count = 0;
    }
    public static void waveCheck(){
        if(panelModel.wave == 2 && !panelModel.wave2){
            if(count == 0) {
                bound -= 20;
            }count++;
            panelModel.wave2 = true;
            panelModel.wave1 = false;
        }
        if(panelModel.wave == 3 && !panelModel.wave3){
            if(count == 0) {
                bound -= 20;
            }count++;
            panelModel.wave3 = true;
            panelModel.wave2 = false;
        }
    }

}
