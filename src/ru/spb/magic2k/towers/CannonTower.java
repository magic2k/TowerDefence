package ru.spb.magic2k.towers;

import ru.spb.magic2k.creatures.ICreature;
import ru.spb.magic2k.towers.bullets.CannonBall;
import ru.spb.magic2k.towers.bullets.IBullet;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
* Date: 21.11.11
* Time: 16:02
*/
    public class CannonTower extends BaseTower {

    private BufferedImage currentFrame;
    private int damage = 1;
    private int projectileSpeed = 90;
    private CannonBall cannonBall;
    private Map<Integer, IBullet> bulletsList = new ConcurrentHashMap<Integer, IBullet>();
//    private AtomicInteger bulletId = new AtomicInteger(0);
    Integer id = 0;

    public CannonTower(int x, int y, int range, float shootingSpeed) {
        super(x, y, range, shootingSpeed);
        bulletsList = new ConcurrentHashMap<Integer, IBullet>();
    }

    public CannonBall getBullet() {
        return cannonBall;
    }

    public void shoot(ICreature creature) {

        if( isReadyToShoot() ) {
//            id = bulletId.incrementAndGet();
            id+=1;
            bulletsList.put( id , new CannonBall(this.getCenter().getX(), this.getCenter().getY(), creature.getCenter().getX(), creature.getCenter().getY(), projectileSpeed) );
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
            currentFrame = ImageIO.read((this.getClass().getClassLoader().getResourceAsStream("ru/spb/magic2k/assets/towers/cannon_tower_01.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentFrame;
    }

}
