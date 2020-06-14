package sample.Restaurante.componentes;

import javafx.scene.control.Button;

public class Tarjeta
{
    private String id;
    private Button btnNumber;
    private int contador;
    private float precioProducto;

    public Tarjeta(String id, Button btnNumber,float precioProducto){
        contador = 0;
        this.id = id;
        this.btnNumber = btnNumber;
        this.precioProducto = precioProducto;
    }
    public String getID(){ return this.id; }
    public Button getButton(){ return this.btnNumber; }
    public int getContador(){ return contador; }
    public float getPrecioProducto(){ return precioProducto; }

    public void restarContador(){
        if (contador != 0)
            contador--;
    }
    public void sumarContador(){ contador++; }
}