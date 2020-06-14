package sample.Restaurante.Modelos.DataAccessObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import sample.Restaurante.Modelos.ConexionDBRestaurante;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class VentasDAO
{
    private int noVenta;
    private Date fechaVenta;
    private float total;

    public VentasDAO(){}
    public VentasDAO(int noVenta,Date fechaVenta,float total){
        this.noVenta = noVenta;
        this.fechaVenta = fechaVenta;
        this.total = total;
    }

    public int getNoVenta() { return noVenta; }
    public Date getFechaVenta() { return fechaVenta; }
    public float getTotal() { return total; }

    public void setNoVenta(int noVenta) { this.noVenta = noVenta; }
    public void setFechaVenta(Date fechaVenta) { this.fechaVenta = fechaVenta; }
    public void setTotal(float total) { this.total = total; }

    public void INSERT(){
        try{
            String query = "INSERT INTO Ventas "
                    + " (fechaVenta)"
                    + " VALUES (?)";
            PreparedStatement st = ConexionDBRestaurante.connRestaurante.prepareStatement(query);
            st.setDate(1,fechaVenta);
            st.execute();
        }catch (SQLException sqle){
            sqle.printStackTrace();
            System.out.println("Error al introducir ventas");
        }
    }
    public int SELECT_ProximoNoVenta(){
        int noVentas = 0;
        String query = "SELECT IDENT_CURRENT('Ventas') + IDENT_INCR('Ventas')ventas";
        try{
            Statement statement = ConexionDBRestaurante.connRestaurante.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                noVentas = resultSet.getInt("ventas");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return noVentas;
    }
    public VentasDAO SELECCION(){
        String query = "SELECT * FROM Ventas WHERE noVenta = "+noVenta;
        VentasDAO ventasDAO = null;
        try{
            Statement statement = ConexionDBRestaurante.connRestaurante.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                ventasDAO = new VentasDAO(resultSet.getInt("noVenta"),
                        resultSet.getDate("fechaVenta"),
                        resultSet.getFloat("total"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ventasDAO;
    }
    public void ELIMINAR(){
        new VentasIndividualesDAO().ELIMINARVenta(noVenta);
        String query = "DELETE FROM Ventas WHERE noVenta = "+noVenta;
        try {
            Statement stmt = ConexionDBRestaurante.connRestaurante.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ObservableList<VentasDAO> SELECCIONAR(){
        ObservableList<VentasDAO> seleccion = FXCollections.observableArrayList();
        String query = "SELECT * FROM Ventas ORDER BY noVenta";
        VentasDAO objeto = null;
        try {
            Statement stmt = ConexionDBRestaurante.connRestaurante.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                objeto = new VentasDAO(resultSet.getInt("noVenta"),
                        resultSet.getDate("fechaVenta"),
                        resultSet.getFloat("total"));
                seleccion.add(objeto);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(seleccion.size());
        return seleccion;
    }
    public void ACTUALIZAR_Total(){
        String query = "UPDATE Ventas SET total="+total+" WHERE" +
                " noVenta = "+noVenta;

        try{
            Statement statement = ConexionDBRestaurante.connRestaurante.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ObservableList<XYChart.Series<String,Number>> SELECCION_VENTAS_DEL_DIA(){
        int entradas=0,platillos=0,bebidas=0,postres=0;
        String queryEntries = "SELECT SUM(vi.cantidad)vendido FROM VentasIndividuales vi INNER JOIN " +
                "VistaEntradas ve ON vi.idProducto = ve.idProducto INNER JOIN " +
                "Ventas v ON vi.noVenta = v.noVenta WHERE v.fechaVenta = '"+new java.sql.Date(new java.util.Date().getTime())+"'";
        String queryDishes = "SELECT SUM(vi.cantidad)vendido FROM VentasIndividuales vi INNER JOIN " +
                "VistaPlatillos vp ON vi.idProducto = vp.idProducto INNER JOIN " +
                "Ventas v ON vi.noVenta = v.noVenta WHERE v.fechaVenta = '"+new java.sql.Date(new java.util.Date().getTime())+"'";
        String queryDrinks = "SELECT SUM(vi.cantidad)vendido FROM VentasIndividuales vi INNER JOIN " +
                "VistaBebidas vb ON vi.idProducto = vb.idProducto INNER JOIN " +
                "Ventas v ON vi.noVenta = v.noVenta WHERE v.fechaVenta = '"+new java.sql.Date(new java.util.Date().getTime())+"'";
        String queryDesserts = "SELECT SUM(vi.cantidad)vendido FROM VentasIndividuales vi INNER JOIN " +
                "VistaPostres vpo ON vi.idProducto = vpo.idProducto INNER JOIN " +
                "Ventas v ON vi.noVenta = v.noVenta WHERE v.fechaVenta = '"+new java.sql.Date(new java.util.Date().getTime())+"'";
        XYChart.Series<String,Number> ventas = new XYChart.Series<>();
        ventas.setName("Ventas Del Dia");
        try{
            Statement stmtEntries = ConexionDBRestaurante.connRestaurante.createStatement();
            Statement stmtDishes = ConexionDBRestaurante.connRestaurante.createStatement();
            Statement stmtDrinks = ConexionDBRestaurante.connRestaurante.createStatement();
            Statement stmtDesserts = ConexionDBRestaurante.connRestaurante.createStatement();
            ResultSet resultSetEntries = stmtEntries.executeQuery(queryEntries);
            ResultSet resultSetDishes = stmtDishes.executeQuery(queryDishes);
            ResultSet resultSetDrinks = stmtDrinks.executeQuery(queryDrinks);
            ResultSet resultSetDesserts = stmtDesserts.executeQuery(queryDesserts);
            while(resultSetEntries.next()&&resultSetDishes.next()&&resultSetDrinks.next()&&resultSetDesserts.next()){
                entradas = resultSetEntries.getInt("vendido");
                platillos = resultSetDishes.getInt("vendido");
                bebidas = resultSetDrinks.getInt("vendido");
                postres = resultSetDesserts.getInt("vendido");
                ventas.getData().addAll(
                        new XYChart.Data<>("Entradas "+entradas,entradas),
                        new XYChart.Data<>("Platillos "+platillos,platillos),
                        new XYChart.Data<>("Bebidas "+bebidas,bebidas),
                        new XYChart.Data<>("Postres "+postres,postres));
            }
        }catch (SQLException sqlE){
            sqlE.printStackTrace();
        }
        ObservableList<XYChart.Series<String,Number>> data = FXCollections.observableArrayList();
        data.add(ventas);
        return data;
    }
    public ObservableList<ProductoDAO> SELECCION_PEDIDOS_DEL_DIA(){
        ObservableList<ProductoDAO> lista = FXCollections.observableArrayList();
        ProductoDAO objeto = null;
        String query = "SELECT vi.cantidad,vi.precioProducto,p.* FROM Productos p INNER JOIN VentasIndividuales vi ON p.idProducto=vi.idProducto " +
                "INNER JOIN Ventas v ON vi.noVenta=v.noVenta " +
                "WHERE v.fechaVenta='"+new java.sql.Date(new java.util.Date().getTime())+"'";
        try{
            Statement statement = ConexionDBRestaurante.connRestaurante.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                objeto = new ProductoDAO(resultSet.getString("idProducto"),
                        resultSet.getString("idCategoria"),
                        resultSet.getString("nombre"),
                        resultSet.getString("descripcion"),
                        resultSet.getFloat("precioProducto"));
                for (int i=0 ; i<resultSet.getInt("cantidad") ; i++){
                    lista.add(objeto);
                }
            }
        }catch(SQLException sqlE){
            sqlE.printStackTrace();
        }
        return lista;
    }
    public ObservableList<XYChart.Series<String,Number>> SELECCION_VENTAS_SEMANA_ACTUAL_ANTERIOR(){
        int entradas=0,platillos=0,bebidas=0,postres=0;
        Calendar fechaA = Calendar.getInstance();
        Calendar fechaB = Calendar.getInstance();
        if (Calendar.DAY_OF_WEEK != 2)
            fechaA.add(fechaA.DAY_OF_YEAR,-(fechaA.get(Calendar.DAY_OF_WEEK) + 7) + 2 );
        else
            fechaA.add(fechaA.DAY_OF_YEAR,- 7 );
        fechaB.setTime(fechaA.getTime());
        fechaB.add(Calendar.DAY_OF_YEAR,+ 6);
        String queryEntries = "SELECT SUM(vi.cantidad)vendido FROM VentasIndividuales vi INNER JOIN " +
                "VistaEntradas ve ON vi.idProducto = ve.idProducto INNER JOIN " +
                "Ventas v ON vi.noVenta = v.noVenta WHERE v.fechaVenta BETWEEN '"+new java.sql.Date(fechaA.getTimeInMillis())+"' AND '"+new java.sql.Date(fechaB.getTimeInMillis())+"'";
        String queryDishes = "SELECT SUM(vi.cantidad)vendido FROM VentasIndividuales vi INNER JOIN " +
                "VistaPlatillos vp ON vi.idProducto = vp.idProducto INNER JOIN " +
                "Ventas v ON vi.noVenta = v.noVenta WHERE v.fechaVenta BETWEEN '"+new java.sql.Date(fechaA.getTimeInMillis())+"' AND '"+new java.sql.Date(fechaB.getTimeInMillis())+"'";
        String queryDrinks = "SELECT SUM(vi.cantidad)vendido FROM VentasIndividuales vi INNER JOIN " +
                "VistaBebidas vb ON vi.idProducto = vb.idProducto INNER JOIN " +
                "Ventas v ON vi.noVenta = v.noVenta WHERE v.fechaVenta BETWEEN '"+new java.sql.Date(fechaA.getTimeInMillis())+"' AND '"+new java.sql.Date(fechaB.getTimeInMillis())+"'";
        String queryDesserts = "SELECT SUM(vi.cantidad)vendido FROM VentasIndividuales vi INNER JOIN " +
                "VistaPostres vpo ON vi.idProducto = vpo.idProducto INNER JOIN " +
                "Ventas v ON vi.noVenta = v.noVenta WHERE v.fechaVenta BETWEEN '"+new java.sql.Date(fechaA.getTimeInMillis())+"' AND '"+new java.sql.Date(fechaB.getTimeInMillis())+"'";
        XYChart.Series<String,Number> ventasSemanaPasada = new XYChart.Series<>();
        ventasSemanaPasada.setName("SemanaPasada");
        XYChart.Series<String,Number> ventasSemanaActual = new XYChart.Series<>();
        ventasSemanaActual.setName("SemanaActual");
        try{
            Statement stmtEntries = ConexionDBRestaurante.connRestaurante.createStatement();
            Statement stmtDishes = ConexionDBRestaurante.connRestaurante.createStatement();
            Statement stmtDrinks = ConexionDBRestaurante.connRestaurante.createStatement();
            Statement stmtDesserts = ConexionDBRestaurante.connRestaurante.createStatement();
            ResultSet resultSetEntries = stmtEntries.executeQuery(queryEntries);
            ResultSet resultSetDishes = stmtDishes.executeQuery(queryDishes);
            ResultSet resultSetDrinks = stmtDrinks.executeQuery(queryDrinks);
            ResultSet resultSetDesserts = stmtDesserts.executeQuery(queryDesserts);
            while(resultSetEntries.next()&&resultSetDishes.next()&&resultSetDrinks.next()&&resultSetDesserts.next()){
                entradas = resultSetEntries.getInt("vendido");
                platillos = resultSetDishes.getInt("vendido");
                bebidas = resultSetDrinks.getInt("vendido");
                postres = resultSetDesserts.getInt("vendido");
                ventasSemanaPasada.getData().addAll(
                        new XYChart.Data<>("Entradas "+entradas,entradas),
                        new XYChart.Data<>("Platillos "+platillos,platillos),
                        new XYChart.Data<>("Bebidas "+bebidas,bebidas),
                        new XYChart.Data<>("Postres "+postres,postres));
            }
            fechaA.add(Calendar.DAY_OF_YEAR,+ 7);
            fechaB.add(Calendar.DAY_OF_YEAR,+ 7);
            queryEntries = "SELECT SUM(vi.cantidad)vendido FROM VentasIndividuales vi INNER JOIN " +
                    "VistaEntradas ve ON vi.idProducto = ve.idProducto INNER JOIN " +
                    "Ventas v ON vi.noVenta = v.noVenta WHERE v.fechaVenta BETWEEN '"+new java.sql.Date(fechaA.getTimeInMillis())+"' AND '"+new java.sql.Date(fechaB.getTimeInMillis())+"'";
            queryDishes = "SELECT SUM(vi.cantidad)vendido FROM VentasIndividuales vi INNER JOIN " +
                    "VistaPlatillos vp ON vi.idProducto = vp.idProducto INNER JOIN " +
                    "Ventas v ON vi.noVenta = v.noVenta WHERE v.fechaVenta BETWEEN '"+new java.sql.Date(fechaA.getTimeInMillis())+"' AND '"+new java.sql.Date(fechaB.getTimeInMillis())+"'";
            queryDrinks = "SELECT SUM(vi.cantidad)vendido FROM VentasIndividuales vi INNER JOIN " +
                    "VistaBebidas vb ON vi.idProducto = vb.idProducto INNER JOIN " +
                    "Ventas v ON vi.noVenta = v.noVenta WHERE v.fechaVenta BETWEEN '"+new java.sql.Date(fechaA.getTimeInMillis())+"' AND '"+new java.sql.Date(fechaB.getTimeInMillis())+"'";
            queryDesserts = "SELECT SUM(vi.cantidad)vendido FROM VentasIndividuales vi INNER JOIN " +
                    "VistaPostres vpo ON vi.idProducto = vpo.idProducto INNER JOIN " +
                    "Ventas v ON vi.noVenta = v.noVenta WHERE v.fechaVenta BETWEEN '"+new java.sql.Date(fechaA.getTimeInMillis())+"' AND '"+new java.sql.Date(fechaB.getTimeInMillis())+"'";
            resultSetEntries = stmtEntries.executeQuery(queryEntries);
            resultSetDishes = stmtDishes.executeQuery(queryDishes);
            resultSetDrinks = stmtDrinks.executeQuery(queryDrinks);
            resultSetDesserts = stmtDesserts.executeQuery(queryDesserts);
            while(resultSetEntries.next()&&resultSetDishes.next()&&resultSetDrinks.next()&&resultSetDesserts.next()){
                entradas = resultSetEntries.getInt("vendido");
                platillos = resultSetDishes.getInt("vendido");
                bebidas = resultSetDrinks.getInt("vendido");
                postres = resultSetDesserts.getInt("vendido");
                ventasSemanaActual.getData().addAll(
                        new XYChart.Data<>("Entradas "+entradas,entradas),
                        new XYChart.Data<>("Platillos "+platillos,platillos),
                        new XYChart.Data<>("Bebidas "+bebidas,bebidas),
                        new XYChart.Data<>("Postres "+postres,postres));
            }
        }catch (SQLException sqlE){
            sqlE.printStackTrace();
        }
        ObservableList<XYChart.Series<String,Number>> data = FXCollections.observableArrayList();
        data.addAll(ventasSemanaActual,ventasSemanaPasada);
        return data;
    }
    public ObservableList<PieChart.Data> SELECCION_PIECHART(){
        DecimalFormat df = new DecimalFormat("#.00");
        double total;
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        Calendar fechaA = Calendar.getInstance();
        Calendar fechaB = Calendar.getInstance();
        if (Calendar.DAY_OF_WEEK != 2)
            fechaA.add(fechaA.DAY_OF_YEAR,-(fechaA.get(Calendar.DAY_OF_WEEK) + 7) + 2 );
        else
            fechaA.add(fechaA.DAY_OF_YEAR,- 7 );
        fechaB.setTime(fechaA.getTime());
        fechaB.add(Calendar.DAY_OF_YEAR,+ 6);
        String query = "SELECT SUM(total)total FROM Ventas WHERE fechaVenta BETWEEN " +
                "'"+new java.sql.Date(fechaA.getTimeInMillis())+"' AND '"+new java.sql.Date(fechaB.getTimeInMillis())+"'";

        try{
            Statement statement = ConexionDBRestaurante.connRestaurante.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                total = resultSet.getFloat("total");
                pieChartData.add(new PieChart.Data("SemanaPasada = $"+df.format(total),total));
            }
            fechaA.add(Calendar.DAY_OF_YEAR,+ 7);
            fechaB.add(Calendar.DAY_OF_YEAR,+ 7);
            query = "SELECT SUM(total)total FROM Ventas WHERE fechaVenta BETWEEN " +
                    "'"+new java.sql.Date(fechaA.getTimeInMillis())+"' AND '"+new java.sql.Date(fechaB.getTimeInMillis())+"'";
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                total = resultSet.getFloat("total");
                pieChartData.add(new PieChart.Data("SemanaActual = $"+df.format(total),total));
            }
        }catch(SQLException sqlE){
            sqlE.printStackTrace();
        }
        return pieChartData;
    }
    public ObservableList<XYChart.Series<String,Number>> SELECCIONPEDIDOSMES(String vista){
        ObservableList<XYChart.Series<String,Number>> data = FXCollections.observableArrayList();
        int total = 0;
        Calendar fecha = Calendar.getInstance();
        String query = "SELECT SUM(vi.cantidad)cantidad,vis.nombre " +
                "FROM VentasIndividuales vi INNER JOIN Vista"+vista+" vis ON vi.idProducto=vis.idProducto " +
                "INNER JOIN Ventas v ON vi.noVenta=v.noVenta WHERE v.fechaVenta BETWEEN '"+fecha.get(Calendar.YEAR)+"-"+(fecha.get(Calendar.MONTH)+1)+"-1' AND '"+fecha.get(Calendar.YEAR)+"-"+(fecha.get(Calendar.MONTH)+1)+"-"+fecha.getActualMaximum(Calendar.DAY_OF_MONTH)+"' GROUP BY vis.nombre";;
        XYChart.Series<String,Number> ventas = new XYChart.Series<>();
        ventas.setName("Ventas "+vista);
        try{
            Statement statement = ConexionDBRestaurante.connRestaurante.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                total = resultSet.getInt("cantidad");
                ventas.getData().add(new XYChart.Data<>(resultSet.getString("nombre")+" "+total,total));
            }
        }catch (SQLException sqlE){
            sqlE.printStackTrace();
        }
        data.add(ventas);
        return data;
    }
    public double SUMA_VENTAS_TOTALES(){
        double total = 0;
        try{
            Statement statement = ConexionDBRestaurante.connRestaurante.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT SUM(total)total FROM Ventas");
            while(resultSet.next()){
                total = resultSet.getFloat("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }
}