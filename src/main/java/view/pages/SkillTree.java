package view.pages;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class SkillTree extends JFrame {
    public enum names{
        ares, astrape,cerberus,aceso,melampus,chiron,athena,proteus,empusa,dolus
    }
    Menu menu;
    private boolean r1, r2, r3, c1, c2, c3, c4, p1, p2, p3;


    Dimension size = new Dimension(200,100);
    Dimension size2 = new Dimension(150,100);
    Color color = new Color(86, 10, 10);
    JButton ares = new JButton("Writ of Ares  " + "\n" + "750 xp");
    JButton r2astrape = new JButton("Writ of Astrape  " + "\n" + "1000 xp");
    JButton r3cerberus = new JButton("Writ of Cerberus  " + "\n" + "1500 xp");
    JButton aceso = new JButton("Writ of Aceso  " + "\n" + "500 xp");
    JButton c2melampus = new JButton("Writ of Melampus  " + "\n" + "750 xp");
    JButton c3chiron = new JButton("Writ of Chiron  " + "\n" + "900 xp");
    JButton c3Athena = new JButton("Writ of Athena  " + "\n" + "1000 xp");
    JButton proteus = new JButton("Writ of Proteus  " + "\n" + "1000 xp");
    JButton p2empusa = new JButton("Writ of Empusa  " + "\n" + "750 xp");
    JButton p3dolus = new JButton("Writ of Dolus  " + "\n" + "1500 xp");
    private int x1 = 10;
    private int x2 = (int) (x1 + size.getWidth()) + x1;
    private int x3 = (int) (x2 + size.getWidth()) + x1;
    private int y1 = 50;
    private int y2 = y1 + 120;
    private int y3 = y2 + 120;
    private int font = 16;
    JPanel panel;
    public SkillTree(Menu menu){
        this.menu = menu;

        setSize(700,700);
        setLocation(300,20);
        setBackground(Color.black);
        setVisible(true);
        requestFocus();
        setFocusable(false);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.black);

        add();

    }
    void add(){
        addAres();

        addAceso();

        addProteus();

        addMenuBut();
    }
    private void addMenuBut(){
        JButton men = new JButton("menu");
        men.setSize(400,50);
        men.setLocation(150,500);
        men.setBackground(color);
        men.setFont(new Font("TimesRoman", Font.PLAIN, 22));
        men.addActionListener(e -> {
            dispose();
            menu.setVisible(true);
        });
        JLabel xp = new JLabel("xp :   " + menu.getXp());
        xp.setSize(300,70);
        xp.setLocation(300, 410);
        xp.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        xp.setLayout(null);
        xp.setBackground(Color.black);

        panel.add(aceso);
        panel.add(c2melampus);
        panel.add(c3chiron);
        panel.add(c3Athena);
        panel.add(ares);
        panel.add(r2astrape);
        panel.add(r3cerberus);
        panel.add(proteus);
        panel.add(p2empusa);
        panel.add(p3dolus);
        panel.add(men);
        panel.add(xp);
        this.add(panel);
    }
    private void addAres(){
        ares.setSize(size);
        ares.setLocation(x1,y1);
        ares.setBackground(color);
        ares.setFont(new Font("TimesRoman", Font.PLAIN, font));
        ares.addActionListener(e ->{
            if(menu.getXp() >= 750) {
                menu.skills.put(String.valueOf(names.ares), true);
                for (String a :
                        menu.skills.keySet()) {
                    if (!Objects.equals(a, String.valueOf(names.ares))) {
                        menu.skills.put(a, false);
                    }
                }
                if (!menu.r1) menu.setXp(menu.getXp() - 750);
                menu.r1 = true;

            }
        });

        r2astrape.setSize(size);
        r2astrape.setLocation(x2,y1);
        r2astrape.setBackground(color);
        r2astrape.setFont(new Font("TimesRoman", Font.PLAIN, font));
        r2astrape.addActionListener(e ->{
            if(menu.getXp() >= 1000 && menu.r1) {
                menu.skills.put(String.valueOf(names.astrape), true);
                for (String a :
                        menu.skills.keySet()) {
                    if (!Objects.equals(a, String.valueOf(names.astrape))) {
                        menu.skills.put(a, false);
                    }
                }
                if (!menu.r2) menu.setXp(menu.getXp() - 1000);
                menu.r2 = true;

            }
        });
        r3cerberus.setSize(size);
        r3cerberus.setLocation(x3,y1);
        r3cerberus.setBackground(color);
        r3cerberus.setFont(new Font("TimesRoman", Font.PLAIN, font));
        r3cerberus.addActionListener(e ->{
            if(menu.getXp() >= 1500 && menu.r1 && menu.r2) {
                    menu.skills.put(String.valueOf(names.cerberus), true);
                    for (String a :
                            menu.skills.keySet()) {
                        if (!Objects.equals(a, String.valueOf(names.cerberus))) {
                            menu.skills.put(a, false);
                        }
                    }
                    if (!menu.r3) menu.setXp(menu.getXp() - 1500);
                    menu.r3 = true;

            }
        });
    }
    private void addAceso(){
        aceso.setSize(size2);
        aceso.setLocation(x1,y2);
        aceso.setBackground(color);
        aceso.setFont(new Font("TimesRoman", Font.PLAIN, font));
        aceso.addActionListener(e -> {
            if(menu.getXp() >= 500) {
                menu.skills.put(String.valueOf(names.aceso), true);
                for (String a :
                        menu.skills.keySet()) {
                    if (!Objects.equals(a, String.valueOf(names.aceso))) {
                        menu.skills.put(a, false);
                    }
                }
                if (!menu.c1) menu.setXp(menu.getXp() - 500);
                menu.c1 = true;
            }
        });

        c2melampus.setSize(size2);
        c2melampus.setLocation(x2 - 50,y2);
        c2melampus.setBackground(color);
        c2melampus.setFont(new Font("TimesRoman", Font.PLAIN, font));
        c2melampus.addActionListener(e -> {
            if(menu.getXp() >= 750 && menu.c1){
                menu.skills.put(String.valueOf(names.melampus), true);
                for (String a :
                        menu.skills.keySet()) {
                    if (!Objects.equals(a, String.valueOf(names.melampus))) {
                        menu.skills.put(a, false);
                    }
                }
                if (!menu.c2) menu.setXp(menu.getXp() - 750);
                menu.c2 = true;
            }
        });
        c3chiron.setSize(size2);
        c3chiron.setLocation(x3 - 100,y2);
        c3chiron.setBackground(color);
        c3chiron.setFont(new Font("TimesRoman", Font.PLAIN, font));
        c3chiron.addActionListener(e -> {
            if (menu.getXp() >= 900 && menu.c1 && menu.c2) {
                menu.skills.put(String.valueOf(names.chiron), true);
                for (String a :
                        menu.skills.keySet()) {
                    if (!Objects.equals(a, String.valueOf(names.chiron))) {
                        menu.skills.put(a, false);
                    }
                }
                if (!menu.c3) menu.setXp(menu.getXp() - 900);
                menu.c3 = true;
            }

        });
        c3Athena.setSize(size2);
        c3Athena.setLocation(x3 + 70,y2);
        c3Athena.setBackground(color);
        c3Athena.setFont(new Font("TimesRoman", Font.PLAIN, font));
        c3Athena.addActionListener(e -> {
            if(menu.getXp() >= 1000 && menu.c1 && menu.c2){
                menu.skills.put(String.valueOf(names.athena),true);
                for (String a :
                        menu.skills.keySet()) {
                    if(!Objects.equals(a, String.valueOf(names.athena))){
                        menu.skills.put(a,false);
                    }
                }
                if(!menu.c4)menu.setXp(menu.getXp() - 1000);
                menu.c4 = true;
            }
        });

    }
    private void addProteus(){
        proteus.setSize(size);
        proteus.setLocation(x1,y3);
        proteus.setBackground(color);
        proteus.setFont(new Font("TimesRoman", Font.PLAIN, font));
        proteus.addActionListener(e -> {
            if(menu.getXp() >= 1000){
                menu.skills.put(String.valueOf(names.proteus),true);
                for (String a :
                        menu.skills.keySet()) {
                    if(!Objects.equals(a, String.valueOf(names.proteus))){
                        menu.skills.put(a,false);
                    }
                }
                if(!menu.p1)menu.setXp(menu.getXp() - 1000);
                menu.p1 = true;
            }
        });
        p2empusa.setSize(size);
        p2empusa.setLocation(x2,y3);
        p2empusa.setBackground(color);
        p2empusa.setFont(new Font("TimesRoman", Font.PLAIN, font));
        p2empusa.addActionListener(e -> {
            if(menu.getXp() >= 750 && menu.p1){
                menu.skills.put(String.valueOf(names.empusa),true);
                for (String a :
                        menu.skills.keySet()) {
                    if(!Objects.equals(a, String.valueOf(names.empusa))){
                        menu.skills.put(a,false);
                    }
                }
                if(!menu.p2)menu.setXp(menu.getXp() - 750);
                menu.p2 = true;
            }
        });

        p3dolus.setSize(size);
        p3dolus.setLocation(x3,y3);
        p3dolus.setBackground(color);
        p3dolus.setFont(new Font("TimesRoman", Font.PLAIN, font));
        p3dolus.addActionListener(e -> {
            if(menu.getXp() >= 1500 && menu.p1 && menu.p2){
                menu.skills.put(String.valueOf(names.dolus),true);
                for (String a :
                        menu.skills.keySet()) {
                    if(!Objects.equals(a, String.valueOf(names.dolus))){
                        menu.skills.put(a,false);
                    }
                }
                if(!menu.p3)menu.setXp(menu.getXp() - 1500);
                menu.p3 = true;
            }
        });

    }

    public static void main(String[] args) {
        new SkillTree(Menu.getMenu());
    }
}
