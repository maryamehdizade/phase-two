package controller.Util;

import controller.Update;
import model.characterModel.enemy.boss.Attacks;
import model.characterModel.enemy.boss.BossModel;
import model.characterModel.enemy.boss.Phand;
import view.charactersView.boss.BossView;
import view.charactersView.boss.pHandView;
import view.drawable.Drawable;

import java.util.ArrayList;

import static controller.Controller.createEnemyView;
import static controller.constants.EntityConstants.FINALBOSS_HP;
import static controller.constants.EntityConstants.R_HAND_SIZE;

public class BossHandler {
    private static BossModel boss;
    private static Update update;
    public static ArrayList<Attacks> attacks = new ArrayList<>();

    public BossHandler(Update u) {
        update = u;
        boss= update.dataBase.getGamePanelModel().boss;
    }

    public static void attack() {
        if(Math.random()<=0.05) {
            switch ((int) (Math.random()*4)){
                case 0 -> {
                    if(boss.hasTwoHands()&&epsilonIsBetweenHeadAndHands()&&!attacks.contains(Attacks.projectile))
                        attacks.add(Attacks.projectile);
                }case 1 -> {
                    if(!attacks.contains(Attacks.vomit)&&(!attacks.contains(Attacks.RapidFire)))attacks.add(Attacks.vomit);
                }case 2 -> {
                   if(!attacks.contains(Attacks.RapidFire)&&!(attacks.contains(Attacks.vomit))) attacks.add(Attacks.RapidFire);
                }
            }
            switch ((int) (Math.random()*5)){
                case 0 -> {
                    if(boss.hasPunchHand())attacks.add(Attacks.powerPunch);
                }case 1 -> {
                    if(boss.hasPunchHand())attacks.add(Attacks.Quake);
                }case 2 -> {
                    if(boss.hasTwoHands()&&!attacks.contains(Attacks.squeeze)&&!attacks.contains(Attacks.Slap)) {
                        attacks.add(Attacks.Slap);
                        switch ((int)(Math.random()*3)){
                            case 0-> boss.r.setSlap(true);
                            case 1 -> boss.l.setSlap(true);
                            case 2 -> {
                                if(boss.hasPunchHand())boss.p.setSlap(true);
                            }
                        }
                    }
                }case 3 -> {
                    if(boss.hasTwoHands()&&!attacks.contains(Attacks.Slap)&&!attacks.contains(Attacks.squeeze))
                        attacks.add(Attacks.squeeze);
                }
            }
            System.out.println(attacks);
            if(attacks.contains(Attacks.vomit)||attacks.contains(Attacks.squeeze)||
                    attacks.contains(Attacks.powerPunch)||attacks.contains(Attacks.RapidFire)
                    ||attacks.contains(Attacks.Slap))boss.vulnerable = true;
            else boss.vulnerable = false;
            if(attacks.contains(Attacks.projectile)){
                if(boss.r!=null)boss.r.vulnerable = true;
                if(boss.l!=null)boss.l.vulnerable = true;
            }
        }
        if(boss.getHp() <= FINALBOSS_HP*2/3&&boss.p==null){
            boss.p = new Phand();
            update.dataBase.getGamePanelModel().movables.add(boss.p);
            pHandView p = (pHandView) createEnemyView(boss.p);
            update.panel.getDrawables().add(p);
            for (Drawable d : update.panel.getDrawables()) {
                if(d instanceof BossView)((BossView) d).p = p;
            }
        }
    }


    private static boolean epsilonIsBetweenHeadAndHands() {
        if(boss.hasTwoHands()) return boss.getLoc().getY() < boss.playerModel.getLocation().getY() &&
                boss.l.getLoc().getX() > boss.playerModel.getLocation().getX() &&
                boss.r.getLoc().getX() < boss.playerModel.getLocation().getX()&&
                boss.r.getLoc().getY() + R_HAND_SIZE.getY()< boss.playerModel.getLocation().getY();
        return false;
    }

    public void setBoss(BossModel boss) {
        this.boss = boss;
    }
}
