package tests.pathfinding;


import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Date: 18.11.11
 * Time: 14:24
 *  Хранение списко существ, башен и списка тайлов
 *  Singleton
 */

public class WorldData {
    private static WorldData ourInstance = new WorldData();

    public HashMap<TilePoint, Tile> getTileList() {
        return tileList;
    }

    private HashMap<TilePoint, Tile> tileList;


    private double fieldVersion = 1;

    public double getFieldVersion() {
        return fieldVersion;
    }

    public void newFieldVersion() {
        this.fieldVersion++;
    }


    public static WorldData getInstance() {
        return ourInstance;
    }

    private WorldData() {;
        tileList = new HashMap<TilePoint, Tile>();
    }


}
