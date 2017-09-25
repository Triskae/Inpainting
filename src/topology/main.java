package topology;

import com.sun.org.apache.xpath.internal.SourceTree;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import javax.imageio.ImageIO;
import javax.xml.soap.SOAPPart;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

/**
 * <> with heart by Doutel Silva Filipe, Nadaud Sörel and Barbero Lucas
 */


public class main {
    public static void main(String[] args) throws IOException {
    	Color c = new Color(255,255,255);
    	Color c1 = new Color(255,255,255);
    	Color c2 = new Color(0,0,0);


    	System.out.println(Color.dist(c,c1));
    	System.out.println(Color.dist(c,c2));


    	byte b = (byte) 255;
    	System.out.println(b);
    	System.out.println(Byte.toUnsignedInt(b));
    	System.out.println(c.toString());
    //	BoundingBox bb = new BoundingBox("C:\\Users\\lucas\\Downloads\\bungee-free.bmp");
		BufferedImage bbb;
		bbb = ImageIO.read(new File("C:\\Users\\lucas\\Downloads\\bungee-free.bmp"));
		int a =bbb.getRGB(0,0);
		System.out.println(a);

		Color ccc= Color.rgbToColor(a);



		System.out.println(ccc);
		int g = ccc.colorToRgb();
		System.out.println(g);
		System.out.println("lol");
		System.out.println(Color.rgbToColor(g));
		Matrix m = new Matrix("C:\\Users\\lucas\\Desktop\\DH4CvoQUMAAecz5.jpg");
		System.out.println(m.val[5][25]);
		m.save("C:\\Users\\lucas\\Desktop\\marchestp");


    	
    	
    }
}

