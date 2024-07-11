package controller;

import controller.Util.Waves;
import model.GamePanelModel;
import model.characterModel.BulletModel;
import model.characterModel.PlayerModel;
import model.characterModel.enemy.*;
import model.movement.Collidable;
import model.movement.Movable;
import model.movement.Util.CollisionUtil;
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
import java.util.Random;
import static controller.Controller.*;
import static controller.Util.Util.*;
import static controller.Util.Waves.*;
import static controller.constants.Constant.FRAME_DIMENSION;
import static controller.constants.Constant.PANEL_DIMENSION;
import static controller.constants.EntityConstants.BALL_SIZE;
import static controller.constants.TimerConstants.FRAME_UPDATE_TIME;
import static controller.constants.TimerConstants.MODEL_UPDATE_TIME;
import static model.movement.Impact.impact;
import static model.movement.Util.CollisionUtil.*;

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

    //model
    public void updateModel() {
        for (int i = 0; i < dataBase.movables.size(); i++) {
            Movable m = dataBase.movables.get(i);
            if (m instanceof TriangleModel) updateTriangles(m);
            else if (m instanceof PlayerModel) epsilonCheck();
            else if (m instanceof BulletModel) updateBullets(m);
            else if (m instanceof RectangleModel) updateRecs(m);
            else if (m instanceof Omenoctmodel) updateOmenoct(m);

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
    private void phaseTwo(){

    }




    private void addingEnemies(){
        if (random.nextDouble(0, bound) < 1) {
            if((panelModel.wave == 1 && panelModel.enemies <= 10) || (panelModel.wave == 2 && panelModel.enemies <= 15) ||
                    (panelModel.wave == 3 && panelModel.enemies <= 20)) {
                Sound.sound().entrance();

                Movable n ;
                if(Game.getGame().getPhase() == 0) {
                    if (panelModel.random.nextInt(0, 2) == 1) {
                        n = new TriangleModel();

                        panel.getDrawables().add(createTriangleView((TriangleModel) n));
                    } else {

                        n = new RectangleModel();
                        panel.getDrawables().add(createRectView((RectangleModel) n));
                    }
                }else{
//                    if (panelModel.random.nextInt(0, 2) == 1){
                        n = new Omenoctmodel();
                        panel.getDrawables().add(createOmenoctView((Omenoctmodel) n));
//                    }
                }
                dataBase.movables.add(n);
                panelModel.enemies ++;
            }
            panelModel.start = true;
        }
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
        if (a == 1) moveLeft();
        else if (a == 2) moveRight();
        else if (a == 3) moveUp();
        else if (a == 4) moveDown();

        if (a != 0) {
            impact(bulletCenter((BulletModel) m), 50);
            removeBullet((BulletModel) m);
        } else {
            checkCollision(m);
        }

    }

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
            EnemyBullets b = new EnemyBullets(m.getLoc());
            b.setTarget(dataBase.playerModel.getLocation());
            b.creator = (Enemy) m;
            dataBase.enemyBullets.add(b);
            panel.getDrawables().add(createEnemyBulletView(b));
        }
    }
    private void updateEnemyBullet(){
        for (int j = 0; j < dataBase.enemyBullets.size(); j++) {
            EnemyBullets e = dataBase.enemyBullets.get(j);
            e.move();
            if (e.getLoc().getX() + panel.getLocation().getX() <= 10 ||
                    e.getLoc().getY() + panel.getLocation().getY() >= FRAME_DIMENSION.getHeight() - 10) {
                dataBase.enemyBullets.remove(e);
                for (int i = 0; i < panel.getDrawables().size(); i++) {
                    Drawable a = panel.getDrawables().get(i);
                    if (a instanceof EnemyBulletView) {
                        if (a.getId().equals(e.getId())) {
                            System.out.println("removed");
                            panel.getDrawables().remove(a);
                            break;
                        }

                    }
                }
            }
        }
    }
    private void updateEpsilon(){
         dataBase.playerModel.setPanelH(panel.getHeight());
         dataBase.playerModel.setPanelW(panel.getWidth());
         dataBase.playerModel.setPoints();

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
