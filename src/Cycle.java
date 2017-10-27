public class Cycle {
    protected Edge head;
    protected Edge left;
    protected Edge right;

    public Cycle(Edge head){
        this.head=head;
    }

    public void setLeft(){
        double min=Double.MAX_VALUE;

        Edge p=head;
        Edge q=p;
        do{
            if(p.line.p1.x<min){
                min=p.line.p1.x;
                left=p;
            }
            else if(p.line.p2.x<min){
                min=p.line.p2.x;
                left=p;
            }

        }while(p!=q);
    }

    public void setRight(){
        double max=Double.MIN_VALUE;

        Edge p=head;
        Edge q=p;
        do{
            if(p.line.p1.x>max){
                max=p.line.p1.x;
                right=p;
            }
            else if(p.line.p2.x>max){
                max=p.line.p2.x;
                right=p;
            }

        }while(p!=q);
    }
}
