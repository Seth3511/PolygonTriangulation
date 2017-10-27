import java.util.LinkedList;

public class Edge
{
    protected LineSegment line;
    protected Point origin;
    protected String name;
    protected String nextS;
    protected String twinS;
    protected Edge twin;
    protected Edge next;
    protected Edge prev;
    protected Cycle cycle;

    public Edge(String name,Point origin, String next,String twinS)
    {
        this.name=name;
        this.origin=origin;
        this.nextS=next;
        this.twinS=twinS;
    }

    public Edge(LineSegment s)
    {
        line=s;
        origin=s.p1;
    }

}
