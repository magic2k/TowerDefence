package tests.pathfinding;

import java.awt.*;

/**
 * Date: 27.10.11
 * Time: 14:46

 * Класс координат тайла
 * TODO: from kkolyan: подумать насчет сеттеров координат. Не следует использовать в качестве ключа HashMap объект, хеш которого может меняться.
 */
public class TilePoint {
    int x,y;
    int x_screen, y_screen;

    public TilePoint(int x,int y) {
        this.x = x;
        this.y = y;

        x_screen=x * Tile.getTileWidth();
        y_screen=y * Tile.getTileHeight();
    }

    public static int convertToScreenX(int x) {
        return x * Tile.getTileWidth();
    }


    public static int convertToScreenY(int y) {
        return y * Tile.getTileHeight();
    }

    public Point getCenter() {
        return new Point(x_screen+(Tile.getTileWidth()/2), y_screen+(Tile.getTileHeight()/2));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getX_screen() {
        x_screen=x * Tile.getTileWidth();
        return x_screen;
    }

    public int getY_screen() {
        y_screen=y * Tile.getTileHeight();
        return y_screen;
    }

    /**
     * Метод находит какому тайлу принадлежат переданные в него координаты
     * @param x
     * @param y
     * @return new TilePoint
     */
    public static TilePoint findTile(int x, int y) {
        return new TilePoint( (int) Math.floor(x / Tile.getTileWidth()), (int) Math.floor(y / Tile.getTileWidth()) );
    }
        
    @Override
    public boolean equals(Object o) {
//        if (this == o) return true;
        // убрал, т.к. это вызывало проблемы в pafthdinding. Эти объект должны сравниваться только по x и y.
//        if (o == null || getClass() != o.getClass()) return false;

        TilePoint tilePoint = (TilePoint) o;

        if (x != tilePoint.x) return false;
        if (y != tilePoint.y) return false;

        return true;
    }

    @Override
    public int hashCode() {    //maybe int is not enough
        int result = x;
        result = 31 * result + y;
        return result;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    // this is for tests for now
    public void setX_screen(int x_screen) {
        this.x_screen = x_screen;
    }

    public void setY_screen(int y_screen) {
        this.y_screen = y_screen;
    }
}