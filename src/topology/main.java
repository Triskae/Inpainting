package topology;

import java.io.IOException;

/**
 * <> with heart by Doutel Silva Filipe, Nadaud Sörel and Barbero Lucas
 */


public class main {
    public static void main(String[] args) throws IOException {
    	Color c = new Color(255,255,255);
    	Color c1 = new Color(0,0,0);
    	System.out.println(Color.dist(c,c1));
    	

    	byte b = (byte) 255;
    	System.out.println(b);
    	System.out.println(Byte.toUnsignedInt(b));
    	System.out.println(c.toString());
    	BoundingBox bb = new BoundingBox( new int []{0,0,2,5});
    	Point p = new Point(bb,0,3);
    	System.out.println(p.onCorner());
    	
    	
    }
}

