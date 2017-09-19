package topology;

import java.io.IOException;

/**
 * Created by bl602538 on 19/09/2017.
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
    	BoundingBox bb = new BoundingBox("P:\\Desktop\\Capture.png");
    	System.out.println(bb.width);
    	
    	
    }
}

