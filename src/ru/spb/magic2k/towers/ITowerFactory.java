package ru.spb.magic2k.towers;

/**
 * Date: 21.12.11
 * Time: 23:09
 */
public interface ITowerFactory {
    ITower constructTower(int x, int y);
    int getTowerCost();
}
