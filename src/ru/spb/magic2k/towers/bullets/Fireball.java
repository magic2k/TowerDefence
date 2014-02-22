package ru.spb.magic2k.towers.bullets;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Date: 18.12.11
 * Time: 18:26
 */
public class Fireball extends BaseBullet {

    private BufferedImage currentFrame;
    static final int ITERATIONS = 1000;
    
    public Fireball(double _x0, double _y0, double _x1, double _y1, int _speed) {
        super(_x0, _y0, _x1, _y1, _speed);
    }

    @Override
    public BufferedImage getPicture() {
        try{
//            currentFrame = ImageIO.read(new File("assets/ammo/cannonball01.png"));
            currentFrame = ImageIO.read((this.getClass().getClassLoader().getResourceAsStream("ru/spb/magic2k/assets/ammo/FireBall.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentFrame;
    }

    public int getIterations() {
        return ITERATIONS;
    }
}
