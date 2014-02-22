package tests.pathfinding;

import javax.swing.*;

/**
 * Date: 04.01.12
 * Time: 14:33
 */
public class PathfindingTest implements Runnable {

    private JFrame frame;
    private DrawPanel drawPanel;
    private volatile boolean isRunning;
    World world;
    public static final long PERIOD =40;
    Field field;

    public PathfindingTest() {
        super();

        String userdir = System.getProperty("user.dir");
        System.out.println(userdir);

        world = World.getInstance();
        field = new Field();
        field.createField();

        //interface
        frame = new JFrame();
        drawPanel = new DrawPanel();
        frame.getContentPane().add(drawPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize((Tile.getTileWidth() * Field.FIELD_WIDTH)+14, (Tile.getTileHeight() * Field.FIELD_HEIGHT)+36 );
        frame.setVisible(true);
//        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);
    }

    public static void main(String args[]) {

        PathfindingTest m = new PathfindingTest();
        m.run();
    }

    public void run() {
        isRunning = true;
        long beforeTime, afterTime, timeDiff, sleepTime;

        while(isRunning) {
            beforeTime = System.nanoTime();
//
            gameUpdate();   // game state is updated
            drawPanel.gameRender();   // render to a buffer
            drawPanel.paintScreen();  // draw buffer to screen

            afterTime = System.nanoTime();
            timeDiff = afterTime - beforeTime;
            sleepTime = PERIOD - (timeDiff/1000000000L);
//                   System.out.println(sleepTime);
            if(sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                Thread.yield();
            }
        }

    }

    public void stopGame() {
        isRunning = false;
    }

    public void gameUpdate() {
        world.update();
    }
}