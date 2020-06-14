package sample.Restaurante.Vistas;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Restaurante.Modelos.ConexionDBRestaurante;
import sample.Restaurante.Modelos.DataAccessObject.ProductoDAO;
import sample.Restaurante.Modelos.DataAccessObject.VentasDAO;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class Dashboard extends Stage
{
    private Scene scene;
    private BorderPane borderPaneMain;
    private DecimalFormat decimalFormat = new DecimalFormat("#.00");
    //COMPONENTES MENU IZQUIERDO
    private GridPane gridPaneMenu;
    private HBox hBoxBotones;
    private Button buttonSalir;
    private Button buttonActualizar;
    private Button buttonAdd;
    private Button buttonEliminar;
    private Label labelVentasTotales;
    //COMPONENTES GRAFICAS
    private AnchorPane anchorPaneGraficas;
    private ScrollPane scrollPaneGraficas;
    private VBox vBoxGraficas;
    //COMPONENTES GRÁFICAS (VENTAS HOY)
    private VBox vBoxVentasHoy;
    private HBox hBoxVentasHoy;
    private Label labelTituloVentasHoy;
    private BarChart barChartVentasDelDia;
    private CategoryAxis xAxisVentasDelDia;
    private NumberAxis yAxisVentasDelDia;
    private TableView tableViewVentasHoy;
    //COMPONENTES GRÁFICAS (VENTAS SEMANA ACTUAL/ANTERIOR)
    private VBox vBoxVentasSemanas;
    private Label labelTituloVentasSemanas;
    private HBox hBoxVentasSemanaActualAnterior;
    private BarChart barChartVentasSemanaActualAnterior;
    private CategoryAxis xAxisVentasSemanaActualAnterior;
    private NumberAxis yAxisVentasSemanaActualAnterior;
    private PieChart pieChartVentasSemanaActualAnterior;
    //COMPONENTES GRÁFICAS (VENTAS DEL MES ENTRADAS)
    private VBox vBoxVentasMesEntradas;
    private Label labelTituloVentasMesEntradas;
    private ScrollPane scrollPaneEntradas;
    private BarChart barChartVentasEntradas;
    private CategoryAxis xAxisVentasEntradas;
    private NumberAxis yAxisVentasEntradas;
    //COMPONENTES GRÁFICAS (VENTAS DEL MES PLATILLOS)
    private VBox vBoxVentasMesPlatillos;
    private Label labelTituloVentasMesPlatillos;
    private ScrollPane scrollPanePlatillos;
    private BarChart barChartVentasPlatillos;
    private CategoryAxis xAxisVentasPlatillos;
    private NumberAxis yAxisVentasPlatillos;
    //COMPONENTES GRÁFICAS (VENTAS DEL MES BEBIDAS)
    private VBox vBoxVentasMesBebidas;
    private Label labelTituloVentasMesBebidas;
    private ScrollPane scrollPaneBebidas;
    private BarChart barChartVentasBebidas;
    private CategoryAxis xAxisVentasBebidas;
    private NumberAxis yAxisVentasBebidas;
    //COMPONENTES GRÁFICAS (VENTAS DEL MES POSTRES)
    private VBox vBoxVentasMesPostres;
    private Label labelTituloVentasMesPostres;
    private ScrollPane scrollPanePostres;
    private BarChart barChartVentasPostres;
    private CategoryAxis xAxisVentasPostres;
    private NumberAxis yAxisVentasPostres;

    public Dashboard(){
        ConexionDBRestaurante.getConnection();
        try {
            if (ConexionDBRestaurante.connRestaurante.isClosed())
                ConexionDBRestaurante.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        crearGUI();
        this.setScene(scene);
        this.initStyle(StageStyle.UNDECORATED);
        this.show();
    }
    private void crearGUI(){
        iniciarComponentes();
        agregarComponentes();
    }
    private void iniciarComponentes(){
        borderPaneMain = new BorderPane();
        borderPaneMain.setId("paneGraficas");
        scrollPaneGraficas = new ScrollPane();
        //AQUI INICIA EL MENU LADO IZQUIERDO
        gridPaneMenu = new GridPane();
        gridPaneMenu.setHgap(10);
        gridPaneMenu.setVgap(10);
        gridPaneMenu.setAlignment(Pos.CENTER);
        hBoxBotones = new HBox();
        hBoxBotones.setSpacing(12);
        hBoxBotones.setAlignment(Pos.CENTER);
        FontAwesomeIconView iconActualizar = new FontAwesomeIconView();
        iconActualizar.setIcon(FontAwesomeIcon.PENCIL_SQUARE);
        iconActualizar.setId("fontIcons");
        buttonActualizar = new Button();
        buttonActualizar.setGraphic(iconActualizar);
        buttonActualizar.setId("btnIcons");
        buttonActualizar.setOnAction(event -> Eventos(2));
        FontAwesomeIconView iconAdd = new FontAwesomeIconView();
        iconAdd.setIcon(FontAwesomeIcon.PLUS_SQUARE);
        iconAdd.setId("fontIcons");
        buttonAdd = new Button();
        buttonAdd.setGraphic(iconAdd);
        buttonAdd.setId("btnIcons");
        buttonAdd.setOnAction(event -> Eventos(1));
        FontAwesomeIconView iconEliminar = new FontAwesomeIconView();
        iconEliminar.setIcon(FontAwesomeIcon.ERASER);
        iconEliminar.setId("fontIcons");
        buttonEliminar = new Button();
        buttonEliminar.setGraphic(iconEliminar);
        buttonEliminar.setId("btnIcons");
        buttonEliminar.setOnAction(event -> Eventos(3));
        FontAwesomeIconView iconExit = new FontAwesomeIconView();
        iconExit.setIcon(FontAwesomeIcon.POWER_OFF);
        iconExit.setId("fontIcons");
        buttonSalir = new Button();
        buttonSalir.setGraphic(iconExit);
        buttonSalir.setId("btnIcons");
        buttonSalir.setOnAction(event -> Eventos(4));
        labelVentasTotales = new Label("Ventas Totales: "+decimalFormat.format(new VentasDAO().SUMA_VENTAS_TOTALES()));
        labelVentasTotales.setId("labelResultados");
        //TERMINAN COMPONENTES DEL MENU LADO IZQUIERDO
        //COMPONENTES GRAFICAS
        anchorPaneGraficas = new AnchorPane();
        vBoxGraficas = new VBox();
        vBoxGraficas.setSpacing(12);
        scrollPaneGraficas = new ScrollPane();
        scrollPaneGraficas.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPaneGraficas.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPaneGraficas.setFitToHeight(true);
        scrollPaneGraficas.setFitToWidth(true);
        scrollPaneGraficas.setContent(anchorPaneGraficas);
        //COMPONENTES GRAFICAS (VENTAS HOY)
        vBoxVentasHoy = new VBox();
        vBoxVentasHoy.setSpacing(15);
        vBoxVentasHoy.setId("vBoxGraficas");
        labelTituloVentasHoy = new Label("V E N T A S    H O Y");
        labelTituloVentasHoy.setId("lblTitleGraficas");
        hBoxVentasHoy = new HBox();
        hBoxVentasHoy.setSpacing(15);
        xAxisVentasDelDia = new CategoryAxis();
        xAxisVentasDelDia.setLabel("Productos");
        yAxisVentasDelDia = new NumberAxis();
        yAxisVentasDelDia.setLabel("Ventas Totales");
        barChartVentasDelDia = new BarChart(xAxisVentasDelDia,yAxisVentasDelDia);
        barChartVentasDelDia.setData(new VentasDAO().SELECCION_VENTAS_DEL_DIA());
        //COMPONENTES GRAFICAS (VENTAS SEMANA ACTUAL/ANTERIOR)
        vBoxVentasSemanas = new VBox();
        vBoxVentasSemanas.setSpacing(15);
        vBoxVentasSemanas.setId("vBoxGraficas");
        labelTituloVentasSemanas = new Label("V E N T A S    S E M A N A    A C T U A L / A N T E R I O R");
        labelTituloVentasSemanas.setId("lblTitleGraficas");
        hBoxVentasSemanaActualAnterior = new HBox();
        hBoxVentasSemanaActualAnterior.setSpacing(15);
        xAxisVentasSemanaActualAnterior = new CategoryAxis();
        xAxisVentasSemanaActualAnterior.setLabel("Productos");
        yAxisVentasSemanaActualAnterior = new NumberAxis();
        yAxisVentasSemanaActualAnterior.setLabel("Ventas Totales");
        barChartVentasSemanaActualAnterior = new BarChart(xAxisVentasSemanaActualAnterior,yAxisVentasSemanaActualAnterior);
        barChartVentasSemanaActualAnterior.setData(new VentasDAO().SELECCION_VENTAS_SEMANA_ACTUAL_ANTERIOR());
        pieChartVentasSemanaActualAnterior = new PieChart(new VentasDAO().SELECCION_PIECHART());
        //COMPONENTES GRAFICAS (VENTAS MES ENTRADAS)
        vBoxVentasMesEntradas = new VBox();
        vBoxVentasMesEntradas.setSpacing(15);
        vBoxVentasMesEntradas.setId("vBoxGraficas");
        labelTituloVentasMesEntradas = new Label("V E N T A S    M E N S U A L E S  (E N T R A D A S)");
        labelTituloVentasMesEntradas.setId("lblTitleGraficas");
        xAxisVentasEntradas = new CategoryAxis();
        xAxisVentasEntradas.setLabel("Productos");
        yAxisVentasEntradas = new NumberAxis();
        yAxisVentasEntradas.setLabel("Ventas Totales");
        barChartVentasEntradas = new BarChart(xAxisVentasEntradas,yAxisVentasEntradas);
        barChartVentasEntradas.setId("barChartGraficas");
        barChartVentasEntradas.setData(new VentasDAO().SELECCIONPEDIDOSMES("Entradas"));
        scrollPaneEntradas = new ScrollPane();
        scrollPaneEntradas.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPaneEntradas.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPaneEntradas.setFitToHeight(true);
        scrollPaneEntradas.setFitToWidth(true);
        scrollPaneEntradas.setContent(barChartVentasEntradas);
        //COMPONENTES GRAFICAS (VENTAS MES PLATILLOS)
        vBoxVentasMesPlatillos = new VBox();
        vBoxVentasMesPlatillos.setSpacing(15);
        vBoxVentasMesPlatillos.setId("vBoxGraficas");
        labelTituloVentasMesPlatillos = new Label("V E N T A S    M E N S U A L E S  (P L A T I L L O S)");
        labelTituloVentasMesPlatillos.setId("lblTitleGraficas");
        xAxisVentasPlatillos = new CategoryAxis();
        xAxisVentasPlatillos.setLabel("Productos");
        yAxisVentasPlatillos = new NumberAxis();
        yAxisVentasPlatillos.setLabel("Ventas Totales");
        barChartVentasPlatillos = new BarChart(xAxisVentasPlatillos,yAxisVentasPlatillos);
        barChartVentasPlatillos.setData(new VentasDAO().SELECCIONPEDIDOSMES("Platillos"));
        scrollPanePlatillos = new ScrollPane();
        scrollPanePlatillos.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPanePlatillos.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPanePlatillos.setFitToHeight(true);
        scrollPanePlatillos.setFitToWidth(true);
        scrollPanePlatillos.setContent(barChartVentasPlatillos);
        //COMPONENTES GRAFICAS (VENTAS MES BEBIDAS)
        vBoxVentasMesBebidas = new VBox();
        vBoxVentasMesBebidas.setSpacing(15);
        vBoxVentasMesBebidas.setId("vBoxGraficas");
        labelTituloVentasMesBebidas = new Label("V E N T A S    M E N S U A L E S  (B E B I D A S)");
        labelTituloVentasMesBebidas.setId("lblTitleGraficas");
        xAxisVentasBebidas = new CategoryAxis();
        xAxisVentasBebidas.setLabel("Productos");
        yAxisVentasBebidas = new NumberAxis();
        yAxisVentasBebidas.setLabel("Ventas Totales");
        barChartVentasBebidas = new BarChart(xAxisVentasBebidas,yAxisVentasBebidas);
        barChartVentasBebidas.setData(new VentasDAO().SELECCIONPEDIDOSMES("Bebidas"));
        scrollPaneBebidas = new ScrollPane();
        scrollPaneBebidas.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPaneBebidas.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPaneBebidas.setFitToHeight(true);
        scrollPaneBebidas.setFitToWidth(true);
        scrollPaneBebidas.setContent(barChartVentasBebidas);
        //COMPONENTES GRAFICAS (VENTAS MES POSTRES)
        vBoxVentasMesPostres = new VBox();
        vBoxVentasMesPostres.setSpacing(15);
        vBoxVentasMesPostres.setId("vBoxGraficas");
        labelTituloVentasMesPostres = new Label("V E N T A S    M E N S U A L E S  (P O S T R E S)");
        labelTituloVentasMesPostres.setId("lblTitleGraficas");
        xAxisVentasPostres = new CategoryAxis();
        xAxisVentasPostres.setLabel("Productos");
        yAxisVentasPostres = new NumberAxis();
        yAxisVentasPostres.setLabel("Ventas Totales");
        barChartVentasPostres = new BarChart(xAxisVentasPostres,yAxisVentasPostres);
        barChartVentasPostres.setData(new VentasDAO().SELECCIONPEDIDOSMES("Postres"));
        scrollPanePostres = new ScrollPane();
        scrollPanePostres.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPanePostres.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPanePostres.setFitToHeight(true);
        scrollPanePostres.setFitToWidth(true);
        scrollPanePostres.setContent(barChartVentasPostres);
        crearTabla();
    }
    private void agregarComponentes(){
        hBoxBotones.getChildren().addAll(buttonAdd,buttonActualizar,buttonEliminar,buttonSalir);
        gridPaneMenu.add(hBoxBotones,0,0);
        gridPaneMenu.add(labelVentasTotales,0,2);

        hBoxVentasHoy.getChildren().addAll(barChartVentasDelDia,tableViewVentasHoy);
        hBoxVentasSemanaActualAnterior.getChildren().addAll(barChartVentasSemanaActualAnterior,pieChartVentasSemanaActualAnterior);
        vBoxVentasHoy.getChildren().addAll(labelTituloVentasHoy,hBoxVentasHoy);
        vBoxVentasSemanas.getChildren().addAll(labelTituloVentasSemanas,hBoxVentasSemanaActualAnterior);
        vBoxVentasMesEntradas.getChildren().addAll(labelTituloVentasMesEntradas,scrollPaneEntradas);
        vBoxVentasMesPlatillos.getChildren().addAll(labelTituloVentasMesPlatillos,scrollPanePlatillos);
        vBoxVentasMesBebidas.getChildren().addAll(labelTituloVentasMesBebidas,scrollPaneBebidas);
        vBoxVentasMesPostres.getChildren().addAll(labelTituloVentasMesPlatillos,scrollPanePostres);

        vBoxGraficas.getChildren().addAll(vBoxVentasHoy,vBoxVentasSemanas,vBoxVentasMesEntradas,vBoxVentasMesPlatillos,vBoxVentasMesBebidas,vBoxVentasMesPostres);
        anchorPaneGraficas.getChildren().addAll(vBoxGraficas);
        borderPaneMain.setLeft(gridPaneMenu);
        borderPaneMain.setCenter(scrollPaneGraficas);
        scene = new Scene(borderPaneMain);
        scene.getStylesheets().add(getClass().getResource("../resources/estiloRestaurante.css").toExternalForm());
    }
    private void crearTabla(){
        tableViewVentasHoy = new TableView<>();
        tableViewVentasHoy.setMaxSize(600,320);
        //tableViewVentasHoy.setOnMouseClicked(event -> Eventos(4));
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

        tableViewVentasHoy.getColumns().addAll(tableColumnId,tableColumnNombre,tableColumnIdCategoria,tableColumnDescripcion,tableColumnPrecio);
        tableViewVentasHoy.setItems(new VentasDAO().SELECCION_PEDIDOS_DEL_DIA());
    }

    private void Eventos(int evento){
        switch (evento){
            case 1: //AÑADIR
                try {
                    if (ConexionDBRestaurante.connRestaurante.isClosed())
                        ConexionDBRestaurante.getConnection();
                    new GuardarPlatillos();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 2: //Actualizar
                try {
                    if (ConexionDBRestaurante.connRestaurante.isClosed())
                        ConexionDBRestaurante.getConnection();
                    new Actualizar();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 3: //Eliminar pedidos
                try {
                    if (ConexionDBRestaurante.connRestaurante.isClosed())
                        ConexionDBRestaurante.getConnection();
                    new EliminarPedidos();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 4: //cerrar
                ConexionDBRestaurante.Disconnect();
                this.close();
                break;
        }
    }
}