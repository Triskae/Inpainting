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
    private boolean availableSeedPoint = true;
    private boolean isAvailablePoint = true;
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
    public Point SeedPoint() throws Exception {
        if(availableSeedPoint){
            for (Edge e : boundary.getEdges()) {
                if (active[index[e.label]]) {
                    if (e.border()[0].onBorder() || e.border()[0].onCorner()) { // est ce que le corner est on border ?
                        active[index[e.label]] = false;
                        nbActive--;
                        return e.border()[0];
                    }
                }
            }
            availableSeedPoint = false;
        }
        if(isAvailablePoint){
            for (Edge e : boundary.getEdges()) {
                if (active[index[e.label]]) {
                    active[index[e.label]] = false;
                    nbActive--;
                    return e.border()[0];
                }
                isAvailablePoint = false;
            }

        }

        return null;
    }
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
