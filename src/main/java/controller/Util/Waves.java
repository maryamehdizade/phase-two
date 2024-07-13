package controller.Util;

import controller.DataBase;
import controller.Update;
import model.model.GamePanelModel;
import sound.Sound;

public class Waves {
    private static GamePanelModel panelModel;
    private static DataBase dataBase;
    public static int bound;
    private static int count= 0;

    public Waves(Update update) {
        dataBase = update.dataBase;
        panelModel = update.dataBase.getGamePanelModel();
        bound = update.bound;

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
