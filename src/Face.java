import java.util.ArrayList;

public class Face
{
    protected String name;
    protected Cycle outerBound;
    protected ArrayList<Cycle> innerBounds;

    public Face(String name,Cycle outerBound, ArrayList<Cycle> innerBounds)
    {
        this.name=name;
        this.innerBounds=innerBounds;
        this.outerBound=outerBound;
    }
}
