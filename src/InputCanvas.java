import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.ArrayList;

public class InputCanvas extends JApplet
{
    private ArrayList<Face> fList;
    private double scale;
    private double yMax;
    private Color color;
    private Color antiColor;

    public InputCanvas(ArrayList<Face> fList, double scale, double yMax, String colorName)
    {
        super();
        this.fList=fList;
        this.scale=scale;
        this.yMax=yMax*scale+20;
        if(colorName.equals("blue")){
            color=Color.blue;
            antiColor=Color.red;
        }
        else{
            color=Color.red;
            antiColor=Color.blue;
        }
    }

    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        super.paint(g);

        for(int i=0;i<fList.size();i++)
        {
            Face f=fList.get(i);
            
            Edge p=f.outerBound.head;
            Edge q=p.next;
            GeneralPath outer = new GeneralPath();
            LineSegment s=p.line;
            outer.moveTo(s.p1.x*scale+10,(yMax-(s.p1.y*scale))-10);
            p=p.next;
            do{
                s=p.line;
                outer.lineTo(s.p1.x*scale+10,(yMax-(s.p1.y*scale))-10);
                p=p.next;
            }while(q!=p);
            outer.closePath();
            g2.setColor(color);
            g2.fill(outer);

            for(int j=0;j<f.innerBounds.size();j++){
                p=f.innerBounds.get(j).head;
                q=p.next;
                GeneralPath inner = new GeneralPath();
                s=p.line;
                inner.moveTo(s.p1.x*scale+10,(yMax-(s.p1.y*scale))-10);
                do{
                    s=p.line;
                    inner.lineTo(s.p1.x*scale+10,(yMax-(s.p1.y*scale))-10);
                }while(q!=p);
                inner.closePath();
                g2.setColor(antiColor);
                g2.fill(inner);
            }
        }
    }
}