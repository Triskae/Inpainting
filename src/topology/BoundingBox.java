package topology;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSize() {
        return size;
    }

    public int getNbEdges() {
        return nbEdges;
    }

    public int getNbEdgesHorizontal() {
        return nbEdgesHorizontal;
    }

    public int getNbEdgesVertical() {
        return nbEdgesVertical;
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
    public BoundingBox(String fileName)  throws IOException {
        BufferedImage image;
        try
        {
            image = ImageIO.read(new File(fileName));
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
    
    

    public int[] getBb() {
		return bb;
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

    @Override
    public String toString() {
        return "BoundingBox{" +
                "bb[] =" + bb[0] + "," + bb[1]+ ","  +bb[2]+ ","+  bb[3] +
                " width=" + width +
                ", height=" + height +
                ", size=" + size +
                ", nbEdges=" + nbEdges +
                ", nbEdgesHorizontal=" + nbEdgesHorizontal +
                ", nbEdgesVertical=" + nbEdgesVertical +
                '}';
    }
    public BoundingBox crop(Patch patch){
        int imin =  bb[0]+Math.abs(patch.getBoundingBox().getBb()[0]);
        int jmin =  bb[1]+Math.abs(patch.getBoundingBox().getBb()[1]);
        int imax =  bb[2]-Math.abs(patch.getBoundingBox().getBb()[2]);
        int jmax =  bb[3]-Math.abs(patch.getBoundingBox().getBb()[3]);
        return new BoundingBox(new int[]{imin,jmin,imax,jmax});

    }

}