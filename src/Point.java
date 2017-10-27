public class Point extends SpacialObject implements Comparable<SpacialObject> {
    protected double x;
    protected double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        isLine=false;
    }

    public boolean equals(Point other) {
        return (Math.abs(x-other.x)<.0000001 && Math.abs(y-other.y)<.0000001);
    }

    @Override
    public int compareTo(SpacialObject other) {
        if(!(Math.abs(getX()-other.getX())<.0000001)){
            return Double.compare(getX(), other.getX());
        }

        else{
            LineSegment o=(LineSegment) other;

            if(Math.abs(o.antislope-0)<.0000001)
                return 1;
            else if(Math.abs(o.slope-0)<.0000001)
                return -1;
            else if(o.slope>0)
                return 1;
            else
                return -1;
        }
    }

    public double getX() {
        return x;
    }
}