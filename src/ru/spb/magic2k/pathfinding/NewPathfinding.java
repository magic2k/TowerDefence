package ru.spb.magic2k.pathfinding;

import org.apache.log4j.Logger;
import ru.spb.magic2k.field.Tile;
import ru.spb.magic2k.field.TilePoint;
import ru.spb.magic2k.field.WorldData;

import java.util.*;
import java.util.Map.Entry;

/**
 * Date: 04.01.12
 * Time: 15:19
 */
public class NewPathfinding implements IPathfinding {

    private int x, y;
    private PathfindingTile currentTile = null;
    private PathfindingTile ifNoPathTile = null;

    private WorldData wd = WorldData.getInstance();
    private Map<TilePoint, Tile> tileList = wd.getTileList();
    //maybe I need TreeMap there, but don't know is constant sorting worth it or not.
    // or PriorityQueue
    private Map<PathfindingTile, Double> openTilesList = new HashMap<PathfindingTile, Double>();
    private List<PathfindingTile> closedTilesList = new LinkedList<PathfindingTile>();
    Logger logger = Logger.getLogger(getClass());
    // TilePoint?
//    List<PathfindingTile> pathList = new LinkedList<PathfindingTile>();
    Deque<PathfindingTile> pathList = new LinkedList<PathfindingTile>();
    public List<PathfindingTile> pathListForTest = new LinkedList<PathfindingTile>();

    //Not Tile, it will be some pathList of Tiles
    public Deque<PathfindingTile> bestWay(int start_x, int start_y, int target_x, int target_y) {
        // find start tile, and its "x, y" for readability
        PathfindingTile startTile = new PathfindingTile(new TilePoint(start_x, start_y), null);
        PathfindingTile targetTile = new PathfindingTile(new TilePoint(target_x, target_y), null);

        openTilesList.put(startTile, 0d);
        currentTile = startTile;

        while (!currentTile.equals(targetTile)) {

            openTilesList.remove(currentTile);
            closedTilesList.add(currentTile);

            findAdjacentTiles(currentTile);
            if (openTilesList.isEmpty()) {
                logger.error("no tiles in openTilesList");
//                return null;
                pathList.add(currentTile);
                return pathList;
            }

            // calculate heuristic cost to target node
            for (PathfindingTile tile : openTilesList.keySet()) {
                tile.setH(calculateHeuristic(tile.getX(), tile.getY(), target_x, target_y));//

                tile.setG(currentTile.getF() + 10);

                tile.setF(tile.getG() + tile.getH());
                openTilesList.put(tile, tile.getF());
            }

            //calculating best node from openlist
            Entry<PathfindingTile, Double> min = null;
            PathfindingTile pfTile = null;

            for (Entry<PathfindingTile, Double> probablyBestTile : openTilesList.entrySet()) {

//                currentTile = probablyBestTile.getKey();  //WTF????7777 its worked!!111

                //все тайлы, что были в openList
                pathListForTest.add(probablyBestTile.getKey());

                if (min == null || probablyBestTile.getValue() < min.getValue())
                    min = probablyBestTile;
                    pfTile = min.getKey();
            }

            currentTile = pfTile;
            ifNoPathTile = pfTile;
            openTilesList.remove(pfTile);
            closedTilesList.add(pfTile);

        }

        // get path
        while(currentTile.getAncestorTile() != null) {
            pathList.add( currentTile.getAncestorTile() );
            currentTile = currentTile.getAncestorTile();
        }

        pathList.pollLast();

        if(pathList.isEmpty()) {
            pathList.add(ifNoPathTile);
        }

        return pathList;

    }

    private Double calculateHeuristic(int start_x, int start_y, int target_x, int target_y) {

        return (double) 10*(Math.abs(start_x - target_x) + (Math.abs(start_y - target_y)));
    }

    private Map<PathfindingTile, Double> findAdjacentTiles(PathfindingTile currentTile) {

        x = currentTile.getX();
        y = currentTile.getY();

        //NW
        TilePoint tileNW = new TilePoint(x-1, y+1);
        if (findAndCheckTile( tileNW ))
                openTilesList.put(new PathfindingTile(tileNW, currentTile), 0d);

        //N
        TilePoint tileN = new TilePoint(x, y+1);
        if (findAndCheckTile( tileN ))
                openTilesList.put(new PathfindingTile(tileN, currentTile), 0d);

         //NE
        TilePoint tileNE = new TilePoint(x+1, y+1);
        if (findAndCheckTile( tileNE ))
                openTilesList.put(new PathfindingTile(tileNE, currentTile), 0d);

         //E
        TilePoint tileE = new TilePoint(x+1, y);
        if (findAndCheckTile( tileE ))
                openTilesList.put(new PathfindingTile(tileE, currentTile), 0d);

         //SE
        TilePoint tileSE = new TilePoint(x+1, y-1);
        if (findAndCheckTile( tileSE ))
                openTilesList.put(new PathfindingTile(tileSE, currentTile), 0d);

         //S
        TilePoint tileS = new TilePoint(x, y-1);
        if (findAndCheckTile( tileS ))
                openTilesList.put(new PathfindingTile(tileS, currentTile), 0d);

         //SW
        TilePoint tileSW = new TilePoint(x-1, y-1);
        if (findAndCheckTile( tileSW ))
                openTilesList.put(new PathfindingTile(tileSW, currentTile), 0d);

         //W
        TilePoint tileW = new TilePoint(x-1, y);
        if (findAndCheckTile( tileW ))
                openTilesList.put(new PathfindingTile(tileW, currentTile), 0d);


        return openTilesList;
    }

    boolean findAndCheckTile(TilePoint tile) {


        if (tileList.containsKey(tile)) {

            if(closedTilesList.contains( tile )) {
                return false;
            } else {

                if(openTilesList.containsKey(tile)) {
                    //compare tile.G with currentTile.G. If tile.G is less, than this path better.
                    // To recalc F delete it from openList and return true

                    // must get rid out of this later
                    PathfindingTile tileInOpen = null;
                    for (Entry<PathfindingTile, Double> t : openTilesList.entrySet()) {
                        if( t.getKey().equals(tile) ) {
                            tileInOpen = t.getKey();
                        }
                    }
//
                    if(tileInOpen.getG() < currentTile.getG()) {
                          openTilesList.remove(tile);
                          return true;

                    }

                } else {
                    if(tileList.get( tile ).isWalkable())
                        return true;
                }

            }
        }
        return false;
    }

}
