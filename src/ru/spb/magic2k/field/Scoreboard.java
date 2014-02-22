package ru.spb.magic2k.field;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * User: magic2k
 * Date: 29.02.12
 * Time: 23:51
 *
 * Scores: FPS, Kills, Gold
 * Singleton, Observer
 */
public class Scoreboard extends JPanel implements Observer{

    long fps = 0;
    int kills = 0;
    int gold = 0;

    private Image dbImage = null;

    public Scoreboard() {
        try {
            dbImage = ImageIO.read((this.getClass().getClassLoader().getResourceAsStream("ru/spb/magic2k/assets/grass.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paintScreen() {

        Graphics2D g2d = (Graphics2D) this.getGraphics();
//        g2d.drawImage(dbImage, 0, 0, null);
        GradientPaint gradient = new GradientPaint(0, 0, Color.BLUE, 500, 400, Color.ORANGE);
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        g2d.setColor(Color.GREEN);
        g2d.drawString("FPS : " + String.valueOf(fps), 30, 15);
        g2d.setColor(Color.RED);
        g2d.drawString("Kills : " + String.valueOf(kills), 90, 15);
        g2d.setColor(Color.YELLOW);
        g2d.drawString("Gold : " + String.valueOf(gold), 160, 15);

    }

    public void update(Observable o, Object s) {
        Stats stats = (Stats) s;
        fps = stats.getFps();
        kills = stats.getKills();
        gold = stats.getGold();
    }

    //    @Override
//    public void paint(Graphics g) {
    public void scoreboardRender() {

    }
}
