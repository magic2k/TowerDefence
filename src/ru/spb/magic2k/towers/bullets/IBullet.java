package ru.spb.magic2k.towers.bullets;

import java.awt.*;

/**
 * Date: 02.12.11
 * Time: 22:48
 */
public interface IBullet {
    public void fly();
    public void draw(Graphics2D g2d);
    public boolean isStillFlying();
}
