package ru.spb.magic2k.creatures;

import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Date: 03.01.12
 * Time: 2:05
 */
public abstract class BaseDeadCreature implements IDeadCreature {
    int screen_x, screen_y;
    BufferedImage deadBodyFrame = null;
    Logger logger = Logger.getLogger(BaseDeadCreature.class);

    public BaseDeadCreature(Point point) {
        screen_x = (int) point.getX();
        screen_y = (int) point.getY();
        try {
            deadBodyFrame = getDeadCreatureFrame();
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(deadBodyFrame, null, screen_x, screen_y);
    }

    public abstract BufferedImage getDeadCreatureFrame() throws IOException;
}
