package sample.Restaurante.Modelos.DataAccessObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Restaurante.Modelos.ConexionDBRestaurante;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VentasIndividualesDAO
{
    private String idProducto;
    private int noVenta;
    private int cantidad;
    private float precioProducto;
    private String nombreProducto;

    public VentasIndividualesDAO(){ }
    public VentasIndividualesDAO(String idProducto,int noVenta,int cantidad){
        this.idProducto = idProducto;
        this.noVenta = noVenta;
        this.cantidad = cantidad;
    }
    public VentasIndividualesDAO(String idProducto,int noVenta,int cantidad,float precioProducto){
        this.idProducto = idProducto;
        this.noVenta = noVenta;
        this.cantidad = cantidad;
        this.precioProducto = precioProducto;
    }
    public VentasIndividualesDAO(String idProducto,String nombreProducto,int noVenta,float precioProducto){
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.noVenta = noVenta;
        this.precioProducto = precioProducto;
    }

    public String getNombreProducto(){return nombreProducto;}
    public String getIdProducto() { return idProducto; }
    public int getNoVenta() { return noVenta; }
    public int getCantidad() { return cantidad; }
    public float getPrecioProducto(){return precioProducto;}

    public void setPrecioProducto(float precioProducto){this.precioProducto = precioProducto;}
    public void setNombreProducto(String nombreProducto){ this.nombreProducto=nombreProducto;}
    public void setIdProducto(String idProducto) { this.idProducto = idProducto; }
    public void setNoVenta(int noVenta) { this.noVenta = noVenta; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public ObservableList<VentasIndividualesDAO> SELECCION(){
        ObservableList<VentasIndividualesDAO> seleccion = FXCollections.observableArrayList();
        String query = "SELECT vi.*,p.nombre FROM VentasIndividuales vi INNER JOIN Productos p ON vi.idProducto=p.idProducto";
        try {
            Statement stmt = ConexionDBRestaurante.connRestaurante.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            VentasIndividualesDAO objeto;
            while (resultSet.next()) {
                cantidad = resultSet.getInt("cantidad");
                objeto = new VentasIndividualesDAO(resultSet.getString("idProducto"),
                        resultSet.getString("nombre"),
                        resultSet.getInt("noVenta"),
                        resultSet.getFloat("precioProducto"));
                objeto.setCantidad(cantidad);
                seleccion.add(objeto);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return seleccion;
    }
    public void INSERTAR(){
        String query = "INSERT INTO VentasIndividuales(idProducto,noVenta,cantidad,precioProducto) " +
                "VALUES('"+idProducto+"',"+noVenta+","+cantidad+","+precioProducto+")";
        try {
            Statement stmt = ConexionDBRestaurante.connRestaurante.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void ELIMINARVenta(int noVenta){
        String query = "DELETE FROM VentasIndividuales WHERE noVenta = "+noVenta;
        try {
            Statement stmt = ConexionDBRestaurante.connRestaurante.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void ELIMINARVentaIndividual(){
        double total;
        String query;
        if (cantidad > 1){
            cantidad--;
            query = "UPDATE VentasIndividuales SET cantidad="+cantidad+" WHERE idProducto='"+idProducto+"' AND noVenta="+noVenta;
        }else {
            query = "DELETE FROM VentasIndividuales WHERE idProducto='" + idProducto + "' AND noVenta = "+noVenta;
        }
        try {
            Statement stmt = ConexionDBRestaurante.connRestaurante.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        total = SELECT_TOTAL(noVenta);
        System.out.println(total);
        String queryb = "UPDATE Ventas SET total="+total+" WHERE" +
                " noVenta = "+noVenta;
        try{
            Statement statement = ConexionDBRestaurante.connRestaurante.createStatement();
            statement.executeUpdate(queryb);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public double SELECT_TOTAL(int noVenta){
        String query = "SELECT SUM(vi.precioProducto * vi.cantidad)total FROM " +
                "VentasIndividuales vi " +
                "WHERE vi.noVenta = "+noVenta;
        double total = 0;
        try{
            Statement statement = ConexionDBRestaurante.connRestaurante.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                total = resultSet.getFloat("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }
    public double SELECTPrecioTotalXProd(){
        double a = 0;
        String query = "SELECT (vi.precioProducto * vi.cantidad)total " +
                "FROM VentasIndividuales vi " +
                "WHERE vi.idProducto = '"+idProducto+"';";

        try{
            Statement statement = ConexionDBRestaurante.connRestaurante.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                a = resultSet.getFloat("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }
    public String SELECTNameProducto(){
        String a = "";
        String query = "SELECT p.nombre " +
                "FROM VentasIndividuales vi INNER JOIN Productos p ON vi.idProducto = p.idProducto " +
                "WHERE vi.idProducto = '"+idProducto+"';";

        try{
            Statement statement = ConexionDBRestaurante.connRestaurante.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                a = resultSet.getString("nombre");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }
}