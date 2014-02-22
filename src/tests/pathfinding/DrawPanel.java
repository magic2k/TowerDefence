package tests.pathfinding;

import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
* Date: 12.11.11
* Time: 2:29
*/
public class DrawPanel extends JPanel {

    private TilePoint clickedPoint = new TilePoint(0, 0);
    private int tileWidth = Tile.getTileWidth();
    private int tileHeight = Tile.getTileHeight();
    private Image dbImage = null;
    private BufferedImage image;
    private BufferedImage image2;
    private BufferedImage backgroundImage;
    private MouseAdapter mouseAdapter;
    private KeyAdapter keyAdapter;
    private HashMap<TilePoint, Tile> tileList;
    private Map<TilePoint, Tile> blockedTileList = new ConcurrentHashMap<TilePoint, Tile>();
    private WorldData wd = WorldData.getInstance();
    private World world = World.getInstance();
    PathfindingCreature pathfindingCreature = world.pathfindingCreature;
    Logger logger = Logger.getLogger(getClass());


    public DrawPanel() {
        super();
        tileList = wd.getTileList();
        init();
//        setDoubleBuffered(true);   // dont need it anymore, since active renderer there
        addMouseListener(mouseAdapter);
        addKeyListener(keyAdapter);
        setFocusable(true);    // focus on Draw Panel to catch Keyboard Events
//            addMouseMotionListener(mouseAdapter);  //dragged
    }

    /**
     * Метод для хранения переменных и прочего. Чтобы не засорять описание конструктора.
     */
    private void init() {

        mouseAdapter = new MouseAdapter() {
            @Override
//                public void mouseClicked(MouseEvent e) {
            public void mousePressed(MouseEvent e) {
                mouseClickedEventHandler(e);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                Field.LOG.debug("Dragged");
            }
        };

        keyAdapter = new KeyAdapter() {
            @Override
            public void keyTyped (KeyEvent e) {
                keyPressedEventHandler(e);
            }
        };
        
        try {
        // картинки тайлов
//            image = ImageIO.read(new File("assets/grass1.png"));
            image2 = ImageIO.read((this.getClass().getClassLoader().getResourceAsStream("ru/spb/magic2k/assets/grass1.png")));
//            image = ImageIO.read(new File("assets/back2.jpg"));
            // картинка заднего фона
            backgroundImage = ImageIO.read((this.getClass().getClassLoader().getResourceAsStream("ru/spb/magic2k/assets/back.jpg")));
        } catch(Exception ex) { Field.LOG.error("Cant load grass file " +ex); }
    }

    public void paintScreen() {
        Graphics g = this.getGraphics();
        g.drawImage(dbImage, 0, 0, null);
    }
    
//    @Override
//    public void paint(Graphics g) {
    public void gameRender() {

        dbImage = createImage( Tile.getTileWidth() * Field.FIELD_WIDTH, Tile.getTileHeight() * Field.FIELD_HEIGHT);
        Graphics g = dbImage.getGraphics();

        if( g != null) {
            Graphics2D g2d = (Graphics2D) g;
//            Shape sh = g2d.getClip();
//            Shape sh = new Rectangle2D.Double(0, 0, 800, 600);

            // I think its not really needed there, but I want to test it
//            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


            // draw field
            int drawWidth = 0;
            g2d.drawImage(backgroundImage, null, 0, 0);

            //pathfinded path
            g2d.setColor(Color.CYAN);
            if (world.pathfindingCreature.path.pathListForTest != null) {
                for(PathfindingTile i : world.pathfindingCreature.path.pathListForTest) {
                    g2d.fill(new Rectangle2D.Double(
                            (double) i.getX_screen(),
                            (double) i.getY_screen(),
                            (double) Tile.TILE_WIDTH+2,
                            (double) Tile.TILE_HEIGHT+2));
                }
            }

            g2d.setColor(Color.ORANGE);
            if (world.pathfindingCreature.path.pathList != null) {
                for(TilePoint i : world.pathfindingCreature.path.pathList) {
                    g2d.fill(new Rectangle2D.Double(
                            (double) i.getX_screen(),
                            (double) i.getY_screen(),
                            (double) Tile.TILE_WIDTH+2,
                            (double) Tile.TILE_HEIGHT+2));
                }
            }


            //pathfinded tile
//            g2d.setColor(Color.BLUE);
//            if (world.pathfindingCreature.pathfindedTile != null)
//                g2d.fill(new Rectangle2D.Double((double) world.pathfindingCreature.pathfindedTile.getX_screen(),
//                        (double) world.pathfindingCreature.pathfindedTile.getY_screen(),
//                        (double) (Tile.TILE_WIDTH+2),
//                        (double) (Tile.TILE_HEIGHT+2)));

            // tile heuristic values
            g2d.setColor(Color.BLACK);
            if (world.pathfindingCreature.path.pathListForTest != null) {
                for(PathfindingTile i : world.pathfindingCreature.path.pathListForTest) {
                    g2d.drawString("G=" + i.getG(), i.getX_screen(), i.getY_screen()+15);
                    g2d.drawString("H=" + i.getH(), i.getX_screen(), i.getY_screen()+30);
                    g2d.drawString("F=" + i.getF(), i.getX_screen(), i.getY_screen()+48);
                }
            }

            if (world.pathfindingCreature.path.pathList != null) {
                for(PathfindingTile i : world.pathfindingCreature.path.pathList) {
                    g2d.drawString("G=" + i.getG(), i.getX_screen(), i.getY_screen()+15);
                    g2d.drawString("H=" + i.getH(), i.getX_screen(), i.getY_screen()+30);
                    g2d.drawString("F=" + i.getF(), i.getX_screen(), i.getY_screen()+48);
                }
            }


            for(int i = 0; i < Field.FIELD_WIDTH; i++) {
                // for vertical
                for(int j = 0; j < Field.FIELD_HEIGHT; j++ ) {
                    Rectangle2D drawableRectangle = new Rectangle2D.Double(drawWidth, j * tileHeight, tileWidth, tileHeight);
//                    if ( sh.contains(drawableRectangle) ){    // very cool method from George. Paint
                    //                    // draw repainted area
                    //                    g2d.setColor(Color.RED);
                    //                    g2d.draw(sh);
                    g2d.setColor(Color.BLACK);
                    g2d.drawImage(image, null, drawWidth, j * tileHeight); // картинка тайла
                    g2d.draw(drawableRectangle);
//                            g2d.setColor(Color.GREEN);
                    //                        g2d.fill( new Rectangle2D.Double(drawWidth, j * tileHeight, tileWidth, tileHeight) );
//                    }
                }
                drawWidth += tileWidth;
            }


            
            // рисование рамки. TODO: переделать грамотнее,
//        g2d.setColor(Color.YELLOW);
//        g2d.setStroke( new BasicStroke(2) );
////            g2d.fillRect( clickedPoint.getX_screen() + 1, clickedPoint.getY_screen() + 1, tileWidth - 1, tileHeight - 1 );
//        g2d.draw(new Rectangle2D.Double(clickedPoint.getX_screen() + 1, clickedPoint.getY_screen() + 1, tileWidth - 1, tileHeight - 1));
            // block
            g2d.setColor(Color.BLACK);
            for (TilePoint tp : blockedTileList.keySet()) {
                g2d.fill(new Rectangle2D.Double((double) blockedTileList.get(tp).getX_screen(),
                        (double) blockedTileList.get(tp).getY_screen(),
                        (double) Tile.TILE_WIDTH,
                        (double) Tile.TILE_HEIGHT));
            }

            g2d.setColor(Color. RED);
            g2d.fillRect((int)pathfindingCreature.pathfinded_x, (int)pathfindingCreature.pathfinded_y, 2, 2);
            g2d.fillRect(pathfindingCreature.x, pathfindingCreature.y, 2, 2);

            pathfindingCreature.draw(g2d);

//            repaint();
            g.dispose();
        }
    }

    private void mouseClickedEventHandler(MouseEvent event) {
        int mouseX = event.getX();
        int mouseY = event.getY();
//        clickedPoint.setX( (int) Math.floor(mouseX / tileWidth) );
//        clickedPoint.setY( (int) Math.floor(mouseY / tileHeight) );
        clickedPoint = TilePoint.findTile(mouseX, mouseY);

//       проверка, принадлежит ли кликнутый тайл координатам игрового поля.
        if( tileList.containsKey(clickedPoint) ) {
            world.placeTower( clickedPoint.getX(), clickedPoint.getY() );   //SETS WALKABLE = FALSE
            blockedTileList.put(clickedPoint, tileList.get(clickedPoint));

            logger.info(" mouse Clicked coords: " + mouseX + " : " + mouseY);
            logger.info(" TILE coords: " + Math.floor(mouseX / tileWidth) + " : " + Math.floor( mouseY / tileHeight) );
        }  else {
            logger.error("UNKNOWN TILE " + clickedPoint.getX() + " " + clickedPoint.getY());
        }
    }

    private void keyPressedEventHandler(KeyEvent event) {
        // 99 - key "c" code
//        char c = 99;
        if (event.getKeyChar() == 99) {

            System.out.println("Key 'c' pressed, more creatures respawned " );
        }
        if (event.getKeyChar() == 49) {
//            world.setCurrentTowerType(new CannonTowerFactory());
//            TowerFactoriesHolder.setCurrentTowerFactory(CannonTowerFactory.class);
            System.out.println("Key '1' pressed, Cannon Tower selected " );
        }
        if (event.getKeyChar() == 50) {
//            world.setCurrentTowerType(new FireballTowerFactory());
//            TowerFactoriesHolder.setCurrentTowerFactory(FireballTowerFactory.class);
            System.out.println("Key '2' pressed, Fireball Tower selected " );
        }
    }
}
