package ru.spb.magic2k.field;


import ru.spb.magic2k.creatures.ICreature;
import ru.spb.magic2k.creatures.IDeadCreature;
import ru.spb.magic2k.creatures.Spider;
import ru.spb.magic2k.creatures.SpiderGoga;
import ru.spb.magic2k.towers.*;
import ru.spb.magic2k.towers.bullets.IBullet;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
* Date: 12.11.11
* Time: 2:29
*/
public class DrawPanel extends JPanel {

    private TilePoint clickedPoint = new TilePoint(0, 0);
    private int tileWidth = Tile.getTileWidth();
    private int tileHeight = Tile.getTileHeight();
    private Image dbImage = null;
//    private BufferedImage image;
    private BufferedImage backgroundImage;
    private BufferedImage caveImage;
    private MouseAdapter mouseAdapter;
    private KeyAdapter keyAdapter;
    private HashMap<TilePoint, Tile> tileList;
    private WorldData wd = WorldData.getInstance();
    private World world = World.getInstance();
    private CreaturesRespawnHandler respawnHandler = CreaturesRespawnHandler.getInstance();
    private Map<Integer, ICreature> creaturesList = wd.getCreaturesList();
    private Map<Integer,IDeadCreature> deadCreaturesList = wd.getDeadCreaturesList();
    private Map<Integer, ITower> towersList = wd.getTowersList();
    private Map<Integer, IBullet> bulletsList;
    Tile mouseOverTile = null;
    Image currentTowerImage = null;


    public DrawPanel() {
        super();
        tileList = wd.getTileList();
        init();
//        setDoubleBuffered(true);   // don't need it anymore, since active renderer there
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        addKeyListener(keyAdapter);
        setFocusable(true);    // focus on Draw Panel to catch Keyboard Events

        // setting default image of currently selected tower factory
        try{
            currentTowerImage = ImageIO.read((this.getClass().getClassLoader().getResourceAsStream("ru/spb/magic2k/assets/towers/cannon_tower_01.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

//            @Override
//            public void mouseDragged(MouseEvent e) {
//                FieldBuilder.LOG.debug("Dragged");
//            }

            @Override
            public void mouseMoved(MouseEvent e) {
                mouseMovedEventHandler(e);
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
//            image = ImageIO.read(new File("assets/back2.jpg"));
            // картинка заднего фона
//            backgroundImage = ImageIO.read(new File("assets/back.jpg"));
            //some change in image loading for jar
            backgroundImage = ImageIO.read((this.getClass().getClassLoader().getResourceAsStream("ru/spb/magic2k/assets/background_01.jpg")));
            caveImage = ImageIO.read((this.getClass().getClassLoader().getResourceAsStream("ru/spb/magic2k/assets/cave_01.png")));
        } catch(Exception ex) { FieldBuilder.LOG.error("Cant load background file " +ex); }
    }

    public void paintScreen() {
        Graphics g = this.getGraphics();
        g.drawImage(dbImage, 0, 0, null);
    }
    
//    @Override
//    public void paint(Graphics g) {
    public void gameRender() {

        dbImage = createImage( Tile.getTileWidth() * FieldBuilder.FIELD_WIDTH, Tile.getTileHeight() * FieldBuilder.FIELD_HEIGHT);
        Graphics g = dbImage.getGraphics();

        if( g != null) {
            Graphics2D g2d = (Graphics2D) g;
//            Shape sh = g2d.getClip();
//            Shape sh = new Rectangle2D.Double(0, 0, 800, 600);

            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


            // drawing background

            g2d.drawImage(backgroundImage, null, 0, 0);

            // drawing of field square grid
            /*
            int drawWidth = 0;
            for(int i = 0; i < FieldBuilder.FIELD_WIDTH; i++) {
                // for vertical
                for(int j = 0; j < FieldBuilder.FIELD_HEIGHT; j++ ) {
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
            */

            //drawing tile under mouse cursor
            if (mouseOverTile != null) {
                g2d.setColor(new Color(0, 255, 150, 60));
                g2d.drawImage(currentTowerImage, mouseOverTile.getX_screen() + 1, mouseOverTile.getY_screen() + 1, null);
                g2d.fillRect(mouseOverTile.getX_screen() + 1, mouseOverTile.getY_screen() + 1, tileWidth - 1, tileHeight - 1);
            }

            //drawing dead bodies
            if (deadCreaturesList != null) {
                for (Integer integer : deadCreaturesList.keySet()) {
                    deadCreaturesList.get(integer).draw(g2d);
                }
            }

            //towers drawing call
            for (Integer integer : towersList.keySet()) {
                ITower tower = towersList.get(integer);
                tower.draw(g2d);

                // projectiles from towers
                bulletsList = tower.getBulletsList();
                if (bulletsList != null) {
                    for ( Integer integer3 : bulletsList.keySet() ) {
                        if(bulletsList.get(integer3).isStillFlying())
                            bulletsList.get(integer3).draw(g2d);
                    }
                }
            }

            //creatures drawing call
            for (Integer integer : creaturesList.keySet()) {
//                if(creaturesList.get(integer) != null ) {  //creature may die and delete itself from the list. Must be lock there
                //not actual as active renderer there
                creaturesList.get(integer).draw(g2d);
//                }
            }

            //drawing cave creatures come from
            g2d.drawImage(caveImage, null, 0, 0);

//            repaint();
            g.dispose();
        }
    }

    private void mouseClickedEventHandler(MouseEvent event) {
        int mouseX = event.getX();
        int mouseY = event.getY();
        clickedPoint = TilePoint.findTile(mouseX, mouseY);
//       проверка, принадлежит ли кликнутый тайл координатам игрового поля.
        if( tileList.containsKey(clickedPoint) ) {
            world.placeTower( clickedPoint.getX(), clickedPoint.getY() );

            FieldBuilder.LOG.info(" mouse Clicked coords: " + mouseX + " : " + mouseY);
            FieldBuilder.LOG.info(" TILE coords: " + Math.floor(mouseX / tileWidth) + " : " + Math.floor( mouseY / tileHeight) );
        }  else {
            FieldBuilder.LOG.error("UNKNOWN TILE " + clickedPoint.getX() + " " + clickedPoint.getY());
        }
    }

    private void mouseMovedEventHandler(MouseEvent event) {
        mouseOverTile = tileList.get(TilePoint.findTile(event.getX(), event.getY()));

    }

    private void keyPressedEventHandler(KeyEvent event) {
        // 99 - key "c" code
//        char c = 99;
        if (event.getKeyChar() == 99) {
            if( Math.random() >= 0.5) {
//                world.spawnCreatures(Spider.class, 1, 4);
                respawnHandler.addToSpawnQueue(Spider.class, 1, 40);
            } else {
                respawnHandler.addToSpawnQueue(SpiderGoga.class, 1, 40);
            }
            System.out.println("Key 'c' pressed, more creatures respawned " );
        }
        if (event.getKeyChar() == 49) {
//            world.setCurrentTowerType(new CannonTowerFactory());
            TowerFactoriesHolder.setCurrentTowerFactory(CannonTowerFactory.class);
            System.out.println("Key '1' pressed, Cannon Tower selected " );
            try{
                currentTowerImage = ImageIO.read((this.getClass().getClassLoader().getResourceAsStream("ru/spb/magic2k/assets/towers/cannon_tower_01.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (event.getKeyChar() == 50) {
//            world.setCurrentTowerType(new FireballTowerFactory());
            TowerFactoriesHolder.setCurrentTowerFactory(FireballTowerFactory.class);
            System.out.println("Key '2' pressed, Fireball Tower selected " );
            try{
                currentTowerImage = ImageIO.read((this.getClass().getClassLoader().getResourceAsStream("ru/spb/magic2k/assets/towers/fireball_tower_01.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
