/**
 * <> with heart by Doutel Silva Filipe, Nadaud Sörel and Barbero Lucas
 */


package topology;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BoundingBox{	// define the domain size


    public int[] bb;
    public int width,height,size;
    int nbEdges,nbEdgesHorizontal,nbEdgesVertical;


    public BoundingBox(int[] a) {
        bb = a;
        width = bb[2]-bb[0];
        height = bb[3]-bb[1];
        size = width*height;
        nbEdgesHorizontal= width * (height + 1);
        nbEdgesVertical = height * (width +1);
        nbEdges = nbEdgesHorizontal+nbEdgesVertical;
    }


    public BoundingBox(BufferedImage image) {

        width = image.getWidth();
        height= image.getHeight();
        bb = new int[]{0,0,width,height};
        size = width*height;
        nbEdgesHorizontal= width * (height + 1);
        nbEdgesVertical = height * (width +1);
        nbEdges = nbEdgesHorizontal+nbEdgesVertical;

    }
    public BoundingBox(String chaine)  throws IOException {
        BufferedImage image;
        try
        {
            image = ImageIO.read(new File(chaine)); 
            width = image.getWidth();
            height= image.getHeight();
            bb = new int[]{0,0,width,height};
            size = width*height;
            nbEdgesHorizontal= width * (height + 1);
            nbEdgesVertical = height * (width +1);
            nbEdges = nbEdgesHorizontal+nbEdgesVertical;

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }



    }

    public BoundingBox(BoundingBox boundingBox) { // method clone ?
 
       this.bb=boundingBox.bb;
       this.width = boundingBox.width;
       this.height= boundingBox.height;
       this.size=boundingBox.size;
       this.nbEdgesVertical=boundingBox.nbEdgesVertical;
       this.nbEdges=boundingBox.nbEdges;
       this.nbEdgesHorizontal=boundingBox.nbEdgesHorizontal;
    }


}