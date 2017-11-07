import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class TriangleFinder {
    private EdgeList edgeList;
    private PriorityQueue<Event> queue;
    private TreeSet<SpacialObject> tree;
    protected ArrayList<Point> pList;
    private double xMax;
    private double yMax;

    public TriangleFinder(String filename) throws IOException {
        queue = new PriorityQueue<>();
        ArrayList<LineSegment> list = new ArrayList<>();
        pList = new ArrayList<>();
        String delimiter = ",";
        String line = "";
        BufferedReader F = new BufferedReader(new FileReader(filename));
        F.readLine();
        xMax = 0;
        yMax = 0;

        while ((line = F.readLine()) != null) {
            String[] row = line.split(delimiter);
            double x = Double.parseDouble(row[0]);
            double y = Double.parseDouble(row[1]);

            if (x > xMax)
                xMax = x;
            if (y > yMax)
                yMax = y;

            Point p = new Point(x, y);
            queue.add(new Event(p));
            pList.add(p);
        }

        Point p1 = pList.get(0);
        for (int i = 1; i < pList.size(); i++) {
            Point p2 = pList.get(i);
            list.add(new LineSegment(p1, p2));
            p1 = p2;
        }
        list.add(new LineSegment(pList.get(pList.size() - 1), pList.get(0)));

        Edge e1, e2;

        Edge head = new Edge(list.get(0));
        e1 = head;
        for (int i = 1; i < list.size(); i++) {
            e2 = new Edge(list.get(i));
            e1.next = e2;
            e2.prev = e1;
            e1 = e2;
        }
        head.prev = e1;
        e1.next = head;
        edgeList = new EdgeList(head);

        Edge p = head;
        p.line.p1.start = p;
        Edge q = p.next;
        while (q != head) {
            q.line.p1.end = p;
            q.line.p1.start = q;
            p = q;
            q = q.next;
        }
        head.line.p1.end = head.prev;
        head.prev.line.p1.start = head.prev;
    }

    public void makeMonotone() {
        tree = new TreeSet<>();

        while (!queue.isEmpty()) {
            Point p = queue.poll().p;
            LineSegment.prevY = p.y;
            LineSegment.prevX = p.x;
            restructure();
            handleVertex(p);
        }
    }

    public void restructure() {
        ArrayList<Edge> set = new ArrayList<>();
        if (tree.size() > 0)
            set.addAll((Collection<? extends Edge>) (Collection<? extends SpacialObject>) tree.descendingSet());
        tree = new TreeSet<>();
        tree.addAll(set.subList(0, set.size()));
    }

    public void handleVertex(Point p) {
        int index = pList.indexOf(p);
        Point left = null;
        Point right = null;

        if (index == 0) {
            left = pList.get(pList.size() - 1);
            right = pList.get(index + 1);
        } else if (index == pList.size() - 1) {
            left = pList.get(index - 1);
            right = pList.get(0);
        } else {
            left = pList.get(index - 1);
            right = pList.get(index + 1);
        }

        boolean isLeftBelow = left.isBelow(p);
        boolean isRightBelow = right.isBelow(p);
        Double angle = findAngle(left, right, p);
        if (isRightTurn(left, p, right))
            angle = (2 * Math.PI) - angle;

        if (isLeftBelow && isRightBelow && angle < Math.PI)
            handleStartVertex(p);
        else if (isLeftBelow && isRightBelow && angle > Math.PI)
            handleSplitVertex(p);
        else if ((!isLeftBelow) && (!isRightBelow) && angle < Math.PI)
            handleEndVertex(p);
        else if ((!isLeftBelow) && (!isRightBelow) && angle > Math.PI)
            handleMergeVertex(p);
        else
            handleRegVertex(p);
    }

    //https://stackoverflow.com/questions/1211212/how-to-calculate-an-angle-from-three-points
    public double findAngle(Point p0, Point p1, Point c) {
        double p0c = Math.sqrt(Math.pow(c.x - p0.x, 2) +
                Math.pow(c.y - p0.y, 2)); // p0->c (b)
        double p1c = Math.sqrt(Math.pow(c.x - p1.x, 2) +
                Math.pow(c.y - p1.y, 2)); // p1->c (a)
        double p0p1 = Math.sqrt(Math.pow(p1.x - p0.x, 2) +
                Math.pow(p1.y - p0.y, 2)); // p0->p1 (c)
        return Math.acos((p1c * p1c + p0c * p0c - p0p1 * p0p1) / (2 * p1c * p0c));
    }

    public boolean isRightTurn(Point a, Point b, Point c) {
        double det = ((b.getX() - a.getX()) * (c.y - a.y)) - ((b.y - a.y) * (c.getX() - a.getX()));
        return (det < 0);
    }

    public void handleStartVertex(Point p) {
        tree.add(p.start);
        p.start.helper = p;
    }

    public void handleEndVertex(Point p) {
        Point helper = p.end.helper;
        if (helper != null && isMergeVertex(helper))
            edgeList.add(new LineSegment(p, helper));
        tree.remove(p.end);
    }

    public void handleSplitVertex(Point p) {
        Edge j = (Edge) tree.lower(p);
        if (j != null && j.line.contains(p))
            j = (Edge) tree.lower(j);
        if (j != null) {
            edgeList.add(new LineSegment(p, j.helper));
            j.helper = p;
        }
        tree.add(p.start);
        p.start.helper = p;
    }

    public void handleMergeVertex(Point p) {
        Point helper = p.end.helper;
        if (helper != null) {
            if (isMergeVertex(helper))
                edgeList.add(new LineSegment(p, helper));
        }
        tree.remove(p.end);

        Edge j = (Edge) tree.lower(p);
        if (j != null && j.line.contains(p))
            j = (Edge) tree.lower(j);
        if (j != null) {
            helper = j.helper;
            if (isMergeVertex(helper))
                edgeList.add(new LineSegment(p, helper));
            j.helper = p;
        }
    }

    public void handleRegVertex(Point p) {
        Point helper;
        Edge j;

        if (pList.get(pList.indexOf(p) - 1).isBelow(p)) {
            helper = p.end.helper;
            if (helper != null && isMergeVertex(helper))
                edgeList.add(new LineSegment(p, helper));
            tree.remove(p.end);
            tree.add(p.start);
            p.start.helper = p;
        } else {
            j = (Edge) tree.lower(p);
            if (j != null && j.line.contains(p))
                j = (Edge) tree.lower(j);
            if (j != null) {
                helper = j.helper;
                if (isMergeVertex(helper))
                    edgeList.add(new LineSegment(p, helper));
                j.helper = p;
            }
        }
    }

    public boolean isMergeVertex(Point p) {
        int index = pList.indexOf(p);
        Point left = null;
        Point right = null;

        if (index == 0) {
            left = pList.get(pList.size() - 1);
            right = pList.get(index + 1);
        } else if (index == pList.size() - 1) {
            left = pList.get(index - 1);
            right = pList.get(0);
        } else {
            left = pList.get(index - 1);
            right = pList.get(index + 1);
        }

        boolean isLeftBelow = left.isBelow(p);
        boolean isRightBelow = right.isBelow(p);
        Double angle = findAngle(left, right, p);
        if (isRightTurn(left, p, right))
            angle = (2 * Math.PI) - angle;

        return (!isLeftBelow) && (!isRightBelow) && angle > Math.PI;
    }

    public void triangulate() {

    }

    public ArrayList<LineSegment> getLines() {
        ArrayList<Edge> eList = edgeList.toArray();
        ArrayList<LineSegment> list = new ArrayList<>();
        for (int i = 0; i < eList.size(); i++)
            list.add(eList.get(i).line);
        return list;
    }

    double getXMax() {
        return xMax;
    }

    double getYMax() {
        return yMax;
    }
}
