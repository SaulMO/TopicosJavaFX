package sample.Modelos.DataAccessObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Modelos.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CategoriaDAO
{
    private int idCategoria;
    private String nombreCategoria;

    public CategoriaDAO(int idCategoria,String nombreCategoria){
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
    }
    public CategoriaDAO(){}

    public int getIdCategoria() { return idCategoria; }
    public String getNombreCategoria() { return nombreCategoria; }

    public void setIdCategoria(int idCategoria) { this.idCategoria = idCategoria; }
    public void setNombreCategoria(String nombreCategoria) { this.nombreCategoria = nombreCategoria; }

    public ObservableList<CategoriaDAO> SELECCIONAR(){
        ObservableList<CategoriaDAO> lista = FXCollections.observableArrayList();
        CategoriaDAO categoriaDAO = null;
        String query = "SELECT * FROM Categoria ORDER BY nombreCategoria";
        try {
            Statement stmt = Conexion.conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()){
                categoriaDAO = new CategoriaDAO();
                categoriaDAO.idCategoria  = resultSet.getInt("idCategoria");
                categoriaDAO.nombreCategoria  = resultSet.getString("nombreCategoria");
                lista.add(categoriaDAO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}