public class Event implements Comparable<Event> {
    protected Point p;

    public Event(Point p) {
        this.p = p;
    }

    public int compareTo(Event other) {
        if (!(Math.abs(p.y-other.p.y)<.0000001))
            return -1*Double.compare(p.y,other.p.y);
        else if(!(Math.abs(p.x-other.p.x)<.0000001))
            return Double.compare(p.x,other.p.x);
        else return 0;
    }

    public boolean equals(Event other){
        return p.equals(other.p);
    }
}