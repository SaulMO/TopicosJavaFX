package sample.Restaurante.Vistas;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javafx.stage.StageStyle;
import sample.Restaurante.componentes.RealizarPDF;
import sample.Restaurante.componentes.Tarjeta;
import sample.Restaurante.Modelos.ConexionDBRestaurante;
import sample.Restaurante.Modelos.DataAccessObject.ProductoDAO;
import sample.Restaurante.Modelos.DataAccessObject.VentasDAO;
import sample.Restaurante.Modelos.DataAccessObject.VentasIndividualesDAO;
import sample.Restaurante.ProductoSt;
import sample.Restaurante.TarjetaSt;

public class Mesa extends Stage
{
    private Scene scene;
    private VBox vBoxMain;
    private HBox hBoxBotones;
    private AnchorPane anchorPaneMain;
    private Label lblTitle;
    private TabPane tabPaneMenu;
    private Tab tabEntradas,tabPlatillos,tabBebidas,tabPostres;
    private FlowPane fpEntradas,fpPlatillos,fpBebidas,fpPostres;
    private ScrollPane scrollPaneEntradas,scrollPanePlatillos,scrollPaneBebidas,scrollPanePostres;
    private Button btnNextStep,btnSalir,btnTicket,btnCancelarVenta,btnVentas;
    //Menu
    private Menu menuOpciones;
    private MenuItem mnItmAbrirMesa,mnItmSalir,mnItmAdministrador;
    private MenuBar menuBar;
    private boolean mesaAbierta,yaInicio;

    private double total;
    private int noVenta;
    private VentasDAO ventasDAO;

    public Mesa(){
        crearGUI();
    }
    private void crearGUI(){
        this.iniciarComponentes();
        this.addComponentes();
        this.setScene(scene);
        this.setResizable(false);
        this.setHeight(600);
        this.setWidth(1024);
        this.initStyle(StageStyle.UNDECORATED);
        this.show();
    }

    private void iniciarComponentes(){
        ConexionDBRestaurante.getConnection();
        yaInicio = true;
        mesaAbierta = false;
        vBoxMain = new VBox();
        vBoxMain.setPrefWidth(1000);
        vBoxMain.setPrefHeight(500);
        vBoxMain.setAlignment(Pos.CENTER);
        vBoxMain.setSpacing(25);
        anchorPaneMain = new AnchorPane();
        anchorPaneMain.setId("paneCocina");
        lblTitle = new Label("S E L E C C I Ó N   D E   M E N U S");
        lblTitle.setId("titulo");

        //COMPONENTES MENU DESPLEGABLE
        menuBar = new MenuBar();
        menuOpciones = new Menu("Opciones");
        mnItmSalir = new MenuItem("Salir");
        mnItmSalir.setOnAction(event -> Eventos(0));
        mnItmAbrirMesa = new MenuItem("Abrir Mesa");
        mnItmAbrirMesa.setOnAction(event -> Eventos(1));
        mnItmAdministrador = new MenuItem("Administración");
        mnItmAdministrador.setOnAction(event -> Eventos(2));
        menuOpciones.getItems().addAll(mnItmAbrirMesa,mnItmAdministrador,mnItmSalir);
        menuBar.getMenus().addAll(menuOpciones);

        //COMPONENTES MENU
        tabPaneMenu = new TabPane();
        tabPaneMenu.setDisable(true);
        fpEntradas = new FlowPane();
        fpPlatillos = new FlowPane();
        fpBebidas = new FlowPane();
        fpPostres = new FlowPane();
        fpEntradas.setId("flowPaneMenu");
        fpPlatillos.setId("flowPaneMenu");
        fpBebidas.setId("flowPaneMenu");
        fpPostres.setId("flowPaneMenu");
        scrollPaneEntradas = new ScrollPane();
        scrollPaneEntradas.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPaneEntradas.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPaneEntradas.setFitToHeight(true);
        scrollPaneEntradas.setFitToWidth(true);
        scrollPaneEntradas.setContent(fpEntradas);
        scrollPanePlatillos = new ScrollPane();
        scrollPanePlatillos.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPanePlatillos.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPanePlatillos.setFitToHeight(true);
        scrollPanePlatillos.setFitToWidth(true);
        scrollPanePlatillos.setContent(fpPlatillos);
        scrollPaneBebidas = new ScrollPane();
        scrollPaneBebidas.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPaneBebidas.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPaneBebidas.setFitToHeight(true);
        scrollPaneBebidas.setFitToWidth(true);
        scrollPaneBebidas.setContent(fpBebidas);
        scrollPanePostres = new ScrollPane();
        scrollPanePostres.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPanePostres.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPanePostres.setFitToHeight(true);
        scrollPanePostres.setFitToWidth(true);
        scrollPanePostres.setContent(fpPostres);
        tabEntradas = new Tab("E N T R A D A S");
        tabEntradas.setClosable(false);
        tabEntradas.setId("tabRestaurante");
        tabEntradas.setContent(scrollPaneEntradas);
        tabPlatillos = new Tab("P L A T I L L O S");
        tabPlatillos.setClosable(false);
        tabPlatillos.setId("tabRestaurante");
        tabPlatillos.setContent(scrollPanePlatillos);
        tabBebidas = new Tab("B E B I D A S");
        tabBebidas.setClosable(false);
        tabBebidas.setId("tabRestaurante");
        tabBebidas.setContent(scrollPaneBebidas);
        tabPostres = new Tab("P O S T R E S");
        tabPostres.setClosable(false);
        tabPostres.setId("tabRestaurante");
        tabPostres.setContent(scrollPanePostres);

        btnSalir = new Button("X");
        btnSalir.setId("btnSalir");
        btnSalir.setOnAction(event -> Eventos(0));
        btnNextStep = new Button("Abrir Mesa");
        btnNextStep.setId("btnSalir");
        btnNextStep.setOnAction(event -> Eventos(3));
        btnTicket = new Button ("Ticket Compra");
        btnTicket.setId("btnSalir");
        btnTicket.setOnAction(event -> Eventos(4));
        btnTicket.setDisable(true);
        btnCancelarVenta = new Button("Cancelar venta");
        btnCancelarVenta.setId("btnSalir");
        btnCancelarVenta.setOnAction(event -> Eventos(5));
        btnCancelarVenta.setDisable(true);
        btnVentas = new Button("P. Realizados");
        btnVentas.setId("btnSalir");
        btnVentas.setOnAction(event -> Eventos(6));

        hBoxBotones = new HBox();
        hBoxBotones.setSpacing(10);
        hBoxBotones.setAlignment(Pos.CENTER);
    }
    private void addComponentes(){
        tabPaneMenu.getTabs().addAll(tabEntradas,tabPlatillos,tabBebidas,tabPostres);
        lblTitle.setLayoutX(380);
        lblTitle.setLayoutY(10);
        vBoxMain.setLayoutX(10);
        vBoxMain.setLayoutY(50);
        btnSalir.setLayoutX(993);
        btnVentas.setLayoutX(877);
        hBoxBotones.getChildren().addAll(btnNextStep,btnTicket,btnCancelarVenta);
        vBoxMain.getChildren().addAll(tabPaneMenu,hBoxBotones);
        anchorPaneMain.getChildren().addAll(btnVentas,btnSalir,menuBar,vBoxMain,lblTitle);
        llenarTabPane();
        scene = new Scene(anchorPaneMain);
        scene.getStylesheets().add(getClass().getResource("../resources/estiloRestaurante.css").toExternalForm());
    }
    private void llenarTabPane(){
        try {
            if (ConexionDBRestaurante.connRestaurante.isClosed())
                ConexionDBRestaurante.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        llenarMenu(fpEntradas,new ProductoDAO().SELECCIONAR_VistaMenu("ENTRADAS"),"En");
        llenarMenu(fpPlatillos,new ProductoDAO().SELECCIONAR_VistaMenu("PLATILLOS"),"Pl");
        llenarMenu(fpBebidas,new ProductoDAO().SELECCIONAR_VistaMenu("BEBIDAS"),"Be");
        llenarMenu(fpPostres,new ProductoDAO().SELECCIONAR_VistaMenu("POSTRES"),"Po");
    }
    private void limpiarTabPane(){
        fpEntradas.getChildren().clear();
        fpPlatillos.getChildren().clear();
        fpBebidas.getChildren().clear();
        fpPostres.getChildren().clear();
    }
    private void llenarMenu(FlowPane fpMenu,ObservableList<ProductoDAO> lista,String tipo) {
        File file = null;
        VBox tarjeta;
        HBox botones;
        Button btnAdd,btnSubtract,btnNumber;
        Image image;
        ImageView imageView;
        Label lblNombre,lblPrecio;
        short cont = 0;
        for(int i=0;i<lista.size();i++) {
            tarjeta = new VBox();
            botones = new HBox();
            botones.setAlignment(Pos.CENTER);
            tarjeta.setId("tarjeta");
            btnAdd = new Button("+");
            btnAdd.setId("botonTarjeta");
            btnSubtract = new Button("-");
            btnSubtract.setId("botonTarjeta");
            btnNumber = new Button("0");
            btnNumber.setId("botonTarjeta");
            file = new File("src/sample/Restaurante/resources/images/" + lista.get(i).getIdProducto() + ".png");
            if (!file.exists())
                file = new File("src/sample/Restaurante/resources/images/default.png");
            image = null;
            BufferedImage bufferedImage = null;
            try {
                bufferedImage = ImageIO.read(file);
                image = SwingFXUtils.toFXImage(bufferedImage, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            final String idProducto = lista.get(i).getIdProducto();
            tarjeta.setOnMouseClicked(event -> {
                try {
                    if (ConexionDBRestaurante.connRestaurante.isClosed())
                        ConexionDBRestaurante.getConnection();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                ProductoSt.idProducto = idProducto;
                new CaracteristicasProducto();
            });
            final Tarjeta tarjetaObjeto = new Tarjeta(lista.get(i).getIdProducto(), btnNumber,lista.get(i).getPrecio());
            TarjetaSt.ArrayListTarjetas.add(tarjetaObjeto);
            btnAdd.setOnAction(event -> {
                TarjetaSt.tarjetaSt = tarjetaObjeto;
                TarjetaSt.tarjetaSt.sumarContador();
                TarjetaSt.tarjetaSt.getButton().setText(String.valueOf(TarjetaSt.tarjetaSt.getContador()));
            });
            btnSubtract.setOnAction(event -> {
                try{
                    TarjetaSt.tarjetaSt.restarContador();
                    TarjetaSt.tarjetaSt.getButton().setText(String.valueOf(TarjetaSt.tarjetaSt.getContador()));
                }catch (NullPointerException nullPE){ }
            });
            imageView = new ImageView(image);
            imageView.setFitHeight(100);
            imageView.setFitWidth(180);
            lblNombre = new Label(lista.get(i).getNombre());
            lblPrecio = new Label("$" + lista.get(i).getPrecio());
            botones.getChildren().addAll(btnSubtract, btnNumber, btnAdd);
            tarjeta.getChildren().addAll(imageView, lblNombre, lblPrecio, botones);
            tarjeta.setAlignment(Pos.CENTER);
            tarjeta.setSpacing(7);
            fpMenu.getChildren().add(tarjeta);
        }
        fpMenu.setAlignment(Pos.CENTER);
    }

    private void Eventos(int i) {
        switch (i){
            case 0: //salir
                ConexionDBRestaurante.Disconnect();
                this.close();
                break;
            case 1: //Abrir Mesa
                new Mesa();
                break;
            case 2: //Administracion
                new Loggeo(this,(byte)0,this);
                break;
            case 3: //Next Step
                if (!mesaAbierta){
                    if (yaInicio){
                        TarjetaSt.ArrayListTarjetas.clear();
                        limpiarTabPane();
                        llenarTabPane();
                    }
                    this.tabPaneMenu.setDisable(false);
                    btnNextStep.setText("CerrarMesa");
                    btnTicket.setDisable(true);
                    btnCancelarVenta.setDisable(false);
                    mesaAbierta = true;
                }else{ //Mesa esta Abierta y se cierra
                    try {
                        if (ConexionDBRestaurante.connRestaurante.isClosed())
                            ConexionDBRestaurante.getConnection();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    this.tabPaneMenu.setDisable(true);
                    btnNextStep.setText("Abrir Mesa");
                    mesaAbierta = false;
                    ArrayList<VentasIndividualesDAO> ventas = new ArrayList<>();
                    VentasIndividualesDAO objeto;
                    noVenta = new VentasDAO().SELECT_ProximoNoVenta();
                    System.out.println(noVenta);
                    for (int h=0 ; h<TarjetaSt.ArrayListTarjetas.size() ; h++){
                        if (TarjetaSt.ArrayListTarjetas.get(h).getContador() != 0){
                            objeto = new VentasIndividualesDAO(TarjetaSt.ArrayListTarjetas.get(h).getID(),
                                    noVenta,
                                    TarjetaSt.ArrayListTarjetas.get(h).getContador(),
                                    TarjetaSt.ArrayListTarjetas.get(h).getPrecioProducto());
                            ventas.add(objeto);
                        }
                    }
                    ejecutarVenta(ventas);
                }
                break;
            case 4: //Ticket
                abrirPDF();
                break;
            case 5: //Cancelar Mesa
                TarjetaSt.ArrayListTarjetas.clear();
                new Loggeo(this,(byte)1, this);
                break;
            case 6: //VentasHoy
                ObservableList<ProductoDAO> productoDAOS = FXCollections.observableArrayList();
                for (int h=0; h<TarjetaSt.ArrayListTarjetas.size() ; h++){
                    if (TarjetaSt.ArrayListTarjetas.get(h).getContador() > 0) {
                        productoDAOS.add(new ProductoDAO(TarjetaSt.ArrayListTarjetas.get(h).getID(), TarjetaSt.ArrayListTarjetas.get(h).getContador()));
                    }
                }
                new PedidosDia(productoDAOS);
                break;
        }
    }

    private void ejecutarVenta(ArrayList<VentasIndividualesDAO> ventas){
        if (ventas.size() != 0){
            Date date = new Date();
            System.out.println(noVenta);
            ventasDAO = new VentasDAO(noVenta,new java.sql.Date(date.getTime()),0);
            ventasDAO.INSERT();
            for (int i=0 ; i<ventas.size() ; i++){
                ventas.get(i).INSERTAR();
            }
            ventasDAO.setTotal((float) new VentasIndividualesDAO().SELECT_TOTAL(noVenta));
            ventasDAO.ACTUALIZAR_Total();
            new RealizarPDF(ventas,date);
            btnTicket.setDisable(false);
            btnCancelarVenta.setDisable(true);
            abrirPDF();
        }
    }
    private void abrirPDF(){
        try {
            File path = new File ("src/sample/Restaurante/resources/tickets/"+noVenta+".pdf");
            Desktop.getDesktop().open(path);
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void cancelarCompra(){
        TarjetaSt.ArrayListTarjetas.clear();
        this.tabPaneMenu.setDisable(true);
        btnNextStep.setText("Abrir Mesa");
        mesaAbierta = false;
        btnCancelarVenta.setDisable(true);
    }
}