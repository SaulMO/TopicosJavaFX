package sample.Restaurante;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Restaurante.Modelos.ConexionDBRestaurante;

public class MenuPrincipal extends Stage implements EventHandler
{
    private Button btnAbrirMesa,btnAdministrador;
    private VBox vBoxMain;
    private Scene scene;

    public MenuPrincipal(){
        crearGUI();
        setScene(scene);
        show();
    }

    private void crearGUI(){
        vBoxMain = new VBox();
        vBoxMain.setAlignment(Pos.CENTER);
        vBoxMain.setSpacing(20);
        btnAbrirMesa = new Button("Abrir Mesa");
        btnAdministrador = new Button ("Administrador");

        btnAbrirMesa.setOnAction(event -> AccionBotones(1));
        btnAdministrador.setOnAction(event -> AccionBotones(2));

        vBoxMain.getChildren().addAll(btnAbrirMesa,btnAdministrador);
        scene = new Scene(vBoxMain);
    }

    private void AccionBotones(int opcion) {
        if (opcion == 1){
            this.close();
        }
    }

    @Override
    public void handle(Event event) {
        ConexionDBRestaurante.getConnection();
        if (ConexionDBRestaurante.connRestaurante != null)
            System.out.println("CONEXIÓN EXITOSA!!");
    }
}
