package ru.spb.magic2k.field;

import java.awt.*;
import java.util.HashMap;
import org.apache.log4j.Logger;

import javax.swing.*;

/**
 * Date: 20.10.11
 * Time: 19:55
 */
public class FieldBuilder {

    public static final int FIELD_WIDTH = 16;
    public static final int FIELD_HEIGHT = 12;
    private final int scoreboardWidth = Tile.getTileHeight() * FieldBuilder.FIELD_HEIGHT;
    private final int scoreboardHeight = 24;

    public static final Logger LOG=Logger.getLogger( FieldBuilder.class.toString() );

    HashMap<TilePoint, Tile> tileList = WorldData.getInstance().getTileList();

    public Tile createTile(int xTile, int yTile, boolean wb) {

        int x_screen=xTile * Tile.getTileWidth();
        int y_screen=yTile * Tile.getTileHeight();
        boolean walkable = wb;

        return new Tile(xTile, yTile, x_screen, y_screen, walkable);
    }

    /**
     *
     * Creates interface and main game screen
     * @param drawPanel is main game screen
     * @param scoreboard score interface panel
     */
    public void createField(DrawPanel drawPanel, Scoreboard scoreboard) {

        // create tile objects
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_HEIGHT; j++) {
                tileList.put( new TilePoint(i, j), createTile(i, j, true) );
            }
        }

        // create interface
        JFrame frame = new JFrame();
        scoreboard.setPreferredSize(new Dimension(scoreboardWidth, scoreboardHeight));
        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, scoreboard);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize((Tile.getTileWidth() * FieldBuilder.FIELD_WIDTH) + 14,
                (Tile.getTileHeight() * FieldBuilder.FIELD_HEIGHT) + 36 + scoreboardHeight);

//        frame.setUndecorated(true);
//        AWTUtilities.setWindowShape(frame, new Ellipse2D.Double(0,0,800,600));

        frame.setVisible(true);
//        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);
    }
}
