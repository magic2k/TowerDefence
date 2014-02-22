//package tests.pathfinding;
//
//import org.apache.log4j.Logger;
//
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
///**
// * Date: 04.01.12
// * Time: 15:19
// */
//public class Pathfinding implements IPathfinding {
//
//    private int x, y;
//    private Tile bestTile = null;
//
//    private WorldData wd = WorldData.getInstance();
//    private Map<TilePoint, Tile> tileList = wd.getTileList();
//    //maybe I need TreeMap there, but don't know is constant sorting worth it.
//    // or PriorityQueue
//    private Map<TilePoint, Double> openTilesList = new HashMap<TilePoint, Double>();
//    private List<TilePoint> closedTilesList = new LinkedList<TilePoint>();
//    Logger logger = Logger.getLogger(getClass());
//
//    public List<PathfindingTile> bestWay(int current_x, int current_y, int target_x, int target_y) {
//
//        // find current tile, and its "x, y" for readability
//        TilePoint currentTile = TilePoint.findTile(current_x, current_y);
//
//
//
//        //clear openList so we can analyze only adjacent tiles
//        openTilesList.clear();
//        closedTilesList.add(currentTile);
//
//        findAdjacentTiles(currentTile);
//        if (openTilesList.isEmpty()) {
////            logger.error("notify() tiles in openTilesList!");
////            return null;
//            //if no moves left - let creature use visited tiles
//            if(openTilesList.isEmpty()) {
//                closedTilesList.clear();
//                findAdjacentTiles(currentTile); //again
//            }
//        }
//
//        // calculate distance to target node
//        for ( TilePoint tile : openTilesList.keySet() ) {
//            openTilesList.put(tile, calculateHeuristic(tile, target_x, target_y));
////            System.out.println( tile.getX() + " : " + tile.getY()  + " - " + openTilesList.get(tile));
//        }
//
//        //calculating best node from openlist
//        Entry<TilePoint, Double> min = null;
//        for (Entry<TilePoint, Double> probablyBestTile : openTilesList.entrySet()) {
//            if (min == null || probablyBestTile.getValue() < min.getValue())
//                min = probablyBestTile;
//        }
//
//        // we need to store visited tiles in closed list, so we cannot return there
////        closedTilesList.put(min.getKey(), min.getValue());
//        closedTilesList.add(min.getKey());
//
//        System.out.println("THE BEST TILE IS : " + min.getKey().getX() + " : " + min.getKey().getY() );
//        bestTile = tileList.get(min.getKey());
//
//        // need to make closedlist
////        closedTilesList.put()
//
//        // WIP
////        return bestTile;
//        return null;
//    }
//
//    private Double calculateHeuristic(TilePoint tile, int target_x, int target_y) {
//        // take tile and calculate Heuristic.
//        return (double) 10*(Math.abs(tile.getX() - target_x) + (Math.abs(tile.getY() - target_y)));
//    }
//
//    private Map<TilePoint, Double> findAdjacentTiles(TilePoint currentTile) {
//
//        x = currentTile.getX();
//        y = currentTile.getY();
//
//        //NW
//        TilePoint tileNW = new TilePoint(x-1, y+1);
//        if (findAndCheckTile( tileNW ))
//                openTilesList.put(tileNW, Double.valueOf(0));
//
//        //N
//        TilePoint tileN = new TilePoint(x, y+1);
//        if (findAndCheckTile( tileN ))
//                openTilesList.put(tileN, Double.valueOf(0));
//
//         //NE
//        TilePoint tileNE = new TilePoint(x+1, y+1);
//        if (findAndCheckTile( tileNE ))
//                openTilesList.put(tileNE, Double.valueOf(0));
//
//         //E
//        TilePoint tileE = new TilePoint(x+1, y);
//        if (findAndCheckTile( tileE ))
//                openTilesList.put(tileE, Double.valueOf(0));
//
//         //SE
//        TilePoint tileSE = new TilePoint(x+1, y-1);
//        if (findAndCheckTile( tileSE ))
//                openTilesList.put(tileSE, Double.valueOf(0));
//
//         //S
//        TilePoint tileS = new TilePoint(x, y-1);
//        if (findAndCheckTile( tileS ))
//                openTilesList.put(tileS, Double.valueOf(0));
//
//         //SW
//        TilePoint tileSW = new TilePoint(x-1, y-1);
//        if (findAndCheckTile( tileSW ))
//                openTilesList.put(tileSW, Double.valueOf(0));
//
//         //W
//        TilePoint tileW = new TilePoint(x-1, y);
//        if (findAndCheckTile( tileW ))
//                openTilesList.put(tileW, Double.valueOf(0));
//
//
//        return openTilesList;
//    }
//
//    boolean findAndCheckTile(TilePoint tile) {
//        if (tileList.containsKey( tile ))
//            if(!closedTilesList.contains( tile ))
//                if(tileList.get( tile ).isWalkable())
//                    return true;
//        return  false;
//    }
//
//}
