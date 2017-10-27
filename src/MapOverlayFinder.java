import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MapOverlayFinder
{
    protected EdgeList s1;
    protected EdgeList s2;
    protected double xMax;
    protected double yMax;
    protected ArrayList<Face> f1;
    protected ArrayList<Face> f2;
    
    public MapOverlayFinder(String fileName1, String fileName2)throws IOException
    {
        xMax=0;
        yMax=0;
        f1=new ArrayList<>();
        f2=new ArrayList<>();
        s1=construct(fileName1,f1);
        s2=construct(fileName2,f2);
    }

    public EdgeList construct(String filename, ArrayList<Face> faceList)throws IOException
    {
        HashMap<String,Edge> hMap=new HashMap<>();
        ArrayList<Edge> list =new ArrayList<>();
        String delimiter=",";
        String line="";
        BufferedReader F=new BufferedReader(new FileReader(filename));
        F.readLine();

        while((line = F.readLine()).charAt(0)!=',')
        {
            String [] row=line.split(delimiter);

            Edge edge=new Edge(row[0],
                    new Point(Double.parseDouble(row[1]),Double.parseDouble(row[2])),
                    row[3],row[4]);

            list.add(edge);
            hMap.put(edge.name,edge);

            if(Double.parseDouble(row[1])>xMax)
                xMax=Double.parseDouble(row[1]);
            if(Double.parseDouble(row[2])>yMax)
                yMax=Double.parseDouble(row[2]);
        }
        list.trimToSize();
        EdgeList s=new EdgeList(list,hMap);
        while((line = F.readLine()) != null)
        {
            String [] row=line.split(delimiter);
            String name=row[0];
            Cycle outer=hMap.get(row[1]).cycle;
            ArrayList<Cycle> inner=new ArrayList<>();
            for(int i=2;i<row.length;i++)
                inner.add(hMap.get(row[i]).cycle);

            faceList.add(new Face(name,outer,inner));
        }
        F.close();
        return s;
    }
    
    public ArrayList<Point> find()
    {
        IntersectionFinder finder=new IntersectionFinder(s1,s2);
        return finder.find();
    }

    public ArrayList<LineSegment> print()
    {
        ArrayList<LineSegment> lineList=new ArrayList<>();

        ArrayList<Edge> edgeArrayList=s1.toArray();
        for(int i=0;i<edgeArrayList.size();i++) {
            lineList.add(edgeArrayList.get(i).line);
        }
        edgeArrayList=s2.toArray();
        for(int i=0;i<edgeArrayList.size();i++) {
            lineList.add(edgeArrayList.get(i).line);
        }

        return lineList;
    }

    public double getXMax(){return xMax;}
    public double getYMax(){return yMax;}
}
