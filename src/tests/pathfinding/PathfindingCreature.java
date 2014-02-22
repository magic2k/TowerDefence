package tests.pathfinding;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

/**
 * Date: 04.01.12
 * Time: 14:46
 */

public class PathfindingCreature {
    protected int x, y, screen_x, screen_y;
//    Point2D coords = new Point2D.Double();
    double dx, dy;
    double pathfinded_x, pathfinded_y;
    int iterations = 30;
    int counter = 30;
    protected int health;
    protected int speed;
    private BufferedImage currentFrame;
    private ArrayList<BufferedImage> frameList = new ArrayList<BufferedImage>();
    private Rectangle bounds;
    private Iterator animationIterator;
    private boolean isDead;
    private boolean first = true;
    private Deque<PathfindingTile> pathList;
    NewPathfinding path = new NewPathfinding();
    PathfindingTile pathfindedTile = new PathfindingTile( new TilePoint(0, 0), null);
    private boolean centerReached = true;
    private double fieldVersion = 0;
    WorldData wd = WorldData.getInstance();

    // If not sync we have concurrent modification there. Not sure where it comes from
    // upd: problem solved by active renderer.
//    public synchronized void animation() {

    public void animation() {

        if (animationIterator.hasNext()) {
            currentFrame = (BufferedImage) animationIterator.next();
        } else {
            animationIterator = frameList.listIterator();
            currentFrame = (BufferedImage) animationIterator.next();
        }

//        notifyAll();
    }

    public Point getPosition() {
        return new Point(x, y);
    }

    public PathfindingCreature(int x, int y, int speed, int health) {
//        this.x = x;
//        this.y = y;
        this.speed = speed;
        this.health = health;
        // this code needed for spawning in center of tile
        this.x = TilePoint.findTile(x, y).getX();
        this.y = TilePoint.findTile(x, y).getY();

        screen_x = this.x * Tile.getTileWidth();
        screen_y = this.y * Tile.getTileHeight();

        isDead = false;

        frameList = initFrameList();
        //init animation array
        currentFrame = frameList.get(0);
        getBounds();

        animationIterator = frameList.listIterator();

    }

    public Rectangle getBounds() {
        bounds = new Rectangle(screen_x, screen_y, currentFrame.getWidth(), currentFrame.getHeight());
        return bounds;
    }

    public Point getCenter() {
        getBounds(); // its important to update current bounds position
//        System.out.println(" center " + new Point((int) bounds.getCenterX(), (int) bounds.getCenterY() ));
        return new Point((int) bounds.getCenterX(), (int) bounds.getCenterY());
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(currentFrame, null, screen_x, screen_y);
    }

    //todo return clip() !

    public void damaged(int damageReceived) {
        health -= damageReceived;
        if (health <= 0) {
            die();
        }
    }

    public void die() {
//        System.out.println(this + " is dead");
        isDead = true;
    }

    public boolean isDead() {
        return isDead;
    }

    public Point move() {
        animation();

        boolean test = true;
        while(test) {
            path = new NewPathfinding();
            path.bestWay(0, 0, 13, 11);
            return null;
        }
//        equals        this
        if (TilePoint.findTile(screen_x, screen_y).equals(new TilePoint(10, 10))) {
            dx=0;
            dy=0;     // creature stops
        }  else {

            if (centerReached) {
                if(fieldVersion != wd.getFieldVersion()) {

//                    NewPathfinding path = new NewPathfinding();
                    pathList = path.bestWay(TilePoint.findTile(screen_x, screen_y).getX(),
                            TilePoint.findTile(screen_x, screen_y).getY(), 10, 10);
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
        return new Point(x, y);
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

    public ArrayList<BufferedImage> initFrameList() {
        ArrayList<BufferedImage> frameList = new ArrayList<BufferedImage>();
        try {
            frameList.add(0, ImageIO.read((this.getClass().getClassLoader().getResourceAsStream("ru/spb/magic2k/assets/creatures/spider_anim/spider1_01.png"))));
            frameList.add(1, ImageIO.read((this.getClass().getClassLoader().getResourceAsStream("ru/spb/magic2k/assets/creatures/spider_anim/spider1_02.png"))));
            frameList.add(2, ImageIO.read((this.getClass().getClassLoader().getResourceAsStream("ru/spb/magic2k/assets/creatures/spider_anim/spider1_03.png"))));
            frameList.add(3, ImageIO.read((this.getClass().getClassLoader().getResourceAsStream("ru/spb/magic2k/assets/creatures/spider_anim/spider1_04.png"))));
            frameList.add(4, ImageIO.read((this.getClass().getClassLoader().getResourceAsStream("ru/spb/magic2k/assets/creatures/spider_anim/spider1_05.png"))));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return frameList;
    }

}
