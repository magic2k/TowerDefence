package ru.spb.magic2k.creatures;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Date: 03.01.12
 * Time: 2:02
 */
public interface IDeadCreature {
    void draw(Graphics2D g2d);
    BufferedImage getDeadCreatureFrame() throws IOException;
}
