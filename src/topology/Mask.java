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
        val = new boolean[width][height];
        for(int i =0; i<width;i++) {
            for (int j = 0; j < height; j++) {
                val[i][j] = color.isequalto(m.val[i][j]);
            }
        }
    }

    public boolean touchedBy(Point p) {

        return false;
    }
}
