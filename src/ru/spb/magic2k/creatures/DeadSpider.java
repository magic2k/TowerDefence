package ru.spb.magic2k.creatures;

import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Date: 24.12.11
 * Time: 0:50
 */
public class DeadSpider extends BaseDeadCreature {
    
    public DeadSpider(Point point) {
        super(point);

    }

    public BufferedImage getDeadCreatureFrame() throws IOException {
        return ImageIO.read((this.getClass().getClassLoader().getResourceAsStream("ru/spb/magic2k/assets/creatures/spider_anim/spider1_03.png")));
    }

}
