package controller;

import view.pages.Menu;

import java.awt.*;
import java.awt.event.KeyEvent;
public class Minimize {
    private Menu menu;

    public  Minimize(Menu m) {
        menu = m;
        try {
            Robot robot = new Robot();


            robot.keyPress(KeyEvent.VK_WINDOWS);
            robot.keyPress(KeyEvent.VK_D);

            robot.keyRelease(KeyEvent.VK_D);
            robot.keyRelease(KeyEvent.VK_WINDOWS);
            menu.a = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
