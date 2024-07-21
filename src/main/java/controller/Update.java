package controller;
import controller.Util.EnemyHandler;
import controller.Util.Util;
import controller.Util.Waves;
import model.characterModel.enemy.boss.Attacks;
import model.characterModel.enemy.boss.BossModel;
import model.characterModel.enemy.boss.Lhand;
import model.model.GamePanelModel;
import model.characterModel.BulletModel;
import model.characterModel.PlayerModel;
import model.characterModel.enemy.*;
import model.model.Enemy;
import model.movement.Collidable;
import model.movement.Movable;
import controller.Util.CollisionUtil;
import sound.Sound;
import view.charactersView.BulletView;
import view.charactersView.PlayerView;
import view.charactersView.boss.BossView;
import view.charactersView.boss.lHandView;
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

import static controller.BossHandler.attack;
import static controller.BossHandler.attacks;
import static controller.Controller.*;
import static controller.Util.EnemyHandler.addingEnemies;
import static controller.Util.Util.*;
import static controller.Util.Waves.*;
import static controller.constants.Constant.FRAME_DIMENSION;
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
    public boolean dismay;
    public static void remove(){
        update = null;
    }
    public GamePanel panel;
    public Timer time;
    public Timer view;
    public Timer model;
    public double a ;
    public int bound ;
    private int second;
    public int waveTime;
    private int dismaySec;
    boolean over;
    public DataBase dataBase;
    private Waves waves;
    Random random;
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
        bound = Menu.getMenu().bound;

        panel = creatGamePanel(panelModel);
        a = Menu.getMenu().aa;
        panel.playerView = createPlayerView();
        panel.getDrawables().add(panel.playerView);
        view = new Timer((int) FRAME_UPDATE_TIME, e -> updateView()){{setCoalesce(true);}};
        view.start();
        model = new Timer((int) MODEL_UPDATE_TIME, e -> updateModel()){{setCoalesce(true);}};
        model.start();


        time = new Timer(1000,e -> {
            if(dismay){
                dismaySec++;
                if(dismaySec == 10){
                    dismaySec = 0;
                    dismay = false;
                    for (Movable m:dataBase.gamePanelModel.movables) {
                        if(m instanceof Enemy)((Enemy)m).move = true;
                    }
                }
            }
            second += 1;
        });
        time.start();

        new CollisionUtil(this);
        util = new Util(dataBase.gamePanelModel);
        new EnemyHandler(this);
        waves = new Waves(this);


    }
    private boolean s=true;
    public void updateView(){
        updatePanelDrawables();
        Util.model = dataBase.gamePanelModel;
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
            else if (d instanceof BlackOrbView) updateOrbView((BlackOrbView) d);
            else if(d instanceof BossView)updateBoss((BossView) d);

        }

    }
    private void updateBoss(BossView view){
        view.l.setLoc(dataBase.boss.l.getLoc());
        view.l.setHp(dataBase.boss.l.getHp());
//
        view.r.setLoc(dataBase.boss.r.getLoc());
        view.r.setHp(dataBase.boss.r.getHp());
//
//        view.p.setLoc(dataBase.boss.p.getLoc());
//        view.p.setHp(dataBase.boss.p.getHp());
        view.setLoc(dataBase.boss.getLoc());
        view.setImg(dataBase.boss.image);
        view.setHp(dataBase.boss.getHp());
    }
    private void updateRectangleView(RectangleView r) {
        for (Movable movable : dataBase.gamePanelModel.movables) {
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
        for (Movable movable : dataBase.gamePanelModel.movables) {
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
        for (Movable movable : dataBase.gamePanelModel.movables) {
            if (movable instanceof BulletModel) {
                if (movable.getId().equals(r.getId())) {
                    r.setLoc(movable.getLoc());
                }
            }
        }
    }
    private void updatePlayerView(PlayerView p) {
        p.setLoc(playerViewLocation(dataBase.gamePanelModel.playerModel));
        p.setXp(playerViewXp(dataBase.gamePanelModel.playerModel));
        p.setHp(playerViewHp(dataBase.gamePanelModel.playerModel));
        p.setSize(dataBase.gamePanelModel.playerModel.size);
        p.setxPoints(dataBase.gamePanelModel.playerModel.getxPoints());
        p.setyPoints(dataBase.gamePanelModel.playerModel.getyPoints());
    }
    private void updateOmenoctView(OmenoctView o){
        for (Movable movable : dataBase.gamePanelModel.movables) {
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
        for (Movable movable : dataBase.gamePanelModel.movables) {
            if (movable instanceof BlackOrbModel) {
                if (movable.getId().equals(b.getId())) {
                    b.setCircles(((BlackOrbModel) movable).getCircles());
                    b.setHp(movable.getHp());
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
    private void updateWrymView(WyrmView v){
        for (Movable movable : dataBase.gamePanelModel.movables) {
            if (movable instanceof WyrmModel) {
                if (movable.getId().equals(v.getId())) {
                    v.setLoc(movable.getLoc());
                    v.setHp(movable.getHp());
                }
            }
        }
    }
    private void updateEnemyBulletView(EnemyBulletView e){
        for (EnemyBullets m : dataBase.enemyBullets) {
            if (m.getId().equals(e.getId())) {
                e.setLoc(m.getLoc());
            }

        }
    }
    private void updateNecroView(NecropicklView n){
        for (int i = 0; i < dataBase.gamePanelModel.movables.size(); i++) {
            Movable d = dataBase.gamePanelModel.movables.get(i);
            if(d instanceof NecropickModel && d.getId().equals(n.getId())){
                n.setLoc(d.getLoc());
                n.visible = ((NecropickModel) d).visible;
                n.setHp(d.getHp());
                n.preLoc = ((NecropickModel) d).preLoc;
            }
        }
    }
    private void uodateArchView(ArchmireView a){
        for (int i = 0; i < dataBase.gamePanelModel.movables.size(); i++) {
            Movable d = dataBase.gamePanelModel.movables.get(i);
            if(d instanceof ArchmireModel && d.getId().equals(a.getId())){
                a.setLoc(d.getLoc());
                a.setHp(d.getHp());
                a.setTrace(((ArchmireModel) d).getTrace());
            }
        }
    }

    public void updateModel() {
        for (int i = 0; i < dataBase.gamePanelModel.movables.size(); i++) {
            Movable m = dataBase.gamePanelModel.movables.get(i);
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

            if(dismay) {
                for (Movable p :
                        dataBase.gamePanelModel.movables) {
                    if (p instanceof Enemy && m.collides() && distance(m.getLoc(),panelModel.playerModel.getLoc()) <= 100)
                        ((Enemy)p).move = false;
                }
            }
            updateEnemyBullet();
        }
        if(!d) addingEnemies();
        if(dataBase.boss != null)bossHandler = new BossHandler(this);
        if (Game.getGame().getPhase() == 0) phaseOne();
        else phaseTwo();
        updateCollectable();
        victory();
    }
    public boolean d;
    private void updateBar(BarricadosModel b){
        if(b.sec >=120){
            b.timer.stop();
            dataBase.gamePanelModel.movables.remove(b);
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
        if(second <= 1 && s){
            initialGame();
            s = false;
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
        timeCheck();
    }
    private void phaseTwo() {
        dataBase.gamePanelModel.setDimension(new Dimension((int) (FRAME_DIMENSION.getWidth() - 30),
                (int) (FRAME_DIMENSION.getHeight()-30)));
        dataBase.gamePanelModel.setLoc(new Point(15,15));
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
                , dataBase.gamePanelModel.playerModel.getLoc().getX(), dataBase.gamePanelModel.playerModel.getLoc().getY()) >= 200) {
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

        if (random.nextDouble(0,100) < 0.5) {
            EnemyBullets b = new EnemyBullets(centerLoc(m), centerLoc(dataBase.gamePanelModel.playerModel), (Enemy) m, false);
            dataBase.enemyBullets.add(b);
            panel.getDrawables().add(createEnemyBulletView(b));
        }
    }
    private void updateOmenoct(Movable m) {
        m.setPanelW(panel.getWidth());
        m.setPanelH(panel.getHeight());
        m.move();
        checkCollision(m);

        if (random.nextDouble(0,100) < 0.5) {
            EnemyBullets b = new EnemyBullets(centerLoc(m), centerLoc(dataBase.gamePanelModel.playerModel), (Enemy) m, false);
            dataBase.enemyBullets.add(b);
            panel.getDrawables().add(createEnemyBulletView(b));
        }
    }
    private void updateEnemyBullet(){
        for (int j = 0; j < dataBase.enemyBullets.size(); j++) {
            EnemyBullets e = dataBase.enemyBullets.get(j);
            e.move();
            e.collision(dataBase.gamePanelModel.playerModel);
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
         dataBase.gamePanelModel.playerModel.setPanelH(panel.getHeight());
         dataBase.gamePanelModel.playerModel.setPanelW(panel.getWidth());
         dataBase.gamePanelModel.playerModel.setPoints();

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
                createRandomBullet(necro);
                necro.bullets ++;
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
                System.out.println(orb.getCircles().size());
            }
        }
        if(orb.getCircles().isEmpty()){
            entityDeath(orb);
        }
    }
    private void updateBossModel(){
        if(dataBase.boss.inPlace)attack();
        dataBase.boss.move();
        checkCollision(dataBase.boss);
        checkCollision(dataBase.boss.r);
        checkCollision(dataBase.boss.l);
        if(!attacks.isEmpty())dataBase.boss.vulnerable = true;
        else dataBase.boss.vulnerable = false;
        if(attacks.contains(Attacks.squeeze))dataBase.boss.squeeze();
        if(attacks.contains(Attacks.projectile)){
            dataBase.boss.projectile();
            if(Math.random() <0.025)createRandomBullet(dataBase.boss);
        }
    }
    private void createRandomBullet(Movable n){
        EnemyBullets e = new EnemyBullets(centerLoc(n), new Point2D.Double(
                Math.random()*panelModel.getDimension().getWidth(),
                panelModel.getDimension().getHeight()*Math.random()), (Enemy) n, true);
        dataBase.enemyBullets.add(e);
        panel.getDrawables().add(createEnemyBulletView(e));
    }
    private void moveEpsilon() {
         dataBase.gamePanelModel.playerModel.move();
        if ( dataBase.gamePanelModel.playerModel.isdForce()) {
             dataBase.gamePanelModel.playerModel.setYvelocity( dataBase.gamePanelModel.playerModel.getYvelocity() + a);
             dataBase.gamePanelModel.playerModel.move( dataBase.gamePanelModel.playerModel.getYvelocity());
        }
        if ( dataBase.gamePanelModel.playerModel.isuForce()) {
             dataBase.gamePanelModel.playerModel.setYvelocity( dataBase.gamePanelModel.playerModel.getYvelocity()+ a);
             dataBase.gamePanelModel.playerModel.move(- dataBase.gamePanelModel.playerModel.getYvelocity());
        }
        if ( dataBase.gamePanelModel.playerModel.isrForce()) {
             dataBase.gamePanelModel.playerModel.setXvelocity( dataBase.gamePanelModel.playerModel.getXvelocity()+ a);
             dataBase.gamePanelModel.playerModel.move( dataBase.gamePanelModel.playerModel.getXvelocity());
        }
        if ( dataBase.gamePanelModel.playerModel.islForce()) {
             dataBase.gamePanelModel.playerModel.setXvelocity( dataBase.gamePanelModel.playerModel.getXvelocity()+ a);
             dataBase.gamePanelModel.playerModel.move(- dataBase.gamePanelModel.playerModel.getXvelocity());
        }
        if ( dataBase.gamePanelModel.playerModel.isR0Force()){
             dataBase.gamePanelModel.playerModel.setXvelocity( dataBase.gamePanelModel.playerModel.getXvelocity()- a);
            if( dataBase.gamePanelModel.playerModel.getXvelocity() <= 0){
                 dataBase.gamePanelModel.playerModel.setR0Force(false);
                 dataBase.gamePanelModel.playerModel.setXvelocity(0);
            }
             dataBase.gamePanelModel.playerModel.move( dataBase.gamePanelModel.playerModel.getXvelocity());
        }
        if ( dataBase.gamePanelModel.playerModel.isL0Force()) {
             dataBase.gamePanelModel.playerModel.setXvelocity( dataBase.gamePanelModel.playerModel.getXvelocity()- a);
            if( dataBase.gamePanelModel.playerModel.getXvelocity() <= 0){
                 dataBase.gamePanelModel.playerModel.setL0Force(false);
                 dataBase.gamePanelModel.playerModel.setXvelocity(0);
            }
             dataBase.gamePanelModel.playerModel.move(- dataBase.gamePanelModel.playerModel.getXvelocity());
        }
        if ( dataBase.gamePanelModel.playerModel.isU0Force()) {
             dataBase.gamePanelModel.playerModel.setYvelocity( dataBase.gamePanelModel.playerModel.getYvelocity()- a);
            if( dataBase.gamePanelModel.playerModel.getYvelocity() <= 0){
                 dataBase.gamePanelModel.playerModel.setU0Force(false);
                 dataBase.gamePanelModel.playerModel.setYvelocity(0);
            }
             dataBase.gamePanelModel.playerModel.move(- dataBase.gamePanelModel.playerModel.getYvelocity());
        }
        if ( dataBase.gamePanelModel.playerModel.isD0Force()) {
             dataBase.gamePanelModel.playerModel.setYvelocity( dataBase.gamePanelModel.playerModel.getYvelocity()- a);
            if( dataBase.gamePanelModel.playerModel.getYvelocity() <= 0){
                 dataBase.gamePanelModel.playerModel.setD0Force(false);
                 dataBase.gamePanelModel.playerModel.setYvelocity(0);
            }
             dataBase.gamePanelModel.playerModel.move( dataBase.gamePanelModel.playerModel.getYvelocity());
        }

        wallCheck();
        getC();
    }
    private void wallCheck(){
        if ( dataBase.gamePanelModel.playerModel.getLocation().getY() + BALL_SIZE> panel.getHeight()) {
             dataBase.gamePanelModel.playerModel.setLocation(
                    new Point2D.Double( dataBase.gamePanelModel.playerModel.getLocation().getX(), panel.getHeight() - BALL_SIZE - 5));
        }else if( dataBase.gamePanelModel.playerModel.getLocation().getY() < 2){
             dataBase.gamePanelModel.playerModel.setLocation(
                    new Point2D.Double( dataBase.gamePanelModel.playerModel.getLocation().getX(),  10));
        }
        if( dataBase.gamePanelModel.playerModel.getLocation().getX() + BALL_SIZE > panel.getWidth()){
             dataBase.gamePanelModel.playerModel.setLocation(
                    new Point2D.Double(panel.getWidth() - BALL_SIZE ,dataBase.gamePanelModel.playerModel.getLocation().getY()));
        }else if( dataBase.gamePanelModel.playerModel.getLocation().getX()  < 2){
             dataBase.gamePanelModel.playerModel.setLocation(
                    new Point2D.Double(10, dataBase.gamePanelModel.playerModel.getLocation().getY()));

        }
    }
    private void getC(){
        for (int i = 0; i <  dataBase.collectableModels.size(); i++) {
            CollectableModel c = dataBase.collectableModels.get(i);
            if (Math.abs(c.getLoc().getX() - dataBase.gamePanelModel.playerModel.getLocation().getX()) <= 13 &&
                    Math.abs(c.getLoc().getY() - dataBase.gamePanelModel.playerModel.getLocation().getY()) <= 13) {

                dataBase.collectableModels.get(i).timer.stop();
                dataBase.gamePanelModel.playerModel.setXp(dataBase.gamePanelModel.playerModel.getXp() + c.getCreator().collectablesXp);

                removeCollectable(c);
                dataBase.collectableModels.remove(i);



            }
        }
    }
    private void checkCollision(Movable movable){
        Collidable c;
        for (int i = 0; i < dataBase.gamePanelModel.movables.size(); i++) {
            if(dataBase.gamePanelModel.movables.get(i) instanceof BlackOrbModel){
                for (int j = 0; j < ((BlackOrbModel) dataBase.gamePanelModel.movables.get(i)).getCircles().size(); j++) {
                    c = ((BlackOrbModel) dataBase.gamePanelModel.movables.get(i)).getCircles().get(j);
                    c.collision(movable);
                }
            }
             else {
                c = (Collidable) dataBase.gamePanelModel.movables.get(i);
                c.collision(movable);
            }
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
        if(panelModel.getDimension().getWidth()  + 200 >=  dataBase.gamePanelModel.playerModel.size) {
             dataBase.gamePanelModel.playerModel.size += 2;
             dataBase.gamePanelModel.playerModel.setPoints();
            if( dataBase.gamePanelModel.playerModel.getLocation().getX() <  panelModel.getDimension().getWidth())  dataBase.gamePanelModel.playerModel.setLocation(new Point2D.Double
                    ( dataBase.gamePanelModel.playerModel.getLocation().getX() - 1,  dataBase.gamePanelModel.playerModel.getLocation().getY() - 1));
        }

        if( dataBase.gamePanelModel.playerModel.size > panelModel.getDimension().getWidth()) v1();
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

}
