package controller;

import model.GamePanelModel;
import model.characterModel.PlayerModel;
import model.characterModel.enemy.CollectableModel;
import model.movement.Movable;

import java.util.ArrayList;

import static controller.constants.Constant.PANEL_DIMENSION;
import static controller.constants.Constant.PANEL_LOCATION;

public class DataBase {
    GamePanelModel gamePanelModel;
    public ArrayList<Movable> movables;
    public ArrayList<CollectableModel> collectableModels = new ArrayList<>();
    public PlayerModel playerModel;

    private static DataBase dataBase;
    public SkillTreeHandler handler;
    private DataBase(){

        movables = new ArrayList<>();

        gamePanelModel = new GamePanelModel(PANEL_LOCATION, PANEL_DIMENSION);
        playerModel = PlayerModel.getPlayer();
        gamePanelModel.playerModel = playerModel;
        movables.add(playerModel);

        handler = new SkillTreeHandler(gamePanelModel);
    }
    public void setXp(int xp){
        playerModel.setXp(xp);
    }
    public void setHp(int hp){
        playerModel.setHp(hp);
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
