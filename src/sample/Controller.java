package sample;

import com.jfoenix.controls.*;
import inpainting.Inpainting;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import topology.Color;
import topology.Mask;
import topology.Matrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable {

    public JFXButton selectImage;
    public JFXButton selectMask;
    public JFXButton destFolder;
    public Label lblProgress;
    public ScrollPane scrollPaneGauche;
    public ScrollPane scrollPaneDroite;
    public StackPane stackPane;
    public JFXTextField demiLargeur;
    public JFXTextField margeMasque;
    public JFXTextField fileName;
    public TextArea displayScript;
    public ImageView imgBase;
    public ImageView imgFinale;
    public JFXProgressBar progressBar;
    public JFXColorPicker colorPicker;

    private Image imageBase;
    private Image imageMasque;
    private String cheminImageBase;
    private String cheminImageMasque;
    private String cheminDestination;

    private SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");

    private Color couleurMasque;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colorPicker.setValue(new javafx.scene.paint.Color(0, 0, 0, 1));
        couleurMasque = new Color(0, 0, 0);
    }

    @FXML
    void selectFolder(MouseEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        //Set extension filter
        directoryChooser.setTitle("Choisir le dossier de destination");

        //Show open file dialog
        File file = directoryChooser.showDialog(null);
        cheminDestination = file.getAbsolutePath();
    }

    @FXML
    void selectImage(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        //Set extension filter
        fileChooser.setTitle("Choisir l'image");
        FileChooser.ExtensionFilter extFilterBMP = new FileChooser.ExtensionFilter("BMP files (*.bmp)", "*.bmp");
        fileChooser.getExtensionFilters().addAll(extFilterBMP);

        //Show open file dialog
        File file = fileChooser.showOpenDialog(null);
        cheminImageBase = file.getAbsolutePath();
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            imageBase = SwingFXUtils.toFXImage(bufferedImage, null);
            imgBase.setImage(imageBase);
            margeMasque.setText(String.valueOf(bufferedImage.getWidth()));
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void selectMask(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        //Set extension filter
        fileChooser.setTitle("Choisir le masque");
        FileChooser.ExtensionFilter extFilterBMP = new FileChooser.ExtensionFilter("BMP files (*.bmp)", "*.bmp");
        fileChooser.getExtensionFilters().addAll(extFilterBMP);

        //Show open file dialog
        File file = fileChooser.showOpenDialog(null);
        cheminImageMasque = file.getAbsolutePath();

        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            imageMasque = SwingFXUtils.toFXImage(bufferedImage, null);
            imgFinale.setImage(imageMasque);
            checkImages();
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void modifyColor() {
        int blue = (int) (colorPicker.getValue().getBlue()*255);
        int green = (int) (colorPicker.getValue().getGreen()*255);
        int red = (int) (colorPicker.getValue().getRed()*255);
        couleurMasque.set(new Color(blue, green, red));
    }

    private void checkImages() {
        displayScript.setWrapText(true);

        if ((imageBase.getWidth() != imageMasque.getWidth()) && (imageBase.getHeight() != imageMasque.getHeight())) {
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("Attention !"));
            content.setBody(new Text("L'image et le masque ne semble pas correspondre."));
            JFXDialog jfxDialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.TOP);
            JFXButton button = new JFXButton("Okay");
            button.setOnAction(event -> jfxDialog.close());
            content.setActions(button);
            jfxDialog.show();
            displayScript.appendText(ft.format(new Date()) + " > L'image et le masque ne semble pas correspondre\n");
        } else {
            displayScript.appendText(ft.format(new Date()) + " > L'image et le masque correspondent\n");
        }
    }

    @FXML
    void validate(MouseEvent event) throws Exception {
        modifyColor();
        if (demiLargeur.getText() != null && cheminImageMasque != null && cheminImageBase != null && cheminDestination != null) {
            String destinationFilePath = cheminDestination + "\\" + fileName.getText();
            System.out.println(destinationFilePath);
            int demieLargeur = Integer.parseInt(demiLargeur.getText());
            Mask mask = new Mask(cheminImageMasque, couleurMasque);
            Matrix matrix = new Matrix(cheminImageBase);
            Inpainting inpainting = new Inpainting(matrix, mask, imgFinale, displayScript, progressBar);

            int marge = matrix.getWidth();

            if (margeMasque.getText() != null) {
                marge = Integer.parseInt(margeMasque.getText());
            }

            long startTime = System.currentTimeMillis();
            int finalMarge = marge;
            progressBar.setProgress(JFXProgressBar.INDETERMINATE_PROGRESS);
            new Thread(() -> {
                try {
                    inpainting.restore(demieLargeur, finalMarge);

                    long endTime = System.currentTimeMillis();
                    inpainting.image.save(destinationFilePath);

                    System.out.println(destinationFilePath + ".bmp");

                    String imgURL = "file:///" + destinationFilePath + ".bmp";
                    Image imageFinale = new Image(imgURL);
                    update(imageFinale);
                    displayScript.appendText(ft.format(new Date()) + " > Traitement terminé en" + formatDuration(endTime - startTime) + "\n");
                    Platform.runLater(() -> progressBar.setProgress(1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

        } else {
            displayScript.setWrapText(true);
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("Attention !"));
            content.setBody(new Text("Veuillez sélectionner au moins une image, un masque, un chemin de destination, un nom pour la nouvelle image et une valeur de demie largeur."));
            JFXDialog jfxDialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.TOP);
            JFXButton button = new JFXButton("Okay");
            button.setOnAction(event2 -> jfxDialog.close());
            content.setActions(button);
            jfxDialog.show();

            displayScript.appendText(ft.format(new Date()) + " > Veuillez sélectionner au moins une image, un masque, un chemin de destination, un nom pour la nouvelle image et une valeur de demie largeur.\n");
        }
    }

    private String formatDuration(long duration)
    {
        String res = "";
        long min = duration/60000;
        if (min > 0) res += " " + min + " minutes";
        duration %= 60000;

        long sec = duration/1000;
        duration %= 1000;
        res += " " + sec + "." + duration + " secondes";

        return res;
    }

    private void update(Image image) {
        imgFinale.setImage(image);
    }

}

