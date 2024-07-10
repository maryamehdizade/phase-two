package controller;

import model.GamePanelModel;
import model.characterModel.BulletModel;
import model.characterModel.PlayerModel;
import model.characterModel.enemy.CollectableModel;
import model.characterModel.enemy.Omenoctmodel;
import model.characterModel.enemy.RectangleModel;
import model.characterModel.enemy.TriangleModel;
import model.movement.Collidable;
import model.movement.Movable;
import model.movement.Util.CollisionUtil;
import sound.Sound;
import view.charactersView.BulletView;
import view.charactersView.PlayerView;
import view.charactersView.enemy.CollectableView;
import view.charactersView.enemy.OmenoctView;
import view.charactersView.enemy.RectangleView;
import view.charactersView.enemy.TriangleView;
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
import static controller.constants.AttackConstants.*;
import static controller.constants.Constant.PANEL_DIMENSION;
import static controller.constants.EntityConstants.BALL_SIZE;
import static controller.constants.TimerConstants.FRAME_UPDATE_TIME;
import static controller.constants.TimerConstants.MODEL_UPDATE_TIME;
import static model.movement.Impact.impact;

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
    private int impactV = 4;
    private final int n = 20;

    public double a ;
    public int bound ;
    private int second;
    boolean over;
    public DataBase dataBase;
    Random random;
    private int count;

    Point2D collision ;
    Point2D collision2;
    GamePanelModel panelModel;
    private void initialDataBase(){
        dataBase = DataBase.getDataBase();
        panelModel = dataBase.getGamePanelModel();
    }
    public Update() {
        random = new Random();

        initialDataBase();
        bound = Menu.getMenu().bound;

//        panel = new GamePanel("");
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

    //model
    public void updateModel() {
        for (int i = 0; i < dataBase.movables.size(); i++) {
            Movable m = dataBase.movables.get(i);
            if (m instanceof TriangleModel) updateTriangles(m);
            else if (m instanceof PlayerModel) epsilonCheck();
            else if (m instanceof BulletModel) updateBullets(m);
            else if (m instanceof RectangleModel) updateRecs(m);
            else if (m instanceof Omenoctmodel) updateOmenoct(m);
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
//        updateRecs();
//        updateTriangles();
        waveCheck();
        Wave();
    }
    private void phaseTwo(){

    }



    public void Wave() {
        if (panelModel.enemies >= 10 && panelModel.wave1 && panelModel.start && dataBase.movables.size() == 1) {

            waveChange();

        } else if (panelModel.enemies >= 15 && panelModel.wave2 && dataBase.movables.size() == 1) {
            waveChange();

        } else if (panelModel.wave == 3 && dataBase.movables.size() == 1 && panelModel.enemies >= 20) {
            panelModel.victory = true;
        }
    }
    private void waveChange(){
        Sound.sound().wave();
        panelModel.wave++;
        panelModel.enemies = 0;
        count = 0;
    }
    private void waveCheck(){
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
                        System.out.println("omenoct");
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
    private void updateRecs(Movable m){
//        for (int i = 0; i < dataBase.movables.size(); i++) {
//            if(dataBase.movables.get(i) instanceof RectangleModel) {
                if (new Random().nextDouble(0, 50) <= 1) {
                    m.setSpeed(2);
                }
                if (second % 2 == 0) m.setSpeed(1);
                m.move();
                checkCollision(m);
//            }
//        }
    }
    private void updateBullets(Movable m){
//        for (int i = 0; i <  dataBase.movables.size(); i++) {
//            if(dataBase.movables.get(i) instanceof BulletModel) {

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
                }else{
                    checkCollision(m);
                }

//            }
//        }
    }

    private void updateTriangles(Movable m){
//        for (int i = 0; i <  dataBase.movables.size() ; i++) {
//            if(m instanceof RectangleModel)
            if (distance(m.getLoc().getX(), m.getLoc().getY()
                    , dataBase.playerModel.getLoc().getX(), dataBase.playerModel.getLoc().getY()) >= 200) {
                m.setSpeed(2);
            } else {
                m.setSpeed(1);
            }
            m.move();
            checkCollision(m);
//        }
    }
    private void updateOmenoct(Movable m) {
//        for (int i = 0; i <  dataBase.movables.size() ; i++) {
        m.setPanelW(panel.getWidth());
        m.setPanelH(panel.getHeight());
        m.move();
        checkCollision(m);
//        }
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
//    private void checkBulletCollisions(BulletModel movable){
//        for (int i = 0; i < dataBase.movables.size(); i++) {
////            if(!dataBase.movables.get(i).equals(movable)){
//                if(dataBase.movables.get(i) instanceof RectangleModel)
//                    bulletRecCollision((RectangleModel) dataBase.movables.get(i), (BulletModel) movable);
//                else if(dataBase.movables.get(i) instanceof TriangleModel)
//                    bulletTriangleCollision((TriangleModel) dataBase.movables.get(i), (BulletModel) movable);
////            }
//        }
//    }
//    private void checkTriCollisions(TriangleModel movable){
//        for (int i = 0; i < dataBase.movables.size(); i++) {
//            if (!dataBase.movables.get(i).equals(movable)) {
//                if (dataBase.movables.get(i) instanceof PlayerModel)
//                    triangleEpsilonCollosion((TriangleModel) movable);
//                else if (dataBase.movables.get(i) instanceof TriangleModel)
//                    traingleTriangleCollision((TriangleModel) dataBase.movables.get(i), (TriangleModel) movable);
//            }
//        }
//    }
//    private void checkRectColiisions(RectangleModel movable){
//        for (int i = 0; i < dataBase.movables.size(); i++) {
//            if(!dataBase.movables.get(i).equals(movable)){
//                if(dataBase.movables.get(i) instanceof PlayerModel)
//                    rectEpsilonCollision((RectangleModel) movable);
//                else if(dataBase.movables.get(i) instanceof TriangleModel)
//                    rectangleTriangleCollision((TriangleModel) dataBase.movables.get(i), (RectangleModel) movable);
//                else  if(dataBase.movables.get(i) instanceof RectangleModel)
//                    rectRectCollision((RectangleModel) dataBase.movables.get(i), (RectangleModel) movable);
//            }
//        }
//    }
//    private void bulletRecCollision(RectangleModel r, BulletModel b){
//            Polygon rec = new Polygon(r.getxPoints(),
//                    r.getyPoints(), 4);
//            if (rec.contains(bulletCenter(b))) {
//                removeBullet(b);
//
//                injured(r);
//                impact(bulletCenter(b), 50);
//            }
//    }
//    private void rectRectCollision(RectangleModel r,RectangleModel movable) {
//        Polygon rec = new Polygon(movable.getxPoints(), movable.getyPoints(), 4);
//
//        for (int j = 0; j < 4; j++) {
//            if (rec.contains(new Point2D.Double(r.getxPoints()[j], r.getyPoints()[j]))) {
//
//                impact(new Point2D.Double(r.getxPoints()[j], r.getyPoints()[j]), 50);
//            }
//        }
//    }
//    private void rectEpsilonCollision(RectangleModel movable){
//        collision = ert( dataBase.playerModel, movable);
//        collision2 = er( dataBase.playerModel,movable);
//        if(collision != null){
//            reduceHp(movable);
//
//            impact(collision, 50);
//        }else if(collision2 != null)
//            impact(collision2, 50);
//
//        Polygon rec = new Polygon(movable.getxPoints(),
//                movable.getyPoints(), 4);
//
//        if(panelModel.proteus){
//            for (int i = 0; i <  dataBase.playerModel.getLevelUp(); i++) {
//                if(rec.contains( dataBase.playerModel.getxPoints()[i],  dataBase.playerModel.getyPoints()[i])){
//                    injured(movable);
//                    impact(new Point2D.Double( dataBase.playerModel.getxPoints()[i],  dataBase.playerModel.getyPoints()[i]), 50);
//                }
//            }
//
//        }
//    }
//
//    private void rectangleTriangleCollision(TriangleModel t, RectangleModel movable) {
//        Polygon rec = new Polygon(movable.getxPoints(), movable.getyPoints(), 4);
//
//        for (int j = 0; j < 3; j++) {
//            if (rec.contains(new Point2D.Double(t.getxPoints()[j], t.getyPoints()[j]))) {
//
//                impact(new Point2D.Double(t.getxPoints()[j], t.getyPoints()[j]), 50);
//            }
//        }
//    }
//    private void triangleEpsilonCollosion(TriangleModel movable){
//        collision = ert( dataBase.playerModel, movable);
//        collision2 = et( dataBase.playerModel, movable);
//        if (collision != null) {
//            reduceHp(movable);
//            impact(collision, 50);
//        }else if(collision2 != null)
//            impact(collision2, 50);
//
//        Polygon tri = new Polygon(movable.getxPoints(), movable.getyPoints(), 3);
//
//        if(panelModel.proteus){
//            for (int i = 0; i <  dataBase.playerModel.getLevelUp(); i++) {
//                if(tri.contains( dataBase.playerModel.getxPoints()[i],  dataBase.playerModel.getyPoints()[i])){
//                    injured(movable);
//                    impact(new Point2D.Double( dataBase.playerModel.getxPoints()[i],  dataBase.playerModel.getyPoints()[i]), 50);
//                }
//            }
//        }
//    }
//    private void traingleTriangleCollision(TriangleModel t, TriangleModel movable) {
//        Polygon tri = new Polygon(movable.getxPoints(), movable.getyPoints(), 3);
//
//        for (int j = 0; j < 3; j++) {
//            if (tri.contains(new Point2D.Double(t.getxPoints()[j],
//                    t.getyPoints()[j]))) {
//
//                impact(new Point2D.Double(t.getxPoints()[j],
//                        t.getyPoints()[j]), 50);
//            }
//        }
//    }
//    private void bulletTriangleCollision(TriangleModel t, BulletModel b) {
//        Polygon rec = new Polygon(t.getxPoints(),
//                t.getyPoints(), 3);
//
//        if (rec.contains(bulletCenter(b))) {
//
//            removeBullet(b);
//
//            injured(t);
//
//            impact(bulletCenter(b), 50);
//        }
//    }
//    private void injured(Movable r){
//        Sound.sound().injured();
//        r.setHp(r.getHp() - panelModel.power);
//        if (r.getHp() <= 0) {
//
//            entityDeath(r);
//        }
//    }
//
//    private void entityDeath(Movable m) {
//        Sound.sound().death();
//        dataBase.movables.remove(m);
//        removeEntity(m);
//        death(m);
//    }
//    private void removeEntity(Movable m){
//        for (int i = 0; i < panel.getDrawables().size(); i++) {
//            if(panel.getDrawables().get(i).getId().equals(m.getId())){
//                panel.getDrawables().remove(i);
//                break;
//            }
//        }
//    }

//
//    public void impact(Point2D point, double r){
//        for (int i = 0; i < dataBase.movables.size(); i ++) {
//            Movable m =  dataBase.movables.get(i);
//            if (!(m instanceof BulletModel)) {
////                System.out.println(m.getLoc() + m.getId());
//                double x = 0;
//                double y = 0;
//                if (m instanceof RectangleModel) {
//                    x = Math.abs(rectCenter((RectangleModel) m).getX() - point.getX());
//                    y = Math.abs(rectCenter((RectangleModel) m).getY() - point.getY());
//                } else if (m instanceof TriangleModel) {
//                    x = Math.abs(m.getLoc().getX() - point.getX());
//                    y = Math.abs(m.getLoc().getY() - point.getY());
//                } else if (m instanceof PlayerModel) {
//                    x = Math.abs(playerCenter(((PlayerModel) m)).getX() - point.getX());
//                    y = Math.abs(playerCenter(((PlayerModel) m)).getY() - point.getY());
//                }
//                if (x <= r && y <= r) {
//                    double speed = distance(x, y, point.getX(), point.getY()) / 500;
//                    m.setImpact(true);
//                    setSpeed(point, m, impactV / speed);
//                }
//            }
//        }
//    }
//    private void setSpeed(Point2D point, Movable m, double impactV){
//        double a = -point.getY() + m.getLoc().getY();
//        double b = -point.getX() + m.getLoc().getX();
//
//        if(b != 0){
//            double angel = Math.atan(a/b);
//            if(b < 0){
//                m.setXvelocity(-impactV*Math.cos(angel));
//                if(a < 0)m.setYvelocity(-impactV*Math.sin(angel));
//                else m.setYvelocity(impactV*Math.sin(angel));
//            }
//            else{
//                m.setXvelocity(impactV*Math.cos(angel));
//                if(a < 0) m.setYvelocity(impactV*Math.sin(angel));
//                else m.setYvelocity(-impactV*Math.sin(angel));
//            }
//
//        }else if(a >= 0){
//            m.setYvelocity(impactV);
//        }else if(a < 0){
//            m.setYvelocity(-impactV);
//        }
//    }
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
//            gameOver();
            initiatePhase2();
            
        }
    }

    private void initiatePhase2(){
        Game.getGame().setPhase(1);
        panelModel.setDimension(PANEL_DIMENSION);
//        Update.remove();
//        DataBase.remove();
        
    }
//    private void death(Movable movable){
//        int n = 1;
//        if(movable instanceof TriangleModel)n = 2;
//        for (int i = 0; i < n; i++) {
//            CollectableModel c = new CollectableModel(addVector(movable.getLoc(), new Point2D.Double(i*10, i *10)));
//            dataBase.collectableModels.add(c);
//            panel.getDrawables().add(createCollectableView(c));
//        }
//    }
//    private void reduceHp(Movable movable){
//        int w = 0;
//        if(movable instanceof TriangleModel)w = TRI_POWER;
//        else if(movable instanceof RectangleModel)w = REC_POWER;
//         dataBase.playerModel.setHp( dataBase.playerModel.getHp() - w);
//        if( dataBase.playerModel.getHp() <= 0){
//            gameOver();
//        }
//    }
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



    private void removeBullet(int i) {

//        panel.getDrawables().remove(i);
        for (int j = 0; j < panel.getDrawables().size(); j++) {
            if(panel.getDrawables().get(j) instanceof BulletView)
                if (panel.getDrawables().get(j).getId().equals(dataBase.movables.get(i).getId())) {
                panel.getDrawables().remove(j);
                break;
            }
        }
        dataBase.movables.remove(i);
    }
    private void removeBullet(BulletModel bulletModel){
        for (int i = 0; i <  dataBase.movables.size(); i++) {
            if(dataBase.movables.get(i) instanceof BulletModel)
                if( dataBase.movables.get(i).getId().equals(bulletModel.getId()))
                    removeBullet(i);
        }
    }
    private void moveLeft(){
        panelModel.setLoc(new Point((int) (panelModel.getLoc().getX() - n), (int) panelModel.getLoc().getY()));
        panelModel.setDimension(new Dimension((int) (panelModel.getDimension().getWidth() + n/2),
                (int) panelModel.getDimension().getHeight()));

    }
    private void moveRight(){
        panelModel.setLoc(new Point((int) (panelModel.getLoc().getX() + n/2), (int) panelModel.getLoc().getY()));
        panelModel.setDimension (new Dimension((int) (panelModel.getDimension().getWidth() + n),
                (int) panelModel.getDimension().getHeight()));
    }
    private void moveUp(){
        panelModel.setLoc(new Point((int) panelModel.getLoc().getX(), (int) (panelModel.getLoc().getY()- n)));
        panelModel.setDimension (new Dimension((int) panelModel.getDimension().getWidth(),
                (int) (panelModel.getDimension().getHeight()+ n/2)));

    }
    private void moveDown(){
        panelModel.setLoc(new Point((int) panelModel.getLoc().getX(), (int) (panelModel.getLoc().getY() +  n/2)));
        panelModel.setDimension (new Dimension((int) panelModel.getDimension().getWidth(),
                (int) (panelModel.getDimension().getHeight()+ n)));
    }

    public double getSecond() {
        return second;
    }
}
