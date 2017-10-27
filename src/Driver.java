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
        System.out.println("Enter Scale:");
        double scale=kb.nextDouble();

        TriangleFinder tFinder=new TriangleFinder(filename);
        ArrayList<LineSegment> lineList=tFinder.list;
        ArrayList<Point> pList=tFinder.pList;

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