package topology;

import java.io.IOException;

public class Mask extends BoundingBox {

    public boolean[][] val;

    public Mask(String fileName,Color color) throws IOException {
        super(fileName);
        Matrix m = new Matrix(fileName); // creation d'une matrice avec le repertoire du fichier
        val = new boolean[width][height]; // width et height de la super classe
        for(int i =0; i<width;i++){
            for (int j =0;j<height;j++){
               val[i][j] = color.isequalto(m.val[i][j]); // m.val[i][j] couleur réelle du pixel (i,j)

            }
        }
    }

    public Mask(Matrix m,Color color) { // on connait déjà une matrice
        super( new BoundingBox( new int[]{0, 0, m.width, m.height}));
        val = new boolean[width][height];

        for(int i =0; i<width;i++) {
            for (int j = 0; j < height; j++) {
                val[i][j] = color.isequalto(m.val[i][j]);
            }
        }
    }

    public boolean touchedBy(Point p) { // on vérifie que les pixels dont le point(i,j) est le coin soient dans la BoundingBox si c'est le cas, on regarde la valeur de val[i][j]

        if(p.getI() < width && p.getJ() < height){
            if(val[p.getI()][p.getJ()]) return true;
        }
        if( p.getI() < width && (p.getJ() -1 )>= 0){
            if(val[p.getI()][p.getJ()-1]) return true;
        }
        if( ((p.getI() -1)>= 0) && p.getJ() < height ){
            if(val[p.getI()-1][p.getJ()]) return true;
        }
        if( ((p.getI() -1)>= 0) && (p.getJ() -1 >= 0)){
            if(val[p.getI()-1][p.getJ()-1]) return true;
        }
        return false;

    }
}
