import javax.swing.*;
import java.awt.*;

public class Store extends JFrame {
    private JButton empower = new JButton("Empower : 75xp");
    private JButton heal = new JButton("Apollo Heal : 50xp");
    private JButton slaughter = new JButton("slaughter : 200xp");
    private JButton slumber = new JButton("slumber : 150xp");
    private JButton dismay = new JButton("dismay: 120xp");
    private JButton banish = new JButton("Hephestus : 100xp");
    private Dimension size = new Dimension(220, 100);
    int x1 = 20;
    int x2 = size.width + x1 + x1;
    int y1 = 15;
    int y2 = size.height + y1 + y1;
    int y3 = size.height + y2 + y1;
    Color color = new Color(100, 0, 0);
    private JPanel main = new JPanel();
    private int font = 22;

    public Store() {

        setSize(500, 500);
        setResizable(false);
        setBackground(Color.black);
        setUndecorated(true);
        setVisible(true);
        setSize(501, 501);
        requestFocus();
        setFocusable(false);

        this.add(main);
        main.setLayout(null);
        main.setBackground(Color.black);

        addButtons();

    }

    private void addButtons() {

        banish.setSize(size);
        banish.setLocation(x1, y3);
        banish.setBackground(color);
        banish.setFont(new Font("TimesRoman", Font.PLAIN, font));

        slaughter.setSize(size);
        slaughter.setLocation(x2, y3);
        slaughter.setBackground(color);
        slaughter.setFont(new Font("TimesRoman", Font.PLAIN, font));

        slumber.setSize(size);
        slumber.setLocation(x2, y2);
        slumber.setBackground(color);
        slumber.setFont(new Font("TimesRoman", Font.PLAIN, font));

        dismay.setSize(size);
        dismay.setLocation(x2, y1);
        dismay.setBackground(color);
        dismay.setFont(new Font("TimesRoman", Font.PLAIN, font));



        empower.setSize(size);
        empower.setLocation(x1, y2);
        empower.setBackground(color);
        empower.setFont(new Font("TimesRoman", Font.PLAIN, font));


        heal.setSize(size);
        heal.setLocation(x1, y1);
        heal.setBackground(color);
        heal.setFont(new Font("TimesRoman", Font.PLAIN, font));


        JButton play = new JButton("back");
        play.setSize(size);
        play.setBackground(color);
        play.setLocation(140, y3 + size.height + y1);
        play.setFont(new Font("TimesRoman", Font.PLAIN, font));
        play.addActionListener(e -> {
            start();
        });


        main.add(heal);
        main.add(dismay);
        main.add(slaughter);
        main.add(slumber);
        main.add(empower);
        main.add(banish);
        main.add(play);
        main.setBackground(Color.black);

    }

    private void start() {
        dispose();

    }

    public static void main(String[] args) {
        new Store();
    }


}
