package tests;

/**
 * Date: 27.10.11
 * Time: 14:46

 * Класс координат тайла   TEST
 */
public class testTilePoint {
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    int x,y;
    int x_screen, y_screen;

    public testTilePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}