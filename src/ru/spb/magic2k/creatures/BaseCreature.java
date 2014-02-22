package ru.spb.magic2k.creatures;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Date: 22.11.11
 * Time: 17:12
 */
public abstract class BaseCreature implements ICreature {
    protected int x, y, screen_x, screen_y;
    protected int health;
    protected int speed;
    private BufferedImage currentFrame;
    private ArrayList<BufferedImage> frameList = new ArrayList<BufferedImage>();
    private Rectangle bounds;
    private Iterator animationIterator;
    private boolean isDead;

    // If not sync we have concurrent modification there. Not sure where it comes from
    // upd: problem solved by active renderer.
//    public synchronized void animation() {

    public void animation() {

        if (animationIterator.hasNext()) {
            currentFrame = (BufferedImage) animationIterator.next();
        } else {
            animationIterator = frameList.listIterator();
            currentFrame = (BufferedImage) animationIterator.next();
        }

//        notifyAll();
    }

    public Point getPosition() {
        return new Point(screen_x, screen_y);
    }

    public BaseCreature(int screen_x, int screen_y, int speed, int health) {
        this.screen_x = screen_x;
        this.screen_y = screen_y;
//        screen_x = x;
//        screen_y = y;
        this.speed = speed;
        this.health = health;
        isDead = false;

        frameList = initFrameList();
        //init animation array
        currentFrame = frameList.get(0);
        getBounds();

        animationIterator = frameList.listIterator();
    }

    public Rectangle getBounds() {
        bounds = new Rectangle(screen_x, screen_y, currentFrame.getWidth(), currentFrame.getHeight());
        return bounds;
    }

    public Point getCenter() {
        getBounds(); // its important to update current bounds position
//        System.out.println(" center " + new Point((int) bounds.getCenterX(), (int) bounds.getCenterY() ));
        return new Point((int) bounds.getCenterX(), (int) bounds.getCenterY());

    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(currentFrame, null, screen_x, screen_y);
    }

    //todo return clip() !

    public void damaged(int damageReceived) {
        health -= damageReceived;
        if (health <= 0) {
            die();
        }
    }

    public void die() {
//        System.out.println(this + " is dead");
        isDead = true;
    }

    public boolean isDead() {
        return isDead;
    }

    //    abstract public void die();
    abstract public ArrayList<BufferedImage> initFrameList();

    abstract public void move();
    
    abstract public IDeadCreature getDeadCreatureBody(Point point);
}
