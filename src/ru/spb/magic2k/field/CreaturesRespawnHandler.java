package ru.spb.magic2k.field;

import org.apache.log4j.Logger;
import ru.spb.magic2k.Main;
import ru.spb.magic2k.creatures.ICreature;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by IntelliJ IDEA.
 * User: magic2k
 * Date: 13.02.12
 * Time: 23:30
 *
 * Singleton
 */

public class CreaturesRespawnHandler {
    long updatesCounter = 0;
    long updateCounterLimit = 650 / Main.PERIOD;
    boolean isWaveRespawnDone = true;
    Logger logger = Logger.getLogger(getClass());
    Queue<ICreature> spawningCreatureList = new LinkedList<ICreature>();
    Constructor<? extends ICreature> creatureConstructor = null;
    AtomicInteger atomCreatureId = new AtomicInteger(0);
    WorldData wd = WorldData.getInstance();
    Map<Integer,ICreature> creaturesList = wd.getCreaturesList();

    private static CreaturesRespawnHandler ourInstance = new CreaturesRespawnHandler();

    public static CreaturesRespawnHandler getInstance() {
        return ourInstance;
    }

    public void checkAndRespawn() {
        if(!isWaveRespawnDone) {
            if(updateCounterLimit <= updatesCounter) {
                updatesCounter = 0;
                ICreature newCreature = spawningCreatureList.poll();
                if (newCreature == null) {
                    isWaveRespawnDone = true;
                } else {
                    creaturesList.put(atomCreatureId.getAndIncrement(), newCreature);
                }
            }
            updatesCounter++;
        }
    }
    
    // replace for creatureSpawn
    public void addToSpawnQueue(Class<? extends ICreature> creatureClass, int number, int health) {

        for(int i = 0; i < number; i++) {

            try {
                creatureConstructor = creatureClass.getConstructor(int.class, int.class, int.class, int.class);

            } catch (NoSuchMethodException e) {
                logger.error("error in getting creature constructor", e);
            }

            try {
                spawningCreatureList.add(creatureConstructor.newInstance(
//                        (int) (Math.random() * 10),
//                        (int) (Math.random() * 10),
                        10,
                        10,
                        (int) ((Math.random() * 1) + 1),   //speed
                        health
                ));

            } catch (InstantiationException e) {
                logger.error(e);
            } catch (IllegalAccessException e) {
                logger.error(e);
            } catch (InvocationTargetException e) {
                logger.error(e);
            }
        }

        isWaveRespawnDone = false;
    }

}