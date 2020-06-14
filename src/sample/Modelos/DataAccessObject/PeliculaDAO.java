package sample.Modelos.DataAccessObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Modelos.Conexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PeliculaDAO
{
    private int idPelicula;
    private String nombre;
    private int duracion;
    private String sipnosis;
    private String clase;
    private int idCategoria;

    public PeliculaDAO(int idPelicula, String nombre, int duracion, String sipnosis, String clase, int idCategoria) {
        this.idPelicula = idPelicula;
        this.nombre = nombre;
        this.duracion = duracion;
        this.sipnosis = sipnosis;
        this.clase = clase;
        this.idCategoria = idCategoria;
    }
    public PeliculaDAO(){}

    public int getIdPelicula() { return idPelicula; }
    public String getNombre() { return nombre; }
    public int getDuracion() { return duracion; }
    public String getSipnosis() { return sipnosis; }
    public String getClase() { return clase; }
    public int getIdCategoria() { return idCategoria; }

    public void setIdPelicula(int idPelicula) {
        this.idPelicula = idPelicula;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
    public void setSipnosis(String sipnosis) {
        this.sipnosis = sipnosis;
    }
    public void setClase(String clase) {
        this.clase = clase;
    }
    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public void INSERTAR(){
        String query = "insert into Peliculas(nombre,duracion,sipnosis,clase,idCategoria) " +
                "values('"+nombre+"',"+duracion+",'"+sipnosis+"','"+clase+"',"+idCategoria+")";
        try {
            Statement stmt = Conexion.conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void ACTUALIZAR(){
        /*try {
            String query = "UPDATE Peliculas "
                    + " SET nombre = ?, duracion = ?, sipnosis = ?, clase = ?, idCategoria = ?"
                    + " WHERE idPelicula = ?";
            PreparedStatement st = Conexion.conn.prepareStatement(query);

            st.setString(1, nombre);
            st.setInt(2, duracion);
            st.setString(3, sipnosis);
            st.setString(4, clase);
            st.setInt(5, idCategoria);
            st.execute();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }*/


        String consulta = "UPDATE Peliculas SET nombre='"+nombre+"', duracion="+duracion+"," +
                "sipnosis='"+sipnosis+"',clase='"+clase+"',idCategoria="+idCategoria+" WHERE" +
                " idPelicula = "+idPelicula;
        try {
            Statement stmt = Conexion.conn.createStatement();
            stmt.executeUpdate(consulta);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void ELIMINAR(){
        String consulta = "DELETE FROM Peliculas WHERE idPelicula = "+idPelicula;
        try {
            Statement stmt = Conexion.conn.createStatement();
            stmt.executeUpdate(consulta);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //crud Create, Read, Update, Delete
    public ObservableList<PeliculaDAO> SELECCIONAR(){
        ObservableList<PeliculaDAO> lista = FXCollections.observableArrayList();
        PeliculaDAO objPDAO = null;
        String query = "SELECT * FROM Peliculas ORDER BY nombre";
        try {
            Statement stmt = Conexion.conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()){
                objPDAO = new PeliculaDAO();
                objPDAO.idPelicula   = resultSet.getInt("idPelicula");
                objPDAO.nombre  = resultSet.getString("nombre");
                objPDAO.duracion     = resultSet.getInt("duracion");
                objPDAO.sipnosis = resultSet.getString("sipnosis");
                objPDAO.clase        = resultSet.getString("clase");
                objPDAO.idCategoria  = resultSet.getInt("idCategoria");
                lista.add(objPDAO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}