package ru.spb.magic2k.creatures;

import java.awt.*;
/**
 * Date: 10.11.11
 * Time: 11:25
 */
//Maybe abstract class will be better?
public interface ICreature {

    public void draw(Graphics2D g);
    public boolean isMoved();
    public void move();
    public void animation();
    public void damaged(int damageReceived);
    public Point getCenter();
    public Point getPosition();
    public Rectangle getBounds();
    public void die();
    public boolean isDead();
    IDeadCreature getDeadCreatureBody(Point point);

}
