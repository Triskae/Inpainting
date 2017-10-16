/**
 * <> with heart by Doutel Silva Filipe, Nadaud Sörel and Barbero Lucas
 */


package topology;

public class Patch {
    public Point point;
    public BoundingBox boundingBox;

    @Override
    public String toString() {
        return "Patch{" +
                "point=" + point +
                ", boundingBox=" + boundingBox +
                '}';
    }

    public Patch(Point p , int halfwidth, BoundingBox window) throws Exception {
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
        right = new Point(window,p.getI()+halfwidth,p.getJ());
        if(right.onBb()){
            imax = halfwidth;
        }
        else{
            imax = p.bb.width - p.getI();
        }
        top = new Point(window,p.getI(),p.getJ()-halfwidth);
        if(top.onBb()){
            jmin = -halfwidth;
        }
        else{
            jmin = -p.getJ();
        }
        bottom = new Point(window,p.getI(),p.getJ()+halfwidth);
        if(bottom.onBb()){
           jmax = halfwidth;
        }
        else{
            jmax = p.bb.height - p.getJ();
        }
        boundingBox = new BoundingBox(new int [] {imin,jmin,imax,jmax});

    }

}
