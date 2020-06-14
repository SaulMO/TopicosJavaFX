package sample.Componentes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClienteSocket
{
    private Socket clienteSocket;
    private InetAddress direccion;
    private int puerto = 5000;
    byte[] ip = new byte[]{(byte)192,(byte)168,(byte)1,(byte)67};

    public ClienteSocket(){
        crearSocketCliente();
    }

    private void crearSocketCliente() {
        try{
            direccion = InetAddress.getByAddress(ip);
            clienteSocket = new Socket(direccion,puerto);

            BufferedReader entrada = new BufferedReader( new InputStreamReader(clienteSocket.getInputStream()));
            System.out.println(entrada.readLine());

            PrintStream salida = new PrintStream(clienteSocket.getOutputStream());
            salida.println("Hola Servidor!!!");
        }catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}