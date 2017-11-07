public class Edge extends SpacialObject implements Comparable<SpacialObject>
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
    protected Point helper;

    public Edge(String name,Point origin, String next,String twinS)
    {
        this.name=name;
        this.origin=origin;
        this.nextS=next;
        this.twinS=twinS;
        isEdge=true;
        isLine=false;
    }

    public Edge(LineSegment s)
    {
        line=s;
        origin=s.p1;
    }

    public double getX(){
        return line.getX();
    }

    public int compareTo(SpacialObject other){
        return line.compareTo(other);
    }

    public boolean equals(Edge other){
        return line.equals(other.line);
    }
}
