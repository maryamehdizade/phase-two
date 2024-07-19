package model.characterModel.enemy.boss;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BossModel {
    private Image img;

    public BossModel(){
        try {
            img = ImageIO.read(new File("src/assets/"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
