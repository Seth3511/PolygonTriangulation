import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.ArrayList;

public class LineSegmentCanvas extends JApplet
{
    private ArrayList points;
    private ArrayList lines;
    private double scale;
    private double yMax;

    public LineSegmentCanvas(ArrayList points, ArrayList lines, double scale, double yMax)
    {
        super();
        this.points=points;
        this.lines=lines;
        this.scale=scale;
        this.yMax=yMax*scale+20;
    }

    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        super.paint(g);

        if(points!=null)
            for(int i=0;i<points.size();i++)
            {
                Point point=(Point)points.get(i);
                Point2D.Double p = new Point2D.Double((point.x*scale+10), (yMax-(point.y*scale))-10);
                g2.fill(new Ellipse2D.Double(p.x-3,p.y-3,7,7));
            }

        for(int i=0;i<lines.size();i++)
        {
            LineSegment s=(LineSegment)lines.get(i);
            g2.draw(new Line2D.Double((s.p1.x*scale+10), (yMax-(s.p1.y*scale))-10,(s.p2.x*scale+10), (yMax-(s.p2.y*scale))-10));
        }
    }
}