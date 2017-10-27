import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class TriangleFinder {
    private EdgeList edgeList;
    private PriorityQueue<Point> queue;
    private TreeSet<LineSegment> tree;
    protected ArrayList<LineSegment> list;
    protected ArrayList<Point> pList;
    private double xMax;
    private double yMax;

    public TriangleFinder(String filename)throws IOException
    {
        queue=new PriorityQueue<>();
        list =new ArrayList<>();
        pList=new ArrayList<>();
        String delimiter=",";
        String line="";
        BufferedReader F=new BufferedReader(new FileReader(filename));
        F.readLine();
        xMax=0;
        yMax=0;

        while((line=F.readLine())!=null)
        {
            String [] row=line.split(delimiter);
            double x=Double.parseDouble(row[0]);
            double y=Double.parseDouble(row[1]);

            if(x>xMax)
                xMax=x;
            if(y>yMax)
                yMax=y;

            Point p=new Point(x,y);
            //queue.add(p);
            pList.add(p);
        }

        Point p1=pList.get(0);
        for(int i=1;i<pList.size();i++)
        {
            Point p2=pList.get(i);
            list.add(new LineSegment(p1,p2));
            p1=p2;
        }
        list.add(new LineSegment(pList.get(pList.size()-1),pList.get(0)));

        Edge e1,e2;

        Edge head=new Edge(list.get(0));
        e1=head;
        for(int i=1;i<list.size()-1;i++)
        {
            e2=new Edge(list.get(i));
            e1.next=e2;
            e2.prev=e1;
            e1=e2;
        }
        head.prev=e1;
        e1.next=head;
    }

    double getXMax(){return xMax;}
    double getYMax(){return yMax;}
}
