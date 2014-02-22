package ru.spb.magic2k.towers;

import java.awt.*;

/**
 * Date: 21.12.11
 * Time: 23:22
 */
public class FireballTowerFactory implements ITowerFactory {
    int towerCost = 150;
    
    public ITower constructTower(int x, int y) {
        return new FireballTower(x, y, 6, 0.9f);
    }
    
    public int getTowerCost() {
        return towerCost;
    }

}
