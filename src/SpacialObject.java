abstract class SpacialObject implements Comparable<SpacialObject> {
    protected boolean isLine;
    protected boolean isEdge;
    public abstract int compareTo(SpacialObject other);
    public abstract double getX();
}
