/**
 * <> with heart by Doutel Silva Filipe, Nadaud Sörel and Barbero Lucas
 */


package inpainting;

import topology.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;

public class Inpainting {
    private static  int tgv=3*255*255+1;
    private Matrix image;
    private Mask m;
    private int[][] penMask;
    private BoundingBox window;
    public Inpainting(Matrix matrix,Mask mask) throws Exception {
        if(!Arrays.equals(matrix.getBb(),mask.getBb())) throw new Exception("Les bounding box ne correspondent pas");
        image = matrix;
        m=mask;
        window = m;
        penMask = new int[window.width][window.height];
        for(int i =0 ; i<window.width;i++){
            for (int j=0;j<window.height;j++){
                if(m.val[i][j])  penMask[i][j]=tgv;
                else penMask[i][j]=0;
            }
        }

    }
    private BoundingBox searchingBox(Component component, int i) {
        return null;
    }
    private void copyPatch(Point point, Patch patch) throws Exception {
        int halfwidth = (patch.getBoundingBox().getBb()[3]-patch.getBoundingBox().getBb()[0])/2;
        Patch copypatch = new Patch(point,halfwidth,window);
        for(int i = patch.getBoundingBox().getBb()[0]; i<patch.getBoundingBox().getBb()[2];i++){
            for(int j =patch.getBoundingBox().getBb()[1]; i<patch.getBoundingBox().getBb()[3];i++){
                if(m.val[i][j]){

                }
            }
        }

    }
    private int[] argmin(double[][] tab) {
        return null;
    }
    private Point best_match(Patch patch, BoundingBox bb) {
        return null;
    }
    public void restore(int i, int j)  throws IOException {

    }
    public static void main(String[] tab) throws IOException, ParseException {


    }

}
