package tests;

import ru.spb.magic2k.creatures.Spider;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//long beforeTime, timeDiff, sleepTime;
//
//beforeTime = System.currentTimeMillis( );
//
//running = true;
//while(running) {
//  gameUpdate( );
//  gameRender( );
//  paintScreen( );
//

//Add the label to the panel.
// Then to move the label you simply invoke setLocation().
// There is no need to override the paintComponent() method or call repaint(). – camickr Dec 2 '10 at 21:17


/**
 * Date: 05.11.11
 * Time: 23:56
 */
//    MediaTracker                   (use it for load images, Luke!)
public class AnimationTest extends Thread implements Runnable {

    JFrame frame;
    testDrawPanel testDrawPanel;
    testTilePoint pointTest;

    public void init() {
        frame = new JFrame();
        pointTest = new testTilePoint(1, 1);
        testDrawPanel = new testDrawPanel(pointTest);

        String userdir = System.getProperty("user.dir");
        System.out.println(userdir);

        frame.getContentPane().add(testDrawPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setVisible(true);
//        Thread drawPanelThread = new Thread(testDrawPanel);
    }

    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                AnimationTest go = new AnimationTest();
                go.init();
                go.start();       // yeah! There was dy problem with painting!
            }
        });
    }

    public void run() {
        this.anim();
    }

    public void anim() {
        int x = 0;
        int y = 0;
        while(true) {
            if(pointTest.getX() <= 200) {
                pointTest.setX(x++);
                pointTest.setY(y++);
                testDrawPanel.repaint();
            }

            if(pointTest.getX() >= 200) {
                pointTest.setX(x++);
                pointTest.setY(y++);
                testDrawPanel.repaint();
            }

            if(pointTest.getX() >= 300) {
                pointTest.setX(x-=300);
                pointTest.setY(y-=300);
                testDrawPanel.run();
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class testDrawPanel extends JPanel implements Runnable {

    testTilePoint pointTest;
    AffineTransform transform;
    BufferedImage image;
    ImageInputStream imageIn;
    ImageReader reader;

    testDrawPanel(testTilePoint point1Test) {
        super();

        this.pointTest = point1Test;
        setDoubleBuffered(true); //automatic Double Buffer!
//        addMouseListener(mouseAdapter);
        try {
            image = ImageIO.read(new File("assets/creatures/spider_anim/spider1_01.png"));
//        imageIn = ImageIO.createImageInputStream(new File("spider0001.gif"));
//        reader.setInput(imageIn, true);
//        image = reader.read(random.nextInt(2));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor( getBackground() );
//        setBackground(Color.BLUE);
        g2d.fill( new Rectangle2D.Double(0, 0, 800, 600) );
        transform = AffineTransform.getTranslateInstance( pointTest.getX(), pointTest.getY() );
        g2d.setColor(Color.BLACK);
//        Rectangle2D drawableRectangle = new Rectangle2D.Double(0, 0 , 22, 22);
//        g2d.draw(drawableRectangle);
        //translate or transform
        int x,y;
        x = y =  (int) (Math.random()*100);
//        g2d.translate(pointTest.getX(), pointTest.getY());
        g2d.transform(transform);
        // need data structure to call drawing objects
//        new Spider(20, 20).draw(g2d);
//        new Spider(20, 10).draw(g2d);
//        new Spider(20, 40).draw(g2d);
//             new Spider(200, 200).draw(g2d);//yee!!! object paint itself!
//             new Spider(210, 210).draw(g2d);//yee!!! object paint itself!
//             new Spider((int) (Math.random()*100), (int) (Math.random()*100)).draw(g2d);//yee!!! object paint itself!
//             new Spider((int) (Math.random()*800), (int) (Math.random()*600)).draw(g2d);//yee!!! object paint itself!
//             new Spider((int) (Math.random()*800), (int) (Math.random()*600)).draw(g2d);//yee!!! object paint itself!
//             new Spider((int) (Math.random()*800), (int) (Math.random()*600)).draw(g2d);//yee!!! object paint itself!
//             new Spider((int) (Math.random()*800), (int) (Math.random()*600)).draw(g2d);//yee!!! object paint itself!
//             new Spider((int) (Math.random()*800), (int) (Math.random()*600)).draw(g2d);//yee!!! object paint itself!
//             new Spider((int) (Math.random()*800), (int) (Math.random()*600)).draw(g2d);//yee!!! object paint itself!
//             new Spider((int) (Math.random()*800), (int) (Math.random()*600)).draw(g2d);//yee!!! object paint itself!
//             new Spider((int) (Math.random()*800), (int) (Math.random()*600)).draw(g2d);//yee!!! object paint itself!
//             new Spider((int) (Math.random()*800), (int) (Math.random()*600)).draw(g2d);//yee!!! object paint itself!
//             new Spider((int) (Math.random()*800), (int) (Math.random()*600)).draw(g2d);//yee!!! object paint itself!
//             new Spider((int) (Math.random()*800), (int) (Math.random()*600)).draw(g2d);//yee!!! object paint itself!
//             new Spider((int) (Math.random()*800), (int) (Math.random()*600)).draw(g2d);//yee!!! object paint itself!
//


//        g2d.drawImage(image, null, 0, 0 );
//        g2d.drawImage(image, null, x, y );    // why????  upd: skobki blyat v Math before cast to int!!!
//        g2d.drawImage(image, null, 100, 100 );
    }

    public void run() {
        repaint();
    }
}