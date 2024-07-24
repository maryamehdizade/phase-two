package controller;
import controller.Util.*;
import controller.model.Cerberus;
import model.characterModel.enemy.boss.Attacks;
import model.characterModel.enemy.boss.BossModel;
import model.model.GamePanelModel;
import model.characterModel.BulletModel;
import model.characterModel.PlayerModel;
import model.characterModel.enemy.*;
import model.model.Enemy;
import model.movement.Collidable;
import model.movement.Movable;
import sound.Sound;
import view.charactersView.BulletView;
import view.charactersView.PlayerView;
import view.charactersView.boss.BossView;
import view.charactersView.enemy.*;
import view.drawable.Drawable;
import view.pages.*;
import view.pages.Menu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

import static controller.Util.BossHandler.attack;
import static controller.Util.BossHandler.attacks;
import static controller.Controller.*;
import static controller.Util.EnemyHandler.addingEnemies;
import static controller.Util.Util.*;
import static controller.Util.Waves.*;
import static controller.constants.Constant.FRAME_DIMENSION;
import static controller.constants.Constant.PANEL_DIMENSION;
import static controller.constants.EntityConstants.BALL_SIZE;
import static controller.constants.EntityConstants.SMALL_BALL_SIZE;
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
    public static void setUpdate(Update u){
        update = u;
    }
    public GamePanel panel;
    public Timer time;
    public Timer view;
    public Timer model;
    public double a ;
    public int waveTime;
    boolean over;
    public DataBase dataBase;
    private Waves waves;
    Random random;
    public boolean phaseTwo;
    GamePanelModel panelModel;
    private void initialDataBase(){
        dataBase = DataBase.getDataBase();
        panelModel = dataBase.getGamePanelModel();
    }
    private Util util;
    public BossHandler bossHandler;
    public Update() {
        random = new Random();

        initialDataBase();
        dataBase.bound = Menu.getMenu().bound;

        panel = creatGamePanel(panelModel);
        a = Menu.getMenu().aa;
        panel.playerView = createPlayerView();
        panel.getDrawables().add(panel.playerView);
        view = new Timer((int) FRAME_UPDATE_TIME, e -> updateView()){{setCoalesce(true);}};
        view.start();
        model = new Timer((int) MODEL_UPDATE_TIME, e -> updateModel()){{setCoalesce(true);}};
        model.start();


        time = new Timer(1000,e -> {
            if(dataBase.dismaySec !=0){
                dataBase.dismaySec++;
                if(dataBase.dismaySec == 10){
                    dataBase.dismaySec = 0;
                    stopEnemies();
                }
            }if(dataBase.slumberSec != 0) {
                dataBase.slumberSec++;
                update.d = true;
                if (dataBase.slumberSec == 11) {
                    dataBase.slumberSec = 0;
                    update.d = false;
                    stopEnemies();
                }
            }
            if(dataBase.getSlaughterSec() !=0||dataBase.isSlaughter()){
                dataBase.setSlaughterSec(dataBase.getSlaughterSec()+1);
                if (dataBase.getSlaughterSec() == 120) {
                    dataBase.setSlaughterSec(0);
                }
            }
            dataBase.second += 1;
            dataBase.waveTime++;
        });
        time.start();

        new CollisionUtil(this);
        util = new Util(panelModel);
        new EnemyHandler(this);
        waves = new Waves(this);


    }
    private void stopEnemies(){
        for (Movable m:panelModel.movables) {
            if(m instanceof Enemy)((Enemy)m).move = true;
        }
    }
    private boolean s=true;
    public void updateView(){
        updatePanelDrawables();
        Util.model = panelModel;
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
            else if(d instanceof WyrmView)updateWrymView((WyrmView) d);
            else if(d instanceof BlackOrbView) updateOrbView((BlackOrbView) d);
            else if(d instanceof BossView)updateBoss((BossView) d);

        }

    }
    private void updateBoss(BossView view){
        if(panelModel.boss.l != null){
            view.l.setLoc(panelModel.boss.l.getLoc());
            view.l.setHp(panelModel.boss.l.getHp());
        }
        if(panelModel.boss.r != null){
            view.r.setLoc(panelModel.boss.r.getLoc());
            view.r.setHp(panelModel.boss.r.getHp());
        }
        if(panelModel.boss.p != null) {
            view.p.setLoc(panelModel.boss.p.getLoc());
            view.p.setHp(panelModel.boss.p.getHp());
        }
        view.setLoc(panelModel.boss.getLoc());
        view.setImg(panelModel.boss.image);
        view.setSize(panelModel.boss.getSize());
        view.setHp(panelModel.boss.getHp());
        view.aoe = panelModel.boss.aoe;

    }
    private void updateRectangleView(RectangleView r) {
        for (Movable movable : panelModel.movables) {
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
        for (Movable movable : panelModel.movables) {
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
        for (Movable movable : panelModel.movables) {
            if (movable instanceof BulletModel) {
                if (movable.getId().equals(r.getId())) {
                    r.setLoc(movable.getLoc());
                }
            }
        }
    }
    private void updatePlayerView(PlayerView p) {
        p.setLoc(playerViewLocation(panelModel.playerModel));
        p.setXp(playerViewXp(panelModel.playerModel));
        p.setHp(playerViewHp(panelModel.playerModel));
        p.setSize(panelModel.playerModel.getSize());
        p.setxPoints(panelModel.playerModel.getxPoints());
        p.setyPoints(panelModel.playerModel.getyPoints());
        p.setCerberus(panelModel.playerModel.getCerberus());
    }
    private void updateOmenoctView(OmenoctView o){
        for (Movable movable : panelModel.movables) {
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
    private void updateOrbView(BlackOrbView b) {
        for (Movable movable : panelModel.movables) {
            if (movable instanceof BlackOrbModel) {
                if (movable.getId().equals(b.getId())) {
                    b.setCircles(((BlackOrbModel) movable).getCircles());
                    b.setHp(movable.getHp());
                    b.draw = ((BlackOrbModel) movable).move;
                }
            }
        }
    }
    private void updatePanel(){
        panel.setSize(panelModel.getDimension());
        panel.setLocation(panelModel.getLoc());
        panel.setSecond(dataBase.second);
        if(Game.getGame().getPhase() == 0)panel.setWave(panelModel.wave);
        else panel.setWave(dataBase.wave);
    }
    private void updateWrymView(WyrmView v){
        for (Movable movable : panelModel.movables) {
            if (movable instanceof WyrmModel) {
                if (movable.getId().equals(v.getId())) {
                    v.setLoc(movable.getLoc());
                    v.setHp(movable.getHp());
                }
            }
        }
    }
    private void updateEnemyBulletView(EnemyBulletView e) {
        for (Movable m : panelModel.movables) {
            if (m instanceof EnemyBullets) {
                if (m.getId().equals(e.getId())) {
                    e.setLoc(m.getLoc());
                }
            }
        }
    }
    private void updateNecroView(NecropicklView n){
        for (int i = 0; i < panelModel.movables.size(); i++) {
            Movable d = panelModel.movables.get(i);
            if(d instanceof NecropickModel && d.getId().equals(n.getId())){
                n.setLoc(d.getLoc());
                n.visible = ((NecropickModel) d).visible;
                n.setHp(d.getHp());
                n.preLoc = ((NecropickModel) d).preLoc;
            }
        }
    }
    private void uodateArchView(ArchmireView a){
        for (int i = 0; i < panelModel.movables.size(); i++) {
            Movable d = panelModel.movables.get(i);
            if(d instanceof ArchmireModel && d.getId().equals(a.getId())){
                a.setLoc(d.getLoc());
                a.setHp(d.getHp());
                a.setTrace(((ArchmireModel) d).getTrace());
            }
        }
    }

    public void updateModel() {
        for (int i = 0; i < panelModel.movables.size(); i++) {
            Movable m = panelModel.movables.get(i);
            if (m instanceof TriangleModel) updateTriangles(m);
            else if (m instanceof PlayerModel) epsilonCheck();
            else if (m instanceof BulletModel) updateBullets(m);
            else if (m instanceof RectangleModel) updateRecs(m);
            else if (m instanceof Omenoctmodel) updateOmenoct(m);
            else if(m instanceof NecropickModel)updateNecro((NecropickModel) m);
            else if(m instanceof ArchmireModel)updateArchModel((ArchmireModel) m);
            else if(m instanceof WyrmModel)updateWrym(m);
            else if(m instanceof BarricadosModel)updateBar((BarricadosModel) m);
            else if(m instanceof BlackOrbModel)updateOrb((BlackOrbModel) m);
            else if(m instanceof BossModel)updateBossModel();
            else if(m instanceof EnemyBullets)updateEnemyBullet((EnemyBullets) m);

            if(dataBase.dismaySec != 0) {
                for (Movable p :
                        panelModel.movables) {
                    if (p instanceof Enemy && m.collides() && distance(m.getLoc(),panelModel.playerModel.getLoc()) <= 100)
                        ((Enemy)p).move = false;
                }
            }
            if(dataBase.slumberSec != 0) {
                for (Movable p : panelModel.movables)
                    if (p instanceof Enemy) ((Enemy) p).move = false;

            }
        }

        if(panelModel.boss != null)bossHandler = new BossHandler(this);
        if (Game.getGame().getPhase() == 0) phaseOne();
        else phaseTwo();
        updateCollectable();
        victory();
    }
    public boolean d;
    private void updateBar(BarricadosModel b){
        if(b.sec >=70){
            b.timer.stop();
            panelModel.movables.remove(b);
            for (int i = 0; i < panel.getDrawables().size(); i++) {
                Drawable d = panel.getDrawables().get(i);
                if(d instanceof BarricadosView&&d.getId().equals(b.getId()))
                    panel.getDrawables().remove(d);
            }
        }
        checkCollision(b);
    }
    private void epsilonCheck(){
        updateEpsilon();
        moveEpsilon();
    }
    private void timeCheck(){
        if(dataBase.second <= 1 && s){
            initialGame();
            s = false;
        }
        if(dataBase.second >= 10) {
            if(dataBase.handler.skills.get(SkillTree.names.athena)) {
                if(random.nextDouble(0,100)<= panelModel.playerModel.melampus)panelModel.shrinkage();
            }
        }
    }
    private void initialGame(){
        panelModel.shrinkage();
    }
    private void phaseOne(){
        waveCheck();
        Wave();
        dataBase.bound = Waves.bound;
        timeCheck();
    }
    private void phaseTwo() {
        panelModel.setDimension(new Dimension((int) (FRAME_DIMENSION.getWidth() - 30),
                (int) (FRAME_DIMENSION.getHeight()-30)));
        panelModel.setLoc(new Point(15,15));
        if(!phaseTwo){
            waves=new Waves(this);
            phaseTwo = true;
        }
        wave();
        if(!d) addingEnemies();

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
        if (dataBase.second % 2 == 0) m.setSpeed(1);
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
                , panelModel.playerModel.getLoc().getX(), panelModel.playerModel.getLoc().getY()) >= 200) {
            m.setSpeed(2);
        } else {
            m.setSpeed(1);
        }
        m.move();
        checkCollision(m);
    }
    private void updateWrym(Movable m){
        m.move();
        checkCollision(m);

        createBullet(m);
    }
    private void updateOmenoct(Movable m) {
        m.setPanelW(panel.getWidth());
        m.setPanelH(panel.getHeight());
        m.move();
        checkCollision(m);

        createBullet(m);
    }
    private void updateEnemyBullet(EnemyBullets e) {
        e.move();
        e.collision(panelModel.playerModel);
        boolean x = bulletIsOutSideOfPanel(e, panelModel);
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
            panelModel.movables.remove(e);

        }
    }
    private void updateEpsilon(){
         panelModel.playerModel.setPanelH(panel.getHeight());
         panelModel.playerModel.setPanelW(panel.getWidth());
         panelModel.playerModel.setPoints();

    }
    private void updateNecro(NecropickModel necro){
        if(necro.move) {
            if (necro.sec % 8 == 0 && necro.sec != 0) {
                necro.visible = false;
                necro.collides = false;
                necro.bullets = 0;
                necro.sec = 0;
            } else if (necro.sec % 2 == 0 && !necro.visible && necro.sec != 0 && Objects.equals(necro.preLoc, new Point2D.Double(0, 0))) {
                necro.preLoc = necro.findPlayer();
            } else if (necro.sec % 4 == 0 && necro.sec != 0 && !necro.visible) {
                necro.findPlayer(necro.getLoc());
                necro.visible = true;
                necro.collides = true;
                necro.sec = 0;
                necro.preLoc = new Point2D.Double();
            }
            if (necro.visible) {
                if (necro.bullets < 8 && Math.random() < 0.004) {
                    createRandomBullet(necro);
                    necro.bullets++;
                }
            }
        }
        checkCollision(necro);

    }
    private void updateArchModel(ArchmireModel arc){
        arc.move();
        checkCollision(arc);
    }
    private void updateOrb(BlackOrbModel orb){
        checkCollision(orb);
        for (int i = 0; i < orb.getCircles().size(); i++) {
            if(orb.getCircles().get(i).getHp()<=0){
                orb.getCircles().remove(i);
            }
        }
        if(orb.getCircles().isEmpty()){
            entityDeath(orb);
        }
    }
    private void updateBossModel(){
        panelModel.boss.move();
        if(panelModel.boss.inPlace)attack();

        checkCollision(panelModel.boss);
        if(panelModel.boss.r!=null)checkCollision(panelModel.boss.r);
        if(panelModel.boss.l!=null)checkCollision(panelModel.boss.l);
        if(panelModel.boss.p!= null)checkCollision(panelModel.boss.p);

        squeezeCheck();
        projectileCheck();
        vomitCheck();
        rapidFireCheck();
        powerPunchCheck();
        quakeCheck();
        slapCheck();
    }
    private void createRandomBullet(Movable n){
        EnemyBullets e = new EnemyBullets(centerLoc(n), new Point2D.Double(
                Math.random()*panelModel.getDimension().getWidth(),
                panelModel.getDimension().getHeight()*Math.random()), (Enemy) n, true);
        panelModel.movables.add(e);
        panel.getDrawables().add(createEnemyBulletView(e));
    }
    private void createBullet(Movable m){
        if (random.nextDouble(0,100) < 0.5) {
            EnemyBullets b = new EnemyBullets(centerLoc(m), centerLoc(panelModel.playerModel), (Enemy) m, true);
            panelModel.movables.add(b);
            panel.getDrawables().add(createEnemyBulletView(b));
        }
    }
    private void moveEpsilon() {
         panelModel.playerModel.move();
        if ( panelModel.playerModel.isdForce()) {
             panelModel.playerModel.setYvelocity( panelModel.playerModel.getYvelocity() + a);
             panelModel.playerModel.move( panelModel.playerModel.getYvelocity());
        }
        if ( panelModel.playerModel.isuForce()) {
             panelModel.playerModel.setYvelocity( panelModel.playerModel.getYvelocity()+ a);
             panelModel.playerModel.move(- panelModel.playerModel.getYvelocity());
        }
        if ( panelModel.playerModel.isrForce()) {
             panelModel.playerModel.setXvelocity( panelModel.playerModel.getXvelocity()+ a);
             panelModel.playerModel.move( panelModel.playerModel.getXvelocity());
        }
        if ( panelModel.playerModel.islForce()) {
             panelModel.playerModel.setXvelocity( panelModel.playerModel.getXvelocity()+ a);
             panelModel.playerModel.move(- panelModel.playerModel.getXvelocity());
        }
        if ( panelModel.playerModel.isR0Force()){
             panelModel.playerModel.setXvelocity( panelModel.playerModel.getXvelocity()- a);
            if( panelModel.playerModel.getXvelocity() <= 0){
                 panelModel.playerModel.setR0Force(false);
                 panelModel.playerModel.setXvelocity(0);
            }
             panelModel.playerModel.move( panelModel.playerModel.getXvelocity());
        }
        if ( panelModel.playerModel.isL0Force()) {
             panelModel.playerModel.setXvelocity( panelModel.playerModel.getXvelocity()- a);
            if( panelModel.playerModel.getXvelocity() <= 0){
                 panelModel.playerModel.setL0Force(false);
                 panelModel.playerModel.setXvelocity(0);
            }
             panelModel.playerModel.move(- panelModel.playerModel.getXvelocity());
        }
        if ( panelModel.playerModel.isU0Force()) {
             panelModel.playerModel.setYvelocity( panelModel.playerModel.getYvelocity()- a);
            if( panelModel.playerModel.getYvelocity() <= 0){
                 panelModel.playerModel.setU0Force(false);
                 panelModel.playerModel.setYvelocity(0);
            }
             panelModel.playerModel.move(- panelModel.playerModel.getYvelocity());
        }
        if ( panelModel.playerModel.isD0Force()) {
             panelModel.playerModel.setYvelocity( panelModel.playerModel.getYvelocity()- a);
            if( panelModel.playerModel.getYvelocity() <= 0){
                 panelModel.playerModel.setD0Force(false);
                 panelModel.playerModel.setYvelocity(0);
            }
             panelModel.playerModel.move( panelModel.playerModel.getYvelocity());
        }

        wallCheck();
        getC();
    }
    private void wallCheck(){
        if ( panelModel.playerModel.getLocation().getY() + BALL_SIZE> panel.getHeight()) {
             panelModel.playerModel.setLocation(
                    new Point2D.Double( panelModel.playerModel.getLocation().getX(), panel.getHeight() - BALL_SIZE - 5));
        }else if( panelModel.playerModel.getLocation().getY() < 2){
             panelModel.playerModel.setLocation(
                    new Point2D.Double( panelModel.playerModel.getLocation().getX(),  10));
        }
        if( panelModel.playerModel.getLocation().getX() + BALL_SIZE > panel.getWidth()){
             panelModel.playerModel.setLocation(
                    new Point2D.Double(panel.getWidth() - BALL_SIZE ,panelModel.playerModel.getLocation().getY()));
        }else if( panelModel.playerModel.getLocation().getX()  < 2){
             panelModel.playerModel.setLocation(
                    new Point2D.Double(10, panelModel.playerModel.getLocation().getY()));

        }
    }
    private void getC(){
        for (int i = 0; i <  dataBase.collectableModels.size(); i++) {
            CollectableModel c = dataBase.collectableModels.get(i);
            if (Math.abs(c.getLoc().getX() - panelModel.playerModel.getLocation().getX()) <= 13 &&
                    Math.abs(c.getLoc().getY() - panelModel.playerModel.getLocation().getY()) <= 13) {

                dataBase.collectableModels.get(i).timer.stop();
                panelModel.playerModel.setXp(panelModel.playerModel.getXp() + c.getCreator().collectablesXp);

                removeCollectable(c);
                dataBase.collectableModels.remove(i);



            }
        }
    }
    private void checkCollision(Movable movable){
        Collidable c;
        for (int i = 0; i < panelModel.movables.size(); i++) {
            if(panelModel.movables.get(i) instanceof BlackOrbModel){
                for (int j = 0; j < ((BlackOrbModel) panelModel.movables.get(i)).getCircles().size(); j++) {
                    c = ((BlackOrbModel) panelModel.movables.get(i)).getCircles().get(j);
                    c.collision(movable);
                }
            }
             else if(!(panelModel.movables.get(i) instanceof EnemyBullets)){
                c = (Collidable) panelModel.movables.get(i);
                c.collision(movable);
            }
        }
        if(!(movable instanceof PlayerModel)&&!(movable instanceof BulletModel)&&!(movable instanceof EnemyBullets)){
            for (int i = 0; i < panelModel.playerModel.getCerberus().size();i++) {
                Cerberus p = panelModel.playerModel.getCerberus().get(i);
                if(!(movable instanceof BlackOrbModel)){
                    if(distance(centerLoc(movable),new Point2D.Double(p.getX() + SMALL_BALL_SIZE/2.0, p.getY() + SMALL_BALL_SIZE/2.0))
                        <= movable.size()/2.0) {
                        cerberus(p,movable);
                    }
                }else{
                    for (int j = 0; j < ((BlackOrbModel) movable).getCircles().size(); j++) {
                        if(distance(centerLoc(((BlackOrbModel) movable).getCircles().get(j)), p) <=
                                ((BlackOrbModel) movable).getCircles().get(j).size()/2.0){
                            cerberus(p,((BlackOrbModel) movable).getCircles().get(j));
                        }
                    }
                }
            }
        }
    }
    Timer t;
    private void rapidFireCheck(){
        if(attacks.contains(Attacks.RapidFire)){
            if (random.nextDouble(0,100) < 0.85) createRandomBullet(panelModel.boss);
            panelModel.boss.rapidFire++;
            if(panelModel.boss.rapidFire>=500){
                panelModel.boss.rapidFire =0;
                attacks.remove(Attacks.RapidFire);
            }
        }
    }
    private void quakeCheck(){
        if(attacks.contains(Attacks.Quake)){
            panelModel.boss.quake();
        }
    }
    private void powerPunchCheck(){
        if(attacks.contains(Attacks.powerPunch)){
            panelModel.boss.powerPunch();
        }
    }
    private void squeezeCheck(){
        if(attacks.contains(Attacks.squeeze)){
            panelModel.boss.squeeze();
            if(panelModel.boss.squeeze>=300){
                panelModel.boss.squeeze =0;
                attacks.remove(Attacks.squeeze);
            }
        }
    }
    private void projectileCheck(){
        if(attacks.contains(Attacks.projectile)) {
            panelModel.boss.projectile();
            if (random.nextDouble(0,100) < 0.5) createBullet(panelModel.boss);
            if(panelModel.boss.projectileCount>=500){
                panelModel.boss.projectileCount =0;
                panelModel.boss.angle= 0;
                attacks.remove(Attacks.projectile);
            }
        }
    }
    private void slapCheck(){
        if(attacks.contains(Attacks.Slap)) {
            panelModel.boss.slap();
        }
    }
    private void vomitCheck(){
        if(attacks.contains(Attacks.vomit)){
            panelModel.boss.vulnerable = true;
            panelModel.boss.vomit();
            bossAoe();
            attacks.remove(Attacks.projectile);
            if(panelModel.boss.vomitCount >= 500){
                panelModel.boss.reset();
                attacks.remove(Attacks.vomit);
            }
        }
    }
    Timer p;
    public void phaseTwoVic() {
        try {
            panelModel.boss.setImage(ImageIO.read(new File("C:\\Users\\EPSILON\\IdeaProjects\\image\\dead.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        p = new Timer(15,e->{
            if(panelModel.boss.getSize()>= -100)panelModel.boss.setSize(panelModel.boss.getSize() - 2);
            attacks.clear();
            if (panelModel.boss.getSize() <= -90) {
                panelModel.movables.remove(panelModel.boss);
                if(!panelModel.victory)panelModel.playerModel.setXp(panelModel.playerModel.getXp()+ 250);
                panelModel.victory = true;
                victory();
            }});
        p.start();
        model.stop();
    }
    private void victory() {
        if (panelModel.victory) {
            Sound.sound().Victory();
            model.stop();
            view.stop();
            time.stop();
            p.stop();
            t = new Timer(10, e -> {
                v();
                updatePlayerView(panel.playerView);
            });
            t.start();
        }
    }
    private void v(){
        if(panelModel.getDimension().getWidth()  + 200 >=  panelModel.playerModel.getSize()) {
             panelModel.playerModel.setSize(panelModel.playerModel.getSize() + 2);
             panelModel.playerModel.setPoints();
            if( panelModel.playerModel.getLocation().getX() <  panelModel.getDimension().getWidth())  panelModel.playerModel.setLocation(new Point2D.Double
                    ( panelModel.playerModel.getLocation().getX() - 1,  panelModel.playerModel.getLocation().getY() - 1));
        }

        if( panelModel.playerModel.getSize() > panelModel.getDimension().getWidth()) v1();
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
        p.stop();
        t.stop();
        over = true;
        PlayerModel.defuse();
        Sound.sound().stop();
        new GameOver(this);
    }

}
