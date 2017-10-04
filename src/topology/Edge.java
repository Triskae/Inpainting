/**
 * <> with heart by Doutel Silva Filipe, Nadaud Sörel and Barbero Lucas
 */

package topology;

public class Edge {

    private static int[][] v={{1,0},{0,1}};
    private int direction,i,j;
    public int orientation;
    public int label;
    private BoundingBox bb;


    public Edge(int direction, int i, int j, int orientation, int label, BoundingBox bb) {
        this.direction = direction;
        this.i = i;
        this.j = j;
        this.orientation = orientation;
        this.label = label;
        this.bb = bb;
    }

    public Point[] border() throws Exception {
        Point[] pp = {new Point(bb,i,j), new Point(bb,i+orientation*v[direction][0],j+orientation*v[direction][1])} ;

        return pp;

    }

    @Override
    public String toString() {

        return null;
    }


}
