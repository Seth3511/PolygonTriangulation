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

    public void normalize(){
        Edge diag,topPrev,topNext,bottomPrev,bottomNext,twin;
        topPrev=null;
        topNext=null;
        bottomNext=null;
        bottomPrev=null;
        ArrayList<Edge> newEdges=new ArrayList<>();
        ArrayList<Edge> newList=new ArrayList<>();
        ArrayList<Edge> temp=new ArrayList<>();

        for(int i=list.size()-1;i>0;i--){
            newEdges.add(list.get(i));
            list.remove(list.get(i));
        }
        ArrayList<Edge> oldEdges=toArray();
        temp.addAll(newEdges);
        temp.addAll(oldEdges);

        for(int i=0;i<newEdges.size();i++){
            diag=newEdges.get(i);
            twin=new Edge(new LineSegment(diag.line.p2,diag.line.p1));
            diag.twin=twin;
            twin.twin=diag;

            for(int j=0;j<oldEdges.size();j++){
                if(oldEdges.get(j).line.p2.equals(diag.line.p2)){
                    topPrev=oldEdges.get(j);
                    topNext=topPrev.next;
                }
                if(oldEdges.get(j).line.p2.equals(diag.origin)){
                    bottomPrev=oldEdges.get(j);
                    bottomNext=bottomPrev.next;
                }

            }

            topPrev.next=twin;
            topNext.prev=diag;
            diag.next=topNext;
            twin.prev=topPrev;
            bottomPrev.next=diag;
            diag.prev=bottomPrev;
            bottomNext.prev=twin;
            twin.next=bottomNext;

            temp.add(twin);
        }
        for(int i=0;i<temp.size();i++){
            Edge e=temp.get(i);
            if(e.cycle==null){
                Cycle c=new Cycle(e);
                e.cycle=c;
                Edge p=e;
                do{
                    p=p.next;
                    p.cycle=c;
                }while(p!=e);

                newList.add(e);
            }
        }

        list=newList;
        System.out.println(newList.size());
    }
}
