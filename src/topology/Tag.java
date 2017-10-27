/**
 * <> with heart by Doutel Silva Filipe, Nadaud Sörel and Barbero Lucas
 */


package topology;

import java.util.Arrays;

public class Tag {
    private int[] index;
    private boolean[] active;
    private int nbActive;
    private Boundary boundary;
    public Tag(Boundary b){
        boundary = b;
        index = new int[b.bb.nbEdges];
        active = new boolean[b.getEdges().size()];
        nbActive = b.bb.nbEdges;
        Arrays.fill(active,true);
        Arrays.fill(index,-1);
        for(int i =0 ; i< boundary.getEdges().size();i++){
            index[boundary.getEdges().get(i).label] = i;
        }

    }
    /*public Point SeedPoint(){

    }*/
    public int indexActiveOuterEdge(Point p) throws Exception {
        Edge[] outerEdges = p.outerEdges();
        for(int i =0 ; i < outerEdges.length ; i++){
            if(boundary.getEdges().contains(outerEdges[i]));{
                if(active[index[outerEdges[i].label]]){
                    return index[outerEdges[i].label];
                }

            }

        }
        return -1;

    }

}
