package tests.pathfinding;

import org.apache.log4j.Logger;

import java.util.HashMap;

/**
 * Date: 20.10.11
 * Time: 19:55
 */
public class Field implements IFieldConstructor {

    public static final int FIELD_WIDTH = 15;
    public static final int FIELD_HEIGHT = 12;

    public static final Logger LOG=Logger.getLogger( Field.class.toString() );

    HashMap<TilePoint, Tile> tileList = WorldData.getInstance().getTileList();

    public Tile createTile(int xTile, int yTile, boolean wb) {

        int x_screen=xTile * Tile.getTileWidth();
        int y_screen=yTile * Tile.getTileHeight();
        boolean walkable = wb;

        return new Tile(xTile, yTile, x_screen, y_screen, walkable);
    }


    public void createField() {

        // create tile objects
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_HEIGHT; j++) {
                tileList.put( new TilePoint(i, j), createTile(i, j, true) );
            }
        }
    }
}
