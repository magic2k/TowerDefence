package tests.pathfinding;

/**
 * дестроед бай Егорыч
 * User: Magic
 * Date: 20.10.11 23:03
 */
public interface IFieldConstructor {
     /**
     *
     * @param xTile x-coordinate of a tile
     * @param yTile y-coordinate of a tile
     * @param wb - walkability
     */
    Tile createTile(int xTile, int yTile, boolean wb);

}
