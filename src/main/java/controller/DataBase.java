package controller;

import controller.Util.SkillTreeHandler;
import model.characterModel.enemy.boss.BossModel;
import model.model.GamePanelModel;
import model.characterModel.enemy.CollectableModel;
import model.characterModel.enemy.EnemyBullets;

import java.util.ArrayList;

import static controller.constants.Constant.PANEL_DIMENSION;
import static controller.constants.Constant.PANEL_LOCATION;

public class DataBase {
    GamePanelModel gamePanelModel;
    public ArrayList<CollectableModel> collectableModels = new ArrayList<>();
    public int shoots;
    public int successShoots;
    public int killedEnemies;
    public int second;
    public int bound ;
    int slumberSec;
    int dismaySec;

    private int slaughterSec;
    private boolean slaughter;
    public int wave = 6;
    public int waveTime;
    private static DataBase dataBase;
    public SkillTreeHandler handler;
    private DataBase(){
        gamePanelModel = new GamePanelModel(PANEL_LOCATION, PANEL_DIMENSION,false,false);


        handler = new SkillTreeHandler(gamePanelModel);
    }
    public void setXp(int xp){
        gamePanelModel.playerModel.setXp(xp);
    }
    public void setHp(int hp){
        gamePanelModel.playerModel.setHp(hp);
    }

    public static DataBase getDataBase() {
        if(dataBase == null){
            System.out.println("creating database");
            dataBase = new DataBase();
        }
        return dataBase;
    }

    public GamePanelModel getGamePanelModel() {
        return gamePanelModel;
    }
    public static void remove(DataBase d){
        dataBase = d;
    }

    public int getSlumberSec() {
        return slumberSec;
    }

    public void setSlumberSec(int slumberSec) {
        this.slumberSec = slumberSec;
    }

    public void setDismaySec(int dismaySec) {
        this.dismaySec = dismaySec;
    }

    public int getSlaughterSec() {
        return slaughterSec;
    }

    public boolean isSlaughter() {
        return slaughter;
    }

    public void setSlaughter(boolean slaughter) {
        this.slaughter = slaughter;
    }

    public void setSlaughterSec(int slaughterSec) {
        this.slaughterSec = slaughterSec;
    }
}
