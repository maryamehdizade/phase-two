package controller;

import model.characterModel.enemy.boss.Attacks;
import model.characterModel.enemy.boss.BossModel;
import model.characterModel.enemy.boss.Phand;

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
        boss= update.dataBase.boss;
    }

    public static void attack() {
        if(Math.random()<=0.1) {
//            if (epsilonIsBetweenHeadAndHands() && !attacks.contains(Attacks.squeeze)) attacks.add(Attacks.squeeze);
//            else if (!epsilonIsBetweenHeadAndHands()) attacks.remove(Attacks.squeeze);
            if(attacks.isEmpty()&&Math.random() <= 0.5&&boss.hasTwoHands())attacks.add(Attacks.projectile);
//            System.out.println(attacks);
            switch ((int) (Math.random()*4)){
                case 0 -> {
                    if(boss.hasTwoHands())attacks.add(Attacks.projectile);
                }case 1 -> {
                    attacks.add(Attacks.vomit);
                }case 2 -> {
                    attacks.add(Attacks.RapidFire);
                }
            }
            switch ((int) (Math.random()*4)){
                case 0 -> {
                    if(!attacks.contains(Attacks.Quake))attacks.add(Attacks.powerPunch);
                }case 1 -> {
                    if(!attacks.contains(Attacks.powerPunch)) attacks.add(Attacks.Quake);
                }case 2 -> {
                    if(boss.hasTwoHands()&&!attacks.contains(Attacks.squeeze))attacks.add(Attacks.Slap);
                }case 3 -> {
                    if(boss.hasTwoHands()&&!attacks.contains(Attacks.Slap))attacks.add(Attacks.squeeze);
                }
            }
        }
        if(boss.getHp() <= FINALBOSS_HP*2/3&&!boss.hasPunchHand()){
            boss.p = new Phand();
            update.dataBase.getGamePanelModel().movables.add(boss.p);
            update.panel.getDrawables().add(createEnemyView(boss.p));
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
