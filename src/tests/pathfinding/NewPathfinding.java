package tests.pathfinding;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.Map.Entry;

/**
 * Date: 04.01.12
 * Time: 15:19
 */
public class NewPathfinding implements IPathfinding {

    private int x, y;
    private Tile bestTile = null;
    private PathfindingTile currentTile = null;

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
//        int current_x = start_x;
//        int current_y = start_y;
        
        System.out.println("START : " + startTile.getX()+ " : " + startTile.getY() + " TARGET : " + targetTile.getX() + " : "
                + targetTile.getY());

        openTilesList.put(startTile, 0d);
        currentTile = startTile;

        while (!currentTile.equals(targetTile)) {

            openTilesList.remove(currentTile);
            closedTilesList.add(currentTile);

            findAdjacentTiles(currentTile);
            if (openTilesList.isEmpty()) {
                logger.error("no tiles in openTilesList");
                return null;
            }

            // calculate heuristic cost to target node
            for (PathfindingTile tile : openTilesList.keySet()) {
                tile.setH(calculateHeuristic(tile.getX(), tile.getY(), target_x, target_y));//
                //its wrong.. G = its the sum of all previous tile.F
//                tile.setG(calculateHeuristic(start_x, start_y, tile.getX(), tile.getY()));
                //*Fuck yeah face*
                tile.setG(currentTile.getF() + 10);
                
                tile.setF(tile.getG() + tile.getH());
                openTilesList.put(tile, tile.getF());
//            System.out.println( tile.getX() + " : " + tile.getY()  + " - " + openTilesList.get(tile));
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
            openTilesList.remove(pfTile);
            closedTilesList.add(pfTile);

            System.out.println("THE BEST TILE IS : " + min.getKey().getX() + " : " + min.getKey().getY());
            bestTile = tileList.get(min.getKey());

        }

        // get path
        while(currentTile.getAncestorTile() != null) {
            pathList.add( currentTile.getAncestorTile() );
            currentTile = currentTile.getAncestorTile();
        }

        pathList.pollLast();

        return pathList;

    }

    private Double calculateHeuristic(int start_x, int start_y, int target_x, int target_y) {

        return (double) 10*(Math.abs(start_x - target_x) + (Math.abs(start_y - target_y)));

//        double h;
//        double xDistance = (double) Math.abs(start_x - target_x);
//        double yDistance = (double) Math.abs(start_y - target_y);
//        if (xDistance > yDistance) {
//            h = 14*yDistance + 10*(xDistance-yDistance);
//        } else {
//            h = 14*xDistance + 10*(yDistance-xDistance);
//        }
//
//        return h;
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
//        PathfindingTile tilePath = new PathfindingTile(tile, null);

        if (tileList.containsKey(tile)) {
            if(closedTilesList.contains( tile )) {

//                if(closedTilesList.get(closedTilesList.indexOf(tile)).getG() < currentTile.getG()) {
//                    closedTilesList.remove(tile);
//                    return true;
//                }

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
//                    if(tileInOpen.getH() > currentTile.getH()) {
//                          openTilesList.remove(tile);
//                          return true;
//
//                    }

                } else {
                    if(tileList.get( tile ).isWalkable())
                        return true;
                }

            }


        }
        return false;
    }

}
