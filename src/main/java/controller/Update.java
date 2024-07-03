package controller;

import model.characterModel.BulletModel;
import model.characterModel.MovePlayer;
import model.characterModel.PlayerModel;
import model.characterModel.enemy.CollectableModel;
import model.characterModel.enemy.RectangleModel;
import model.characterModel.enemy.TriangleModel;
import model.movement.Movable;
import sound.Sound;
import view.charactersView.BulletView;
import view.charactersView.enemy.RectangleView;
import view.charactersView.enemy.TriangleView;
import view.pages.GameOver;
import view.pages.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

import static controller.Constant.BALL_SIZE;
import static controller.Constant.MODEL_UPDATE_TIME;
import static controller.Controller.*;
import static controller.Util.*;

public class Update {
    public   Timer model;

    public Timer time;

    public Timer view;
    public GamePanel panel;
    public double a ;
    private double second;
    Point2D collision ;
    Point2D collision2;
    private boolean impact = false;
    private double aresSec = 0;
    private double acesoSec = 0;
    private double proteusSec = 0;
    public boolean ares ;
    public boolean aceso;
    public boolean proteus;
    public boolean aresC ;
    public boolean acesoC;
    public boolean proteusC;
    private double empowerSec;
    public Update(GamePanel panel) {
        this.panel = panel;
        a = this.panel.game.getMenu().aa;
        view = new Timer((int) 15, e -> updateView()){{setCoalesce(true);}};
        view.start();
        model = new Timer((int) MODEL_UPDATE_TIME, e -> {
            try {
                updateModel();
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }){{setCoalesce(true);}};
        model.start();

        time = new Timer(100,e -> {
            second += 0.1;
            skiilTree();
            store();

        });
        time.start();

    }
    private void store(){
        //store empower
        if(panel.empower) {
            empowerSec += 0.1;
            if (empowerSec >= 15) panel.empower = false;
        }
    }
    private void skiilTree(){
        //skill tree ares
        if(ares || aresC) {
            aresSec += 0.1;
            if (aresSec >= 15 && !aresC) {
                ares = false;
                panel.setPower(5);
                aresSec = 0;
                aresC = true;
            }
            if (aresSec >= 300) {
                aresC = false;
                panel.aresCount = 0;
            }
        }
        //skill tree aceso
        if(aceso || acesoC){
            acesoSec += 0.1;
            if(acesoSec%1 >= 0  && acesoSec%1 <= 0.12 && !acesoC)
                panel.playerModel.setHp(panel.playerModel.getHp() + panel.heal);
            if(acesoSec >= 15 && !acesoC){
                aceso = false;
                acesoSec = 0;
                acesoC = true;
            }
            if(acesoSec >= 300){
                acesoC = false;
                panel.acesoCount = 0;
            }
        }
        //proteus
        if(proteus || proteusC){
            proteusSec += 0.1;
            if(proteusSec >= 10 && !proteusC){
                proteus = false;
                proteusSec = 0;
                proteusC = true;
                panel.setProteus(false);
            }
            if(proteusSec >= 300){
                proteusC = false;
                panel.proteusCount = 0;
            }
        }
    }
    public void updateView(){
        updatePlayerView();
        updateBulletsView();
        updateRectangleView();
        updateTrianglesView();
    }
    private void updateRectangleView(){
        for (RectangleView r : panel.getRectangleView()) {
            for (RectangleModel model: panel.getRectangleModels()) {
                if(model.getId().equals(r.getId())){
                    r.setLoc(model.getLoc());
                    r.setHp(model.getHp());
                }
            }
        }
    }
    private void updateTrianglesView(){
        for (TriangleView t : panel.getTriangleViews()) {
            for (TriangleModel model: panel.getTriangleModels()) {
                if(model.getId().equals(t.getId())){
                    t.setX1(model.getX1());
                    t.setX2(model.getX2());
                    t.setX3(model.getX3());
                    t.setY1(model.getY1());
                    t.setY2(model.getY2());
                    t.setY3(model.getY3());
                    t.setHp(model.getHp());

                }
            }
        }
    }
    private void updateBulletsView(){
        for (BulletView b : panel.getBullets()) {
            for (BulletModel m : panel.getBulletsModel()) {
                if(m.getId().equals(b.getId())){
                    b.setLoc(m.getLoc());
                }
            }
        }
    }
    private void updatePlayerView(){
        panel.playerView.setLocation(playerViewLocation(panel.playerModel));
        panel.playerView.setXp(playerViewXp(panel.playerModel));
        panel.playerView.setHp(playerViewHp(panel.playerModel));
        panel.playerView.size = panel.playerModel.size;
    }

    //model
    public void updateModel() throws Exception{
        moveEpsilon();
        updateBullets();
        updateRecs();
        updateTriangles();
        updateCollectable();
        victory();
        if(second <= 0.1){
            panel.xmin();
            panel.ymin();
            panel.setSize(panel.getDimension());
            panel.setLocation(panel.getLoc());
        }
        if(second >= 100) {
            panel.xmin();
            panel.ymin();

        }
        panel.setSize(panel.getDimension());
        panel.setLocation(panel.getLoc());
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
        if (panel.random.nextDouble(0, panel.bound) < 1) {
            if((panel.wave == 1 && panel.enemies <= 10) || (panel.wave == 2 && panel.enemies <= 15) ||
                    (panel.wave == 3 && panel.enemies <= 20)) {
                Sound.sound().entrance();
                RectangleModel r1 = new RectangleModel(panel);
                panel.getRectangleModels().add(r1);
                panel.getRectangleView().add(createRectView(r1));
                panel.getMovables().add(r1);
                panel.enemies ++;
            }
            panel.start = true;
        }
        if (panel.random.nextDouble(0, panel.bound) < 1) {
            if((panel.wave == 1 && panel.enemies <= 10) || (panel.wave == 2 && panel.enemies <= 15) ||
                    (panel.wave == 3 && panel.enemies <= 20)) {
                Sound.sound().entrance();
                TriangleModel t1 = new TriangleModel(panel);
                panel.getTriangleModels().add(t1);
                panel.getTriangleViews().add(createTriangleView(t1));
                panel.getMovables().add(t1);
                panel.enemies ++;
            }
            panel.start = true;
        }
        panel.Wave();
    }
    private void updateCollectable(){
        for (int i = 0; i < panel.getCollectableModels().size();i ++){
            if(panel.getCollectableModels().get(i).getSecond() >= 10){
                panel.getCollectableModels().remove(i);
                panel.getCollectableViews().remove(i);
            }
        }
    }
    private void updateRecs() throws Exception{
        for (int i = 0; i < panel.getRectangleModels().size(); i++) {
            if(new Random().nextDouble(0,50) <= 1){
                panel.getRectangleModels().get(i).setSpeed(2);
            }if(second % 2 == 0)panel.getRectangleModels().get(i).setSpeed(1);
            panel.getRectangleModels().get(i).move();
            checkCollision(panel.getRectangleModels().get(i));
        }
    }
    private void updateBullets() throws Exception{
        for (int i = 0; i < panel.getBulletsModel().size(); i++) {
            int a = panel.getBulletsModel().get(i).move();
            if(a == 1)moveLeft();
            else if(a == 2)moveRight();
            else if(a == 3)moveUp();
            else if(a == 4)moveDown();
            checkCollision(panel.getBulletsModel().get(i));
            if (a != 0) {
                impact(bulletCenter(panel.getBulletsModel().get(i)), 50);
                removeBullet(i);
            }

        }
    }

    private void updateTriangles() throws Exception{
        for (int i = 0; i < panel.getTriangleModels().size() ; i++) {
            if (Math.abs(panel.getTriangleModels().get(i).getY3() - panel.playerModel.getLocation().getY()) >= 200) {
                panel.getTriangleModels().get(i).setSpeed(2);
            }else{
                panel.getTriangleModels().get(i).setSpeed(1);
            }
            panel.getTriangleModels().get(i).move();
            checkCollision(panel.getTriangleModels().get(i));
        }
    }

    private void moveEpsilon(){
        panel.movePlayer.move();
        if (panel.movePlayer.isdForce()) {
            panel.movePlayer.setYvelocity(panel.movePlayer.getYvelocity() + a);
            panel.movePlayer.move(panel.movePlayer.getYvelocity());
        }
        if (panel.movePlayer.isuForce()) {
            panel.movePlayer.setYvelocity(panel.movePlayer.getYvelocity()+ a);
            panel.movePlayer.move(-panel.movePlayer.getYvelocity());
        }
        if (panel.movePlayer.isrForce()) {
            panel.movePlayer.setXvelocity(panel.movePlayer.getXvelocity()+ a);
            panel.movePlayer.move(panel.movePlayer.getXvelocity());
        }
        if (panel.movePlayer.islForce()) {
            panel.movePlayer.setXvelocity(panel.movePlayer.getXvelocity()+ a);
            panel.movePlayer.move(-panel.movePlayer.getXvelocity());
        }
        if (panel.movePlayer.isR0Force()){
            panel.movePlayer.setXvelocity(panel.movePlayer.getXvelocity()- a);
            if(panel.movePlayer.getXvelocity() <= 0){
                panel.movePlayer.setR0Force(false);
                panel.movePlayer.setXvelocity(0);
            }
            panel.movePlayer.move(panel.movePlayer.getXvelocity());
        }
        if (panel.movePlayer.isL0Force()) {
            panel.movePlayer.setXvelocity(panel.movePlayer.getXvelocity()- a);
            if(panel.movePlayer.getXvelocity() <= 0){
                panel.movePlayer.setL0Force(false);
                panel.movePlayer.setXvelocity(0);
            }
            panel.movePlayer.move(-panel.movePlayer.getXvelocity());
        }
        if (panel.movePlayer.isU0Force()) {
            panel.movePlayer.setYvelocity(panel.movePlayer.getYvelocity()- a);
            if(panel.movePlayer.getYvelocity() <= 0){
                panel.movePlayer.setU0Force(false);
                panel.movePlayer.setYvelocity(0);
            }
            panel.movePlayer.move(-panel.movePlayer.getYvelocity());
        }
        if (panel.movePlayer.isD0Force()) {
            panel.movePlayer.setYvelocity(panel.movePlayer.getYvelocity()- a);
            if(panel.movePlayer.getYvelocity() <= 0){
                panel.movePlayer.setD0Force(false);
                panel.movePlayer.setYvelocity(0);
            }
            panel.movePlayer.move(panel.movePlayer.getYvelocity());
        }
        //wall
        if (panel.playerModel.getLocation().getY() + BALL_SIZE> panel.getHeight()) {
            panel.playerModel.setLocation(
                    new Point2D.Double(panel.playerModel.getLocation().getX(), panel.getHeight() - BALL_SIZE - 5));
        }else if(panel.playerModel.getLocation().getY() < 2){
            panel.playerModel.setLocation(
                    new Point2D.Double(panel.playerModel.getLocation().getX(),  10));
        }
        if(panel.playerModel.getLocation().getX() + BALL_SIZE > panel.getWidth()){
            panel.playerModel.setLocation(
                    new Point2D.Double(panel.getWidth() - BALL_SIZE ,panel.playerModel.getLocation().getY()));
        }else if(panel.playerModel.getLocation().getX()  < 2){
            panel.playerModel.setLocation(
                    new Point2D.Double(10,panel.playerModel.getLocation().getY()));

        }
        getC();
    }
    private void getC(){
        for (int i = 0; i < panel.getCollectableModels().size(); i++) {
            CollectableModel c = panel.getCollectableModels().get(i);
            if(Math.abs(c.getLoc().getX() - panel.playerModel.getLocation().getX()) <= 13 &&
                    Math.abs(c.getLoc().getY() - panel.playerModel.getLocation().getY()) <= 13 ){

                panel.getCollectableModels().get(i).timer.stop();

                panel.getCollectableModels().get(i).timer.stop();

                panel.getCollectableModels().remove(i);
                panel.getCollectableViews().remove(i);

                panel.playerModel.setXp(panel.playerModel.getXp() + 5);

            }
        }
    }
    private void checkCollision(Movable movable) throws Exception {
        if (movable instanceof BulletModel) {
            bulletRecCollision(movable);
            //tria
            bulletTriangleCollision(movable);

        } else if (movable instanceof RectangleModel) {
            //epsilon
             rectEpsilonCollision(movable);
            //rect
            rectRectCollision(movable);
            //tria
            rectangleTriangleCollision(movable);
        } else if (movable instanceof TriangleModel) {
            //epsilon
            triangleEpsilonCollosion(movable);
            //tria
            traingleTriangleCollision(movable);
        }
    }
    private void bulletRecCollision(Movable movable) throws Exception {
        for (int j = 0; j < panel.getRectangleModels().size(); j++) {
            Polygon rec = new Polygon(panel.getRectangleModels().get(j).getxPoints(),
                    panel.getRectangleModels().get(j).getyPoints(), 4);
            if (rec.contains(bulletCenter((BulletModel) movable))) {
                Sound.sound().injured();
                removeBullet((BulletModel) movable);
                panel.getRectangleModels().get(j).setHp(panel.getRectangleModels().get(j).getHp() - panel.getPower());
                if (panel.getRectangleModels().get(j).getHp() <= 0) {
                    Sound.sound().death();
                    removeFromMovables(panel.getRectangleModels().get(j));
                    death(panel.getRectangleModels().get(j));
                    removeRect(j);
                }
                impact(bulletCenter((BulletModel) movable), 50);
            }
        }
    }
    private void rectRectCollision(Movable movable){
        Polygon rec = new Polygon(movable.getxPoints(),
                movable.getyPoints(), 4);
        for (int i = 0; i < panel.getRectangleModels().size(); i++) {
            if(movable != panel.getRectangleModels().get(i)) {
                for (int j = 0; j < 4; j++) {
                    if (rec.contains(new Point2D.Double(panel.getRectangleModels().get(i).getxPoints()[j],
                            panel.getRectangleModels().get(i).getyPoints()[j]))) {

                        impact(new Point2D.Double(panel.getRectangleModels().get(i).getxPoints()[j],
                                panel.getRectangleModels().get(i).getyPoints()[j]), 50);
                    }
                }
            }
        }
    }
    private void rectEpsilonCollision(Movable movable){
        collision = ert(panel.playerModel, movable);
        collision2 = er(panel.playerModel, (RectangleModel) movable);
        if(collision != null){
            try {
                reduceHp(movable);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            impact(collision, 50);
        }else if(collision2 != null){
            impact(collision2, 50);
        }

        Polygon rec = new Polygon(movable.getxPoints(),
                movable.getyPoints(), 4);
        if(panel.isProteus()){
            if(rec.contains(playerCenter(panel.playerModel).getX(), playerCenter(panel.playerModel).getY() - BALL_SIZE / 3.0)){
                movable.setHp(movable.getHp() - 5);

                impact(new Point2D.Double(playerCenter(panel.playerModel).getX(),
                        playerCenter(panel.playerModel).getY() - BALL_SIZE / 3.0), 50);
            }
        }
    }

    private void rectangleTriangleCollision(Movable movable){
        Polygon rec = new Polygon(movable.getxPoints(),
                movable.getyPoints(), 4);
        for (int i = 0; i < panel.getTriangleModels().size(); i++) {
            for (int j = 0; j < 3; j++) {
                if (rec.contains(new Point2D.Double(panel.getTriangleModels().get(i).getxPoints()[j],
                        panel.getTriangleModels().get(i).getyPoints()[j]))) {

                    impact(new Point2D.Double(panel.getTriangleModels().get(i).getxPoints()[j],
                            panel.getTriangleModels().get(i).getyPoints()[j]), 50);
                }
            }
        }
    }
    private void triangleEpsilonCollosion(Movable movable){
        collision = ert(panel.playerModel, movable);
        collision2 = et(panel.playerModel, (TriangleModel) movable);
        if (collision != null) {
            try {
                reduceHp(movable);
            } catch (Exception e) {
                e.printStackTrace();
            }
            impact(collision, 50);
        }else if(collision2 != null){
            impact(collision2, 50);
        }
        Polygon tri = new Polygon(movable.getxPoints(), movable.getyPoints(), 3);
        if(panel.isProteus()){
            if(tri.contains(playerCenter(panel.playerModel).getX(), playerCenter(panel.playerModel).getY() - BALL_SIZE / 3.0)){
                movable.setHp(movable.getHp() - 5);
                impact(new Point2D.Double(playerCenter(panel.playerModel).getX(),
                        playerCenter(panel.playerModel).getY() - BALL_SIZE / 3.0), 50);
            }
        }
    }
    private void traingleTriangleCollision(Movable movable){
        Polygon tri = new Polygon(movable.getxPoints(), movable.getyPoints(), 3);
        for (int i = 0; i < panel.getTriangleModels().size(); i++) {
            if(movable != panel.getTriangleModels().get(i)) {
                for (int j = 0; j < 3; j++) {
                    if (tri.contains(new Point2D.Double(panel.getTriangleModels().get(i).getxPoints()[j],
                            panel.getTriangleModels().get(i).getyPoints()[j]))) {

                        impact(new Point2D.Double(panel.getTriangleModels().get(i).getxPoints()[j],
                                panel.getTriangleModels().get(i).getyPoints()[j]), 50);
                    }
                }
            }
        }
    }
    private void bulletTriangleCollision(Movable movable){
        for (int p = 0; p < panel.getTriangleModels().size(); p++) {
            Polygon rec = new Polygon(panel.getTriangleModels().get(p).getxPoints(),
                    panel.getTriangleModels().get(p).getyPoints(), 3);
            if(rec.contains(bulletCenter((BulletModel) movable))){
                try {
                    Sound.sound().injured();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
//                if (distance(bulletCenter((BulletModel) movable).getX(), bulletCenter((BulletModel) movable).getY(),
//                        triangleCenter(panel.getTriangleModels().get(p)).getX(), triangleCenter(panel.getTriangleModels().get(p)).getY())
//                        <= (double) TRI_SIZE / 2 + (double) BULLET_SIZE / 2 + 20) {
                removeBullet((BulletModel) movable);
                panel.getTriangleModels().get(p).setHp(panel.getTriangleModels().get(p).getHp() - panel.getPower());
                if (panel.getTriangleModels().get(p).getHp() <= 0) {
                    try {
                        Sound.sound().death();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    removeFromMovables(panel.getTriangleModels().get(p));
                    death(panel.getTriangleModels().get(p));
                    removeTriangle(p);
                }
                impact(bulletCenter((BulletModel) movable), 50);
            }
        }
    }
    private int impactV = 4;

    public void impact(Point2D point, double r){
        //todo:fix
        for (int i = 0; i < panel.getMovables().size(); i ++) {
            Movable m = panel.getMovables().get(i);
            double x = 0;
            double y = 0;
            if(m instanceof RectangleModel){
               x = Math.abs(rectCenter((RectangleModel) m).getX() - point.getX());
               y = Math.abs(rectCenter((RectangleModel) m).getY() - point.getY());
            }
            else if(m instanceof TriangleModel){
                x = Math.abs(m.getLoc().getX() - point.getX());
                y = Math.abs(m.getLoc().getY() - point.getY());
            }else if (m instanceof MovePlayer){
                x = Math.abs(playerCenter(((MovePlayer) m).playerModel).getX() - point.getX());
                y = Math.abs(playerCenter(((MovePlayer) m).playerModel).getY() - point.getY());
            }
            if(x  <= r && y <= r){
                double speed = distance(x, y, point.getX(), point.getY()) / 500;
                m.setImpact(true);
               setSpeed(point, m , impactV / speed);
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
    private void victory()throws Exception{
        if(panel.isVictory()){
            Sound.sound().Victory();
            model.stop();
            view.stop();
            time.stop();
            t = new Timer(10,e -> {
                try {
                    v();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                updatePlayerView();
            });
            t.start();
        }
    }
    private void v() throws Exception{
        if(panel.getDimension().getWidth()  + 200 >= panel.playerModel.size) {
            panel.playerModel.size += 2;
            if(panel.playerModel.getLocation().getX() < panel.getDimension().getWidth()) panel.playerModel.setLocation(new Point2D.Double
                    (panel.playerModel.getLocation().getX() - 1, panel.playerModel.getLocation().getY() - 1));
        }

        if(panel.playerModel.size > panel.getDimension().getWidth()) v1();
    }
    private void v1() throws Exception {
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
            panel.getCollectableModels().add(c);
            panel.getCollectableViews().add(createCollectableView(c));
        }
    }
    private void reduceHp(Movable movable) throws Exception {
        int w = 10;
        if(movable instanceof RectangleModel)w = 6;
        panel.playerModel.setHp(panel.playerModel.getHp() - w);
        if(panel.playerModel.getHp() <= 0){
            gameOver();
        }
    }
    private void gameOver() throws Exception {
        if(!panel.isVictory())Sound.sound().Losing();
        model.stop();
        view.stop();
        time.stop();
        PlayerModel.setPlayer( null);
        Sound.sound().stop();

        new GameOver(this);
    }


    //remove
    private void removeFromMovables(Movable movable){
        for (int i = 0; i < panel.getMovables().size(); i ++) {
            if(movable == panel.getMovables().get(i)){
                removeFromMovables(i);
            }
        }
    }
    private void removeFromMovables(int i) {
     panel.getMovables().remove(i);
    }

    private void removeTriangle(int i){
        panel.getTriangleViews().remove(i);
        panel.getTriangleModels().remove(i);
    }
    private void removeTriangle(TriangleModel t){
        for (int i = 0; i < panel.getTriangleModels().size(); i++) {
            if(panel.getTriangleModels().get(i) == t){
                removeTriangle(i);
            }
        }
    }

    private void removeRect(int i){
        panel.getRectangleModels().remove(i);
        panel.getRectangleView().remove(i);
    }
    private void removeRect(RectangleModel i){
        for (int j = 0; j < panel.getRectangleModels().size(); j++) {
            if(panel.getRectangleModels().get(j) == i){
                removeRect(j);
            }
        }
    }
    private void removeBullet(int i) {
        if(!panel.getBulletsModel().isEmpty()) {
            panel.getBullets().remove(i);
            for (int j = 0; j < panel.getBullets().size(); j++) {
                if (panel.getBullets().get(j).getId().equals(panel.getBulletsModel().get(i).getId())) {
                    panel.getBullets().remove(j);
                    break;
                }
            }
            panel.getBulletsModel().remove(i);
        }
    }
    private void removeBullet(BulletModel bulletModel){
        for (int i = 0; i < panel.getBulletsModel().size(); i++) {
            if(panel.getBulletsModel().get(i).getId().equals(bulletModel.getId()))removeBullet(i);
        }
    }
    private final int n = 20;
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
