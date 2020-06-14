package sample.Restaurante.Vistas;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import sample.Restaurante.Modelos.ConexionDBRestaurante;
import sample.Restaurante.Modelos.DataAccessObject.ProductoDAO;
import sample.Restaurante.Modelos.DataAccessObject.VentasDAO;
import sample.Restaurante.Modelos.DataAccessObject.VentasIndividualesDAO;

import java.net.URL;
import java.util.ResourceBundle;

public class controllerIngresar implements Initializable {
    @FXML Button update,add;
    @FXML TextField cant,no;
    @FXML ComboBox<String> combo;
    VentasIndividualesDAO ventasIndividualesDAO = new VentasIndividualesDAO();
    VentasDAO ventasDAO = new VentasDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ConexionDBRestaurante.getConnection();
        update.setOnAction(event -> Eventos(0));
        add.setOnAction(event -> Eventos(1));
        combo.getItems().addAll(new ProductoDAO().SELECCIONProductos());
    }
    private void Eventos(int i){
        switch (i){
            case 0://update
                ventasDAO.setTotal((float) new VentasIndividualesDAO().SELECT_TOTAL(Integer.parseInt(no.getText())));
                ventasDAO.setNoVenta(Integer.parseInt(no.getText()));
                ventasDAO.ACTUALIZAR_Total();
                break;
            case 1://add
                ventasIndividualesDAO.setIdProducto(new ProductoDAO().getIDPRODUCTO(combo.getValue()));
                ventasIndividualesDAO.setCantidad(Integer.parseInt(cant.getText()));
                ventasIndividualesDAO.setNoVenta(Integer.parseInt(no.getText()));
                ventasIndividualesDAO.INSERTAR();
                break;
        }
    }
}