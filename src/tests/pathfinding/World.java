package tests.pathfinding;

import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Date: 17.11.11
 * Time: 21:56
 *  class for world description
 *  stores list of current creatures, spawns new waves. maybe control users gold
 *
 *  Singleton
 */
public class World {

    WorldData wd = WorldData.getInstance();


    HashMap<TilePoint, Tile> tileList = wd.getTileList();
    AtomicInteger atomTowerId = new AtomicInteger(0);
    AtomicInteger atomCreatureId = new AtomicInteger(0);
    AtomicInteger atomDeadCreatureId = new AtomicInteger(0);

    protected static final Logger logger = Logger.getLogger(World.class);
    private static World ourInstance = new World();
//    PathfindingCreature pathfindingCreature = new PathfindingCreature(200, 158, 5, 100);
    PathfindingCreature pathfindingCreature = new PathfindingCreature(0, 0, 2, 100);



    public static World getInstance() {
        return ourInstance;
    }

    private World() {
        init();
    }

    public void init() {
        //TODO: вынести текст во внешний файл (strings.xml , etc...)
        System.out.printf("MOTD: Pathfinding test.");

    }
             // walkable tag toggle
    public void placeTower(int x, int y) {
        TilePoint tilePoint = new TilePoint(x, y);
        if( tileList.get(tilePoint).isEmpty() ) {
            tileList.get(tilePoint).setEmpty(false);
            tileList.get(tilePoint).setWalkable(false);
            wd.newFieldVersion();
        } else {
            logger.debug("Tile is not empty");
        }
    }

    public void update() {

    pathfindingCreature.move();

    }
}
