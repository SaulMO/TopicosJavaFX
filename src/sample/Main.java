package sample;

import javafx.application.Application;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import sample.Modelos.Conexion;
import sample.Restaurante.MenuPrincipal;
import sample.Restaurante.Vistas.*;
import sample.Vistas.*;

public class Main extends Application implements EventHandler
{
    private Scene scene;
    //private Button btnSalir;
    private MenuBar menuBar;
    private Menu menuCompetencia1,menuCompetencia2,menuSalir;
    private MenuItem menuItmCalculadora,menuItmTaquimecanografo,menuItmSalir,menuItmRestaurante,
                        menuItmListaPeliculas,menuItmCuadroMagico,menuItmCuadroMagico2,menuItmVengadromo,menuItmRestaurante2;
    private BorderPane pane;

    @Override
    public void start(Stage primaryStage) throws Exception{
        pane = new BorderPane();
        scene = new Scene(pane);
        createMenu();

        /**TitledPane tl = new TitledPane();
        tl.getStyleClass().add("danger");
        pane.setLeft(tl);*/

        primaryStage.setTitle("Prácticas Topicos Avanzados de Programación");
        primaryStage.addEventHandler(WindowEvent.WINDOW_SHOWN,this);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();

        //RecursoCompartido recursoCompartido = new RecursoCompartido();
        //new Productor(recursoCompartido).start();
        //new Consumidor(recursoCompartido).start();
        /*
        new Vengador("THOR").start();
        new Vengador("Capitan America").start();
        new Vengador("HULK").start();
        new Vengador("Iron Man").start();
        new Vengador("Spider Man").start();
        new Vengador("Black Panther").start();
        new Vengador("Pantera Rosa").start();*/
    }

    private void createMenu(){
        iniciarComponents();
        anadirComponentes();
        scene.getStylesheets().add(getClass().getResource("resources/CSS/estilos.css").toExternalForm());
    }

    private void iniciarComponents(){
        menuBar = new MenuBar();
        menuCompetencia1 = new Menu("Competencia 1");
        menuCompetencia2 = new Menu("Competencia 2");
        menuSalir = new Menu("Salir");

        menuItmCalculadora = new MenuItem("Calculadora");
        menuItmRestaurante = new MenuItem("Restaurante");
        menuItmTaquimecanografo = new MenuItem("Taquimecanografo");
        menuItmListaPeliculas = new MenuItem("Lista Peliculas");
        menuItmCuadroMagico = new MenuItem("Cuadro Mágico");
        menuItmCuadroMagico2 = new MenuItem("Cuadro Mágico version 2");
        menuItmRestaurante2 = new MenuItem("Restaurante v2");
        menuItmVengadromo = new MenuItem("Vengadromo (HILOS)");
        menuItmSalir = new MenuItem("Bye");
        menuItmSalir.setAccelerator(KeyCombination.keyCombination("Ctrl+x"));

        menuItmCalculadora.setOnAction(event->EventoItem(1));
        menuItmTaquimecanografo.setOnAction(event->EventoItem(2));
        menuItmRestaurante.setOnAction(event->EventoItem(3));
        menuItmListaPeliculas.setOnAction(event ->EventoItem(4));
        menuItmCuadroMagico.setOnAction(event ->EventoItem(5));
        menuItmCuadroMagico2.setOnAction(event -> EventoItem(6));
        menuItmRestaurante2.setOnAction(event -> EventoItem(7));

        menuItmVengadromo.setOnAction(event -> EventoItem(11));
        menuItmSalir.setOnAction(evento->EventoItem(10));
    }

    private void anadirComponentes(){
        menuCompetencia1.getItems().addAll(menuItmCalculadora,menuItmTaquimecanografo,menuItmRestaurante,menuItmListaPeliculas,
                menuItmCuadroMagico,menuItmCuadroMagico2,menuItmRestaurante2);
        menuCompetencia2.getItems().addAll(menuItmVengadromo);
        menuSalir.getItems().add(menuItmSalir);
        menuBar.getMenus().addAll(menuCompetencia1,menuCompetencia2,menuSalir);
        pane.setTop(menuBar);
    }

    private void EventoItem(int opcion) {
        switch (opcion){
            case 1:
                new Calculadora();
                break;
            case 2:
                new Taquimecanografo();
                break;
            case 3:
                new Restaurante();
                break;
            case 4:
                new ListPeliculas();
                break;
            case 5:
                new CuadroMagico();
                break;
            case 6:
                new CuadroMagicoV2();
                break;
            case 7:
                new Mesa();
                break;
            case 11:
                new Vengadromo();
                break;
            case 10:
                System.exit(0);
                break;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(Event event) {
        Conexion.getConnection();
        if (Conexion.conn != null)
            System.out.println("CONEXIÓN EXITOSA!!");
    }
}