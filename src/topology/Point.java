/**
 * <> with heart by Doutel Silva Filipe, Nadaud Sörel and Barbero Lucas
 */


package topology;

public class Point {
	
	public int i,j;
	BoundingBox bb;
	public boolean isEqualTo(Point p) {
		return true;
		
	}
	public boolean onCorner() {
		return true;
	}
	public boolean onBorder() {
		return false;
	}
	
	@Override public String toString() {
		return null;
		
	}
	public Point(BoundingBox bb,int a,int b) {
		
	}
	
	
}
