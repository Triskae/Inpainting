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
        int status = 0; // si status est pair m.val[i][j]=false si status est impaire m.val[i][j]=true
        edges = new ArrayList<Edge>();
        for (int i = 1; i < bb.width; i++) { //left to right
            {
                for (int j = 0; j < bb.height; j++) {
                    if (m.val[i][j] != m.val[i - 1][j] && status % 2 == 0) {
                        edges.add(new Edge(1, i-1, j, -1, bb)); // edge vers le haut

                    }
                    if (m.val[i][j] != m.val[i - 1][j] && status % 2 == 1) {
                        edges.add(new Edge(1, i-1, j, 1, bb)); // edge vers le bas
                    }
                    status ++;

                }
            }

        }
        status = 0; // reinitialisation
        for (int j = 1; j < bb.height; j++) { // top to bottom

            for (int i = 0; i < bb.width; i++) {
                if (m.val[i][j] != m.val[i][j - 1] && status % 2 == 0) {
                    edges.add(new Edge(0, i, j-1, 1, bb)); // edge vers la droite
                }
                if (m.val[i][j] != m.val[i][j - 1] && status % 2 == 1) {
                    edges.add(new Edge(0, i, j-1, -1, bb)); // edge vers la gauche
                }
                status ++;

            }
        }
    }

    @Override
    public String toString() {
        String allEdge ="";
        for (Edge e : edges){
            allEdge += e.toString() + "\n";
        }

        return "Boundary: \n{" +
                "bb=" + bb +
                "\n" + "Edges=" + "\n"
                + allEdge +
+                '}';
    }
    public ArrayList<Edge> getEdges() {
        return edges;
    }
}