package sample.Restaurante.Vistas;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import sample.Restaurante.Modelos.DataAccessObject.ProductoDAO;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class GuardarPlatillos extends Stage
{
    private Scene scene;
    private GridPane gridMenu;
    private HBox hBoxImagen,hBoxFinal;
    private VBox vBox;
    private Label lblTitulo;
    private TextField txtFieldNombre,txtFieldPrecio;
    private ComboBox<String> comboBoxCategoria;
    private Image image;
    private ImageView imageView;
    private Button btnElegirImagen,btnGuardar,btnSalir;
    private TextArea txtAreaDescripcion;
    private File archivoImagen;
    private String cveProducto;
    private ProductoDAO productoDAO;

    public GuardarPlatillos(){
        crearGUI();
        this.setScene(scene);
        initStyle(StageStyle.UNDECORATED);
        this.initModality(Modality.APPLICATION_MODAL);
        this.show();
    }

    private void crearGUI(){
        try{
            archivoImagen = new File("src/sample/Restaurante/resources/images/default.png");
            BufferedImage bufferedImage = ImageIO.read(archivoImagen);
            image = SwingFXUtils.toFXImage(bufferedImage,null);
            imageView = new ImageView();
            imageView.setFitWidth(150);
            imageView.setFitHeight(150);
            imageView.setImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        lblTitulo = new Label("P R O D U C T O");
        lblTitulo.setId("titulo");
        txtFieldNombre = new TextField();
        txtFieldNombre.setPromptText("N o m b r e");
        txtAreaDescripcion = new TextArea();
        txtAreaDescripcion.setWrapText(true);
        txtAreaDescripcion.setPromptText("D e s c r i p c i ó n");
        txtFieldPrecio = new TextField();
        txtFieldPrecio.setPromptText("P r e c i o");
        comboBoxCategoria = new ComboBox<>();
        comboBoxCategoria.setValue("ENTRADAS");
        comboBoxCategoria.getItems().addAll(new ProductoDAO().SELECCIONAR_Categorias());
        btnElegirImagen = new Button("Elegir Imagen");
        btnElegirImagen.setOnAction(event -> elegirImagen());
        btnElegirImagen.setId("btn");
        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> guardarProducto());
        btnGuardar.setId("btn");
        btnSalir = new Button("Salir");
        btnSalir.setOnAction(event -> salir());
        btnSalir.setId("btn");

        vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(comboBoxCategoria,btnElegirImagen);
        vBox.setSpacing(20);
        hBoxImagen = new HBox();
        hBoxImagen.setSpacing(20);
        hBoxImagen.setAlignment(Pos.CENTER);
        hBoxImagen.getChildren().addAll(imageView,vBox);
        hBoxFinal = new HBox();
        hBoxFinal.setSpacing(20);
        hBoxFinal.setAlignment(Pos.CENTER);
        hBoxFinal.getChildren().addAll(btnGuardar,btnSalir);
        gridMenu = new GridPane();
        gridMenu.setId("paneGuardar");
        gridMenu.setAlignment(Pos.CENTER);
        gridMenu.setPadding(new Insets(20,20,20,20));
        gridMenu.setVgap(10);
        gridMenu.add(lblTitulo,0,0);
        gridMenu.add(txtFieldNombre,0,2);
        gridMenu.add(txtFieldPrecio,0,3);
        gridMenu.add(hBoxImagen,0,4);
        gridMenu.add(txtAreaDescripcion,0,5);
        gridMenu.add(hBoxFinal,0,7);

        scene = new Scene(gridMenu);
        scene.getStylesheets().add(getClass().getResource("../resources/estiloRestaurante.css").toExternalForm());
    }

    private void salir(){
        this.close();
    }
    private void elegirImagen(){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG,extFilterPNG);
        archivoImagen = fileChooser.showOpenDialog(this);
        try{
            if ((archivoImagen != null) && ((archivoImagen.getName().endsWith("png")) || (archivoImagen.getName().endsWith("jpg")))) {
                BufferedImage bufferedImage = ImageIO.read(archivoImagen);
                image = SwingFXUtils.toFXImage(bufferedImage, null);
                imageView.setImage(image);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void guardarProducto(){
        if (verificarDatos()){
            if (archivoImagen == null)
                archivoImagen = new File("src/sample/Restaurante/resources/images/default.png");
            cveProducto = (comboBoxCategoria.getValue().substring(0,1) +
                    (char)getLetra() +
                    (char)getLetra() +
                    (char)getLetra() +
                    (char)getLetra());
            productoDAO = new ProductoDAO(cveProducto,comboBoxCategoria.getValue().substring(0,2),
                    txtFieldNombre.getText(),txtAreaDescripcion.getText(),Float.parseFloat(txtFieldPrecio.getText()));
            productoDAO.INSERTAR();
            guardarImagen();
            txtFieldNombre.setText("");
            txtFieldPrecio.setText("");
            txtAreaDescripcion.setText("");
            try{
                archivoImagen = new File("src/sample/Restaurante/resources/images/default.png");
                BufferedImage bufferedImage = ImageIO.read(archivoImagen);
                image = SwingFXUtils.toFXImage(bufferedImage,null);
                imageView.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    private void guardarImagen(){
        File directorio_guardar = new File("src/sample/Restaurante/resources/images/"+cveProducto+".png");
        try{
            FileInputStream fis = new FileInputStream(archivoImagen); //inFile -> Archivo a copiar
            FileOutputStream fos = new FileOutputStream(directorio_guardar); //outFile -> Copia del archivo
            FileChannel inChannel = fis.getChannel();
            FileChannel outChannel = fos.getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
            fis.close();
            fos.close();
        }catch (IOException ioe) {
            System.err.println("Error al Generar Copia");
        }
    }
    private boolean verificarDatos(){
        boolean bandera = false;
        if ( (txtFieldNombre.getText().isEmpty()) || txtFieldPrecio.getText().isEmpty()){
        }else{
            try{
                double a = Double.parseDouble(txtFieldPrecio.getText());
                bandera = true;
            }catch(NumberFormatException ex){
                bandera = false;
            }
        }
        return bandera;
    }
    private int getLetra(){ return (int)(Math.random() * 25) + 65; }
}