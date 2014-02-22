package tests.pathfinding;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Magic
 * Date: 18.10.11
 * Time: 10:34
 * To change this template use File | Settings | File Templates.
 */
public class Tile {
    //or tileSize
    static final int TILE_HEIGHT = 48;
    static final int TILE_WIDTH = 48;
    private int xTile;
    private int yTile;
    private int x_screen;
    private int y_screen;
    private boolean isWalkable;
    private boolean isEmpty;
    private TilePoint tileCoordinates;


    /**
     *
     * @param xTile mouseX-coordinate of a tile
     * @param yTile mouseY-coordinate of a tile
     * @param walkable - walkability
     */

    public Tile(int xTile, int yTile, int x_screen, int y_screen, boolean walkable) {
        this.isWalkable = walkable;
        this.xTile = xTile;
        this.yTile = yTile;
        this.x_screen = x_screen;
        this.y_screen = y_screen;
        isEmpty = true;
        tileCoordinates = new TilePoint(xTile, yTile);
    }

    public TilePoint getTileCoordinates() {
        return tileCoordinates;
    }

    public boolean isWalkable() {
        return isWalkable;
    }

    public void setWalkable(boolean walkable) {
        isWalkable = walkable;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public int getY_screen() {
        return y_screen;
    }

    public int getX_screen() {
        return x_screen;
    }

    public Point getCenter() {
        return new Point(x_screen+(TILE_WIDTH/2), y_screen+(TILE_HEIGHT/2));
    }

    public static int getTileWidth() {
        return TILE_WIDTH;
    }

    public static int getTileHeight() {
        return TILE_HEIGHT;
    }
}
