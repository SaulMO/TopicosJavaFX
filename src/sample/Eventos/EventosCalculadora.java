package sample.Eventos;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

public class EventosCalculadora implements EventHandler
{
    private TextField txtOperacion;
    private Label lblOperacion;
    private char valor;
    private static boolean existeN,punto,masMenos,ingresoNumero;
    private static char operadorTemp;
    private static double tempNumero,resultado;

    public EventosCalculadora(char valor,TextField txtOperacion,Label lblOperacion){
        this.valor = valor;
        this.lblOperacion = lblOperacion;
        this.txtOperacion = txtOperacion;
        resetearCalculadora();
    }

    @Override
    public void handle(Event event) {
        correrCalculadora();
    }
    private void correrCalculadora(){
        switch (valor){
            case 'c':
                resetearCalculadora();
                break;
            case 's':
                break;
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                txtOperacion.appendText(valor+"");
                existeN = true;
                break;
            case '.':
                if (!punto){
                    punto = true;
                    txtOperacion.appendText(valor+"");
                }
                break;
            case '-':
            case '+':
                if (txtOperacion.getText().equals("") && (!masMenos)){
                    txtOperacion.setText(valor+"");
                    masMenos = true;
                }
                trabajarOperadores();
                break;
            case 'x':
            case '/':
            case '=':
                System.out.println("Existe Número: "+existeN+"\nmasMenos: "+masMenos+"\nOperador Temp: "+operadorTemp+
                        "\nPunto: "+punto+"\nIngreso Número: "+ingresoNumero+"\nNum Temp: "+tempNumero+"\nResult: "+resultado);
                trabajarOperadores();
                break;
        }
    }

    private void trabajarOperadores(){
        if (existeN){
            if (ingresoNumero && valor=='=' && operadorTemp!='='){
                hacerOperacion();
            }else if (ingresoNumero && valor=='=' && operadorTemp=='='){
                tempNumero = Double.parseDouble(txtOperacion.getText());
                txtOperacion.setText("");
                resultado = tempNumero;
                lblOperacion.setText(tempNumero+"");
            }else if (ingresoNumero && valor!='=' && operadorTemp=='='){
                lblOperacion.setText(resultado+" "+valor);
                tempNumero = resultado;
                operadorTemp = valor;
            }else if(ingresoNumero && valor!='=' && operadorTemp!='='){
                hacerOperacion();
            }else if (!ingresoNumero){
                ingresoNumero = true;
                operadorTemp = valor;
                tempNumero = Double.parseDouble(txtOperacion.getText());
                resultado = tempNumero;
                txtOperacion.setText("");
                lblOperacion.setText((valor == '=') ? tempNumero+"" : tempNumero + " " + valor);
            }
        }else if (txtOperacion.getText().length()==1 && txtOperacion.getText().equals(".")){
            txtOperacion.setText("");
        }

        else if(txtOperacion.getText().equals("")){
            if (ingresoNumero){
                if (valor != '='){
                    operadorTemp = valor;
                    lblOperacion.setText(tempNumero+" "+valor);
                }
            }
        }else if(txtOperacion.getText().charAt(txtOperacion.getLength()-1) == '.' && txtOperacion.getText().length()==2){
            if (txtOperacion.getText().charAt(txtOperacion.getLength()-2) == '+' || txtOperacion.getText().charAt(txtOperacion.getLength()-2) == '-'){
                txtOperacion.setText("");
                ingresoNumero = false;
                operadorTemp = 'n';
            }
        }
        else if (txtOperacion.getText().length()==1 && txtOperacion.getText().charAt(0)=='.'){
            txtOperacion.setText("");
        }
        existeN = false;
        punto = false;
        masMenos = false;
    }

    private void hacerOperacion(){
        double numero = Double.parseDouble(txtOperacion.getText());
        switch (operadorTemp) {
            case '+':
                resultado = tempNumero + numero;
                lblOperacion.setText(tempNumero+" "+operadorTemp+" "+Double.parseDouble(txtOperacion.getText())+" = "+resultado);
                break;
            case '-':
                resultado = tempNumero - numero;
                lblOperacion.setText(tempNumero+" "+operadorTemp+" "+Double.parseDouble(txtOperacion.getText())+" = "+resultado);
                break;
            case 'x':
                resultado = tempNumero * numero;
                lblOperacion.setText(tempNumero+" "+operadorTemp+" "+Double.parseDouble(txtOperacion.getText())+" = "+resultado);
                break;
            case '/':
                if (numero == 0) {
                    lblOperacion.setText((tempNumero == 0) ? "Resultado Indefinido" : "No se puede dividir entre 0");
                } else {
                    resultado = tempNumero / Double.parseDouble(txtOperacion.getText());
                    lblOperacion.setText(tempNumero+" "+operadorTemp+" "+Double.parseDouble(txtOperacion.getText())+" = "+resultado);
                }
                break;
        }
        tempNumero = resultado;
        operadorTemp = valor;
        txtOperacion.setText("");
    }

    private void resetearCalculadora(){
        existeN = false;
        punto = false;
        masMenos = false;
        ingresoNumero = false;
        operadorTemp = 'n';
        tempNumero = 0;
        resultado = 0;
        txtOperacion.setText("");
        lblOperacion.setText("");
    }
}