package controller;

import controller.Util.EnemyHandler;
import controller.Util.Util;
import controller.Util.Waves;
import model.GamePanelModel;
import model.characterModel.BulletModel;
import model.characterModel.PlayerModel;
import model.characterModel.enemy.*;
import model.movement.Collidable;
import model.movement.Movable;
import controller.Util.CollisionUtil;
import sound.Sound;
import view.charactersView.BulletView;
import view.charactersView.PlayerView;
import view.charactersView.enemy.*;
import view.drawable.Drawable;
import view.pages.Game;
import view.pages.GameOver;
import view.pages.GamePanel;
import view.pages.Menu;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Objects;
import java.util.Random;
import static controller.Controller.*;
import static controller.Util.EnemyHandler.addingEnemies;
import static controller.Util.Util.*;
import static controller.Util.Waves.*;
import static controller.constants.Constant.PANEL_DIMENSION;
import static controller.constants.EntityConstants.BALL_SIZE;
import static controller.constants.TimerConstants.FRAME_UPDATE_TIME;
import static controller.constants.TimerConstants.MODEL_UPDATE_TIME;
import static model.movement.Impact.impact;
import static controller.Util.CollisionUtil.*;

public class Update {
    private static Update update;

    public static Update getUpdate() {
        if(update == null){
            System.out.println("creating update");
            update = new Update();
        }
        return update;
    }
    public static void remove(){
        update = null;
    }

    public GamePanel panel;
    public Timer time;
    public Timer view;
    public   Timer model;


    public double a ;
    public int bound ;
    private int second;
    boolean over;
    public DataBase dataBase;
    private Waves waves;
    Random random;
    GamePanelModel panelModel;
    private void initialDataBase(){
        dataBase = DataBase.getDataBase();
        panelModel = dataBase.getGamePanelModel();
    }
    public Update() {
        random = new Random();

        initialDataBase();
        bound = Menu.getMenu().bound;

        panel = creatGamePanel(panelModel);
        a = Menu.getMenu().aa;
        panel.playerView = createPlayerView();
        panel.getDrawables().add(panel.playerView);
        view = new Timer((int) FRAME_UPDATE_TIME, e -> updateView()){{setCoalesce(true);}};
        view.start();
        model = new Timer((int) MODEL_UPDATE_TIME, e -> updateModel()){{setCoalesce(true);}};
        model.start();


        time = new Timer(1000,e -> second += 1);
        time.start();

        new CollisionUtil(this);
        new Util(dataBase.gamePanelModel);
        new EnemyHandler(this);
        waves = new Waves(this);

    }
    public void updateView(){
        updatePanelDrawables();
    }
    private void updatePanelDrawables(){
        updatePanel();
        for(Drawable d: panel.getDrawables()){
            if(d instanceof PlayerView)updatePlayerView((PlayerView) d);
            else if(d instanceof RectangleView)updateRectangleView((RectangleView) d);
            else if(d instanceof TriangleView)updateTrianglesView((TriangleView) d);
            else if(d instanceof BulletView)updateBulletsView((BulletView) d);
            else if(d instanceof OmenoctView)updateOmenoctView((OmenoctView) d);
            else if(d instanceof EnemyBulletView)updateEnemyBulletView((EnemyBulletView) d);
            else if(d instanceof NecropicklView)updateNecroView((NecropicklView) d);
            else if(d instanceof ArchmireView)uodateArchView((ArchmireView) d);
        }

    }
    private void updateRectangleView(RectangleView r) {
        for (Movable movable : dataBase.movables) {
            if(movable instanceof RectangleModel) {
                if (movable.getId().equals(r.getId())) {
                    r.setLoc(movable.getLoc());
                    r.setHp(movable.getHp());
                    r.setxPoints(movable.getxPoints());
                    r.setyPoints(movable.getyPoints());
                }
            }
        }
    }
    private void updateTrianglesView(TriangleView r) {
        for (Movable movable : dataBase.movables) {
            if (movable instanceof TriangleModel) {
                if (movable.getId().equals(r.getId())) {
                    r.setLoc(movable.getLoc());
                    r.setHp(movable.getHp());
                    r.setxPoints(movable.getxPoints());
                    r.setyPoints(movable.getyPoints());
                }
            }
        }
    }
    private void updateBulletsView(BulletView r) {
        for (Movable movable : dataBase.movables) {
            if (movable instanceof BulletModel) {
                if (movable.getId().equals(r.getId())) {
                    r.setLoc(movable.getLoc());
                }
            }
        }
    }
    private void updatePlayerView(PlayerView p) {
        p.setLoc(playerViewLocation(dataBase.playerModel));
        p.setXp(playerViewXp(dataBase.playerModel));
        p.setHp(playerViewHp(dataBase.playerModel));
        p.setSize(dataBase.playerModel.size);
        p.setxPoints(dataBase.playerModel.getxPoints());
        p.setyPoints(dataBase.playerModel.getyPoints());
    }
    private void updateOmenoctView(OmenoctView o){
        for (Movable movable : dataBase.movables) {
            if (movable instanceof Omenoctmodel) {
                if (movable.getId().equals(o.getId())) {

                    o.setLoc(movable.getLoc());
                    o.setxPoints(movable.getxPoints());
                    o.setyPoints(movable.getyPoints());
                    o.setHp(movable.getHp());
                }
            }
        }
    }
    private void updatePanel(){
        panel.setSize(panelModel.getDimension());
        panel.setLocation(panelModel.getLoc());
        panel.setSecond(second);
        panel.setWave(panelModel.wave);
        
    }
    private void updateEnemyBulletView(EnemyBulletView e){
        for (EnemyBullets m : dataBase.enemyBullets) {
            if (m.getId().equals(e.getId())) {
                e.setLoc(m.getLoc());
            }

        }
    }
    private void updateNecroView(NecropicklView n){
        for (int i = 0; i < dataBase.movables.size(); i++) {
            Movable d = dataBase.movables.get(i);
            if(d instanceof NecropickModel && d.getId().equals(n.getId())){
                n.setLoc(d.getLoc());
                n.visible = ((NecropickModel) d).visible;
                n.setHp(d.getHp());
                n.preLoc = ((NecropickModel) d).preLoc;
            }
        }
    }
    private void uodateArchView(ArchmireView a){
        for (int i = 0; i < dataBase.movables.size(); i++) {
            Movable d = dataBase.movables.get(i);
            if(d instanceof ArchmireModel && d.getId().equals(a.getId())){
                a.setLoc(d.getLoc());
                a.setHp(d.getHp());
                a.setTrace(((ArchmireModel) d).getTrace());
            }
        }
    }

    //model
    public void updateModel() {
        for (int i = 0; i < dataBase.movables.size(); i++) {
            Movable m = dataBase.movables.get(i);
            if (m instanceof TriangleModel) updateTriangles(m);
            else if (m instanceof PlayerModel) epsilonCheck();
            else if (m instanceof BulletModel) updateBullets(m);
            else if (m instanceof RectangleModel) updateRecs(m);
            else if (m instanceof Omenoctmodel) updateOmenoct(m);
            else if(m instanceof NecropickModel)updateNecro((NecropickModel) m);
            else if(m instanceof ArchmireModel)updateArchModel(m);

            updateEnemyBullet();
        }
        addingEnemies();
        if (Game.getGame().getPhase() == 0) phaseOne();
        updateCollectable();
        victory();
        timeCheck();
    }
    private void epsilonCheck(){
        updateEpsilon();
        moveEpsilon();
    }
    private void timeCheck(){
        if(second <= 1){
            initialGame();
        }
        if(second >= 10) {
            panelModel.shrinkage();
        }
    }

    private void initialGame(){
        panelModel.shrinkage();
    }
    private void phaseOne(){
        waveCheck();
        Wave();
        bound = Waves.bound;
    }
    private void phaseTwo() {
    }


    private void updateCollectable(){
        for (int i = 0; i < dataBase.collectableModels.size();i ++) {
            if (dataBase.collectableModels.get(i).getSecond() >= 10) {
                removeCollectable(dataBase.collectableModels.get(i));
                dataBase.collectableModels.remove(i);

            }
        }
    }
    private void removeCollectable(CollectableModel c){
        for(int i = 0; i < panel.getDrawables().size(); i++){
            if(panel.getDrawables().get(i) instanceof CollectableView){
                if(panel.getDrawables().get(i).getId().equals(c.getId())){
                    panel.getDrawables().remove(i);
                    break;
                }
            }
        }
    }
    private void updateRecs(Movable m) {
        if (new Random().nextDouble(0, 50) <= 1) {
            m.setSpeed(2);
        }
        if (second % 2 == 0) m.setSpeed(1);
        m.move();
        checkCollision(m);
    }
    private void updateBullets(Movable m) {
        m.setPanelH(panel.getHeight());
        m.setPanelW(panel.getWidth());
        int a = m.move();
        if (a == 1) {moveLeft();checkLeftOmenocts();}
        else if (a == 2) {moveRight();checkRightOmenocts();}
        else if (a == 3) {moveUp();checkTopOmenocts();}
        else if (a == 4) {moveDown();checkDownOmenocts();}
        if (a != 0) {impact(bulletCenter((BulletModel) m), 50);removeBullet((BulletModel) m);} else {checkCollision(m);}}

    private void updateTriangles(Movable m) {
        if (distance(m.getLoc().getX(), m.getLoc().getY()
                , dataBase.playerModel.getLoc().getX(), dataBase.playerModel.getLoc().getY()) >= 200) {
            m.setSpeed(2);
        } else {
            m.setSpeed(1);
        }
        m.move();
        checkCollision(m);
    }
    private void updateOmenoct(Movable m) {
        m.setPanelW(panel.getWidth());
        m.setPanelH(panel.getHeight());
        m.move();
        checkCollision(m);

        if (random.nextDouble(0,100) < 0.5) {
            EnemyBullets b = new EnemyBullets(centerLoc(m), centerLoc(dataBase.playerModel), (Enemy) m, false);
            dataBase.enemyBullets.add(b);
            panel.getDrawables().add(createEnemyBulletView(b));
        }
    }
    private void updateEnemyBullet(){
        for (int j = 0; j < dataBase.enemyBullets.size(); j++) {
            EnemyBullets e = dataBase.enemyBullets.get(j);
            e.move();
            e.collision(dataBase.playerModel);
            boolean x = bulletIsOutSideOfFrame(e, panelModel);
            if(e.rigidBody())x = bulletIsOutSideOfPanel(e,panelModel);
            if (x) {
                for (int i = 0; i < panel.getDrawables().size(); i++) {
                    Drawable a = panel.getDrawables().get(i);
                    if (a instanceof EnemyBulletView) {
                        if (a.getId().equals(e.getId())) {
                            panel.getDrawables().remove(a);
                            break;
                        }

                    }
                }
                dataBase.enemyBullets.remove(e);
            }
        }
    }
    private void updateEpsilon(){
         dataBase.playerModel.setPanelH(panel.getHeight());
         dataBase.playerModel.setPanelW(panel.getWidth());
         dataBase.playerModel.setPoints();

    }
    private void updateNecro(NecropickModel necro){

        if(necro.sec % 8 == 0 && necro.sec != 0){
            necro.visible = false;
            necro.collides = false;
            necro.bullets = 0;
            necro.sec = 0;
        }else if(necro.sec%2 == 0 && !necro.visible && necro.sec !=0 && Objects.equals(necro.preLoc, new Point2D.Double(0, 0))){
            necro.preLoc = necro.findPlayer();
        }
        else if(necro.sec % 4 == 0 && necro.sec != 0 && !necro.visible){
            necro.findPlayer(necro.getLoc());
            necro.visible = true;
            necro.collides = true;
            necro.sec = 0;
            necro.preLoc = new Point2D.Double();
        }
        if(necro.visible){
            if(necro.bullets < 8 && Math.random() < 0.004) {
                EnemyBullets e = new EnemyBullets(centerLoc(necro), new Point2D.Double(
                        Math.random()*panelModel.getDimension().getWidth(),
                        panelModel.getDimension().getHeight()*Math.random()), necro, true);
                dataBase.enemyBullets.add(e);
                panel.getDrawables().add(createEnemyBulletView(e));
                necro.bullets ++;
            }
        }
        checkCollision(necro);

    }

    private void updateArchModel(Movable arc){
        arc.move();
        checkCollision(arc);
    }

    private void moveEpsilon(){
         dataBase.playerModel.move();
        if ( dataBase. playerModel.isdForce()) {
             dataBase. playerModel.setYvelocity( dataBase. playerModel.getYvelocity() + a);
             dataBase. playerModel.move( dataBase. playerModel.getYvelocity());
        }
        if ( dataBase. playerModel.isuForce()) {
             dataBase. playerModel.setYvelocity( dataBase. playerModel.getYvelocity()+ a);
             dataBase. playerModel.move(- dataBase. playerModel.getYvelocity());
        }
        if ( dataBase. playerModel.isrForce()) {
             dataBase. playerModel.setXvelocity( dataBase. playerModel.getXvelocity()+ a);
             dataBase. playerModel.move( dataBase. playerModel.getXvelocity());
        }
        if ( dataBase. playerModel.islForce()) {
             dataBase. playerModel.setXvelocity( dataBase. playerModel.getXvelocity()+ a);
             dataBase. playerModel.move(- dataBase. playerModel.getXvelocity());
        }
        if ( dataBase. playerModel.isR0Force()){
             dataBase. playerModel.setXvelocity( dataBase. playerModel.getXvelocity()- a);
            if( dataBase. playerModel.getXvelocity() <= 0){
                 dataBase. playerModel.setR0Force(false);
                 dataBase. playerModel.setXvelocity(0);
            }
             dataBase. playerModel.move( dataBase. playerModel.getXvelocity());
        }
        if ( dataBase. playerModel.isL0Force()) {
             dataBase. playerModel.setXvelocity( dataBase. playerModel.getXvelocity()- a);
            if( dataBase. playerModel.getXvelocity() <= 0){
                 dataBase. playerModel.setL0Force(false);
                 dataBase. playerModel.setXvelocity(0);
            }
             dataBase. playerModel.move(- dataBase. playerModel.getXvelocity());
        }
        if ( dataBase. playerModel.isU0Force()) {
             dataBase. playerModel.setYvelocity( dataBase. playerModel.getYvelocity()- a);
            if( dataBase. playerModel.getYvelocity() <= 0){
                 dataBase. playerModel.setU0Force(false);
                 dataBase. playerModel.setYvelocity(0);
            }
             dataBase. playerModel.move(- dataBase. playerModel.getYvelocity());
        }
        if ( dataBase. playerModel.isD0Force()) {
             dataBase. playerModel.setYvelocity( dataBase. playerModel.getYvelocity()- a);
            if( dataBase. playerModel.getYvelocity() <= 0){
                 dataBase. playerModel.setD0Force(false);
                 dataBase. playerModel.setYvelocity(0);
            }
             dataBase. playerModel.move( dataBase. playerModel.getYvelocity());
        }

        wallCheck();
        getC();
    }
    private void wallCheck(){
        if ( dataBase.playerModel.getLocation().getY() + BALL_SIZE> panel.getHeight()) {
             dataBase.playerModel.setLocation(
                    new Point2D.Double( dataBase.playerModel.getLocation().getX(), panel.getHeight() - BALL_SIZE - 5));
        }else if( dataBase.playerModel.getLocation().getY() < 2){
             dataBase.playerModel.setLocation(
                    new Point2D.Double( dataBase.playerModel.getLocation().getX(),  10));
        }
        if( dataBase.playerModel.getLocation().getX() + BALL_SIZE > panel.getWidth()){
             dataBase.playerModel.setLocation(
                    new Point2D.Double(panel.getWidth() - BALL_SIZE ,dataBase.playerModel.getLocation().getY()));
        }else if( dataBase.playerModel.getLocation().getX()  < 2){
             dataBase.playerModel.setLocation(
                    new Point2D.Double(10, dataBase.playerModel.getLocation().getY()));

        }
    }

    private void getC(){
        for (int i = 0; i <  dataBase.collectableModels.size(); i++) {
            CollectableModel c = dataBase.collectableModels.get(i);
            if (Math.abs(c.getLoc().getX() - dataBase.playerModel.getLocation().getX()) <= 13 &&
                    Math.abs(c.getLoc().getY() - dataBase.playerModel.getLocation().getY()) <= 13) {

                dataBase.collectableModels.get(i).timer.stop();
                dataBase.playerModel.setXp(dataBase.playerModel.getXp() + c.getCreator().collectablesXp);

                removeCollectable(c);
                dataBase.collectableModels.remove(i);



            }
        }
    }

    private void checkCollision(Movable movable){
        for (int i = 0; i < dataBase.movables.size(); i++) {
            Collidable c = (Collidable) dataBase.movables.get(i);
            c.collision(movable);
        }

    }

    Timer t;
    private void victory() {
        if (panelModel.victory) {
            Sound.sound().Victory();
            model.stop();
            view.stop();
            time.stop();
            t = new Timer(10, e -> {
                v();
                updatePlayerView(panel.playerView);
            });
            t.start();
        }
    }
    private void v(){
        if(panelModel.getDimension().getWidth()  + 200 >=  dataBase.playerModel.size) {
             dataBase.playerModel.size += 2;
             dataBase.playerModel.setPoints();
            if( dataBase.playerModel.getLocation().getX() <  panelModel.getDimension().getWidth())  dataBase.playerModel.setLocation(new Point2D.Double
                    ( dataBase.playerModel.getLocation().getX() - 1,  dataBase.playerModel.getLocation().getY() - 1));
        }

        if( dataBase.playerModel.size > panelModel.getDimension().getWidth()) v1();
    }
    private void v1(){
        if(panelModel.getDimension().getWidth() >= 1 && panelModel.getDimension().getHeight() >= 1){

            panelModel.getDimension().setSize(new Dimension((int) (panelModel.getDimension().getWidth() - 0.1),
                    (int) (panelModel.getDimension().getHeight()  - 0.1)));
        }
        if(panelModel.getDimension().getHeight() <= 1 || panelModel.getDimension().getWidth() <= 1){
            t.stop();
            if(Game.getGame().getPhase() == 0) initiatePhase2();
            else gameOver();
        }
    }

    private void initiatePhase2(){
        Game.getGame().setPhase(1);
        panelModel.setDimension(PANEL_DIMENSION);
    }

    public void gameOver(){
        if(!panelModel.victory)Sound.sound().Losing();
        model.stop();
        view.stop();
        time.stop();
        over = true;
        PlayerModel.defuse();
        Sound.sound().stop();

        new GameOver(this);
    }



    public double getSecond() {
        return second;
    }
}
