package tests.pathfinding;

/**
 * Date: 08.02.12
 * Time: 21:25
 */
public class PathfindingTile extends TilePoint {

    /**
     * @comeFromTile Тайл, с которого мы попали на данный тайл.
     */
    private PathfindingTile ancestorTile = null;
    private double g;
    private double h;
    private double f;

    public PathfindingTile(TilePoint successorTile, PathfindingTile ancestorTile) {
        super(successorTile.getX(), successorTile.getY());
        this.ancestorTile = ancestorTile;
    }
//    public PathfindingTile(Tile successorTile, PathfindingTile ancestorTile) {
//        super(successorTile.getTileCoordinates().getX(), successorTile.getTileCoordinates().getY(), successorTile.getX_screen(), successorTile.getY_screen(), successorTile.isWalkable());
//        this.ancestorTile = ancestorTile;
//    }

    // Tile or TilePoint?
    public PathfindingTile getAncestorTile() {
        return ancestorTile;
    }

    public static PathfindingTile findTile(int x, int y) {
        TilePoint tilePoint = TilePoint.findTile(x, y);
        return new PathfindingTile(tilePoint , null);
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }
}
