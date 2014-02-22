package ru.spb.magic2k.towers.bullets;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Date: 29.11.11
 * Time: 16:51
 */
public class CannonBall extends BaseBullet {

    private BufferedImage currentFrame;
    private static final int ITERATIONS = 2000;

    public CannonBall(double _x0, double _y0, double _x1, double _y1, int _speed) {
        super(_x0, _y0, _x1, _y1, _speed);
    }

    public BufferedImage getPicture() {
        try{
            currentFrame = ImageIO.read((this.getClass().getClassLoader().getResourceAsStream("ru/spb/magic2k/assets/ammo/cannonball01.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentFrame;
    }

    public int getIterations() {
        return ITERATIONS;
    }

}
