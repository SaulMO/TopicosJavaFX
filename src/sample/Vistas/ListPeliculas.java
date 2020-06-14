package sample.Vistas;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.Componentes.ButtonCell;
import sample.Modelos.DataAccessObject.PeliculaDAO;

public class ListPeliculas extends Stage
{
    private Scene escena;
    private VBox vBox;
    private TableView<PeliculaDAO> tbVPeliculas;
    private Button btnAgregar;

    public ListPeliculas(){
        CrearGUI();
        setTitle("CRUD Peliculas");
        setScene(escena);
        show();
    }

    private void CrearGUI() {
        vBox = new VBox();
        tbVPeliculas = new TableView<>();
        CrearTabla();
        btnAgregar = new Button("Agregar");
        btnAgregar.setOnAction(event -> agregarPelicula());
        vBox.getChildren().addAll(tbVPeliculas,btnAgregar);
        escena = new Scene(vBox,250,180);
    }

    private void agregarPelicula() {
        new Pelicula(tbVPeliculas);
    }

    private void CrearTabla(){
        TableColumn<PeliculaDAO, Integer> tbCIdPelicula = new TableColumn<>("ID");
        tbCIdPelicula.setCellValueFactory(new PropertyValueFactory<>("idPelicula"));

        TableColumn<PeliculaDAO, String> tbCNombre = new TableColumn<>("Nombre");
        tbCNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<PeliculaDAO, Integer> tbCDuracion = new TableColumn<>("Duración");
        tbCDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));

        TableColumn<PeliculaDAO, String> tbCSipnosis = new TableColumn<>("Sipnosis");
        tbCSipnosis.setCellValueFactory(new PropertyValueFactory<>("sipnosis"));

        TableColumn<PeliculaDAO, String> tbCClase = new TableColumn<>("Clase");
        tbCClase.setCellValueFactory(new PropertyValueFactory<>("clase"));

        TableColumn<PeliculaDAO, Integer> tbCIdCategoria = new TableColumn<>("Categoria");
        tbCIdCategoria.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));

        TableColumn<PeliculaDAO,String> tbCEditar = new TableColumn<>("Editar");
        tbCEditar.setCellFactory(new Callback<TableColumn<PeliculaDAO, String>, TableCell<PeliculaDAO, String>>() {
            @Override
            public TableCell<PeliculaDAO, String> call(TableColumn<PeliculaDAO, String> param) {
                return new ButtonCell(1);
            }
        });
        TableColumn<PeliculaDAO,String> tbCEliminar = new TableColumn<>("Eliminar");
        tbCEliminar.setCellFactory(new Callback<TableColumn<PeliculaDAO, String>, TableCell<PeliculaDAO, String>>() {
            @Override
            public TableCell<PeliculaDAO, String> call(TableColumn<PeliculaDAO, String> param) {
                return new ButtonCell(2);
            }
        });

        tbVPeliculas.getColumns().addAll(tbCIdPelicula,tbCNombre,tbCDuracion,tbCSipnosis,tbCClase,tbCIdCategoria,tbCEditar,tbCEliminar);
        tbVPeliculas.setItems(new PeliculaDAO().SELECCIONAR());
    }

}