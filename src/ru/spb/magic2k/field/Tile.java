package ru.spb.magic2k.field;

import java.awt.*;

/**
 * User: Magic
 * Date: 18.10.11
 * Time: 10:34
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
