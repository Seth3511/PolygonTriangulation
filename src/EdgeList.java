import java.util.ArrayList;
import java.util.HashMap;

public class EdgeList {
    protected ArrayList<Edge> list;
    protected HashMap<String, Edge> hMap;

    public EdgeList(ArrayList<Edge> list, HashMap<String, Edge> hMap) {
        this.hMap = hMap;
        this.list = new ArrayList<>();
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).cycle == null) {
                Edge p = list.get(i);
                Edge q = p;
                Cycle cycle = new Cycle(p);

                do {
                    p.twin = hMap.get(p.twinS);
                    p.line = new LineSegment(p.origin, p.twin.origin);
                    p.next = hMap.get(p.nextS);
                    p.next.prev = p;
                    p.cycle = cycle;
                    p = p.next;
                } while (q != p);

                this.list.add(list.get(i));
            }
    }

    public EdgeList(Edge head) {
        list = new ArrayList<>();
        list.add(head);
    }

    public void add(LineSegment s) {
        list.add(new Edge(s));
    }

    public ArrayList<Edge> toArray() {
        ArrayList<Edge> ret = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Edge p = list.get(i);
            Edge q = p;

            if (p.next != null)
                do {
                    ret.add(p);
                    p = p.next;
                } while (q != p);
            else
                ret.add(p);
        }

        return ret;
    }
}
