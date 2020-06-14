package sample;

import javafx.collections.ObservableList;
import sample.Componentes.Consumidor;
import sample.Componentes.Productor;
import sample.Componentes.RecursoCompartido;
import sample.Modelos.Conexion;
import sample.Modelos.DataAccessObject.PeliculaDAO;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class pruebas
{
    public static void main(String[] args)
    {
        String cadenas[] = {"a","b","c","d","e","f"};
        String nuevasC[] = new String[cadenas.length];
        Map<Character,Integer> map = new HashMap<Character, Integer>();
        map.put('a',1);
        System.out.println(map.size()+""+map.remove('a')+map.size());

        /*
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("cuadroMagico5.dat","r");
            System.out.println("Longitud: "+randomAccessFile.length());
            for (int i=0 ; i<25 ; i++){
                System.out.println("Numero: "+randomAccessFile.readInt());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //Prueba RandomAccessFile
        /*RandomAccessFile file;
        int[] unidades={5, 7, 12, 8, 30};
        try {
            file = new RandomAccessFile("salida2.dat","r");
            System.out.println("Longitud: "+file.length());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //Leer archivos primitivos
        /*
        int a,b;
        try {
            DataInputStream entrada = new DataInputStream(new FileInputStream("salida.dat"));
            a = entrada.readInt();
            b = entrada.readInt();
            System.out.println("numero"+a);
            System.out.println("numero"+b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //Escribir Archivos Primitivos

        /*
        char p = 50;
        int unidades[] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,50,18,19,20,21,22,23,24,25};
        try {
            DataOutputStream salida = new DataOutputStream(new FileOutputStream("salidaMal.txt"));
            for (int i=0 ; i<unidades.length ; i++){
                salida.writeInt(unidades[i]);
            }
            //salida.writeChar(p);
            salida.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}