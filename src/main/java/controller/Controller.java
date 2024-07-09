package controller;

import model.GamePanelModel;
import model.characterModel.BulletModel;
import model.characterModel.PlayerModel;
import model.characterModel.enemy.CollectableModel;
import model.characterModel.enemy.Omenoctmodel;
import model.characterModel.enemy.RectangleModel;
import model.characterModel.enemy.TriangleModel;
import view.charactersView.BulletView;
import view.charactersView.PlayerView;
import view.charactersView.enemy.CollectableView;
import view.charactersView.enemy.OmenoctView;
import view.charactersView.enemy.RectangleView;
import view.charactersView.enemy.TriangleView;
import view.pages.GamePanel;

import java.awt.geom.Point2D;

public class Controller  {

    public static PlayerView createPlayerView(){
        return new PlayerView(PlayerModel.getPlayer().getId(), PlayerModel.getPlayer().getLocation());
    }
    public static RectangleView createRectView(RectangleModel rectangleModel){
        return new RectangleView(rectangleModel.getId(), rectangleModel.getLoc());
    }
    public static Point2D playerViewLocation(PlayerModel playerModel){
        return playerModel.getLocation();

    }
    public static int playerViewXp(PlayerModel playerModel){
        return playerModel.getXp();

    }
    public static int playerViewHp(PlayerModel playerModel){
        return playerModel.getHp();

    }
    public static BulletView createBulletView(BulletModel bulletModel){
        return new BulletView(bulletModel.getId(), bulletModel.getLoc());
    }
    public static CollectableView createCollectableView(CollectableModel collectableModel){
        return new CollectableView(collectableModel.getId(), collectableModel.getLoc());
    }
    public static GamePanel creatGamePanel(GamePanelModel g){
        return new GamePanel(g.getId());
    }
//    public static GamePanelModel findGamePanelModel(GamePanel g){
//
//    }
    public static OmenoctView createOmenoctView(Omenoctmodel omenoctmodel){
        return new OmenoctView(omenoctmodel.getId(), omenoctmodel.getLoc());
    }

    public static TriangleView createTriangleView(TriangleModel t){
        return new TriangleView(t.getId());
    }



}
