package ru.spb.magic2k.towers.bullets;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Date: 29.11.11
 * Time: 16:50
 */
public abstract class BaseBullet implements IBullet {
    double x0, x1, y0, y1, x_now, y_now, dy, dx;
        int iterations;
    int counter = 1;
    int speed;
    private BufferedImage currentFrame;
    boolean stillFlying=true;

    protected BaseBullet(double _x0, double _y0, double _x1, double _y1, int _speed) {
        this.x0 = _x0;
        this.y0 = _y0;
        this.x1 = _x1;
        this.y1 = _y1;
        this.speed = _speed;
        this.iterations = getIterations();

        dx = ((x1 - x0) / iterations) * speed;
        dy = ((y0 - y1) / iterations) * speed;
//        x_now = dx;
//        y_now = dy;
        x_now = x0;
        y_now = y0;
//        System.out.println(" dx and dy = " + dx + "  " + dy);

        currentFrame = getPicture();
    }

    public boolean isStillFlying() {
        return stillFlying;
    }


    public void fly() {

        if ( stillFlying ) {
            x_now += dx;
            y_now -= dy;
            counter += 1;
            //при проверках отваливаются противоположные четверти. Возможно, надо ввести проверку положения.

            if (x0 <= x1 && y0 <= y1)
                if (x_now >= x1 && y_now >= y1) { stillFlying = false; } // low-right quarter

            if (x0 >= x1 && y0 <= y1)
                if (x_now <= x1 && y_now >= y1) { stillFlying = false; } // low-left quarter

            if (x0 >= x1 && y0 >= y1)
                if (x_now <= x1 && y_now <= y1) { stillFlying = false; } // upper-left quarter

            if (x0 <= x1 && y0 >= y1)
                if (x_now >= x1 && y_now <= y1) { stillFlying = false; } // upper-right quarter
//
//            System.out.println(" x_now and y_now = " + x_now + "  " + y_now);
//            System.out.println(" FLY() IS CALLED! :D ");
        }
//
        if ( (counter < iterations)) {
            x_now += dx;
            y_now -= dy;
            counter += 1;
//            System.out.println(" x_now and y_now = " + x_now + "  " + y_now);
//            System.out.println(" FLY() IS CALLED! :D ");
        } else {
//            System.out.println(" FLY() IS FALSE! ");
            stillFlying = false;
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(currentFrame, null, (int) x_now, (int) y_now);
//        System.out.println(" x_now and y_now = " + x_now + "  " + y_now);

    }

    abstract BufferedImage getPicture();
    abstract int getIterations();
}
