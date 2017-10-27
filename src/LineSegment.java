import java.util.ArrayList;

public class LineSegment extends SpacialObject implements Comparable<SpacialObject> {
    protected Point p1;
    protected Point p2;
    protected ArrayList<Point> intersections;
    protected double slope;
    protected double antislope;
    protected double yInt;
    protected double xInt;
    protected static double currY;
    protected static double prevY;
    protected static double currX;
    protected static double prevX;

    public LineSegment(Point p1, Point p2) {
        intersections = new ArrayList<>();

        this.p1=p1;
        this.p2=p2;

        slope = (p2.y - p1.y) / (p2.x - p1.x);
        antislope = (p2.x - p1.x) / (p2.y - p1.y);
        yInt = p1.y - slope * p1.x;
        xInt = p1.x - antislope * p1.y;
        isLine=true;
    }

    public boolean contains(Point p) {
        return(isUpper(p)||isIntersection(p)||isLower(p));
    }

    public boolean isUpper(Point p) {
        return p1.equals(p);
    }

    public boolean isLower(Point p) {
        return p2.equals(p);
    }

    public boolean isIntersection(Point p) {
        return intersections.contains(p);
    }

    public boolean equals(LineSegment other) {
        return (this.p1.equals(other.p1) && this.p2.equals(other.p2));
    }

    public double getX() {

        if(antislope==0)
            return p1.x;
        else if(slope>0||slope<0)
            return (prevY - yInt)/slope;
        else
            return prevX;
    }

    @Override
    public int compareTo(SpacialObject other) {
        if(!(Math.abs(getX()-other.getX())<.0000001)){
            return Double.compare(getX(), other.getX());
        }

        else if(!other.isLine){
            if(Math.abs(antislope)<.0000001)
                return -1;
            else if(Math.abs(slope)<.0000001)
                return 1;
            else if(slope>0)
                return -1;
            else if(slope<0)
                return 1;
        }

        else if(other.isLine){
            LineSegment o=(LineSegment) other;
            if(!(Math.abs(slope)<.0000001)&&!(Math.abs(o.slope)<.0000001))
                return Double.compare(xInt,o.xInt);
            else if(Math.abs(slope)<.0000001)
                return 1;
            else
                return -1;
            /*if(antislope==0||o.antislope==0){
                if(antislope==0){
                        return -1;
                }
                else if(o.antislope==0)
                        return 1;
            }
            else if(slope<0&&o.slope<0) {
                return Double.compare(slope,o.slope);
            }
            else if(slope>0&&o.slope>0)
                return Double.compare(slope,o.slope);

            else if((slope>0&&o.slope<0)||(slope<0&&o.slope>0))
                return -1*Double.compare(slope,o.slope);

            else if(slope==0&&o.slope!=0)
                return 1;

            else if(slope!=0&&o.slope==0)
                return -1;*/
        }
        return 0;
    }
}