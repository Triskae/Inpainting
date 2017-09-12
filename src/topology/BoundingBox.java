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

    }


    public BoundingBox(BufferedImage image) {

    }
    public BoundingBox(String chaine)  throws IOException {

    }

    public BoundingBox(BoundingBox boundingBox) {

    }


}