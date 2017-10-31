import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class TriangleFinder {
    private EdgeList edgeList;
    private PriorityQueue<Event> queue;
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
            queue.add(new Event(p));
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

    public void makeMonotone()
    {
        tree=new TreeSet<>();

        while(!queue.isEmpty())
        {
            Point p=queue.poll().p;
            handleVertex(p);
        }
    }

    public void handleVertex(Point p)
    {
        int index=pList.indexOf(p);
        Point left=null;
        Point right=null;

        if(index==0) {
            left = pList.get(pList.size() - 1);
            right=pList.get(index+1);
        }
        else if(index==pList.size()-1){
            left=pList.get(index-1);
            right=pList.get(0);
        }
        else{
            left=pList.get(index-1);
            right=pList.get(index+1);
        }

        boolean isLeftBelow=left.isBelow(p);
        boolean isRightBelow=right.isBelow(p);
        Double angle=findAngle(left,right,p);

        if(isLeftBelow&&isRightBelow&&angle<Math.PI)
            handleStartVertex(p);
        else if(isLeftBelow&&isRightBelow&&angle>Math.PI)
            handleSplitVertex(p);
        else if((!isLeftBelow)&&(!isRightBelow)&&angle<Math.PI)
            handleEndVertex(p);
        else if((!isLeftBelow)&&(!isRightBelow)&&angle>Math.PI)
            handleMergeVertex(p);
        else
            handleRegVertex(p);
    }

    //https://stackoverflow.com/questions/1211212/how-to-calculate-an-angle-from-three-points
    public double findAngle(Point p0,Point p1,Point c){
        double p0c = Math.sqrt(Math.pow(c.x-p0.x,2)+
                Math.pow(c.y-p0.y,2)); // p0->c (b)
        double p1c = Math.sqrt(Math.pow(c.x-p1.x,2)+
                Math.pow(c.y-p1.y,2)); // p1->c (a)
        double p0p1 = Math.sqrt(Math.pow(p1.x-p0.x,2)+
                Math.pow(p1.y-p0.y,2)); // p0->p1 (c)
        return Math.acos((p1c*p1c+p0c*p0c-p0p1*p0p1)/(2*p1c*p0c));
    }

    public void handleStartVertex(Point p){

    }

    public void handleEndVertex(Point p){

    }

    public void handleSplitVertex(Point p){

    }

    public void handleMergeVertex(Point p){

    }

    public void handleRegVertex(Point p){

    }

    public void triangulate(){

    }

    double getXMax(){return xMax;}
    double getYMax(){return yMax;}
}
