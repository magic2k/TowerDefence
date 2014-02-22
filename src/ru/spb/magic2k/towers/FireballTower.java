package ru.spb.magic2k.towers;

import ru.spb.magic2k.creatures.ICreature;
import ru.spb.magic2k.towers.bullets.Fireball;
import ru.spb.magic2k.towers.bullets.IBullet;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Date: 18.12.11
 * Time: 18:42
 */
public class FireballTower extends BaseTower {

    private final int COST = 100;
    private BufferedImage currentFrame;
    private int damage = 1;
    private int projectileSpeed = 90;
    private Fireball fireball;
    private Map<Integer, IBullet> bulletsList = new ConcurrentHashMap<Integer, IBullet>();
    Integer id = 0;

    public FireballTower(int x, int y, int range, float shootingSpeed) {
        super(x, y, range, shootingSpeed);
        bulletsList = new ConcurrentHashMap<Integer, IBullet>();
    }

    public Fireball getBullet() {
        return fireball;
    }

    public void shoot(ICreature creature) {
        if( isReadyToShoot() ) {
            id+=1;
            bulletsList.put( id , new Fireball(this.getCenter().getX(), this.getCenter().getY(), creature.getCenter().getX(), creature.getCenter().getY(), projectileSpeed) );
//            System.out.println(this + " shooting " + creature);
            creature.damaged(damage);
        }
    }

    @Override
    public Map<Integer, IBullet> getBulletsList() {
        return bulletsList;
    }


    public BufferedImage getPicture() {
        try{
            currentFrame = ImageIO.read((this.getClass().getClassLoader().getResourceAsStream("ru/spb/magic2k/assets/towers/fireball_tower_01.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentFrame;
    }

}

