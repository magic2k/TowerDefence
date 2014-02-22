package ru.spb.magic2k.towers;

import ru.spb.magic2k.creatures.ICreature;
import ru.spb.magic2k.towers.bullets.IBullet;

import java.awt.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Date: 10.11.11
 * Time: 11:56
 */
public interface ITower {
    public void shoot(ICreature creature);
//    public void setCost(int towerCost);
    public boolean checkRange(Point creature);
    public void draw(Graphics2D g2d);
    public Point getCenter();
    public Rectangle getBounds();
//     public IBullet getBullet();
    public Map<Integer, IBullet> getBulletsList();

}
