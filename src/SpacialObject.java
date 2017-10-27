abstract class SpacialObject implements Comparable<SpacialObject> {
    protected boolean isLine;
    public abstract int compareTo(SpacialObject other);
    public abstract double getX();
}
