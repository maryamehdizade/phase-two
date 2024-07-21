package controller;

import model.characterModel.enemy.boss.Attacks;
import model.characterModel.enemy.boss.BossModel;

import java.util.ArrayList;

import static controller.constants.EntityConstants.R_HAND_SIZE;

public class BossHandler {
    private static BossModel boss;
    private Update update;
    public static ArrayList<Attacks> attacks = new ArrayList<>();

    public BossHandler(Update u) {
        update = u;
        boss= update.dataBase.boss;
    }

    public static void attack() {
        if(Math.random()<=0.3) {
//            if (epsilonIsBetweenHeadAndHands() && !attacks.contains(Attacks.squeeze)) attacks.add(Attacks.squeeze);
//            else if (!epsilonIsBetweenHeadAndHands()) attacks.remove(Attacks.squeeze);
            if(attacks.isEmpty()&&Math.random() <= 0.5)attacks.add(Attacks.projectile);
//            System.out.println(attacks);
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
