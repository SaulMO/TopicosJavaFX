package sample.Componentes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

//Clase que va a escuchar
public class ServidorSocket
{
    private ServerSocket servidor;  //Socket que recibe al socket cliente
    private Socket socketServidor;  //Socket CLiente
    private int noCte = 0;

    private void crearServer(){
        try{
            servidor = new ServerSocket(5000);//Configurar el puerto
            do{
                System.out.println("Servidor En Espera...");
                socketServidor = servidor.accept(); //Metodo que hace que el servidor este escuchando;
                noCte++;
                System.out.println("Llega el cliente: "+noCte);
                PrintStream salida = new PrintStream(socketServidor.getOutputStream());
                salida.println("Bienvenido...");
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socketServidor.getInputStream()));
                System.out.println("El cliente dice: "+entrada.readLine());
                socketServidor.close();
            }while(true);

        }catch(Exception e){}
    }

}