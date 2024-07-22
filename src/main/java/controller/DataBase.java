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
    public static void remove(){
        dataBase = null;
    }
}
