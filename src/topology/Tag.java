/**
 * <> with heart by Doutel Silva Filipe, Nadaud Sörel and Barbero Lucas
 */


package topology;

import org.omg.PortableServer.POA;

public class Tag {
   private int[] index;
    private boolean[] active;
    private int nbActive;
    private Boundary boundary;
    public Tag(Boundary b){
        boundary = b;
        index = new int[b.bb.nbEdges];

    }
    /*public Point SeedPoint(){

    }*/
    public int indexActiveOuterEdge(Point p){
        return 0;
    }


}
