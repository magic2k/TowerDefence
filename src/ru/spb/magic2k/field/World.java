package ru.spb.magic2k.field;

import org.apache.log4j.Logger;
import ru.spb.magic2k.creatures.DeadSpider;
import ru.spb.magic2k.creatures.ICreature;
import ru.spb.magic2k.creatures.IDeadCreature;
import ru.spb.magic2k.creatures.Spider;
import ru.spb.magic2k.creatures.SpiderGoga;
import ru.spb.magic2k.towers.ITower;
import ru.spb.magic2k.towers.TowerFactoriesHolder;
import ru.spb.magic2k.towers.bullets.IBullet;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Date: 17.11.11
 * Time: 21:56
 *  class for world description
 *  spawns new waves. controls user gold
 *
 *  Singleton
 */
public class World {

    WorldData wd = WorldData.getInstance();
    
    Map<Integer,ICreature> creaturesList = wd.getCreaturesList();
    Map<Integer,IDeadCreature> deadCreaturesList = wd.getDeadCreaturesList();
    Map<Integer,ITower> towersList = wd.getTowersList();
    Map<Integer,IBullet> bulletsList;

    HashMap<TilePoint, Tile> tileList = wd.getTileList();
    IBullet bullet;
    AtomicInteger atomTowerId = new AtomicInteger(0);
    AtomicInteger atomCreatureId = new AtomicInteger(0);
    AtomicInteger atomDeadCreatureId = new AtomicInteger(0);

    protected static final Logger logger = Logger.getLogger(World.class);
    private static World ourInstance = new World();
    Constructor<? extends ICreature> creatureConstructor = null;

    private CreaturesRespawnHandler respawnHandler = CreaturesRespawnHandler.getInstance();


    public static World getInstance() {
        return ourInstance;
    }
    
    private World() {
        init();
    }


    public void init() {
        //TODO: вынести текст во внешний файл (strings.xml , etc...)
        System.out.printf("MOTD: Welcome! Press '1' to select Cannon Towers \n"
        + "Press '2' to select Fireball Tower \n"
        + "Press 'c' to spawn more creatures.");

        // temporary
//        spawnCreatures(Spider.class, 10, 400);
//          spawnCreatures(SpiderGoga.class, 3, 15);
        respawnHandler.addToSpawnQueue(Spider.class, 10, 5);
        respawnHandler.addToSpawnQueue(SpiderGoga.class, 3, 15);
        respawnHandler.addToSpawnQueue(SpiderGoga.class, 3, 15);
        respawnHandler.addToSpawnQueue(Spider.class, 10, 10);
        respawnHandler.addToSpawnQueue(Spider.class, 10, 10);
        respawnHandler.addToSpawnQueue(SpiderGoga.class, 3, 15);
        respawnHandler.addToSpawnQueue(SpiderGoga.class, 3, 15);
        respawnHandler.addToSpawnQueue(Spider.class, 10, 10);
        respawnHandler.addToSpawnQueue(SpiderGoga.class, 3, 15);
        respawnHandler.addToSpawnQueue(Spider.class, 10, 10);
        respawnHandler.addToSpawnQueue(Spider.class, 10, 10);
        respawnHandler.addToSpawnQueue(SpiderGoga.class, 3, 15);
        respawnHandler.addToSpawnQueue(SpiderGoga.class, 3, 15);
        respawnHandler.addToSpawnQueue(Spider.class, 10, 10);
        respawnHandler.addToSpawnQueue(SpiderGoga.class, 3, 15);
        respawnHandler.addToSpawnQueue(Spider.class, 10, 10);
        respawnHandler.addToSpawnQueue(Spider.class, 10, 10);
        respawnHandler.addToSpawnQueue(SpiderGoga.class, 3, 15);
        respawnHandler.addToSpawnQueue(SpiderGoga.class, 3, 15);
        respawnHandler.addToSpawnQueue(Spider.class, 10, 10);
        respawnHandler.addToSpawnQueue(SpiderGoga.class, 3, 15);
        respawnHandler.addToSpawnQueue(Spider.class, 10, 10);
        respawnHandler.addToSpawnQueue(Spider.class, 10, 10);
        respawnHandler.addToSpawnQueue(SpiderGoga.class, 3, 15);
        respawnHandler.addToSpawnQueue(SpiderGoga.class, 3, 15);
        respawnHandler.addToSpawnQueue(SpiderGoga.class, 100, 100);
    }

    public HashMap<TilePoint, Tile> getTileList() {
        return tileList;
    }

    public void placeTower(int x, int y) {
        TilePoint tilePoint = new TilePoint(x, y);
        if( tileList.get(tilePoint).isEmpty() ) {

            if( TowerFactoriesHolder.getCurrentTowerFactory().getTowerCost() <= wd.getPlayerGold() ) {
                wd.decreasePlayerGold( TowerFactoriesHolder.getCurrentTowerFactory().getTowerCost() );
                towersList.put(atomTowerId.getAndIncrement(), TowerFactoriesHolder.getCurrentTowerFactory().constructTower(x, y));
                tileList.get(tilePoint).setEmpty(false);
                tileList.get(tilePoint).setWalkable(false);
                wd.newFieldVersion();
                logger.debug("Placed " + TowerFactoriesHolder.getCurrentTowerFactory().getClass() +" N: " + atomTowerId.get());
            } else {
                System.out.println("Cannot place new tower - not enough gold!");
            }

        } else {
            logger.debug("Tile is not empty");
        }
    }

    public void update() {

        respawnHandler.checkAndRespawn();

        //before foreach there were stupid iterators

        for (Integer integer : creaturesList.keySet()) {
            ICreature creature = creaturesList.get(integer);
            if (creature.isDead()) {
//                Point point = creature.getBounds();
                wd.increaseKilledCreaturesCounter();

                if (creature.getClass() == Spider.class) {
                    wd.increasePlayerGold(50);
                }
                if(creature.getClass() == SpiderGoga.class) {
                    wd.increasePlayerGold(150);
                }
                
                deadCreaturesList.put( atomDeadCreatureId.getAndIncrement(), creature.getDeadCreatureBody(creature.getPosition()) );
                creaturesList.remove(integer);
            } else {
                creature.move();
            }
        }

        for (Integer integer1 : towersList.keySet()) {
            ITower tower = towersList.get(integer1);

            for (Integer integer2 : creaturesList.keySet()) {
                ICreature creature = creaturesList.get(integer2);

                if (tower.checkRange(creature.getCenter())) {
                    tower.shoot(creature);
                    break;
                }
            }

            bulletsList = tower.getBulletsList();
            if (bulletsList != null) {
                for ( Integer integer3 : bulletsList.keySet() ) {
                    if(bulletsList.get(integer3).isStillFlying()) {
                        bulletsList.get(integer3).fly();
                    } else {
                        bulletsList.remove(integer3);
                    }
                }
            }
        }

        wd.checkForStatsUpdate();
    }
}
