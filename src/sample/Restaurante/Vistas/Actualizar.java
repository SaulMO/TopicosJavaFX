package sample.Restaurante.Vistas;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Restaurante.Modelos.DataAccessObject.ProductoDAO;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class Actualizar extends Stage
{
    private Scene scene;
    private AnchorPane anchorPaneMain;
    private TableView<ProductoDAO> tableView;
    private Label lblTitulo;
    private GridPane gridMenu;
    private HBox hBoxImagen,hBoxFinal;
    private VBox vBox;
    private TextField txtFieldNombre,txtFieldPrecio;
    private ComboBox<String> comboBoxCategoria;
    private Image image;
    private ImageView imageView;
    private Button btnElegirImagen,btnGuardar,btnSalir,btnEliminar;
    private TextArea txtAreaDescripcion;
    private File archivoImagen;
    private String idProducto;
    private ProductoDAO productoDAO = null;
    private boolean cambioImagen;
    private File archivoImagenTemp;

    public Actualizar(){
        iniciarComponentes();
        addComponentes();
        this.setScene(scene);
        this.initStyle(StageStyle.UNDECORATED);
        this.initModality(Modality.APPLICATION_MODAL);
        this.show();
    }

    private void iniciarComponentes(){
        anchorPaneMain = new AnchorPane();
        anchorPaneMain.setId("paneActualizar");
        lblTitulo = new Label("A c t u a l i z a r / E l i m i n a r");
        lblTitulo.setId("titulo");
        menuDatos();
        crearTabla();
    }
    private void addComponentes(){
        gridMenu.setLayoutX(635); gridMenu.setLayoutY(50);
        lblTitulo.setLayoutX(20); lblTitulo.setLayoutY(5);
        tableView.setLayoutY(50); tableView.setLayoutX(20);
        anchorPaneMain.getChildren().addAll(gridMenu,lblTitulo,tableView);
        scene = new Scene(anchorPaneMain);
        scene.getStylesheets().add(getClass().getResource("../resources/estiloRestaurante.css").toExternalForm());
    }
    private void menuDatos(){
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
        txtFieldNombre = new TextField();
        txtFieldNombre.setPromptText("N o m b r e");
        txtAreaDescripcion = new TextArea();
        txtAreaDescripcion.setWrapText(true);
        txtAreaDescripcion.setPrefSize(200,100);
        txtAreaDescripcion.setPromptText("D e s c r i p c i ó n");
        txtFieldPrecio = new TextField();
        txtFieldPrecio.setPromptText("P r e c i o");
        comboBoxCategoria = new ComboBox<>();
        comboBoxCategoria.setValue("ENTRADAS");
        comboBoxCategoria.getItems().addAll(new ProductoDAO().SELECCIONAR_Categorias());
        btnElegirImagen = new Button("Elegir Imagen");
        btnElegirImagen.setOnAction(event -> Eventos(1));
        btnElegirImagen.setId("btn");
        btnGuardar = new Button("Actualizar");
        btnGuardar.setOnAction(event -> Eventos(2));
        btnGuardar.setId("btn");
        btnSalir = new Button("Salir");
        btnSalir.setOnAction(event -> Eventos(0));
        btnSalir.setId("btn");
        btnEliminar = new Button("Eliminar");
        btnEliminar.setOnAction(event -> Eventos(3));
        btnEliminar.setId("btn");
        vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(comboBoxCategoria,btnElegirImagen);
        vBox.setSpacing(10);
        hBoxImagen = new HBox();
        hBoxImagen.setSpacing(10);
        hBoxImagen.setAlignment(Pos.CENTER);
        hBoxImagen.getChildren().addAll(imageView,vBox);
        hBoxFinal = new HBox();
        hBoxFinal.setSpacing(10);
        hBoxFinal.setAlignment(Pos.CENTER);
        hBoxFinal.getChildren().addAll(btnGuardar,btnSalir,btnEliminar);
        gridMenu = new GridPane();
        gridMenu.setAlignment(Pos.CENTER);
        gridMenu.setVgap(6);
        //gridMenu.add(lblTitulo,0,0);
        gridMenu.add(txtFieldNombre,0,0);
        gridMenu.add(txtFieldPrecio,0,1);
        gridMenu.add(hBoxImagen,0,2);
        gridMenu.add(txtAreaDescripcion,0,3);
        gridMenu.add(hBoxFinal,0,5);
    }
    private void crearTabla(){
        tableView = new TableView<>();
        tableView.setOnMouseClicked(event -> Eventos(4));
        tableView.setPrefSize(570,385);
        TableColumn<ProductoDAO,String> tableColumnId = new TableColumn<>("ID");
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        TableColumn<ProductoDAO,String> tableColumnIdCategoria = new TableColumn<>("IdCategoria");
        tableColumnIdCategoria.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));
        TableColumn<ProductoDAO,String> tableColumnNombre = new TableColumn<>("Nombre");
        tableColumnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        TableColumn<ProductoDAO,String> tableColumnDescripcion = new TableColumn<>("Descripción");
        tableColumnDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        TableColumn<ProductoDAO,Float> tableColumnPrecio = new TableColumn<>("Precio");
        tableColumnPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        tableView.getColumns().addAll(tableColumnId,tableColumnNombre,tableColumnIdCategoria,tableColumnDescripcion,tableColumnPrecio);
        tableView.setItems(new ProductoDAO().SELECCIONAR());
    }

    private void Eventos(int opcion){
        switch (opcion) {
            case 0://Salir
                this.close();
                break;
            case 1:
                elegirImagen();
                break;
            case 2://Guardar
                guardarProducto();
                break;
            case 3://Eliminar
                if (productoDAO != null){
                    if (deleteFile()){
                        productoDAO.ELIMINAR();
                        limpiar();
                        productoDAO = null;
                    }
                }
                break;
            case 4:
                productoDAO = tableView.getSelectionModel().getSelectedItem();
                txtFieldNombre.setText(productoDAO.getNombre());
                txtFieldPrecio.setText(String.valueOf(productoDAO.getPrecio()));
                txtAreaDescripcion.setText(productoDAO.getDescripcion());
                comboBoxCategoria.setValue(productoDAO.SELECCIONDescripcionCategoria());
                archivoImagen = new File("src/sample/Restaurante/resources/images/" + productoDAO.getIdProducto() + ".png");
                try {
                    if ((archivoImagen != null) && ((archivoImagen.getName().endsWith("png")) || (archivoImagen.getName().endsWith("jpg")))) {
                        BufferedImage bufferedImage = ImageIO.read(archivoImagen);
                        image = SwingFXUtils.toFXImage(bufferedImage, null);
                        imageView.setImage(image);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    private void guardarProducto(){
        if (verificarDatos()){
            if (archivoImagen == null || !archivoImagen.exists()){
                archivoImagen = new File("src/sample/Restaurante/resources/images/default.png");
            }
            productoDAO.setPrecio(Float.parseFloat(txtFieldPrecio.getText()));
            productoDAO.setNombre(txtFieldNombre.getText());
            productoDAO.setDescripcion(txtAreaDescripcion.getText());
            productoDAO.setIdCategoria(productoDAO.SELECCIONIdCategoria(comboBoxCategoria.getValue()));
            productoDAO.ACTUALIZAR();
            guardarImagen();
            limpiar();
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
    private void elegirImagen(){
        archivoImagenTemp = archivoImagen;
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG,extFilterPNG);
        archivoImagen = fileChooser.showOpenDialog(null);
        try{
            if (archivoImagen==null && archivoImagenTemp!=null && archivoImagenTemp.exists()){
                archivoImagen = archivoImagenTemp;
            }else if ((archivoImagen != null) && ((archivoImagen.getName().endsWith("png")) || (archivoImagen.getName().endsWith("jpg")))) {
                BufferedImage bufferedImage = ImageIO.read(archivoImagen);
                image = SwingFXUtils.toFXImage(bufferedImage, null);
                imageView.setImage(image);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void guardarImagen(){
        if (archivoImagen != archivoImagenTemp){
            File directorio_guardar = new File("src/sample/Restaurante/resources/images/"+productoDAO.getIdProducto()+".png");
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
    }
    private boolean deleteFile(){
        File fileBorrar = new File("src/sample/Restaurante/resources/images/"+productoDAO.getIdProducto()+".png");
        if (fileBorrar.delete()){
            return true;
        }
        return false;
    }
    private void limpiar(){
        txtFieldNombre.setText("");
        txtAreaDescripcion.setText("");
        txtFieldPrecio.setText("");
        imageView.setImage(new Image(new File("src/sample/Restaurante/resources/images/default.png").toURI().toString()));
        tableView.getItems().clear();
        tableView.setItems(productoDAO.SELECCIONAR());
    }
}