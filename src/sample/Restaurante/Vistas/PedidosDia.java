package sample.Restaurante.Vistas;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Restaurante.Modelos.DataAccessObject.ProductoDAO;

public class PedidosDia extends Stage
{
    private Scene scene;
    private VBox vBox;
    private Label lblTitle;
    private TableView<ProductoDAO> table;
    private Button buttonAceptar;
    private ObservableList<ProductoDAO> productoDAOS;

    public PedidosDia(ObservableList<ProductoDAO> productoDAOS){
        this.productoDAOS = productoDAOS;
        this.crearGUI();
        this.setScene(scene);this.initStyle(StageStyle.UNDECORATED);
        this.initModality(Modality.APPLICATION_MODAL);
        this.show();
    }
    private void crearGUI(){
        String styleButton = "-fx-text-fill: white; " +
                "-fx-font-family: \"Comic Sans MS\";" +
                "-fx-background-color: #43423c; " +
                "-fx-border-color: white; " +
                "-fx-border-radius: 20px; " +
                "-fx-background-radius: 20px; " +
                "-fx-font-size: 10pt; ";
        vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        lblTitle = new Label("P E D I D O S");
        lblTitle.setId("titulo");

        table = new TableView<>();
        TableColumn<ProductoDAO,String> tableColumnNombre = new TableColumn<>("Producto");
        tableColumnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        TableColumn<ProductoDAO,String> tableColumnNombreCategoria = new TableColumn<>("Categoria");
        tableColumnNombreCategoria.setCellValueFactory(new PropertyValueFactory<>("nombreCategoria"));
        TableColumn<ProductoDAO,Float> tableColumnPrecio = new TableColumn<>("Precio");
        tableColumnPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        TableColumn<ProductoDAO,String> tableColumnDescripcion = new TableColumn<>("Descripción");
        tableColumnDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        TableColumn<ProductoDAO,Integer> tableColumnCantidad = new TableColumn<>("Cantidad");
        tableColumnCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        table.getColumns().addAll(tableColumnNombre,tableColumnNombreCategoria,tableColumnPrecio,tableColumnDescripcion,tableColumnCantidad);
        table.setItems(productoDAOS);

        buttonAceptar = new Button("Aceptar");
        buttonAceptar.setId("btnSalir");
        buttonAceptar.setStyle(styleButton);
        buttonAceptar.setOnAction(event -> {
            this.close();
        });

        vBox.getChildren().addAll(lblTitle,table,buttonAceptar);
        vBox.setId("PanePedidosDia");
        scene = new Scene(vBox);
        scene.getStylesheets().add(getClass().getResource("../resources/estiloRestaurante.css").toExternalForm());
    }
}