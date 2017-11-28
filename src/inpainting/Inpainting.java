/**
 * <> with heart by Doutel Silva Filipe, Nadaud Sörel and Barbero Lucas
 */


package inpainting;

import org.apache.commons.cli.*;
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
        Point pt0=component.getPoints().get(0);
        int[] bb=new int[]{pt0.i,pt0.j,pt0.i,pt0.j};
        for(Point pt:component.getPoints()){
            bb[0]=Math.min(bb[0],pt.i);
            bb[1]=Math.min(bb[1],pt.j);
            bb[2]=Math.max(bb[2],pt.i);
            bb[3]=Math.max(bb[3],pt.j);
        };
        return new BoundingBox(new int[]{Math.max(bb[0]-i,0),
                Math.max(bb[1]-i,0),
                Math.min(bb[2]+i,window.width),
                Math.min(bb[3]+i,window.height)});
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

    private Point best_match(Patch patch, BoundingBox bb) throws Exception {

        if(bb==null)
            bb=new BoundingBox(new int[]{0,0,m.width,m.height});
        BoundingBox searchBox=bb.crop(patch);
        // We compute the difference on all the search box, then
        // look at the minimum.
        // It should be a little faster to do it all in one step
        double[][] norms=new double[searchBox.width][searchBox.height];	// filled with zero by default
        for(int i=0;i<searchBox.width;i++)
            for(int j=0;j<searchBox.height;j++) norms[i][j]=0;		// A priori inutile
        for(int dx=patch.boundingBox.bb[0];dx<patch.boundingBox.bb[2];dx++)
            for(int dy=patch.boundingBox.bb[1];dy<patch.boundingBox.bb[3];dy++){
                int I=patch.point.i+dx;			int J=patch.point.j+dy;
                int i_min=searchBox.bb[0]+dx; 	int i_max=searchBox.bb[2]+dx;
                int j_min=searchBox.bb[1]+dy;	int j_max=searchBox.bb[3]+dy;
                // penalization of patch intersectin the mask
                for(int k=0;k<searchBox.width;k++) for(int l=0;l<searchBox.height;l++)
                    norms[k][l]+=penMask[i_min+k][j_min+l];

                // add contribution to points outside the mask
                if (!m.val[I][J])
                    for(int k=0;k<searchBox.width;k++)  for(int l=0;l<searchBox.height;l++)
                        norms[k][l]+=Color.dist(image.val[i_min+k][j_min+l],image.val[I][J]);
            }
        int[] bestIndex=argmin(norms);
        return new Point(m,bestIndex[0]+searchBox.bb[0],bestIndex[1]+searchBox.bb[1]);
    }

    public void restore(int halfwidth,int searchWidth) throws Exception {
        Boundary b=new Boundary(m);

        Components C=new Components(b);
        while (C.size()!=0){
            int k=0;
            for(Component component:C.getComponents()){
                k+=1;
                BoundingBox searchBox=null;
                if(searchWidth!=0)
                    searchBox=searchingBox(component,searchWidth);

                for(Point point:component.getPoints())
                {
                    if (m.touchedBy(point)){
                        Patch patch=new Patch(point,halfwidth,window);
                        Point best_point=best_match(patch,searchBox);
                        copyPatch(best_point,patch);	//update image and mask
                    }
                }
            }
            b=new Boundary(m);
            C=new Components(b);
            if(searchWidth!=0) searchWidth+=halfwidth;
        }
        System.out.println("Done");

    }

    public static void main(String[] tab) throws Exception {
        DefaultParser parser = new DefaultParser();
        Options options = new Options();
        options.addOption("p",true,"Halfwidth");
        options.addOption("s",true,"searchWidth");
        options.addOption("m",true,"mask.bmp");
        options.addOption("i",true,"image to restore");
        options.addOption("o",true,"restored image");
        CommandLine c = parser.parse(options,tab);
        if(c.hasOption("p")&& c.hasOption("m")&& c.hasOption("i")&& c.hasOption("o")){
            String imagePath = c.getOptionValue("i");
            String maskPath = c.getOptionValue("m");
            String newImagePath = c.getOptionValue("o");
            int searchWidth;
            int halfwidth = Integer.parseInt(c.getOptionValue("p"));

            Mask mask = new Mask(maskPath,new Color(0,0,0));
            Matrix matrix = new Matrix(imagePath);
            Inpainting inpainting = new Inpainting(matrix,mask);
            matrix.applyMask(mask);

            searchWidth = matrix.getWidth();
            if(c.hasOption("s")){
                searchWidth = Integer.parseInt(c.getOptionValue("s"));
            }
            inpainting.restore(halfwidth,searchWidth);
            matrix.save(newImagePath);
            matrix.save(newImagePath);
        }
        else{
            System.out.println("Erreur syntaxe arguments -p -m -i et -o obligatoires");
        }


    }

}
