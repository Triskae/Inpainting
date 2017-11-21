/**
 * <> with heart by Doutel Silva Filipe, Nadaud Sörel and Barbero Lucas
 */


package topology;

import java.util.ArrayList;

public class Component {

    private ArrayList<Point> points;

    public Component(Tag t,Point p) throws Exception {
        points = new ArrayList<Point>();
        Point point = p;
        while(t.indexActiveOuterEdge(p) != -1){
            Edge e = t.getBoundary().getEdges().get(t.indexActiveOuterEdge(p));
            points.add(p);
            point = e.border()[1];
            t.getActive()[t.getIndex()[e.label]] = false;
        }
    }

    @Override
    public String toString() {
        String temp ="Component= ";
        for(Point p : points){
            temp+= p.toString() + " , ";
        }
        return temp;
    }
}
