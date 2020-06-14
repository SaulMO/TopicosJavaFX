package sample.Restaurante.Vistas;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import java.util.Optional;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import sample.Restaurante.Modelos.DataAccessObject.VentasIndividualesDAO;
import sample.Restaurante.Modelos.DataAccessObject.VentasDAO;

import java.sql.Date;

public class EliminarPedidos extends Stage
{
    private Scene scene;
    private VBox vBox;
    private HBox hBox;
    private Label labelTitulo;
    private TableView<VentasDAO> tableVentas;
    private TableView<VentasIndividualesDAO> tableVentasDetalle;
    private Button buttonSalir;
    private VentasIndividualesDAO ventasIndividualesDAO;
    private VentasDAO ventasDAO;

    public EliminarPedidos(){
        crearGUI();
        this.setScene(scene);
        this.initStyle(StageStyle.UNDECORATED);
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
        vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setId("eliminarPedidos");
        hBox = new HBox(15);
        hBox.setAlignment(Pos.CENTER);

        crearTablaVentas();
        crearTablaVentasDetalle();

        hBox.getChildren().addAll(tableVentas,tableVentasDetalle);

        labelTitulo = new Label("EliminarPedidos");
        labelTitulo.setId("titulo");
        buttonSalir = new Button("Salir");
        buttonSalir.setStyle(styleButton);
        buttonSalir.setOnAction(event -> Eventos(2));

        vBox.getChildren().addAll(labelTitulo,hBox,buttonSalir);
        scene = new Scene(vBox);
        scene.getStylesheets().add(getClass().getResource("../resources/estiloRestaurante.css").toExternalForm());
    }
    private void crearTablaVentas(){
        tableVentas = new TableView<>();
        tableVentas.setOnMouseClicked(event -> Eventos(0));
        TableColumn<VentasDAO,Integer> tableColumnNoVenta = new TableColumn<>("No Venta");
        tableColumnNoVenta.setCellValueFactory(new PropertyValueFactory<>("noVenta"));
        TableColumn<VentasDAO, Date> tableColumnFecha = new TableColumn<>("Fecha");
        tableColumnFecha.setCellValueFactory(new PropertyValueFactory<>("fechaVenta"));
        TableColumn<VentasDAO,Float> tableColumnPrecioTotal = new TableColumn<>("Total");
        tableColumnPrecioTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        tableVentas.getColumns().addAll(tableColumnNoVenta,tableColumnFecha,tableColumnPrecioTotal);
        tableVentas.setItems(new VentasDAO().SELECCIONAR());
    }
    private void crearTablaVentasDetalle(){
        tableVentasDetalle = new TableView<>();
        tableVentasDetalle.setOnMouseClicked(event -> Eventos(1));
        TableColumn<VentasIndividualesDAO,Integer> tableColumnNoVenta = new TableColumn<>("No Venta");
        tableColumnNoVenta.setCellValueFactory(new PropertyValueFactory<>("noVenta"));
        TableColumn<VentasIndividualesDAO,String> tableColumnNombre = new TableColumn<>("Producto");
        tableColumnNombre.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        TableColumn<VentasIndividualesDAO,Float> tableColumnPrecio = new TableColumn<>("Precio");
        tableColumnPrecio.setCellValueFactory(new PropertyValueFactory<>("precioProducto"));
        TableColumn<VentasIndividualesDAO,Integer> tableColumnCantidad = new TableColumn<>("Cantidad");
        tableColumnCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        tableVentasDetalle.getColumns().addAll(tableColumnNoVenta,tableColumnNombre,tableColumnPrecio,tableColumnCantidad);
        tableVentasDetalle.setItems(new VentasIndividualesDAO().SELECCION());
    }
    private void Eventos(int opcion){
        switch (opcion) {
            case 0: //EliminarVentas
                ventasDAO = null;
                ventasDAO = tableVentas.getSelectionModel().getSelectedItem();
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.initStyle(StageStyle.UNDECORATED);
                alert.setTitle("Alerta");
                alert.setHeaderText("Esta a punto de eliminar\nla venta " + ventasDAO.getNoVenta() + " del dia " + ventasDAO.getFechaVenta());
                alert.setContentText("desea completar esta operación?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    ventasDAO.ELIMINAR();
                    recargarTablas();
                }
                break;
            case 1: //Eliminar Venta Individual
                ventasIndividualesDAO = null;
                ventasIndividualesDAO = tableVentasDetalle.getSelectionModel().getSelectedItem();
                Alert alert2 = new Alert(AlertType.CONFIRMATION);
                alert2.initModality(Modality.APPLICATION_MODAL);
                alert2.initStyle(StageStyle.UNDECORATED);
                alert2.setTitle("Alerta");
                alert2.setHeaderText("Esta a punto de eliminar el producto "+ventasIndividualesDAO.getNombreProducto()+"\n de la venta numero "+ventasIndividualesDAO.getNoVenta());
                alert2.setContentText("Are you ok with this?");
                Optional<ButtonType> result2 = alert2.showAndWait();
                if (result2.get() == ButtonType.OK){
                    ventasIndividualesDAO.ELIMINARVentaIndividual();
                    recargarTablas();
                }
                break;
            case 2: //Salir
                this.close();
        }
    }

    private void recargarTablas(){
        tableVentas.getItems().clear();
        tableVentas.setItems(new VentasDAO().SELECCIONAR());
        tableVentasDetalle.getItems().clear();
        tableVentasDetalle.setItems(new VentasIndividualesDAO().SELECCION());
    }

}