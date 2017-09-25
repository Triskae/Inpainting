/**
 * <> with heart by Doutel Silva Filipe, Nadaud Sörel and Barbero Lucas
 */


package topology;

import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class Matrix extends BoundingBox {

    public Color[][]val;
    public Matrix(String fileName) throws IOException {
        super(fileName);
        BufferedImage image;
        image = ImageIO.read(new File(fileName));
        val = new Color[this.width][this.height];
        for ( int i = 0 ; i< width ; i++){
            for ( int j=0 ; j< height; j++){
                val[i][j] = Color.rgbToColor(image.getRGB(i,j));
            }
        }
    }
    public void save(String fileName) throws IOException{
        BufferedImage image = new BufferedImage(super.width,super.height, BufferedImage.TYPE_INT_RGB);
        int rgb;
        for (int i = 0; i < width ; i ++){
            for (int j =0;j< height;j++){
                rgb = val[i][j].colorToRgb();
                image.setRGB(i,j,rgb);
            }
        }
        File output = new File(fileName+".bmp");
        ImageIO.write(image,"bmp",output);

    }
}
