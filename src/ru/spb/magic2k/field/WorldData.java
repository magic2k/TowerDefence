package ru.spb.magic2k.field;

import ru.spb.magic2k.creatures.DeadSpider;
import ru.spb.magic2k.creatures.ICreature;
import ru.spb.magic2k.creatures.IDeadCreature;
import ru.spb.magic2k.towers.ITower;

import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Date: 18.11.11
 * Time: 14:24
 *  Хранение существ, башен и списка тайлов
 *  Singleton, Observable
 */

public class WorldData extends Observable{
    private static WorldData ourInstance = new WorldData();
    private ConcurrentHashMap<Integer, ICreature> creaturesList;
    private ConcurrentHashMap<Integer, IDeadCreature> deadCreaturesList;
    private ConcurrentHashMap<Integer, ITower> towersList;
    private long fps = 0;
    private int killedCreaturesCounter = 0;
    private int playerGold = 500;
    private double fieldVersion = 1;

    public double getFieldVersion() {
        return fieldVersion;
    }

    public void newFieldVersion() {
        this.fieldVersion++;
    }

    public void checkForStatsUpdate() {
        if(hasChanged()) {
            Stats s = new Stats();
            s.setFps(fps);
            s.setKills(killedCreaturesCounter);
            s.setGold(playerGold);
            notifyObservers(s);
        }
    }


    public HashMap<TilePoint, Tile> getTileList() {
        return tileList;
    }

    private HashMap<TilePoint, Tile> tileList;

    public ConcurrentHashMap<Integer, ITower> getTowersList() {
        return towersList;
    }

    public ConcurrentHashMap<Integer, ICreature> getCreaturesList() {
        return creaturesList;
    }

    public ConcurrentHashMap<Integer, IDeadCreature> getDeadCreaturesList() {
        return deadCreaturesList;
    }

    public static WorldData getInstance() {
        return ourInstance;
    }

    public long getFps() {
        return fps;
    }

    public void setFps(long fps) {
        this.fps = fps;
        setChanged();
    }

    public int getKilledCreaturesCounter() {
        return killedCreaturesCounter;
    }

    public void increaseKilledCreaturesCounter() {
        killedCreaturesCounter++;
        setChanged();
    }

    public int getPlayerGold() {
        return playerGold;
    }

    public void increasePlayerGold(int gold) {
        playerGold += gold;
        setChanged();
    }

    public void decreasePlayerGold(int gold) {
        playerGold -= gold;
        setChanged();
    }

    private WorldData() {
        creaturesList = new ConcurrentHashMap<Integer, ICreature>();
        deadCreaturesList = new ConcurrentHashMap<Integer, IDeadCreature>();
        towersList = new ConcurrentHashMap<Integer, ITower>();
        tileList = new HashMap<TilePoint, Tile>();
//   TODO:     addObserver(); - will be in main for now. Will be in spring later
    }


}
