package ru.spb.magic2k.towers;

import ru.spb.magic2k.Main;
import ru.spb.magic2k.creatures.ICreature;
import ru.spb.magic2k.field.TilePoint;
import ru.spb.magic2k.towers.bullets.IBullet;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Date: 22.11.11
 * Time: 16:55
 */
public abstract class BaseTower implements ITower {
    protected int x, y, x_screen, y_screen;
//    private int towerCost;
    protected int range;
    protected float shootingSpeed;  // in seconds
    private BufferedImage currentFrame;
    private float readinessTime = 0;
    protected Ellipse2D rangeRadius;
    protected Rectangle bounds;
    protected TilePoint tilePoint;


    BaseTower(int x, int y, int towerRange, float shootingSpeed) {
        this.x = x;
        this.y = y;
        tilePoint = new TilePoint(x, y);
        x_screen = tilePoint.getX_screen();
        y_screen = tilePoint.getY_screen();
        range = TilePoint.convertToScreenX(towerRange);
        this.shootingSpeed = shootingSpeed;
        readinessTime = (Main.PERIOD * 20) * shootingSpeed;
        int diameter = range*2;
        currentFrame = getPicture();
        getBounds();
        getCenter();
        rangeRadius = new Ellipse2D.Float( (float)(getCenter().getX() - range), (float)(getCenter().getY() - range), diameter, diameter );
    }


//
//    public void setPicture(BufferedImage image) {
//        currentFrame = image;
//    }

    public Rectangle getBounds() {
        bounds = new Rectangle( x_screen, y_screen, currentFrame.getWidth(), currentFrame.getHeight() );
        return bounds;
    }

    public Point getCenter() {
        return new Point((int) bounds.getCenterX(), (int) bounds.getCenterY() );
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(currentFrame, null, x_screen, y_screen);
        g2d.setColor(Color.RED);
//        радиус башен.
//        g2d.draw(rangeRadius);
    }

    public boolean checkRange(Point creature) {

        if( rangeRadius.contains(creature.getX(), creature.getY()) ) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isReadyToShoot() {
        if(readinessTime >= 0) {
            readinessTime -= Main.PERIOD;

            return false;
        }  else {
            readinessTime = (Main.PERIOD * 20) * shootingSpeed;

            return true;
        }
    }

     /**
     * subclasses MUST use this method to set their images.
     * Images need to calculate some parameters as: bounds, center and range radius
     * @return  - image of this object.
     */
    abstract BufferedImage getPicture();
    abstract public void shoot(ICreature creature);
    abstract public Map<Integer, IBullet> getBulletsList();
}
