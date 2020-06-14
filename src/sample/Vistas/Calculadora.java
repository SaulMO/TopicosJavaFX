package sample.Vistas;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import sample.Eventos.EventosCalculadora;

public class Calculadora extends Stage implements EventHandler
{
    private TextField txtOperacion;
    private Label lblOperacion;
    private VBox vbox;
    private HBox[] arHBox;
    private Button btnClear,btnSalir;
    private Button[] arBtns;
    private char[] valores = {'7','8','9','+','4','5','6','-','1','2','3','x','0','.','=','/','c'};
    private Scene scene;

    public Calculadora(){
        CrearGUI();
        scene = new Scene(vbox);
        scene.getStylesheets().add(getClass().getResource("../resources/CSS/estiloCalculadora.css").toExternalForm());
        setScene(scene);
        initStyle(StageStyle.UNDECORATED);
        setResizable(false);
        setTitle("Calculadora");
        this.addEventHandler(WindowEvent.WINDOW_SHOWN,this);
        show();
        //Para trabajar con eventos de ventana usamos la clase EventHandler
    }

    private void CrearGUI(){
        vbox = new VBox();
        txtOperacion = new TextField();
        lblOperacion = new Label();
        txtOperacion.setEditable(false);
        btnSalir = new Button("Salir");
        btnClear = new Button("C / CE");
        btnSalir.addEventHandler(MouseEvent.MOUSE_CLICKED,new EventosCalculadora('s',txtOperacion,lblOperacion));
        btnClear.addEventHandler(MouseEvent.MOUSE_CLICKED,new EventosCalculadora('c',txtOperacion,lblOperacion));
        arHBox = new HBox[5];
        arHBox[0] = new HBox();
        arHBox[0].setAlignment(Pos.BASELINE_CENTER);
        arHBox[0].setSpacing(15);
        arHBox[0].getChildren().addAll(btnClear,btnSalir);
        arBtns = new Button[16];
        vbox.getChildren().addAll(txtOperacion,lblOperacion,arHBox[0]);
        byte posBtn = 0;
        for (int i=1 ; i<arHBox.length ; i++){
            arHBox[i] = new HBox();
            arHBox[i].setSpacing(10);
            arHBox[i].setPadding(new Insets(10));
            for (int j=0 ; j<4 ; j++){
                //Aqui puede incrementar el contador con ++posBtn y funcionaria igual;
                arBtns[posBtn] = new Button(""+valores[posBtn]);
                //arBtns[posBtn].addEventHandler(MouseEvent.MOUSE_CLICKED,this);
                //EventosCalculadora objEC = new EventosCalculadora();
                arBtns[posBtn].addEventHandler(MouseEvent.MOUSE_CLICKED,new EventosCalculadora(valores[posBtn],txtOperacion,lblOperacion));
                //Esto hace una sobrecarga a la clase EventHandler
                /**arBtns[posBtn].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("Hola desde el interior de la clase (Clase Anonima - Clase EventHandler misma)");
                    }
                });*/
                arBtns[posBtn].setPrefSize(50,50);
                //arBtns[posBtn].setStyle("-fx-font: 15 arial; -fx-base: #B6E7C9");
                arHBox[i].getChildren().add(arBtns[posBtn]);
                posBtn++;
            }
        }
        vbox.getChildren().addAll(arHBox[1],arHBox[2],arHBox[3],arHBox[4]);
    }

    private void alertaAdios(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Adios");
        alert.setContentText("Adios x3");
        alert.setHeaderText("Adios x2");
        alert.showAndWait();
    }

    @Override
    public void handle(Event event) {

    }
}