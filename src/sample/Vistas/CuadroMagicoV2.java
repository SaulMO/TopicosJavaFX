package sample.Vistas;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class CuadroMagicoV2 extends Stage
{
    private Scene scene;
    private SplitPane splitPaneMain;
    //COMPONENTES MENU DATOS;
    private Label lblTitulo;
    private TextField txtGrado;
    private Button btnComenzar;
    private VBox vBoxDatos;
    //COMPONENTES CUADRO MÁGICO
    private int gradoCuadro;
    private GridPane gridCuadroMagico;
    private Button[][] matrizBotones;
    private Button[] buttons;
    //COMPONENTES CREAR ARCHIVO
    private RandomAccessFile randomAccessFile;
    private String nombreArchivo;

    public CuadroMagicoV2(){
        crearGUI();
        setScene(scene);
        setTitle("Cuadro Magico 2.0");
        show();
    }

    private void crearGUI(){
        splitPaneMain = new SplitPane();
        splitPaneMain.setOrientation(Orientation.HORIZONTAL);
        //IniciarlizarComponentes MenuDatos
        vBoxDatos = new VBox();
        vBoxDatos.setAlignment(Pos.CENTER);
        vBoxDatos.setSpacing(10);
        lblTitulo = new Label("C U A D R O   M Á G I C O");
        txtGrado = new TextField();
        txtGrado.setPromptText("Grado de la matriz");
        btnComenzar = new Button("C O M E N Z A R");
        btnComenzar.getStyleClass().add("warning");
        btnComenzar.setOnAction(event -> hacerCuadro());
        //InicializarComponentes Cuadro Màgico
        gridCuadroMagico = new GridPane();
        gridCuadroMagico.setAlignment(Pos.CENTER);
        gridCuadroMagico.setHgap(2);
        gridCuadroMagico.setVgap(2);
        //AñadirComponentets
        vBoxDatos.getChildren().addAll(lblTitulo,txtGrado,btnComenzar);
        splitPaneMain.getItems().addAll(vBoxDatos,gridCuadroMagico);
        scene = new Scene(splitPaneMain,1000,600);
        scene.getStylesheets().add(getClass().getResource("../resources/CSS/Bootstrap3.css").toExternalForm());
        //Manejo De Archivos
    }

    private void hacerCuadro(){
        hacerArchivo();
        pintarCuadro();
    }
    private void hacerArchivo(){
        if (inicializarBotones()) {
            int posCol = ((gradoCuadro/2)+(1/2));
            int posFil = 0;
            int posFilTemp = posFil;
            int posColTemp = posCol;
            long posicionSeek;
            long bytesFila = gradoCuadro * 4;
            int cont = 1;
            for (int i=0 ; i<(gradoCuadro*gradoCuadro) ; i++){
                /*if (matrizBotones[posCol][posFil].getText().length()!=0){
                    posFil = posFilTemp;
                    posCol = posColTemp;
                    posFil = posFil + 1;
                }*/
                try{
                    posicionSeek = (posFil*bytesFila) + posCol*4;
                    randomAccessFile.seek(posicionSeek);
                    randomAccessFile.writeInt(cont);
                    //matrizBotones[posCol][posFil].setText("0");
                    cont++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                posFilTemp = posFil;
                posColTemp = posCol;
                posCol = posCol + 1;
                posFil = posFil - 1;
                if (posCol == gradoCuadro){
                    posCol = 0;
                }
                if (posFil == -1){
                    posFil = gradoCuadro - 1;
                }
            }
        }
    }
    private boolean inicializarBotones(){
        boolean estadoCuadro = true;
        try{
            gradoCuadro = Integer.parseInt(txtGrado.getText());
            if (!(gradoCuadro%2==0)){
                matrizBotones = new Button[gradoCuadro][gradoCuadro];
                nombreArchivo = "cuadroMagico"+gradoCuadro+".dat";
                randomAccessFile = new RandomAccessFile(nombreArchivo,"rw");
                buttons = new Button[gradoCuadro*gradoCuadro];
                for (int i=0 ; i<gradoCuadro*gradoCuadro ; i++){
                    randomAccessFile.writeInt(0);
                    buttons[i] = new Button();
                }
                /**for (int i=0; i<gradoCuadro ; i++){
                    for (int j=0 ; j<gradoCuadro ; j++){
                        matrizBotones[i][j] = new Button("");
                        matrizBotones[i][j].setId("cuadroMagicoBoton");
                    }
                }*/
            }else
                estadoCuadro = false;
        }catch(NumberFormatException nfe){
            System.out.println("Error al teclear el grado, recuerda que debe ser entero y un nùmero impar");
            estadoCuadro = false;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return estadoCuadro;
    }
    private void pintarCuadro(){
        gridCuadroMagico.getChildren().clear();
        int cont = 0;
        try {
            randomAccessFile.seek(0);
            for (int i = 0; i < gradoCuadro; i++) {
                for (int j = 0; j < gradoCuadro; j++) {
                    buttons[cont].setText(randomAccessFile.readInt() + "");
                    gridCuadroMagico.add(buttons[cont], j, i);
                    cont++;
                }
            }
            /**for (int i = 0; i < gradoCuadro; i++) {
                for (int j = 0; j < gradoCuadro; j++) {
                    matrizBotones[j][i].setText(randomAccessFile.readInt() + "");
                    gridCuadroMagico.add(matrizBotones[j][i], j, i);
                }
            }*/
            randomAccessFile.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
}