package sample.Restaurante.Vistas;

import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;
import sample.Restaurante.Modelos.ConexionDBRestaurante;
import sample.Restaurante.Modelos.DataAccessObject.ProductoDAO;

import java.sql.SQLException;

public class Loggeo extends Stage
{
    private Scene scene;
    private VBox vBoxMain;
    private HBox hBoxFinal;
    private Button btnCancelar,btnEntrar;
    private TextField txtFieldNombre;
    private PasswordField passwordField;

    private Stage stage;
    private byte opcion;
    private Mesa mesa;

    public Loggeo(Stage stage,byte opcion,Mesa mesa){
        this.mesa = mesa;
        this.stage = mesa;
        this.opcion = opcion;
        crearGUI();
        this.initStyle(StageStyle.UNDECORATED);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setScene(scene);
        this.show();
        stage.setOpacity(0.89);
    }

    public void crearGUI(){
        vBoxMain = new VBox();
        vBoxMain.setId("paneLogging");
        vBoxMain.setAlignment(Pos.CENTER);
        vBoxMain.setSpacing(10);
        hBoxFinal = new HBox();
        hBoxFinal.setAlignment(Pos.CENTER);
        hBoxFinal.setSpacing(10);
        btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(event -> Eventos(0));
        btnCancelar.setId("btn");
        btnEntrar = new Button("Aceptar");
        btnEntrar.setOnAction(event -> Eventos(1));
        btnEntrar.setId("btn");
        txtFieldNombre = new TextField();
        txtFieldNombre.setPromptText("U s u a r i o");
        passwordField = new PasswordField();
        passwordField.setPromptText("P a s s w o r d");
        hBoxFinal.getChildren().addAll(btnCancelar,btnEntrar);
        vBoxMain.getChildren().addAll(txtFieldNombre,passwordField,hBoxFinal);
        scene = new Scene(vBoxMain);
        scene.getStylesheets().add(getClass().getResource("../resources/estiloRestaurante.css").toExternalForm());
    }

    private void Eventos(int opcion){
        try {
            if (ConexionDBRestaurante.connRestaurante.isClosed())
                ConexionDBRestaurante.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        switch(opcion){
            case 0: //Cancelar
                this.stage.setOpacity(1);
                this.close();
                break;
            case 1: //Aceptar
                if (txtFieldNombre.getText().equals(new ProductoDAO().getAdmin()[0]) && passwordField.getText().equals(new ProductoDAO().getAdmin()[1])){
                    switch(this.opcion){
                        case 0: //Administracion
                            new Dashboard();
                            this.stage.setOpacity(1);
                            this.close();
                            break;
                        case 1: //Cancelar Compra
                            this.stage.setOpacity(1);
                            mesa.cancelarCompra();
                            this.close();
                            break;
                    }
                }else{
                    alerta();
                }
                break;
        }
    }

    private void alerta(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText("El usuario o la contraseña son incorrectos");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initStyle(StageStyle.UNDECORATED);
        txtFieldNombre.setText("");
        passwordField.setText("");
        alert.showAndWait();
    }
}