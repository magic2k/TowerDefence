package tests;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

/**
 * Date: 29.11.11
 * Time: 15:32
 */
public class BulletTest implements Runnable {

    JFrame frame;
    testPanel testPanel;
    double x0, x1, y0, y1, x_now, y_now, dy, dx;
    int iterations = 100;
    int counter = 0;
    int speed = 8;

    public static void main(String args[]) {

        BulletTest bt = new BulletTest();
        bt.run();

    }

    public void run() {
        while (counter < (iterations/speed)) {
            try {
                x_now +=dx;
                y_now -=dy;
                counter+=1;
//                System.out.println(" x_now and y_now = " + x_now + "  " + y_now);
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            testPanel.repaint();
        }
    }

    public BulletTest() {
        calc();
        init();
    }

    public void calc() {

        x0 = 10;
        y0 = 200;
        x1 = 200;
        y1 = 50;

        dx = ((x1 - x0) / iterations)*speed;
        dy = ((y0 - y1) / iterations)*speed;

        x_now = x0;
        y_now = y0;


//        System.out.println(" dx and dy = " + dx + "  " + dy);
    }


    public void init() {
        frame = new JFrame();
        testPanel = new testPanel();
        frame.getContentPane().add(testPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);
//        Thread thrd = new Thread(testPanel);

    }

    class testPanel extends JPanel implements Runnable {

        public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(getBackground());
            setBackground(Color.BLUE);
            g2d.fill(new Rectangle2D.Double(0, 0, 400, 400));
            g2d.setColor(Color.RED);
            g2d.draw(new Rectangle2D.Double(x0, y0, 2, 2));
            g2d.draw(new Rectangle2D.Double(x1, y1, 2, 2));
            g2d.draw(new Line2D.Double(x0, y0, x1, y1));
            g2d.setColor(Color.GREEN);
            g2d.draw(new Rectangle2D.Double(x_now, y_now, 2, 2));

        }

        public void run() {
            repaint();
        }
    }

}
