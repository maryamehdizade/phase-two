package controller.Util;

import controller.DataBase;
import model.model.GamePanelModel;
import view.pages.SkillTree;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.HashMap;

public class SkillTreeHandler {

    private double sec = 0;
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
        });
        time.start();
        initialSkills();
    }
    private void initialSkills(){
        skills.put(SkillTree.names.aceso,false);
        skills.put(SkillTree.names.cerberus,false);
        skills.put(SkillTree.names.ares,false);
        skills.put(SkillTree.names.astrape,false);
        skills.put(SkillTree.names.empusa,false);
        skills.put(SkillTree.names.melampus,false);
        skills.put(SkillTree.names.chiron,false);
        skills.put(SkillTree.names.dolus,false);
        skills.put(SkillTree.names.proteus,false);
        skills.put(SkillTree.names.athena,false);
    }
    private void store(){
        //store empower
        if(panel.empower) {
            empowerSec += 0.1;
            if (empowerSec >= 15)panel.empower = false;
        }
    }
    private void skiilTree(){
        aresCheck();
        acesoCheck();
        proteusCheck();

    }
    private void aresCheck() {
        if (skills.get(SkillTree.names.ares)||skills.get(SkillTree.names.astrape)||skills.get(SkillTree.names.cerberus)) {
            sec += 0.1;
            if (sec >= 300) {
                panel.skillCount = 0;
                skills.put(SkillTree.names.ares, false);
                skills.put(SkillTree.names.astrape, false);
                skills.put(SkillTree.names.cerberus, false);
                sec = 0;
            }
        }
    }
    private void acesoCheck(){
        if(skills.get(SkillTree.names.aceso)){
            sec += 0.1;
            if(sec%1 >= 0  && sec%1 <= 0.12)
                DataBase.getDataBase().getGamePanelModel().playerModel.setHp(DataBase.getDataBase().getGamePanelModel().playerModel.getHp() + panel.heal);
            if(sec >= 300){
                panel.skillCount = 0;
                skills.put(SkillTree.names.aceso,false);
                acesoC = true;
                sec = 0;
            }
        }else if(acesoC)
               DataBase.getDataBase().getGamePanelModel().playerModel.setHp(DataBase.getDataBase().getGamePanelModel().playerModel.getHp() + panel.heal);

        if (skills.get(SkillTree.names.melampus)||skills.get(SkillTree.names.chiron)) {
            sec += 0.1;
            if (sec >= 300) {
                panel.skillCount = 0;
                skills.put(SkillTree.names.melampus, false);
                skills.put(SkillTree.names.chiron, false);
                sec = 0;
            }
        }
    }
    private void proteusCheck(){
        if (skills.get(SkillTree.names.proteus)||skills.get(SkillTree.names.empusa)||skills.get(SkillTree.names.dolus)) {
            sec += 0.1;
            if (sec >= 300) {
                panel.skillCount = 0;
                skills.put(SkillTree.names.proteus, false);
                skills.put(SkillTree.names.empusa, false);
                skills.put(SkillTree.names.dolus, false);
                sec = 0;
            }
        }
    }
    void stop(){
        time.stop();
    }



}
