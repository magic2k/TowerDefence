package ru.spb.magic2k.towers;

/**
 * Date: 21.12.11
 * Time: 23:16
 */
public class CannonTowerFactory implements ITowerFactory {
    int towerCost = 100;
    
    public ITower constructTower(int x, int y) {
        return new CannonTower(x, y, 5, 0.6f);
    }
    
    public int getTowerCost() {
        return towerCost;
    }
}
