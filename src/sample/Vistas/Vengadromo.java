package sample.Vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Componentes.Vengador;

public class Vengadromo extends Stage
{
    private ProgressBar[] pgbCarriles = new ProgressBar[7];
    private GridPane gridPane;
    private VBox vBox;
    private Button btnIniciar;
    private Scene scene;
    private Label lblTitulo;
    private Vengador[] threadVengador = new Vengador[7];
    private Label[] labelsVengadores = new Label[7];
    private String[] vengador = {"THOR","Capitan America","HULK","Iron Man","Spider Man","Black Panther","Pantera Rosa"};

    public Vengadromo(){
        crearGUI();
        this.setScene(scene);
        this.setTitle("Corriendo de Thanos");
        this.show();
    }

    private void crearGUI() {
        vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        lblTitulo = new Label("V E N G A D R O M O");
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(10);
        gridPane.setHgap(15);
        for (int i=0 ; i<vengador.length ; i++){
            pgbCarriles[i] = new ProgressBar(0);
            labelsVengadores[i] = new Label(vengador[i]);
            pgbCarriles[i].setMinWidth(400);
            threadVengador[i] = new Vengador(vengador[i],pgbCarriles[i]);
            gridPane.add(labelsVengadores[i],0,i);
            gridPane.add(pgbCarriles[i],1,i);
        }
        btnIniciar = new Button("INICIAR CARRERA");
        btnIniciar.setOnAction(event -> IniciarCarrera());
        scene = new Scene(vBox,600,500);
        vBox.getChildren().addAll(lblTitulo,gridPane,btnIniciar);
    }

    private void IniciarCarrera(){
        for (int i=0 ; i<vengador.length ; i++){
            threadVengador[i].start();
        }
    }
}