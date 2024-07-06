package controller;

import model.characterModel.BulletModel;
import model.characterModel.PlayerModel;
import model.characterModel.enemy.CollectableModel;
import model.characterModel.enemy.RectangleModel;
import model.characterModel.enemy.TriangleModel;
import model.movement.Movable;
import sound.Sound;
import view.charactersView.BulletView;
import view.charactersView.PlayerView;
import view.charactersView.enemy.CollectableView;
import view.charactersView.enemy.RectangleView;
import view.charactersView.enemy.TriangleView;
import view.drawable.Drawable;
import view.pages.GameOver;
import view.pages.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

import static controller.Constant.*;
import static controller.Controller.*;
import static controller.Util.*;

public class Update {
    public GamePanel panel;

    public Timer time;
    public Timer view;
    public   Timer model;
    private int impactV = 4;
    private final int n = 20;

    public double a ;
    private double second;
    boolean over;
    public DataBase dataBase;

    Point2D collision ;
    Point2D collision2;
    private void initialDataBase(){
        DataBase.setDataBase(new DataBase(this));
        dataBase = DataBase.getDataBase();
        panel.playerView = createPlayerView();
        panel.getDrawables().add(panel.playerView);
    }

    public Update(GamePanel panel) {


        this.panel = panel;
        a = this.panel.game.getMenu().aa;

        initialDataBase();
        view = new Timer((int) FRAME_UPDATE_TIME, e -> updateView()){{setCoalesce(true);}};
        view.start();
        model = new Timer((int) MODEL_UPDATE_TIME, e -> updateModel()){{setCoalesce(true);}};
        model.start();


        time = new Timer(1000,e -> second += 1);
        time.start();

    }
    public void updateView(){

        updatePanelDrawables();
    }
    private void updatePanelDrawables(){
        for(Drawable d: panel.getDrawables()){
            if(d instanceof PlayerView)updatePlayerView((PlayerView) d);
            if(d instanceof RectangleView)updateRectangleView((RectangleView) d);
            if(d instanceof TriangleView)updateTrianglesView((TriangleView) d);
            if(d instanceof BulletView)updateBulletsView((BulletView) d);
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

    //model
    public void updateModel(){
        epsilonCheck();
        updateBullets();
        if(panel.phase == 1) {
            phaseOne();
        }else{

        }
        updateCollectable();

        victory();
        timeCheck();
        setPanelSize();


    }
    private void epsilonCheck(){
        updateEpsilon();
        moveEpsilon();
    }
    private void timeCheck(){
        if(second <= 0.1){
            initialGame();
        }
        if(second >= 10) {
            shrinkage();
        }
    }
    private void setPanelSize(){
        panel.setSize(panel.getDimension());
        panel.setLocation(panel.getLoc());
    }
    private void shrinkage(){
        xmin();
        ymin();
    }
    private void initialGame(){
        shrinkage();
        setPanelSize();
    }
    private void phaseOne(){
        updateRecs();
        updateTriangles();
        waveCheck();
        addingEnemies();
        Wave();
    }
    private void phaseTwo(){

    }

    public void xmin(){
        if(panel.getDimension().width > MIN_SIZE.width) {
            panel.getDimension().width -= 1;
            if(panel.getLoc().getX() < 200) panel.getLoc().setLocation(panel.getLoc().getX() + 0.5, panel.getLoc().getY() );
        }
        if( dataBase.playerModel.getLocation().getX() + BALL_SIZE > panel.getDimension().getWidth()){
             dataBase.playerModel.setLocation(
                    new Point2D.Double(panel.getDimension().getWidth() - BALL_SIZE , dataBase.playerModel.getLocation().getY()));
        }else if( dataBase.playerModel.getLocation().getX()  < 2){
             dataBase.playerModel.setLocation(
                    new Point2D.Double(5, dataBase.playerModel.getLocation().getY()));

        }

    }
    public void ymin(){
        if(panel.getDimension().height > MIN_SIZE.height) {
            panel.getDimension().height -= 1;
            if(panel.getLoc().getY() < 200) panel.getLoc().setLocation(panel.getLoc().getX(), panel.getLoc().getY() + 0.5);
        }
        if ( dataBase.playerModel.getLocation().getY() + BALL_SIZE> panel.getDimension().getHeight()) {
             dataBase.playerModel.setLocation(
                    new Point2D.Double( dataBase.playerModel.getLocation().getX(), panel.getDimension().getHeight() - BALL_SIZE - 5));
        }else if( dataBase.playerModel.getLocation().getY() < 2){
             dataBase.playerModel.setLocation(
                    new Point2D.Double( dataBase.playerModel.getLocation().getX(),  5));
        }
    }

    public void Wave() {
        if (panel.enemies >= 10 && panel.wave1 && panel.start && dataBase.movables.size() == 1) {

            waveChange();

        } else if (panel.enemies >= 15 && panel.wave2 && dataBase.movables.size() == 1) {
            waveChange();

        } else if (panel.wave == 3 && dataBase.movables.size() == 1 && panel.enemies >= 20) {
            panel.victory = true;
        }
    }
    private void waveChange(){
        Sound.sound().wave();
        panel.wave++;
        panel.enemies = 0;
        panel.count = 0;
    }
    private void waveCheck(){
        if(panel.wave == 2 && !panel.wave2){
            if(panel.count == 0) {
                panel.bound -= 20;
            }panel.count++;
            panel.wave2 = true;
            panel.wave1 = false;
        }
        if(panel.wave == 3 && !panel.wave3){
            if(panel.count == 0) {
                panel.bound -= 20;
            }panel.count++;
            panel.wave3 = true;
            panel.wave2 = false;
        }

    }
    private void addingEnemies(){
        if (panel.random.nextDouble(0, panel.bound) < 1) {
            if((panel.wave == 1 && panel.enemies <= 10) || (panel.wave == 2 && panel.enemies <= 15) ||
                    (panel.wave == 3 && panel.enemies <= 20)) {
                Sound.sound().entrance();
                TriangleModel t1 = new TriangleModel();
                dataBase.movables.add(t1);
                panel.getDrawables().add(createTriangleView(t1));
                panel.enemies ++;
            }
            panel.start = true;
        }

        if (panel.random.nextDouble(0, panel.bound) < 1) {
            if((panel.wave == 1 && panel.enemies <= 10) || (panel.wave == 2 && panel.enemies <= 15) ||
                    (panel.wave == 3 && panel.enemies <= 20)) {
                Sound.sound().entrance();
                RectangleModel r1 = new RectangleModel();
                dataBase.movables.add(r1);
                panel.getDrawables().add(createRectView(r1));
                panel.enemies ++;
            }
            panel.start = true;
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
    private void updateRecs(){
        for (int i = 0; i < dataBase.movables.size(); i++) {
            if(dataBase.movables.get(i) instanceof RectangleModel) {
                if (new Random().nextDouble(0, 50) <= 1) {
                    dataBase.movables.get(i).setSpeed(2);
                }
                if (second % 2 == 0) dataBase.movables.get(i).setSpeed(1);
                dataBase.movables.get(i).move();
                checkCollision(dataBase.movables.get(i));
            }
        }
    }
    private void updateBullets(){
        for (int i = 0; i <  dataBase.movables.size(); i++) {
            if(dataBase.movables.get(i) instanceof BulletModel) {

                dataBase.movables.get(i).setPanelH(panel.getHeight());
                dataBase.movables.get(i).setPanelW(panel.getWidth());

                int a = dataBase.movables.get(i).move();
                if (a == 1) moveLeft();
                else if (a == 2) moveRight();
                else if (a == 3) moveUp();
                else if (a == 4) moveDown();

                if (a != 0) {
                    impact(bulletCenter((BulletModel) dataBase.movables.get(i)), 50);
                    removeBullet(i);
                }else{
                    checkCollision(dataBase.movables.get(i));
                }

            }
        }
    }

    private void updateTriangles(){
        for (int i = 0; i <  dataBase.movables.size() ; i++) {
            if (distance(dataBase.movables.get(i).getLoc().getX(), dataBase.movables.get(i).getLoc().getY()
                    , dataBase.playerModel.getLoc().getX(), dataBase.playerModel.getLoc().getY()) >= 200) {
                dataBase.movables.get(i).setSpeed(2);
            } else {
                dataBase.movables.get(i).setSpeed(1);
            }
            dataBase.movables.get(i).move();
            checkCollision(dataBase.movables.get(i));
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


                removeCollectable(dataBase.collectableModels.get(i));
                dataBase.collectableModels.remove(i);

                dataBase.playerModel.setXp(dataBase.playerModel.getXp() + 5);

            }
        }
    }

    private void checkCollision(Movable movable){
        if (movable instanceof BulletModel) {
            checkBulletCollisions((BulletModel) movable);
        } else if (movable instanceof RectangleModel) {
            checkRectColiisions((RectangleModel) movable);
        } else if (movable instanceof TriangleModel) {
            checkTriCollisions((TriangleModel) movable);
        }
    }
    private void checkBulletCollisions(BulletModel movable){
        for (int i = 0; i < dataBase.movables.size(); i++) {
            if(!dataBase.movables.get(i).equals(movable)){
                if(dataBase.movables.get(i) instanceof RectangleModel)
                    bulletRecCollision((RectangleModel) dataBase.movables.get(i), (BulletModel) movable);
                else if(dataBase.movables.get(i) instanceof TriangleModel)
                    bulletTriangleCollision((TriangleModel) dataBase.movables.get(i), (BulletModel) movable);
            }
        }
    }
    private void checkTriCollisions(TriangleModel movable){
        for (int i = 0; i < dataBase.movables.size(); i++) {
            if (!dataBase.movables.get(i).equals(movable)) {
                if (dataBase.movables.get(i) instanceof PlayerModel)
                    triangleEpsilonCollosion((TriangleModel) movable);
                else if (dataBase.movables.get(i) instanceof TriangleModel)
                    traingleTriangleCollision((TriangleModel) dataBase.movables.get(i), (TriangleModel) movable);
            }
        }
    }
    private void checkRectColiisions(RectangleModel movable){
        for (int i = 0; i < dataBase.movables.size(); i++) {
            if(!dataBase.movables.get(i).equals(movable)){
                if(dataBase.movables.get(i) instanceof PlayerModel)
                    rectEpsilonCollision((RectangleModel) movable);
                else if(dataBase.movables.get(i) instanceof TriangleModel)
                    rectangleTriangleCollision((TriangleModel) dataBase.movables.get(i), (RectangleModel) movable);
                else  if(dataBase.movables.get(i) instanceof RectangleModel)
                    rectRectCollision((RectangleModel) dataBase.movables.get(i), (RectangleModel) movable);
            }
        }
    }
    private void bulletRecCollision(RectangleModel r, BulletModel b){
            Polygon rec = new Polygon(r.getxPoints(),
                    r.getyPoints(), 4);
            if (rec.contains(bulletCenter(b))) {
                removeBullet(b);

                injured(r);
                impact(bulletCenter(b), 50);
            }
    }
    private void rectRectCollision(RectangleModel r,RectangleModel movable) {
        Polygon rec = new Polygon(movable.getxPoints(), movable.getyPoints(), 4);

        for (int j = 0; j < 4; j++) {
            if (rec.contains(new Point2D.Double(r.getxPoints()[j], r.getyPoints()[j]))) {

                impact(new Point2D.Double(r.getxPoints()[j], r.getyPoints()[j]), 50);
            }
        }
    }
    private void rectEpsilonCollision(RectangleModel movable){
        collision = ert( dataBase.playerModel, movable);
        collision2 = er( dataBase.playerModel,movable);
        if(collision != null){
            reduceHp(movable);

            impact(collision, 50);
        }else if(collision2 != null)
            impact(collision2, 50);

        Polygon rec = new Polygon(movable.getxPoints(),
                movable.getyPoints(), 4);

        if(panel.isProteus()){
            for (int i = 0; i <  dataBase.playerModel.getLevelUp(); i++) {
                if(rec.contains( dataBase.playerModel.getxPoints()[i],  dataBase.playerModel.getyPoints()[i])){
                    injured(movable);
                    impact(new Point2D.Double( dataBase.playerModel.getxPoints()[i],  dataBase.playerModel.getyPoints()[i]), 50);
                }
            }

        }
    }
    private void injured(Movable r){
        Sound.sound().injured();
        r.setHp(r.getHp() - panel.getPower());
        if (r.getHp() <= 0) {

            entityDeath(r);
        }
    }

    private void rectangleTriangleCollision(TriangleModel t, RectangleModel movable) {
        Polygon rec = new Polygon(movable.getxPoints(), movable.getyPoints(), 4);

        for (int j = 0; j < 3; j++) {
            if (rec.contains(new Point2D.Double(t.getxPoints()[j], t.getyPoints()[j]))) {

                impact(new Point2D.Double(t.getxPoints()[j], t.getyPoints()[j]), 50);
            }
        }
    }
    private void triangleEpsilonCollosion(TriangleModel movable){
        collision = ert( dataBase.playerModel, movable);
        collision2 = et( dataBase.playerModel, movable);
        if (collision != null) {
            reduceHp(movable);
            impact(collision, 50);
        }else if(collision2 != null)
            impact(collision2, 50);

        Polygon tri = new Polygon(movable.getxPoints(), movable.getyPoints(), 3);

        if(panel.isProteus()){
            for (int i = 0; i <  dataBase.playerModel.getLevelUp(); i++) {
                if(tri.contains( dataBase.playerModel.getxPoints()[i],  dataBase.playerModel.getyPoints()[i])){
                    injured(movable);
                    impact(new Point2D.Double( dataBase.playerModel.getxPoints()[i],  dataBase.playerModel.getyPoints()[i]), 50);
                }
            }
        }
    }
    private void traingleTriangleCollision(TriangleModel t, TriangleModel movable) {
        Polygon tri = new Polygon(movable.getxPoints(), movable.getyPoints(), 3);

        for (int j = 0; j < 3; j++) {
            if (tri.contains(new Point2D.Double(t.getxPoints()[j],
                    t.getyPoints()[j]))) {

                impact(new Point2D.Double(t.getxPoints()[j],
                        t.getyPoints()[j]), 50);
            }
        }
    }
    private void bulletTriangleCollision(TriangleModel t, BulletModel b) {
            Polygon rec = new Polygon(t.getxPoints(),
                    t.getyPoints(), 3);

            if (rec.contains(bulletCenter(b))) {

                removeBullet(b);

                injured(t);

                impact(bulletCenter(b), 50);
            }
    }
    private void entityDeath(Movable m) {
        Sound.sound().death();
        dataBase.movables.remove(m);
        removeEntity(m);
        death(m);
    }
    private void removeEntity(Movable m){
        for (int i = 0; i < panel.getDrawables().size(); i++) {
            if(panel.getDrawables().get(i).getId().equals(m.getId())){
                panel.getDrawables().remove(i);
                break;
            }
        }
    }


    public void impact(Point2D point, double r){
        for (int i = 0; i < dataBase.movables.size(); i ++) {
            Movable m =  dataBase.movables.get(i);
            if (!(m instanceof BulletModel)) {

                double x = 0;
                double y = 0;
                if (m instanceof RectangleModel) {
                    x = Math.abs(rectCenter((RectangleModel) m).getX() - point.getX());
                    y = Math.abs(rectCenter((RectangleModel) m).getY() - point.getY());
                } else if (m instanceof TriangleModel) {
                    x = Math.abs(m.getLoc().getX() - point.getX());
                    y = Math.abs(m.getLoc().getY() - point.getY());
                } else if (m instanceof PlayerModel) {
                    x = Math.abs(playerCenter(((PlayerModel) m)).getX() - point.getX());
                    y = Math.abs(playerCenter(((PlayerModel) m)).getY() - point.getY());
                }
                if (x <= r && y <= r) {
                    double speed = distance(x, y, point.getX(), point.getY()) / 500;
                    m.setImpact(true);
                    setSpeed(point, m, impactV / speed);
                }
            }
        }
    }
    private void setSpeed(Point2D point, Movable m, double impactV){
        double a = -point.getY() + m.getLoc().getY();
        double b = -point.getX() + m.getLoc().getX();

        if(b != 0){
            double angel = Math.atan(a/b);
            if(b < 0){
                m.setXvelocity(-impactV*Math.cos(angel));
                if(a < 0)m.setYvelocity(-impactV*Math.sin(angel));
                else m.setYvelocity(impactV*Math.sin(angel));
            }
            else{
                m.setXvelocity(impactV*Math.cos(angel));
                if(a < 0) m.setYvelocity(impactV*Math.sin(angel));
                else m.setYvelocity(-impactV*Math.sin(angel));
            }

        }else if(a >= 0){
            m.setYvelocity(impactV);
        }else if(a < 0){
            m.setYvelocity(-impactV);
        }
    }
    Timer t;
    private void victory() {
        if (panel.isVictory()) {
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
        if(panel.getDimension().getWidth()  + 200 >=  dataBase.playerModel.size) {
             dataBase.playerModel.size += 2;
             dataBase.playerModel.setPoints();
            if( dataBase.playerModel.getLocation().getX() <  panel.getDimension().getWidth())  dataBase.playerModel.setLocation(new Point2D.Double
                    ( dataBase.playerModel.getLocation().getX() - 1,  dataBase.playerModel.getLocation().getY() - 1));
        }

        if( dataBase.playerModel.size > panel.getDimension().getWidth()) v1();
    }
    private void v1(){
        if(panel.getDimension().getWidth() >= 1 && panel.getDimension().getHeight() >= 1){

            panel.setDimension(new Dimension((int) (panel.getDimension().getWidth() - 0.1),
                    (int) (panel.getDimension().getHeight()  - 0.1)));
            panel.setSize(panel.getDimension());
        }
        if(panel.getDimension().getHeight() <= 1 || panel.getDimension().getWidth() <= 1){
            t.stop();
            gameOver();
            panel.game.dispose();
        }
    }

    private void death(Movable movable){
        int n = 1;
        if(movable instanceof TriangleModel)n = 2;
        for (int i = 0; i < n; i++) {
            CollectableModel c = new CollectableModel(addVector(movable.getLoc(), new Point2D.Double(i*10, i *10)));
            dataBase.collectableModels.add(c);
            panel.getDrawables().add(createCollectableView(c));
        }
    }
    private void reduceHp(Movable movable){
        int w = 0;
        if(movable instanceof TriangleModel)w = 10;
        else if(movable instanceof RectangleModel)w = 6;
         dataBase.playerModel.setHp( dataBase.playerModel.getHp() - w);
        if( dataBase.playerModel.getHp() <= 0){
            gameOver();
        }
    }
    private void gameOver(){
        if(!panel.isVictory())Sound.sound().Losing();
        model.stop();
        view.stop();
        time.stop();
        over = true;
        PlayerModel.setPlayer( null);
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
        panel.setLoc(new Point((int) (panel.getLoc().getX() - n), (int) panel.getLoc().getY()));
        panel.setDimension(new Dimension((int) (panel.getDimension().getWidth() + n/2), (int) panel.getDimension().getHeight()));

    }
    private void moveRight(){
        panel.setLoc(new Point((int) (panel.getLoc().getX() + n/2), (int) panel.getLoc().getY()));
        panel.setDimension(new Dimension((int) (panel.getDimension().getWidth() + n), (int) panel.getDimension().getHeight()));
    }
    private void moveUp(){
        panel.setLoc(new Point((int) (panel.getLoc().getX() ), (int) panel.getLoc().getY()- n));
        panel.setDimension(new Dimension((int)(panel.getDimension().getWidth()), (int)panel.getDimension().getHeight()+ n/2));

    }
    private void moveDown(){
        panel.setLoc(new Point((int) (panel.getLoc().getX() ), (int) panel.getLoc().getY() +  n/2));
        panel.setDimension(new Dimension((int)(panel.getDimension().getWidth()), (int)panel.getDimension().getHeight()+ n));
    }

    public double getSecond() {
        return second;
    }
}
