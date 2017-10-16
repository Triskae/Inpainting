/**
 * <> with heart by Doutel Silva Filipe, Nadaud Sörel and Barbero Lucas
 */


package topology;

import java.util.ArrayList;

public class Boundary {
    public ArrayList<Edge> edges;
    BoundingBox bb;


    public Boundary(Mask m) throws Exception {
        bb = new BoundingBox(m);
        Point p ;
        edges = new ArrayList<Edge> ();
        for(int i = 0 ; i < bb.width ; i++){
            {
                for (int j = 0 ; j < bb.height ; j++){
                    p = new Point(bb,i,j);
                    if(m.touchedBy(p) && m.touchedBy(new Point(bb,i+1,j)) && !m.touchedBy(new Point(bb,i,j-1)) ){
                        edges.add(new Edge(0,i,j,1,0,bb));

                    }

                }
            }
        }

    }

    @Override
    public String toString() {
        return "Boundary{" +
                "edges=" + edges +
                ", bb=" + bb +
                '}';
    }
}