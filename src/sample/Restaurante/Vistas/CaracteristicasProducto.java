package sample.Restaurante.Vistas;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.stage.StageStyle;
import sample.Restaurante.Modelos.DataAccessObject.ProductoDAO;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CaracteristicasProducto extends Stage
{
    private Scene scene;
    private VBox vBoxMain;
    private Label labelNombreProducto,labelPrecioProducto,labelCategoriaProducto;
    private JFXTextArea textAreaDescripcionProducto;
    private JFXButton buttonAceptar;
    private ImageView imageView;

    public CaracteristicasProducto(){
        iniciarComponentes();
        agregarComponentes();
        this.initStyle(StageStyle.UNDECORATED);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setScene(scene);
        this.show();
    }
    private void iniciarComponentes(){
        File file;
        ProductoDAO productoDAO = new ProductoDAO();
        productoDAO.SELECCIONINDIVIDUAL();
        String style = "-fx-text-fill: white; " +
                        "-fx-font-family: \"Comic Sans MS\";" +
                        "-fx-font-size: 10pt;";
        String styleButton = "-fx-text-fill: white; " +
                             "-fx-font-family: \"Comic Sans MS\";" +
                             "-fx-background-color: #43423c; " +
                             "-fx-border-color: white; " +
                             "-fx-border-radius: 20px; " +
                             "-fx-background-radius: 20px; " +
                             "-fx-font-size: 10pt; ";
        vBoxMain = new VBox();
        vBoxMain.setId("caracProducto");
        file = new File("src/sample/Restaurante/resources/images/" + productoDAO.getIdProducto() + ".png");
        if (!file.exists())
            file = new File("src/sample/Restaurante/resources/images/default.png");
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file);
            imageView = new ImageView(SwingFXUtils.toFXImage(bufferedImage, null));
            imageView.setFitWidth(150);
            imageView.setFitHeight(150);
        } catch (IOException e) {
            e.printStackTrace();
        }
        labelNombreProducto = new Label(productoDAO.getNombre());
        labelNombreProducto.setStyle(style);
        labelPrecioProducto = new Label("$ "+productoDAO.getPrecio());
        labelPrecioProducto.setStyle(style);
        textAreaDescripcionProducto = new JFXTextArea(productoDAO.getDescripcion());
        textAreaDescripcionProducto.setWrapText(true);
        textAreaDescripcionProducto.setPrefSize(200,300);
        textAreaDescripcionProducto.setDisableAnimation(true);
        textAreaDescripcionProducto.setEditable(false);
        textAreaDescripcionProducto.setStyle(style);
        labelCategoriaProducto = new Label(productoDAO.getNombreCategoria());
        labelCategoriaProducto.setStyle(style);
        buttonAceptar = new JFXButton("Salir");
        buttonAceptar.setStyle(styleButton);
        buttonAceptar.setOnAction(event -> {
            this.close();
        });
    }
    private void agregarComponentes(){
        vBoxMain.getChildren().addAll(imageView,labelNombreProducto,labelCategoriaProducto,textAreaDescripcionProducto,labelPrecioProducto,buttonAceptar);
        scene = new Scene(vBoxMain);
        scene.getStylesheets().add(getClass().getResource("../resources/estiloRestaurante.css").toExternalForm());
    }
}
