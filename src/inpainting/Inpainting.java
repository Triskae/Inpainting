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

    public Inpainting() {

    }

    private BoundingBox searchingBox(Component component, int i) {

         int imin= component.getPoints().get(0).getI(),imax= component.getPoints().get(0).getI(),jmin= component.getPoints().get(0).getJ(),jmax= component.getPoints().get(0).getJ();
         for(Point p : component.getPoints()){
             if(p.getI()<imin) imin=p.getI();
             if(p.getI()>imax) imax=p.getI();
             if(p.getJ()<jmin) imin=p.getJ();
             if(p.getJ()>jmax) imax=p.getJ();
         }
        if(imin- i < window.getBb()[0]){
             imin=window.getBb()[0];
        }
        else{
            imin-=3;
        }
        if(jmin- i < window.getBb()[1]){
            jmin=window.getBb()[1];
        }
        else{
            jmin-=3;
        }
        if(imax+ i > window.getBb()[2]){
            imax=window.getBb()[2];
        }
        else{
            imax+=3;
        }
        if(jmax+ i > window.getBb()[3]){
            jmax=window.getBb()[3];
        }
        else{
            jmax+=3;
        }
        int [] a = {imin,jmin,imax,jmax};
        return new BoundingBox(a);
    }
    private void copyPatch(Point point, Patch patch) throws Exception {
        int halfwidth = (patch.getBoundingBox().getBb()[3]-patch.getBoundingBox().getBb()[0])/2;
        Patch copypatch = new Patch(point,halfwidth,window);
        for(int i = patch.getBoundingBox().getBb()[0]; i<patch.getBoundingBox().getBb()[2];i++) {
            for(int j =patch.getBoundingBox().getBb()[1]; i<patch.getBoundingBox().getBb()[3];i++){
                if(m.val[i][j]){
                    int I=patch.point.i+i;
                    int J=patch.point.j+j;
                    if(m.val[I][J])
                        image.val[I][J].set(image.val[i+i][j+j]);
                    m.val[I][J]=false;
                }
            }
        }
    }

    public int[] argmin(double[][] tab) {
        double min = tab[0][0];
        int[] temp = new int[2];
        Arrays.fill(temp,0);
        for(int i=0; i<tab.length; i++) {
            for(int j=0; j<tab.length; j++) {
                if(tab[i][j]<min){
                    temp[0]=i;
                    temp[1]=j;
                }
            }
        }
        return temp;
    }

    private Point best_match(Patch patch, BoundingBox bb) {

        if()
    }

    public void restore(int i, int j)  throws IOException {

    }

    public static void main(String[] tab) throws IOException, ParseException {

    }

}
