import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;
public class Driver {
    public static void main(String [] args)throws IOException
    {
        Scanner kb=new Scanner(System.in);
        System.out.println("Enter input File:");
        String filename=kb.nextLine();

        TriangleFinder tFinder=new TriangleFinder(filename);
        tFinder.makeMonotone();
        ArrayList<LineSegment> lineList=tFinder.getLines();
        ArrayList<Point> pList=tFinder.pList;

        System.out.println("Enter Scale:");
        double scale=kb.nextDouble();

        JFrame f = new JFrame("LineSegmentCanvas");
        f.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        JApplet applet = new LineSegmentCanvas(pList, lineList, scale, tFinder.getYMax());
        f.getContentPane().add("Center", applet);
        applet.init();
        f.pack();
        f.setSize(new Dimension((int) Math.round(scale * tFinder.getXMax() + scale), (int) Math.round(scale * tFinder.getYMax() + scale)));
        f.setVisible(true);
    }
}
