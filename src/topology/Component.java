package topology;

import java.util.ArrayList;

public class Component {

    private ArrayList<Point> points;

    public Component(Tag t, Point p) throws Exception {

        points=new ArrayList<Point>();
        Point currentPoint=p;
        int k=t.indexActiveOuterEdge(currentPoint);
        while(k!=-1) {
            points.add(t.getBoundary().edges.get(k).border()[0]);
            t.getActive()[k]=false;
            t.setNbActive(t.getNbActive()-1);
            currentPoint=t.getBoundary().edges.get(k).border()[1];
            k=t.indexActiveOuterEdge(currentPoint);}
        if(!(p.isEqualTo(currentPoint))) points.add(currentPoint);


        /*points = new ArrayList<Point>();
        Point point = p;
        while(t.indexActiveOuterEdge(p) != -1){
            Edge e = t.getBoundary().getEdges().get(t.indexActiveOuterEdge(p));
            points.add(p);
            point = e.border()[1];
            t.getActive()[t.getIndex()[e.label]] = false;
        }*/
    }

    public ArrayList<Point> getPoints() {
        return points;
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
