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
        if(Math.random()<=0.1) {
//            if (epsilonIsBetweenHeadAndHands() && !attacks.contains(Attacks.squeeze)) attacks.add(Attacks.squeeze);
//            else if (!epsilonIsBetweenHeadAndHands()) attacks.remove(Attacks.squeeze);
            if(attacks.isEmpty()&&Math.random() <= 0.5 && boss.hasPunchHand()){
                boss.toggleOccupation();
                attacks.add(Attacks.powerPunch);
            }
//            System.out.println(attacks);
//            switch ((int) (Math.random()*4)){
//                case 0 -> {
//                    if(boss.hasTwoHands()&&epsilonIsBetweenHeadAndHands()&&!attacks.contains(Attacks.projectile))
//                        attacks.add(Attacks.projectile);
//                }case 1 -> {
//                    if(!attacks.contains(Attacks.vomit))attacks.add(Attacks.vomit);
//                }case 2 -> {
//                   if(!attacks.contains(Attacks.RapidFire)) attacks.add(Attacks.RapidFire);
//                }
//            }
//            switch ((int) (Math.random()*4)){
//                case 0 -> {
//                    if(!attacks.contains(Attacks.Quake) && !attacks.contains(Attacks.powerPunch))attacks.add(Attacks.powerPunch);
//                }case 1 -> {
//                    if(!attacks.contains(Attacks.powerPunch)&&!attacks.contains(Attacks.Quake))attacks.add(Attacks.Quake);
//                }case 2 -> {
//                    if(boss.hasTwoHands()&&!attacks.contains(Attacks.squeeze)&&!attacks.contains(Attacks.Slap))
//                        attacks.add(Attacks.Slap);
//                }case 3 -> {
//                    if(boss.hasTwoHands()&&!attacks.contains(Attacks.Slap)&&!attacks.contains(Attacks.squeeze))
//                        attacks.add(Attacks.squeeze);
//                }
//            }
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
        return boss.getLoc().getY() < boss.playerModel.getLocation().getY() &&
                boss.l.getLoc().getX() > boss.playerModel.getLocation().getX() &&
                boss.r.getLoc().getX() < boss.playerModel.getLocation().getX()&&
                boss.r.getLoc().getY() + R_HAND_SIZE.getY()< boss.playerModel.getLocation().getY();
    }

    public void setBoss(BossModel boss) {
        this.boss = boss;
    }
}
