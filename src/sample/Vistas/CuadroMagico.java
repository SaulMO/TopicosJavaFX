package sample.Vistas;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class CuadroMagico extends Stage
{
    private Scene scene;
    private SplitPane mainPane;
    //Componentes Menu Datos Cuadro Magico
    private VBox menuDatos;
    private Label lblTitulo;
    private Label lblCuadroMagicoFile;
    private Button btnAbrirArchivo;
    private Button btnEmpezar;
    private Label lblResultados;
    //Componentes Menu Cuadro Magico
    private GridPane cuadroMagico;
    private Button[][] casillas;
    private Button diagonalSuma;
    private VBox vBoxCuadroMagico;
    private short grado;
    //Componentes Archivos
    private FileChooser fileChooser;
    private File file;
    private RandomAccessFile randomAccessFile;

    public CuadroMagico(){
        crearGUI();
        setScene(scene);
        setTitle("Cuadro Mágico");
        show();
    }
    private void crearGUI(){
        inicializarComponentes();
        addComponentes();
        scene.getStylesheets().add(getClass().getResource("../resources/CSS/Bootstrap3.css").toExternalForm());
    }

    private void inicializarComponentes(){
        mainPane = new SplitPane();
        mainPane.setOrientation(Orientation.HORIZONTAL);
        // INICIAR COMPONENTES PARA MENU DE DATOS
        menuDatos = new VBox();
        menuDatos.setAlignment(Pos.CENTER);
        menuDatos.setSpacing(20);
        lblTitulo = new Label("C U A D R O   M Á G I C O");
        lblCuadroMagicoFile = new Label("File No Existe");
        lblResultados = new Label();
        btnAbrirArchivo = new Button ("Abrir Archivo");
        btnAbrirArchivo.getStyleClass().add("info");
        btnAbrirArchivo.setOnAction(event -> abrirArchivo());
        btnEmpezar = new Button("Empezar");
        btnEmpezar.setDisable(true);
        btnEmpezar.getStyleClass().add("warning");
        btnEmpezar.setOnAction(event -> hacerCuadro());
        // INICIAR COMPONENTES PARA MENU CUADRO MAGICO;
        vBoxCuadroMagico = new VBox();
        vBoxCuadroMagico.setAlignment(Pos.CENTER);
        cuadroMagico = new GridPane();
        cuadroMagico.setAlignment(Pos.CENTER);
    }
    private void addComponentes(){
        menuDatos.getChildren().addAll(lblTitulo,btnAbrirArchivo,lblCuadroMagicoFile,btnEmpezar,lblResultados);
        vBoxCuadroMagico.getChildren().add(cuadroMagico);
        mainPane.getItems().addAll(menuDatos,vBoxCuadroMagico);
        scene = new Scene(mainPane,1000,600);
    }
    private void abrirArchivo(){
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterDAT = new FileChooser.ExtensionFilter("DAT (*.dat)", "*.dat");
        FileChooser.ExtensionFilter extFilterTXT = new FileChooser.ExtensionFilter("TXT (*.txt)", "*.txt");
        file = fileChooser.showOpenDialog(this);
        if (file!=null && ( file.getName().endsWith("txt") || file.getName().endsWith("dat"))){
            try {
                randomAccessFile = new RandomAccessFile(file,"r");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            btnEmpezar.setDisable(false);
            lblCuadroMagicoFile.setText("File:  "+file.getName());
        }
        lblResultados.setText("");
    }
    private void inicializarCasillas(){
        casillas = new Button[grado+1][grado+1];
        for (int i=0 ; i<=grado ; i++){
            for (int j=0 ; j<=grado ; j++){
                casillas[i][j] = new Button();
                casillas[i][j].setId("cuadroMagicoBoton");
            }
        }
    }
    private void hacerCuadro(){
        cuadroMagico.getChildren().clear();
        int numeros = 0;
        try {
            numeros = (int) randomAccessFile.length()/4;
        } catch (IOException e) { lblResultados.setText("Ocurrio un error\n" +
                                                        "a la hora de leer archivo\n" +
                                                        "favor de ingresar un archivo correcto"); }

        grado = (short) Math.sqrt(numeros);
        if (grado*grado == numeros){
            if (grado%2 != 0){
                inicializarCasillas();
                int posCol = ((grado/2)+(1/2));
                int posFil = 0;
                int posFilTemp = posFil;
                int posColTemp = posCol;
                for (int i=0 ; i<numeros ; i++){
                    if (casillas[posCol][posFil].getText().length()!=0){
                        posFil = posFilTemp;
                        posCol = posColTemp;
                        posFil = posFil + 1;
                    }
                    try {
                        casillas[posCol][posFil].setText(randomAccessFile.readInt()+"");
                    } catch (IOException e) {
                        lblResultados.setText("Ocurrio un error\n" +
                                "a la hora de leer archivo\n" +
                                "favor de ingresar un archivo correcto");
                    }
                    cuadroMagico.add(casillas[posCol][posFil],posCol,posFil);
                    posFilTemp = posFil;
                    posColTemp = posCol;
                    posCol = posCol + 1;
                    posFil = posFil - 1;
                    if (posCol == grado){
                        posCol = 0;
                    }
                    if (posFil == -1){
                        posFil = grado - 1;
                    }
                }
                for (int i=0 ; i<grado ; i++){
                    casillas[i][grado].setText(sumarColumna(i)+"");
                    casillas[grado][i].setText(sumarFila(i)+"");
                    casillas[i][grado].setId("butonResultadoCM");
                    casillas[grado][i].setId("butonResultadoCM");
                    cuadroMagico.add(casillas[i][grado],i,grado);
                    cuadroMagico.add(casillas[grado][i],grado,i);
                }
                diagonalSuma = sumarDiagonales()[1];
                casillas[grado][grado] = sumarDiagonales()[0];
                cuadroMagico.add(casillas[grado][grado],grado,grado);
                casillas[grado][grado].setId("butonResultadoCM");
                diagonalSuma.setId("butonResultadoCM");
                cuadroMagico.add(diagonalSuma,(grado+1),0);
                if (comprobarCuadroMagico()){
                    lblResultados.setText("Prueba Completada...\n" +
                                          "El archivo ingresado\nes un cuadro mágico:\n" +
                                          "Grado: "+grado+"\n" +
                                          "Números: "+numeros+"\n" +
                                          "Suma (diagonales,filas,columnas): "+casillas[grado][grado].getText());
                }else{
                    lblResultados.setText("Prueba Completada...\n" +
                            "El archivo ingresado\nno es un cuadro mágico:");
                }
            }else
                lblResultados.setText("El cuadro que se obtiene\n" +
                                      "No tiene un grado impar\n" +
                                    "Grado: "+grado);
        }else{
            lblResultados.setText("No hay suficientes números\n" +
                              "para llenar un cuadro mágico\n" +
                              "Números: "+numeros+"\n\n" +
                              "O el archivo no es correcto");
        }
        btnEmpezar.setDisable(true);
    }

    private double sumarColumna(int columna){
        double sum = 0;
        for (int i=0 ; i<grado ; i++){
            sum = sum + Double.parseDouble(casillas[columna][i].getText());
        }
        return sum;
    }
    private double sumarFila(int fila){
        double sum = 0;
        for (int i=0 ; i<grado ; i++){
            sum = sum + Double.parseDouble(casillas[i][fila].getText());
        }
        return sum;
    }
    private Button[] sumarDiagonales(){
        Button[] buttons= new Button[2];
        double suma = 0;
        for (int i=0 ; i<grado ; i++){
            suma = suma + Double.parseDouble(casillas[i][i].getText());
        }
        buttons[0] = new Button(""+suma);
        suma = 0;
        int filTemp = grado-1;
        for (int i=0 ; i<grado ; i++){
            suma = suma + Double.parseDouble(casillas[i][filTemp].getText());
            filTemp--;
        }
        buttons[1] = new Button(""+suma);
        return buttons;
    }
    private boolean comprobarCuadroMagico(){
        boolean result = true;
        double numeroComun = Double.parseDouble(casillas[grado][0].getText());
        for (int i=0 ; i<=grado ; i++){
            if (Double.parseDouble(casillas[grado][i].getText())!=numeroComun)
                result = false;
        }
        for (int i=0 ; i<grado ; i++){
            if (Double.parseDouble(casillas[i][grado].getText())!=numeroComun)
                result = false;
        }
        return result;
    }
}