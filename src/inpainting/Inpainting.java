package inpainting;

import com.jfoenix.controls.JFXProgressBar;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import topology.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Inpainting {

    private ImageView imgFinale;
    private TextArea ta;
    private JFXProgressBar bar;


    public int tgv = 3 * 255 * 255 + 1;
    public Matrix image;
    public Mask m;
    public int[][] penMask;
    public BoundingBox window;
    private SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");

    public Inpainting(Matrix matrix, Mask mask, ImageView iv, TextArea t, JFXProgressBar b) throws Exception {
        if (!Arrays.equals(matrix.getBb(), mask.getBb())) throw new Exception("Les bounding box ne correspondent pas");
        image = matrix;
        m = mask;
        window = mask;
        penMask = new int[window.width][window.height];
        for (int i = 0; i < window.width; i++) {
            for (int j = 0; j < window.height; j++) {
                if (m.val[i][j]) penMask[i][j] = tgv;
                else penMask[i][j] = 0;
            }
        }
        imgFinale = iv;
        ta = t;
        bar = b;
    }

    private BoundingBox searchingBox(Component component, int i) {
        Point pt0 = component.getPoints().get(0);
        int[] bb = new int[]{pt0.i, pt0.j, pt0.i, pt0.j};
        for (Point pt : component.getPoints()) {
            bb[0] = Math.min(bb[0], pt.i);
            bb[1] = Math.min(bb[1], pt.j);
            bb[2] = Math.max(bb[2], pt.i);
            bb[3] = Math.max(bb[3], pt.j);
        }
        ;
        return new BoundingBox(new int[]{Math.max(bb[0] - i, 0),
                Math.max(bb[1] - i, 0),
                Math.min(bb[2] + i, window.width),
                Math.min(bb[3] + i, window.height)});
    }

    private void copyPatch(Point point, Patch patch) throws Exception {
        int i = point.getI();
        int j = point.getJ();
        for (int dx = patch.boundingBox.bb[0]; dx < patch.boundingBox.bb[2]; dx++)
            for (int dy = patch.boundingBox.bb[1]; dy < patch.boundingBox.bb[3]; dy++) {
                int I = patch.point.i + dx;
                int J = patch.point.j + dy;
                if (m.val[I][J])
                    image.val[I][J].set(image.val[i + dx][j + dy]);
                m.val[I][J] = false;
            }
    }

    public int[] argmin(double[][] tab) {

        double currentMin = tab[0][0];
        int besti = 0;
        int bestj = 0;
        for (int i = 0; i < tab.length; i++)
            for (int j = 0; j < tab[i].length; j++)
                if (currentMin >= tab[i][j]) {
                    currentMin = tab[i][j];
                    besti = i;
                    bestj = j;
                }
        return new int[]{besti, bestj};
    }

    private Point best_match(Patch patch, BoundingBox bb) throws Exception {

        if (bb == null)
            bb = new BoundingBox(new int[]{0, 0, m.width, m.height});
        BoundingBox searchBox = bb.crop(patch);
        // We compute the difference on all the search box, then
        // look at the minimum.
        // It should be a little faster to do it all in one step
        double[][] norms = new double[searchBox.width][searchBox.height]; // filled with zero by default
        for (int i = 0; i < searchBox.width; i++)
            for (int j = 0; j < searchBox.height; j++) norms[i][j] = 0; // A priori inutile
        for (int dx = patch.boundingBox.bb[0]; dx < patch.boundingBox.bb[2]; dx++)
            for (int dy = patch.boundingBox.bb[1]; dy < patch.boundingBox.bb[3]; dy++) {
                int I = patch.point.i + dx;
                int J = patch.point.j + dy;
                int i_min = searchBox.bb[0] + dx;
                int j_min = searchBox.bb[1] + dy;
                int i_max = searchBox.bb[2] + dx;
                int j_max = searchBox.bb[3] + dy;
                // penalization of patch intersectin the mask
                for (int k = 0; k < searchBox.width; k++)
                    for (int l = 0; l < searchBox.height; l++)
                        norms[k][l] += penMask[i_min + k][j_min + l];

                // add contribution to points outside the mask
                if (!m.val[I][J])
                    for (int k = 0; k < searchBox.width; k++)
                        for (int l = 0; l < searchBox.height; l++)
                            norms[k][l] += Color.dist(image.val[i_min + k][j_min + l], image.val[I][J]);
            }
        int[] bestIndex = argmin(norms);
        return new Point(m, bestIndex[0] + searchBox.bb[0], bestIndex[1] + searchBox.bb[1]);
    }

    public void restore(int halfwidth, int searchWidth) throws Exception {
        ta.appendText(ft.format(new Date()) + " > Début du traitement\n");
        Boundary b = new Boundary(m);

        Components C = new Components(b);
        while (C.size() != 0) {
            ta.appendText(ft.format(new Date()) + " > Nombre restant de bords à traiter : " + b.getEdges().size() + "\n");
            int k = 0;
            for (Component component : C.getComponents()) {
                k += 1;
                BoundingBox searchBox = null;
                if (searchWidth != 0)
                    searchBox = searchingBox(component, searchWidth);


                Platform.runLater(new Runnable() {public void run() {bar.setProgress(0);}});
                int count = 0, max = component.getPoints().size();

                for (Point point : component.getPoints()) {
                    if (m.touchedBy(point)) {
                        Patch patch = new Patch(point, halfwidth, window);
                        Point best_point = best_match(patch, searchBox);
                        copyPatch(best_point, patch); //update image and mask
                        imgFinale.setImage(SwingFXUtils.toFXImage(image.getImage(), null));
                    }
                    count++;
                    int finalCount = count;
                    Platform.runLater(new Runnable() {public void run() {bar.setProgress((double) finalCount /max);}});
                }
            }
            b = new Boundary(m);
            C = new Components(b);
            if (searchWidth != 0) searchWidth += halfwidth;
        }
    }
}
