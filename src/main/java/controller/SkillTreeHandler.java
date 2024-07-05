package controller;

import view.pages.GamePanel;

import javax.swing.*;

public class SkillTreeHandler {

    private double aresSec = 0;
    private double acesoSec = 0;
    private double proteusSec = 0;
    private double empowerSec;
    public boolean ares ;
    public boolean aceso;
    public boolean proteus;
    public boolean acesoC;
    private Timer time;
    private GamePanel panel;
    public SkillTreeHandler(GamePanel panel){
        this.panel = panel;
        
        time = new Timer(100, e -> {
            skiilTree();
            store();
            
            if(panel.update.over)stop();

        });
        time.start();
    }
    private void store(){
        //store empower
        if( panel.empower) {
            empowerSec += 0.1;
            if (empowerSec >= 15)    panel.empower = false;
        }
    }
    private void skiilTree(){
        aresCheck();
        acesoCheck();
        proteusCheck();

    }
    private void aresCheck(){
        if(ares) {
            aresSec += 0.1;
            if (aresSec >= 300) {
                   panel.aresCount = 0;
                ares = false;
                aresSec = 0;
            }
        }
    }
    private void acesoCheck(){
        if(aceso){
            acesoSec += 0.1;
               panel.playerModel.setHp(   panel.playerModel.getHp() +    panel.heal);
            if(acesoSec >= 300){
                   panel.acesoCount = 0;
                aceso = false;
                acesoC = true;
                acesoSec = 0;
            }
        }else if(acesoC)
               panel.playerModel.setHp(   panel.playerModel.getHp() +    panel.heal);
    }
    private void proteusCheck(){
        if(proteus){
            proteusSec += 0.1;
            if(proteusSec >= 300){
                   panel.proteusCount = 0;
                proteusSec = 0;
                proteus = false;
            }
        }
    }
    void stop(){
        time.stop();
    }
}
