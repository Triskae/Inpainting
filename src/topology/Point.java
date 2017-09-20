/**
 * <> with heart by Doutel Silva Filipe, Nadaud Sörel and Barbero Lucas
 */


package topology;

public class Point {

	public int i,j;
	BoundingBox bb;
	
	public Point(BoundingBox bb,int a,int b) {
		this.bb = bb;
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
		return null;

	}



}
