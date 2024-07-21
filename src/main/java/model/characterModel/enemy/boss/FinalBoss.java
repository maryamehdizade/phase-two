package model.characterModel.enemy.boss;
import model.model.Enemy;
import java.util.UUID;

public class FinalBoss extends Enemy {
    public  Lhand l;
    public  Rhand r;
    public Phand p;
    public  BossModel b;
    private String id;

    public FinalBoss() {
        id = UUID.randomUUID().toString();
        this.l = new Lhand();
        this.r = new Rhand();
        this.b = new BossModel();
    }

    public boolean hasTwoHand(){
        return l.getHp()>0&&r.getHp()>0;
    }


    public String getId() {
        return id;
    }
}
