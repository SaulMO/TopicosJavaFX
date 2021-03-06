package sample.Restaurante.Modelos;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDBRestaurante
{
    public static Connection connRestaurante = null;

    public static void getConnection(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://LAPTOP-ADP81M5S:1433;databaseName=Restaurante";
            connRestaurante = DriverManager.getConnection(url,"sa","muse1997");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            alertError();
        } catch (SQLException e) {
            e.printStackTrace();
            alertError();
        }
    }

    public static void  Disconnect(){
        try {
            connRestaurante.close();
            System.out.println("Se termino la conexion a la base de datos");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void alertError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText("ERROR 404... No se pudo conectar a la base de datos Mexflix");
        alert.showAndWait();
    }
}