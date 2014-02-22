package ru.spb.magic2k;

import ru.spb.magic2k.field.*;

/**
 * Date: 21.10.11
 * Time: 2:31
 */

// TODO: find move direction of creatures
    //TODO: moving algorithm

public class Main implements Runnable {
    private final String TAG = getClass().getName();

    private DrawPanel drawPanel;
    private Scoreboard scoreboard;
    private volatile boolean isRunning;
    private World world;
    private WorldData worldData;
    public static final long PERIOD = 35;
    private FieldBuilder field;
    private long second;
    private long fpsCounter = 0;

    public Main() {
        super();
        // TODO: maybe better to use Spring for this initialization.
        world = World.getInstance();
        worldData = WorldData.getInstance();
        drawPanel = new DrawPanel();
        scoreboard = new Scoreboard();
        field = new FieldBuilder();
        field.createField(drawPanel, scoreboard);

        //temp
        worldData.addObserver(scoreboard);
    }

    public static void main(String[] args) {

        Main m = new Main();
        m.run();
    }

    public void run() {
        isRunning = true;
        long beforeTime;

        while(isRunning) {
            beforeTime = System.nanoTime();
//
            gameUpdate();   // game state is updated
            drawPanel.gameRender();   // render to a buffer
            drawPanel.paintScreen();  // draw buffer to screen
            scoreboard.paintScreen();

            checkTime(beforeTime);
        }

    }

    public void stopGame() {
        isRunning = false;
    }

    public void gameUpdate() {
        world.update();
    }

    public void checkTime(long beforeTime) {
        long afterTime, timeDiffNano, sleepTime, timeDiffMillis;

        afterTime = System.nanoTime();
        timeDiffNano = afterTime - beforeTime;
        timeDiffMillis = timeDiffNano/1000000000L;
        sleepTime = PERIOD - timeDiffMillis;
//        System.out.println(sleepTime);

        if (second > 1000) {
            second = 0;
            worldData.setFps(fpsCounter);
            fpsCounter = 0;
        } else {
            fpsCounter++;
        }

        if(sleepTime > 0) {
            try {
                second += (timeDiffMillis + sleepTime);
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            second += timeDiffMillis;
            Thread.yield();
        }

    }

}
