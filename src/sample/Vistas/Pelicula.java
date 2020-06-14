package sample.Vistas;

import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import sample.Modelos.DataAccessObject.PeliculaDAO;

public class Pelicula extends Stage
{
    TableView<PeliculaDAO> tVPeliculas;
    private Scene scene;
    private Label lblTitle;
    private VBox vBox;
    private TextField txtNombre,txtDuracion,txtSipnosis,txtClase,txtCategoria;
    private ComboBox<String> comboBoxCategoria;
    private Button btnGuardar;
    private PeliculaDAO peliculaDAO = null;

    public Pelicula(TableView<PeliculaDAO> tVPeliculas){
        this.tVPeliculas = tVPeliculas;
        crearGUI();
        setTitle("Modificar/Agregar Peliculas");
        setScene(scene);
        show();
    }
    private void crearGUI(){
        inicializarComponentes();
        addComponentes();
    }
    private void inicializarComponentes() {
        vBox = new VBox();
        lblTitle = new Label("Pelicula");
        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre");
        txtDuracion = new TextField();
        txtDuracion.setPromptText("Duración");
        txtSipnosis = new TextField();
        txtSipnosis.setPromptText("Sipnosis");
        txtClase = new TextField();
        txtClase.setPromptText("clase");
        txtCategoria = new TextField();
        comboBoxCategoria = new ComboBox<>();
        txtCategoria.setPromptText("categoria");
        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> guardarPelicula());
    }
    private void addComponentes() {
        comboBoxCategoria.getItems().addAll();
        vBox.getChildren().addAll(lblTitle,txtNombre,txtDuracion,txtSipnosis,txtClase,
                txtCategoria,comboBoxCategoria,btnGuardar);
        scene = new Scene(vBox);
    }
    private void guardarPelicula(){
        String nombre = txtNombre.getText();
        int duracion = Integer.parseInt(txtDuracion.getText());
        String sipnosis = txtSipnosis.getText();
        String clase = txtClase.getText();
        int categoria = Integer.parseInt(txtCategoria.getText());

        if (peliculaDAO == null){
            peliculaDAO = new PeliculaDAO();
            peliculaDAO.setNombre(nombre);
            peliculaDAO.setDuracion(duracion);
            peliculaDAO.setSipnosis(sipnosis);
            peliculaDAO.setClase(clase);
            peliculaDAO.setIdCategoria(categoria);
            peliculaDAO.INSERTAR();
        }else{
            peliculaDAO.setNombre(nombre);
            peliculaDAO.setDuracion(duracion);
            peliculaDAO.setSipnosis(sipnosis);
            peliculaDAO.setClase(clase);
            peliculaDAO.setIdCategoria(categoria);
            peliculaDAO.ACTUALIZAR();
        }

        tVPeliculas.setItems(peliculaDAO.SELECCIONAR());
        tVPeliculas.refresh();
        this.close();
    }
    public void setPeliculaDAO(PeliculaDAO peliculaDAO){
        this.peliculaDAO = peliculaDAO;
        txtNombre.setText(peliculaDAO.getNombre());
        txtSipnosis.setText(peliculaDAO.getSipnosis());
        txtCategoria.setText(String.valueOf(peliculaDAO.getIdCategoria()));
        txtClase.setText(peliculaDAO.getClase());
        txtDuracion.setText(String.valueOf(peliculaDAO.getDuracion()));
    }
}