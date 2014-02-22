package ru.spb.magic2k.creatures;

import ru.spb.magic2k.field.TilePoint;
import ru.spb.magic2k.field.WorldData;
import ru.spb.magic2k.pathfinding.DiagonalShortcutPathfinding;
import ru.spb.magic2k.pathfinding.IPathfinding;
import ru.spb.magic2k.pathfinding.NewPathfinding;
import ru.spb.magic2k.pathfinding.PathfindingTile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;

/**
 * Date: 03.01.12
 * Time: 1:14
 */
public class SpiderGoga extends BaseCreature {

    private boolean centerReached = true;
    private double dx, dy;
    private double pathfinded_x, pathfinded_y;
    private int iterations = 30;
    private int counter = 30;
    private double fieldVersion = 0;
    private Deque<PathfindingTile> pathList;
    private PathfindingTile pathfindedTile = new PathfindingTile( new TilePoint(0, 0), null);
    private WorldData wd = WorldData.getInstance();

    public SpiderGoga(int x_screen, int y_screen, int speed, int health) {
        super(x_screen, y_screen, speed, health);
    }

    public ArrayList<BufferedImage> initFrameList() {
        ArrayList<BufferedImage> frameList = new ArrayList<BufferedImage>();
        try{
            frameList.add( 0, ImageIO.read((this.getClass().getClassLoader().getResourceAsStream("ru/spb/magic2k/assets/creatures/goga_spider/fr0000.png"))) );
            frameList.add( 1, ImageIO.read((this.getClass().getClassLoader().getResourceAsStream("ru/spb/magic2k/assets/creatures/goga_spider/fr0001.png"))) );
            frameList.add( 2, ImageIO.read((this.getClass().getClassLoader().getResourceAsStream("ru/spb/magic2k/assets/creatures/goga_spider/fr0002.png"))) );
            frameList.add( 3, ImageIO.read((this.getClass().getClassLoader().getResourceAsStream("ru/spb/magic2k/assets/creatures/goga_spider/fr0003.png"))) );
            frameList.add( 4, ImageIO.read((this.getClass().getClassLoader().getResourceAsStream("ru/spb/magic2k/assets/creatures/goga_spider/fr0004.png"))) );
            frameList.add( 5, ImageIO.read((this.getClass().getClassLoader().getResourceAsStream("ru/spb/magic2k/assets/creatures/goga_spider/fr0005.png"))) );
            frameList.add( 6, ImageIO.read((this.getClass().getClassLoader().getResourceAsStream("ru/spb/magic2k/assets/creatures/goga_spider/fr0006.png"))) );
            frameList.add( 7, ImageIO.read((this.getClass().getClassLoader().getResourceAsStream("ru/spb/magic2k/assets/creatures/goga_spider/fr0007.png"))) );
            frameList.add( 8, ImageIO.read((this.getClass().getClassLoader().getResourceAsStream("ru/spb/magic2k/assets/creatures/goga_spider/fr0008.png"))) );
            frameList.add( 9, ImageIO.read((this.getClass().getClassLoader().getResourceAsStream("ru/spb/magic2k/assets/creatures/goga_spider/fr0009.png"))) );


        } catch (IOException e) {
            e.printStackTrace();
        }

        return frameList;
    }

    public void move() {
        animation();
//        screen_x +=speed;
//        screen_y +=speed;
        //        equals        this
        if (TilePoint.findTile(screen_x, screen_y).equals(new TilePoint(13, 11))) {
            dx=0;
            dy=0;     // creature stops
        }  else {

            if (centerReached) {
                if(fieldVersion != wd.getFieldVersion()) {

                    IPathfinding path = new DiagonalShortcutPathfinding();
                    pathList = path.bestWay(TilePoint.findTile(screen_x, screen_y).getX(),
                            TilePoint.findTile(screen_x, screen_y).getY(), 13, 11);
                    fieldVersion = wd.getFieldVersion();
                }

                if ((pathfindedTile = pathList.pollLast()) != null) {
                    pathfinded_x = pathfindedTile.getX_screen();
                    pathfinded_y = pathfindedTile.getY_screen();
                    findDirection(pathfinded_x, pathfinded_y);  // Y U NEED TO BE CALLED ALL THE TIME?
                    centerReached= false;
                }
            } else {
                checkCenterApproach();
                screen_x += dx;
                screen_y -= dy;

                counter++;
            }
        }
    }

    private void checkCenterApproach() {
        if(screen_x == pathfinded_x && screen_y == pathfinded_y) {
            dx = 0;
            dy = 0;
            centerReached = true;
        }
    }

    private void findDirection(double pathfinded_x, double pathfinded_y) {
        dx = Math.round(( (pathfinded_x - screen_x) / (iterations / speed) )) ;
        dy = Math.round(( (screen_y - pathfinded_y) / (iterations / speed) ));
    }


    public boolean isMoved() {
        return true;
    }

    public IDeadCreature getDeadCreatureBody(Point point) {
        return new DeadSpiderGoga(point);
    }
}
