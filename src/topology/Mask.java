/**
 * <> with heart by Doutel Silva Filipe, Nadaud Sörel and Barbero Lucas
 */

package topology;

import java.io.IOException;

public class Mask extends BoundingBox {

    public boolean[][] val;

    public Mask(String fileName,Color color) throws IOException {
        super(fileName);
        Matrix m = new Matrix(fileName);
        val = new boolean[width][height];
        for(int i =0; i<width;i++){
            for (int j =0;j<height;j++){
               val[i][j] = color.isequalto(m.val[i][j]);

            }
        }
    }

    public Mask(Matrix m,Color color) {
        super( new BoundingBox( new int[]{0, 0, m.width, m.height}));
        val = new boolean[width][height];

        for(int i =0; i<width;i++) {
            for (int j = 0; j < height; j++) {
                val[i][j] = color.isequalto(m.val[i][j]);
            }
        }
    }

    public boolean touchedBy(Point p) {

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
