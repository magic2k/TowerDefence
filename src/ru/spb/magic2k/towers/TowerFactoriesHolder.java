package ru.spb.magic2k.towers;

import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * User: v.kolosov
 * Date: 22.12.11
 * Time: 16:27
 * Содержит список-кэш фабрик. Хранит созданные фабрики. Ключем будет служить класс фабрик.
 * static init?
 */
public class TowerFactoriesHolder {
    
    protected static final Logger logger = Logger.getLogger(TowerFactoriesHolder.class);
    
    static private ITowerFactory currentTowerFactory = new CannonTowerFactory();
//    static private ITowerFactory currentTowerFactory = null;

    static Map<Class<? extends ITowerFactory>, ITowerFactory> towerFactoryCache =
            new HashMap<Class<? extends ITowerFactory>, ITowerFactory>(){{
//                put(currentTowerFactory.getClass(), currentTowerFactory);
            }};

    public static ITowerFactory getCurrentTowerFactory() {
        return currentTowerFactory;
    }

    public static void setCurrentTowerFactory(Class<? extends ITowerFactory> factoryClass) {
        ITowerFactory factoryInstance = null;
        if(!towerFactoryCache.containsKey(factoryClass)) {
            // reflection. Create new instance of received class, if don't have it in our cache yet.
            try {
                factoryInstance = factoryClass.newInstance();
//                factoryInstance = factoryClass.getConstructor();
            } catch (InstantiationException e) {
                logger.error(e, e);
            } catch (IllegalAccessException e) {
                logger.error(e, e);
            }
            towerFactoryCache.put(factoryClass, factoryInstance);
        }

        currentTowerFactory = towerFactoryCache.get(factoryClass);
    }

//    static public boolean hasFactory(Class<? extends ITowerFactory> check) {
//        return towerFactoryCache.containsKey(check);
//    }
//
//    static void put(ITowerFactory factory) {
//        towerFactoryCache.put(factory.getClass(), factory);
//    }
}
