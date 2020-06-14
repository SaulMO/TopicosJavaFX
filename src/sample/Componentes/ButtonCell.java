package sample.Componentes;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import sample.Modelos.DataAccessObject.PeliculaDAO;
import sample.Vistas.Pelicula;

public class ButtonCell extends TableCell<PeliculaDAO,String>
{
    private Button celdaBoton;
    private int opc;
    private PeliculaDAO peliculaDAO;

    public ButtonCell(int opc){
        this.opc = opc;
        if (this.opc ==1) {
            celdaBoton = new Button("Editar");
            celdaBoton.setOnAction(event -> Editar());
        }else{
            celdaBoton = new Button("Eliminar");
            celdaBoton.setOnAction(event -> Eliminar());
        }
    }
    private void Editar(){
        peliculaDAO = ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
        new Pelicula(ButtonCell.this.getTableView()).setPeliculaDAO(peliculaDAO);
    }
    private void Eliminar(){
        peliculaDAO = ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
        peliculaDAO.ELIMINAR();
        ButtonCell.this.getTableView().setItems(peliculaDAO.SELECCIONAR());
        ButtonCell.this.getTableView().refresh();
    }

    @Override
    protected void updateItem(String item,boolean empty){
        super.updateItem(item,empty);
        if (!empty)
            setGraphic(celdaBoton);
    }
}