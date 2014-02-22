package tests;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Date: 28.11.11
 * Time: 14:30
 */
public class MathTests implements Runnable {


    JFrame frame;
    testPanel testPanel;
    double x0, x1, y0, y1, xc, yc, a, b, a_now, b_now, alpha;
    int speed = 10;

        public static void main(String args[]) {

            MathTests mt = new MathTests();

            mt.run();

        }

    public void run() {
        testPanel.repaint();
    }

    public MathTests() {
        calc();
        init();
    }

    public void calc() {

        x0 = 50;
        y0 = 200;
        x1 = 200;
        y1 = 50;

        //находим катеты
        a = y0 - y1;
        b = x1 - x0;

        //вычисляем угол, переводим его в градусы
//        alpha =  Math.toDegrees(Math.atan(dy/dx));
        alpha = Math.atan(a/b);
        double alpha_in_degree = Math.toDegrees(alpha);

        //начинаем смещаться по иксу (катету dx)
        b_now = b+speed;

        //находим теперь катет a_now при таком катете b_now
        a_now = b_now / Math.sin(Math.toRadians(90) - alpha)* Math.sin(alpha);
        // высчитыавем точку c
        xc = x0+b_now;
        yc = y0-a_now;
//                alpha = Math.atan(a_now/b_now);
//        alpha_in_degree = Math.toDegrees(alpha);

        // сделать проверку - найти угол  dy;pha_now (должен быть таким же)
//        .......

        System.out.println(" xc = " + xc + " yc = " + yc );
        System.out.println(" dy and dx = " + a + "  " + b);
        System.out.println(" alpha in rad = " + alpha + " alpha in degree = " + alpha_in_degree );
        System.out.println(" a_now = " + a_now);
        System.out.println(" b_now = " + b_now);
        System.out.println(" 90 angle in rad = " + Math.toRadians(90));
        System.out.println(" sin alpha = " + Math.sin(alpha));
    }


    public void init() {
        frame = new JFrame();
        testPanel = new testPanel();
        frame.getContentPane().add(testPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,400);
        frame.setVisible(true);
//        Thread thrd = new Thread(testPanel);

    }

    class testPanel extends JPanel implements Runnable {

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor( getBackground() );
        setBackground(Color.BLUE);
        g2d.fill(new Rectangle2D.Double(0, 0, 400, 400));
        g2d.setColor(Color.RED);
        g2d.draw(new Rectangle2D.Double(x0, y0, 2, 2));
        g2d.draw(new Rectangle2D.Double(x1, y1, 2, 2));
        g2d.draw(new Line2D.Double(x0,y0,x1,y1));
        g2d.setColor(Color.GREEN);
        g2d.draw(new Line2D.Double(x0, y0, xc, yc));


    }
        public void run() {
            repaint();
        }
    }
}
