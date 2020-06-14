package sample.Vistas;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Eventos.EventosTeclado;

public class Taquimecanografo extends Stage
{
    private Scene scene;
    private Button btnPrueba;
    private VBox vbox;
    private ToolBar toolBMenu;
    private TextArea txtATexto,txtAEscritura;

    public Taquimecanografo(){
        crearGUI();
        scene = new Scene(vbox);
        scene.getStylesheets().add(getClass().getResource("../resources/CSS/estiloTaquimecanografo.css").toExternalForm());
        setTitle("TAQUIMECANOGRAFO");
        setScene(scene);
        show();
    }

    private void crearGUI() {
        iniciarComponentes();
        anadirComponentes();
    }
    private void iniciarComponentes(){
        vbox = new VBox();
        toolBMenu = new ToolBar();
        txtATexto = new TextArea();
        btnPrueba = new Button("Prueba");
        txtATexto.setEditable(false);
        txtAEscritura = new TextArea();
        //txtAEscritura.addEventHandler(KeyEvent.KEY_TYPED,new EventosTeclado());
        txtAEscritura.setOnKeyPressed(new EventosTeclado());
    }
    private void anadirComponentes(){
        vbox.getChildren().addAll(toolBMenu,txtATexto,txtAEscritura,btnPrueba);
    }
}