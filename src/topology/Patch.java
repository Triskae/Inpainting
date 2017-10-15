/**
 * <> with heart by Doutel Silva Filipe, Nadaud Sörel and Barbero Lucas
 */


package topology;

public class Patch {
    public Point point;
    public BoundingBox boundingBox;

    public Patch(Point p ,int halfwidth, BoundingBox window) throws Exception {
        point = p;
        int imin,imax,jmin,jmax;
        Point left,right,top,bottom;
        left = new Point(window,p.getI()-halfwidth,p.getJ());
        if(left.onBb()){
            imin = -halfwidth;
        }
        else{
            imin = -p.getI();
        }

    }

}
