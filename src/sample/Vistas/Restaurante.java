package sample.Vistas;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.FlowPane;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Restaurante extends Stage
{
    private Scene scene;
    private VBox vBoxMain;
    private Label lblTitle;
    private TabPane tabPaneMenu;
    private Tab tabMeat,tabKids,tabDessert,tabPizza,tabDiet;
    private FlowPane fpMeat,fpKids,fpDessert,fpPizza,fpDiet;
    private Button btnNextStep;
    private String[][] meatMenu = {{"Chefs Steak Special","$525.10"},{"Rib Fillet Steak","$220.00"},{"Steak Diavolo","$255.00"},{"Steak Funghi","$310.50"},{"Vitello Boscaiola","$120.10"}};
    private String[][] kidsMenu = {{"Hamburguesa y Papas","$70.00"},{"Hot Dog","$50.00"},{"Club Sandwich","$60.00"},{"Coctel Frutas","$45.00"},{"Caldo de Pollo","$80.00"}};
    private String[][] dessertMenu = {{"Flan Vainilla","$20.00"},{"Pay de Queso","$30.00"},{"Yogur Casero","$25.00"},{"Helado Stracciatella","$100.00"},{"Pastel Chocolate","$90.00"}};
    private String[][] pizzaMenu = {{"Pizza Margherita","$220.50"},{"Pizza Hawaiana","$180.50"},{"Pizza Napolitana","$200.00"},{"Pizza 4 Quesos","$150.00"},{"Pizza Pepperoni","$160.00"}};
    private String[][] dietMenu = {{"Hamburguesa Champiñones","$78.50"},{"Ensalada Acelgas","85.50"},{"Ratatouille","$50.00"},{"Curry light de Pollo","$80.00"},{"Ensalada","$55.00"}};

    public Restaurante(){
        crearGUI();
    }
    private void crearGUI(){
        iniciarComponentes();
        addComponentes();
        setScene(scene);
        setTitle("Restaurante");
        setResizable(false);
        setIconified(false);
        show();
    }
    private void iniciarComponentes(){
        String estiloContent = "-fx-background-color: #FEF7E4;" +
                                "-fx-padding: 10 10 10 10;" +
                                "-fx-hgap: 10px;" +
                                "-fx-pref-height: 100px;";

        vBoxMain = new VBox();
        vBoxMain.setId("paneCocina");
        tabPaneMenu = new TabPane();
        lblTitle = new Label("S E L E C T   Y O U R   M A I N S");
        lblTitle.setStyle("-fx-font-family: \"MV Boli\";" +
                            "-fx-font-size: 17;" +
                            "-fx-background-color: #82401E;" +
                            "-fx-text-fill: #ffffff;" +
                            "-fx-pref-width: 1100px;" +
                            "-fx-alignment: center center;");

        fpMeat = new FlowPane();
        fpMeat.setStyle(estiloContent);
        fpKids = new FlowPane();
        fpKids.setStyle(estiloContent);
        fpDessert = new FlowPane();
        fpDessert.setStyle(estiloContent);
        fpPizza = new FlowPane();
        fpPizza.setStyle(estiloContent);
        fpDiet = new FlowPane();
        fpDiet.setStyle(estiloContent);

        tabMeat = new Tab("M E A T");
        tabMeat.setClosable(false);
        tabMeat.setContent(fpMeat);
        tabKids = new Tab("K I D S");
        tabKids.setClosable(false);
        tabKids.setContent(fpKids);
        tabDessert = new Tab("D E S S E R T S");
        tabDessert.setClosable(false);
        tabDessert.setContent(fpDessert);
        tabPizza = new Tab("P I Z Z A");
        tabPizza.setClosable(false);
        tabPizza.setContent(fpPizza);
        tabDiet = new Tab("D I E T");
        tabDiet.setClosable(false);
        tabDiet.setContent(fpDiet);
        tabPaneMenu.setId("tabRestaurante");

        btnNextStep = new Button("Next Step >>");
        btnNextStep.getStyleClass().add("success");
    }
    private void llenarMenu(FlowPane fpMenu, String[][]arreglo,String menu) {
        //175 * 98
        File file = null;
        for(int i=0;i<5;i++) {
            VBox tarjeta = new VBox();
            tarjeta.setId("tarjeta");
            Button btnAdd = new Button("Add to order");
            btnAdd.getStyleClass().add("danger");
            file = new File("src/sample/resources/imagenes/Restaurante/"+i+menu+".jpg");
            Image image = null;
            try {
                BufferedImage bufferedImage = ImageIO.read(file);
                image = SwingFXUtils.toFXImage(bufferedImage,null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ImageView imageView = new ImageView(image);
            imageView.setId("imagenTarjeta");
            imageView.setFitHeight(100);
            imageView.setFitWidth(180);
            Label lblNombre = new Label(arreglo[i][0]);
            Label lblPrecio = new Label(arreglo[i][1]);
            tarjeta.getChildren().addAll(imageView,lblNombre,lblPrecio,btnAdd);
            tarjeta.setAlignment(Pos.CENTER);
            tarjeta.setSpacing(10);
            fpMenu.getChildren().add(tarjeta);
        }
        fpMenu.setAlignment(Pos.CENTER);
    }

    private void addComponentes(){
        tabPaneMenu.getTabs().addAll(tabMeat,tabKids,tabDessert,tabPizza,tabDiet);
        vBoxMain.getChildren().addAll(lblTitle,tabPaneMenu,btnNextStep);
        llenarMenu(fpMeat,meatMenu,"Meat");
        llenarMenu(fpKids,kidsMenu,"Kids");
        llenarMenu(fpDessert,dessertMenu,"Dessert");
        llenarMenu(fpPizza,pizzaMenu,"Pizza");
        llenarMenu(fpDiet,dietMenu,"Diet");
        scene = new Scene(vBoxMain);
        scene.getStylesheets().add(getClass().getResource("../resources/CSS/Bootstrap3.css").toExternalForm());
    }
}