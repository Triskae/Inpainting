package topology;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Matrix extends BoundingBox {

    public Color[][]val;

    /**
     * Cette fonction permet de créer une matrice qui va contenir les pixels de l'image.
     * Elle va récuperer le pixel sous forme rgb, puis elle converti la couleur RGB en couleur (de la classe Color)
     *
     * @param fileName Le nom de l'image
     * @throws IOException
     */
    public Matrix(String fileName) throws IOException {
        super(fileName);
        BufferedImage image;
        image = ImageIO.read(new File(fileName));
        val = new Color[this.width][this.height]; // Matrice de la taille de l'image
        for ( int i = 0 ; i< width ; i++){
            for ( int j=0 ; j< height; j++){
                val[i][j] = Color.rgbToColor(image.getRGB(i,j));
            }
        }
    }

    /**
     *Cette fonction fait l'inverse de celle juste au dessus. Elle utilise une matrice pour reconstruire une image.
     * Elle utilise aussi une autre fonction inverse, on passe d'une couleur de la classe Color en RGB. L'image sera au format .bmp
     *
     * @param fileName Nom que l'utilisateur souhaite donner à l'image rendu
     * @throws IOException
     */
    /*public void save(String fileName) throws IOException{
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
    }*/

    public void save(String fileName) throws IOException{
        BufferedImage image = getImage();
        File output = new File(fileName+".bmp");
        ImageIO.write(image,"bmp",output);
    }

    public BufferedImage getImage()
    {
        BufferedImage image = new BufferedImage(super.width,super.height, BufferedImage.TYPE_INT_RGB);
        int rgb;
        for (int i = 0; i < width ; i ++){
            for (int j =0;j< height;j++){
                rgb = val[i][j].colorToRgb();
                image.setRGB(i,j,rgb);
            }
        }

        return image;
    }


    public void applyMask(Mask mask){
        for(int i = 0 ; i<width ; i++){
            for (int j =0 ; j<height;j++){
                if(mask.val[i][j]) val[i][j].set(new Color(0,0,0));
            }
        }
    }
}
