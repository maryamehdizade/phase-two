package controller;

import model.characterModel.BulletModel;
import model.characterModel.PlayerModel;
import model.characterModel.enemy.CollectableModel;
import model.movement.Movable;
import view.pages.GamePanel;

import java.util.ArrayList;

public class DataBase {
    Update update;
    public ArrayList<Movable> movables = new ArrayList<>();
    ArrayList<CollectableModel> collectableModels = new ArrayList<>();
    public PlayerModel playerModel;

    private static DataBase dataBase;
    DataBase(Update update){
        this.update = update;
        playerModel = PlayerModel.getPlayer();
        movables.add(playerModel);
    }
    public void setXp(int xp){
        playerModel.setXp(xp);
    }
    public void setHp(int hp){
        playerModel.setHp(hp);
    }

    public static DataBase getDataBase() {
        return dataBase;
    }

    public static void setDataBase(DataBase dataBase) {
        DataBase.dataBase = dataBase;
    }

}
