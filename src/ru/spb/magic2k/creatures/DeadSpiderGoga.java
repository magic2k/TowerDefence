package ru.spb.magic2k.creatures;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Date: 03.01.12
 * Time: 2:36
 */
public class DeadSpiderGoga extends BaseDeadCreature {

    public DeadSpiderGoga(Point point) {
        super(point);

    }

    public BufferedImage getDeadCreatureFrame() throws IOException {
        return ImageIO.read((this.getClass().getClassLoader().getResourceAsStream("ru/spb/magic2k/assets/creatures/goga_spider/f0006.png")));
    }
}
