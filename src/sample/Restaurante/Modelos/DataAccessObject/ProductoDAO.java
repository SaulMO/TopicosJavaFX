package sample.Restaurante.Modelos.DataAccessObject;

import sample.Restaurante.Modelos.ConexionDBRestaurante;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Restaurante.ProductoSt;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProductoDAO {
    private String idProducto;
    private String idCategoria;
    private String descripcion;
    private String nombre;
    private float precio;
    private String nombreCategoria;
    private int cantidad;

    public ProductoDAO(){}
    public ProductoDAO(String idProducto,String nombre,String descripcion,float precio){
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
    }
    public ProductoDAO(String idProducto,String idCategoria,String nombre,String descripcion,float precio){
        this.idProducto = idProducto;
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }
    public ProductoDAO(String idProducto,int cantidad){
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        SELECCIONPedidos();
    }
    //Setter
    public void setIdProducto(String idProducto) { this.idProducto = idProducto; }
    public void setIdCategoria(String idCategoria) { this.idCategoria = idCategoria; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPrecio(float precio) { this.precio = precio; }
    public void setNombreCategoria(String nombreCategoria){ this.nombreCategoria = nombreCategoria;}
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    //Getter
    public String getIdProducto() { return idProducto; }
    public String getIdCategoria() { return idCategoria; }
    public String getDescripcion() { return descripcion; }
    public String getNombre() { return nombre; }
    public float getPrecio() { return precio; }
    public String getNombreCategoria(){ return nombreCategoria; }
    public int getCantidad(){ return cantidad; }

    /*CRUD*/
    public ObservableList<ProductoDAO> SELECCIONAR_VistaMenu(String vista){
        ObservableList<ProductoDAO> vistaMenu = FXCollections.observableArrayList();
        ProductoDAO productoDAO = null;
        String query = "SELECT * FROM Vista"+vista+" ORDER BY nombre";
        try {
            Statement stmt = ConexionDBRestaurante.connRestaurante.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()){
                productoDAO = new ProductoDAO(resultSet.getString("idProducto"),
                                            resultSet.getString("nombre"),
                                            resultSet.getString("descripcion"),
                                            resultSet.getFloat("Precio"));
                vistaMenu.add(productoDAO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vistaMenu;
    }
    public ObservableList<ProductoDAO> SELECCIONAR(){
        ObservableList<ProductoDAO> seleccion = FXCollections.observableArrayList();
        ProductoDAO productoDAO = null;
        String query = "SELECT * FROM Productos ORDER BY idCategoria";
        try {
            Statement stmt = ConexionDBRestaurante.connRestaurante.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()){
                productoDAO = new ProductoDAO(resultSet.getString("idProducto"),
                        resultSet.getString("idCategoria"),
                        resultSet.getString("nombre"),
                        resultSet.getString("descripcion"),
                        resultSet.getFloat("precio"));
                seleccion.add(productoDAO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seleccion;
    }
    public void INSERTAR(){
        String query = "INSERT INTO Productos(idProducto,idCategoria,descripcion,nombre,precio) " +
                "values('"+idProducto+"','"+idCategoria+"','"+descripcion+"','"+nombre+"',"+precio+")";
        try {
            Statement stmt = ConexionDBRestaurante.connRestaurante.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void ACTUALIZAR(){
        String consulta = "UPDATE Productos SET idCategoria='"+idCategoria+"'," +
                "descripcion='"+descripcion+"',nombre='"+nombre+"',precio="+precio+" WHERE" +
                " idProducto = '"+idProducto+"'";
        try {
            Statement stmt = ConexionDBRestaurante.connRestaurante.createStatement();
            stmt.executeUpdate(consulta);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void ELIMINAR(){
        String consulta = "DELETE FROM Productos WHERE idProducto = '"+idProducto+"'";
        try {
            Statement stmt = ConexionDBRestaurante.connRestaurante.createStatement();
            stmt.executeUpdate(consulta);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void SELECCIONINDIVIDUAL(){
        String query = "SELECT p.*,(c.descripcion)des FROM Productos p INNER JOIN Categorias c ON p.idCategoria=c.idCategoria " +
                "WHERE idProducto='"+ProductoSt.idProducto+"'";

        try {
            Statement statement = ConexionDBRestaurante.connRestaurante.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                idProducto = resultSet.getString("idProducto");
                idCategoria = resultSet.getString("idCategoria");
                descripcion = resultSet.getString("descripcion");
                nombre = resultSet.getString("nombre");
                precio = resultSet.getFloat("precio");
                nombreCategoria = resultSet.getString("des");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void SELECCIONPedidos(){
        String query = "SELECT p.*,(c.descripcion)des FROM Productos p INNER JOIN Categorias c ON p.idCategoria=c.idCategoria " +
                "WHERE idProducto='"+idProducto+"'";

        try {
            Statement statement = ConexionDBRestaurante.connRestaurante.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                idProducto = resultSet.getString("idProducto");
                idCategoria = resultSet.getString("idCategoria");
                descripcion = resultSet.getString("descripcion");
                nombre = resultSet.getString("nombre");
                precio = resultSet.getFloat("precio");
                nombreCategoria = resultSet.getString("des");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> SELECCIONAR_Categorias(){
        ArrayList<String> categorias = new ArrayList<>();
        String query = "SELECT descripcion FROM Categorias";
        try{
            Statement statement = ConexionDBRestaurante.connRestaurante.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                categorias.add(resultSet.getString("descripcion"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorias;
    }
    public String SELECCIONIdCategoria(String descripcion){
        String idCategoria = null;
        String query = "SELECT idCategoria FROM Categorias WHERE descripcion='"+descripcion+"'";
        try{
            Statement statement = ConexionDBRestaurante.connRestaurante.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                idCategoria = resultSet.getString("idCategoria");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idCategoria;
    }
    public String SELECCIONDescripcionCategoria(){
        String categoria = null;
        String query = "SELECT descripcion FROM Categorias WHERE idCategoria='"+idCategoria+"'";
        try{
            Statement statement = ConexionDBRestaurante.connRestaurante.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                categoria = resultSet.getString("descripcion");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoria;
    }
    public String[] getAdmin(){
        String[] admin = new String[2];
        String query = "SELECT * FROM Usuarios";
        try{
            Statement statement = ConexionDBRestaurante.connRestaurante.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                admin[0] = resultSet.getString("usuario");
                admin[1] = resultSet.getString("contraseña");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }

    public ArrayList<String> SELECCIONProductos(){
        ArrayList<String> productos = new ArrayList<>();
        try{
            Statement statement = ConexionDBRestaurante.connRestaurante.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT nombre FROM Productos");
            while (resultSet.next()){
                productos.add(resultSet.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  productos;
    }
    public String getIDPRODUCTO(String nombre){
        String id = "";
        try{
            Statement statement = ConexionDBRestaurante.connRestaurante.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT idProducto FROM Productos WHERE nombre = '"+nombre+"'");
            while (resultSet.next()){
                id = resultSet.getString("idProducto");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}