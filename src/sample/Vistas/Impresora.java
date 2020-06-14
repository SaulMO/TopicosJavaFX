package sample.Vistas;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.ProgressBar;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import sample.Componentes.Documento;
import sample.Componentes.DocumentoAImprimir;

public class Impresora extends Stage
{
    private Scene scene;
    private ProgressBar progressBar;
    private TableView<Documento> tablaColaImpresion;
    private TableColumn<Documento,Integer> columnaNumeroHojas,columnaNumeroDocumento;
    private TableColumn<Documento,String> columnaNombre,columnaStatus,columnaOrigen;
    private Label lbltitulo,lblLapA,lblLapB;
    private VBox boxLapA,boxLapB,mainBox;
    private HBox boxImagenes,boxBotones;
    private Button btnIniciar,btnSalir,btnLapA,btnLapB;
    private ImageView impresora,lapA,lapB;
    private int contDocumento;
    private DocumentoAImprimir documentoAImprimir;

    public Impresora(){
        this.crearGUI();
        this.setScene(scene);
        this.initStyle(StageStyle.UNDECORATED);
        this.initModality(Modality.APPLICATION_MODAL);
        this.show();
    }
    private void crearGUI(){
        try {
            Image impresora,lapA,lapB;
            impresora = SwingFXUtils.toFXImage(ImageIO.read(new File("src/sample/resources/imagenes/impresora.png")),null);
            lapA = SwingFXUtils.toFXImage(ImageIO.read(new File("src/sample/resources/imagenes/lap.png")),null);
            lapB = SwingFXUtils.toFXImage(ImageIO.read(new File("src/sample/resources/imagenes/lap.png")),null);
            this.impresora = new ImageView(impresora);
            this.lapA = new ImageView(lapA);
            this.lapB = new ImageView(lapB);
            this.impresora.setFitWidth(150);   this.impresora.setFitHeight(150);
            this.lapA.setFitWidth(150);    this.lapA.setFitHeight(150);
            this.lapB.setFitWidth(150);    this.lapB.setFitHeight(150);
        } catch (IOException e) {
            e.printStackTrace();
        }
        boxLapA = new VBox();
        lblLapA = new Label("LAPTOP_A");
        btnLapA = new Button("Imprimir");
        btnLapA.setCursor(Cursor.HAND);
        btnLapA.setOnAction(event -> eventos(0));
        boxLapA.setAlignment(Pos.CENTER);
        boxLapA.getChildren().addAll(lapA,lblLapA,btnLapA);
        boxLapB = new VBox();
        lblLapB = new Label("LAPTOP_B");
        btnLapB = new Button("Imprimir");
        btnLapB.setCursor(Cursor.HAND);
        btnLapB.setOnAction(event -> eventos(1));
        boxLapB.getChildren().addAll(lapB,lblLapB,btnLapB);
        boxLapB.setAlignment(Pos.CENTER);
        boxImagenes = new HBox(10);
        boxImagenes.getChildren().addAll(boxLapA,impresora,boxLapB);
        boxImagenes.setAlignment(Pos.CENTER);

        lbltitulo = new Label("S I M U L A D O R   I M P R E S I Ó N");
        progressBar = new ProgressBar(0);
        progressBar.setMinWidth(400);

        btnIniciar = new Button("Iniciar");
        btnIniciar.setOnAction(event -> eventos(2));
        btnIniciar.setCursor(Cursor.HAND);
        btnSalir = new Button("Salir");
        btnSalir.setCursor(Cursor.HAND);;;;;;;;;;;;;;;;;;;;;;
        btnSalir.setOnAction(event -> eventos(3));
        boxBotones = new HBox(10);
        boxBotones.setAlignment(Pos.CENTER);
        boxBotones.getChildren().addAll(btnSalir);

        contDocumento=1;

        columnaNumeroDocumento = new TableColumn<>("Número");
        columnaNumeroDocumento.setCellValueFactory(new PropertyValueFactory<>("numero"));
        columnaNombre = new TableColumn<>("Nombre");
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombreArchivo"));
        columnaNumeroHojas = new TableColumn<>("Hojas");
        columnaNumeroHojas.setCellValueFactory(new PropertyValueFactory<>("noHojas"));
        columnaStatus = new TableColumn<>("Estatus");
        columnaStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        columnaOrigen = new TableColumn<>("Origen");
        columnaOrigen.setCellValueFactory(new PropertyValueFactory<>("pc"));
        tablaColaImpresion = new TableView<>();
        tablaColaImpresion.setPrefSize(700,300);
        tablaColaImpresion.getColumns().addAll(columnaNumeroDocumento,columnaNombre,columnaNumeroHojas,columnaStatus,columnaOrigen);
        mainBox = new VBox(10);
        mainBox.getChildren().addAll(lbltitulo,boxImagenes,tablaColaImpresion,progressBar,boxBotones);
        mainBox.setAlignment(Pos.CENTER);
        scene = new Scene(mainBox);
        documentoAImprimir = new DocumentoAImprimir(this.progressBar,this.tablaColaImpresion);
        documentoAImprimir.run();
    }
    private void eventos(int i){
        switch (i){
            case 0: //lapA
                documentoAImprimir.addDocumento(new Documento(contDocumento,numeroAleatorio(),generarNombre(),"en espera","Laptop A"));
                contDocumento++;
                break;
            case 1: //lapB
                documentoAImprimir.addDocumento(new Documento(contDocumento,numeroAleatorio(),generarNombre(),"en espera","Laptop B"));
                contDocumento++;
                break;
            case 2:
                break;
            case 3:
                documentoAImprimir.stop();
                this.close();
                break;

        }
    }
    private int numeroAleatorio(){
        return (int) ((Math.random()*50)+1);
    }
    private String generarNombre(){
        String nombre = "";
        switch((int)(Math.random()*10)){
            case 0:
                nombre = "reporte"; break;
            case 1:
                nombre = "ensayo"; break;
            case 2:
                nombre = "informe"; break;
            case 3:
                nombre = "carta"; break;
            case 4:
                nombre = "bibliografia"; break;
            case 5:
                nombre = "investigación"; break;
            case 6:
                nombre = "catalogo"; break;
            case 7:
                nombre = "documento"; break;
            case 8:
                nombre = "cuestionario"; break;
            case 9:
                nombre = "mapa conceptual"; break;
            case 10:
                nombre = "material"; break;
        }
        switch ((int)(Math.random()*10)){
            case 0:
                nombre = nombre + " calculo diferencial"; break;
            case 1:
                nombre = nombre + " calculo integral"; break;
            case 2:
                nombre = nombre + " POO"; break;
            case 3:
                nombre = nombre + " Probabilidad y estadistica"; break;
            case 4:
                nombre = nombre + " Química"; break;
            case 5:
                nombre = nombre + " Administración"; break;
            case 6:
                nombre = nombre + " Física"; break;
            case 7:
                nombre = nombre + " Investigación operaciones"; break;
            case 8:
                nombre = nombre + " topicos avanzados programación"; break;
            case 9:
                nombre = nombre + " base de datos"; break;
            case 10:
                nombre = nombre + " sistemas operativos"; break;
        }
        return nombre;
    }
}