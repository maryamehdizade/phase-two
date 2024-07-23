package controller.Util;

import controller.DataBase;
import model.model.GamePanelModel;
import view.pages.SkillTree;

import javax.swing.*;
import java.util.HashMap;

public class SkillTreeHandler {
    private double aresSec = 0;
    private double acesoSec = 0;
    private double proteusSec = 0;
    private double empowerSec;
    public boolean acesoC;
    private Timer time;
    private GamePanelModel panel;
    public HashMap<SkillTree.names,Boolean> skills = new HashMap<>();
    public SkillTreeHandler(GamePanelModel panel){
        this.panel = panel;
        
        time = new Timer(100, e -> {
            skiilTree();
            store();
//            if(update.over)stop();

        });
        time.start();
        initialSkills();
    }
    private void initialSkills(){
        skills.put(SkillTree.names.aceso,false);
        skills.put(SkillTree.names.cerberus,false);
        skills.put(SkillTree.names.athena,false);
        skills.put(SkillTree.names.ares,false);
        skills.put(SkillTree.names.astrape,false);
        skills.put(SkillTree.names.empusa,false);
        skills.put(SkillTree.names.melampus,false);
        skills.put(SkillTree.names.chiron,false);
        skills.put(SkillTree.names.dolus,false);
        skills.put(SkillTree.names.proteus,false);
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
        if(skills.get(SkillTree.names.ares)) {
            aresSec += 0.1;
            if (aresSec >= 300) {
                   panel.aresCount = 0;
                skills.put(SkillTree.names.ares,false);
                aresSec = 0;
            }
        }
    }
    private void acesoCheck(){
        if(skills.get(SkillTree.names.aceso)){
            acesoSec += 0.1;
            if(acesoSec%1 >= 0  && acesoSec%1 <= 0.12)
                DataBase.getDataBase().getGamePanelModel().playerModel.setHp(DataBase.getDataBase().getGamePanelModel().playerModel.getHp() + panel.heal);
            if(acesoSec >= 300){
                panel.acesoCount = 0;
                skills.put(SkillTree.names.aceso,false);
                acesoC = true;
                acesoSec = 0;
            }
        }else if(acesoC)
               DataBase.getDataBase().getGamePanelModel().playerModel.setHp(DataBase.getDataBase().getGamePanelModel().playerModel.getHp() + panel.heal);
    }
    private void proteusCheck(){
        if(skills.get(SkillTree.names.proteus)){
            proteusSec += 0.1;
            if(proteusSec >= 300){
                   panel.proteusCount = 0;
                proteusSec = 0;
                skills.put(SkillTree.names.proteus,false);
            }
        }
    }
    void stop(){
        time.stop();
    }



}
