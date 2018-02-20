package topology;

import java.util.ArrayList;
import java.util.List;

public class Point {

	public int i,j;
	public BoundingBox bb;
	
	public Point(BoundingBox bb,int a,int b) throws Exception {
		this.bb = bb;
		//if ( i < bb.bb[0] || i> bb.width || j<bb.bb[1] || j>bb.height) throw new Exception("Le point ne fait pas partie de la bounding box");
		i=a;
		j=b;

	}

	public boolean isEqualTo(Point p) {
		if((this.i == p.i) && (this.j == p.j)) {
			return true;
		} else {
			return false;
		}

	}

	public boolean onCorner() {
		int [] tab = bb.getBb();
		return ((i == tab[0] || i == tab[2]  ) && (j == tab[1] || j == tab[3]));
	}

	public boolean onBorder() {
		return (i == 0 && !this.onCorner()) ||(j==0 && !this.onCorner());
	}


	@Override public String toString() {
		return "("+i+","+j+")";

	}

	public Edge[] outerEdges() throws Exception {
		Edge[] tampon = new Edge[4];
		List<Edge> outerEdge = new ArrayList<Edge>();
		tampon[0] = new Edge(0, i, j, 1, bb);
		tampon[1] = new Edge(0, i, j, -1, bb);
		tampon[2] = new Edge(1, i, j, 1, bb);
		tampon[3] = new Edge(1, i, j, -1, bb);

			for (int i = 0; i < 4; i++) {
				if (tampon[i].border()[1].onBb())
				{
					outerEdge.add(tampon[i]);
				}

			}
		return  outerEdge.toArray(new Edge[outerEdge.size()]);
	}


	public boolean onBb(){

		return  ( i >= bb.bb[0] && i<= bb.width && j>=bb.bb[1] && j<= bb.height ) ;
	}

	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}
}
