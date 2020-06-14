package sample;

public class Pruebas2
{
    public static void main(String[] args){
        System.out.println(generarNombre());
    }
    public static String generarNombre(){
        String nombre = "";
        switch((int)(Math.random()*10)){
            case 0:
                nombre = "reporte"; break;
            case 1:
                nombre = "ensayo"; break;
            case 2:
                nombre = "informe"; break;
            case 3:
                nombre = "carta"; break;
            case 4:
                nombre = "bibliografia"; break;
            case 5:
                nombre = "investigación"; break;
            case 6:
                nombre = "catalogo"; break;
            case 7:
                nombre = "documento"; break;
            case 8:
                nombre = "cuestionario"; break;
            case 9:
                nombre = "mapa conceptual"; break;
            case 10:
                nombre = "material"; break;
        }
        switch ((int)(Math.random()*10)){
            case 0:
                nombre = nombre + " calculo diferencial"; break;
            case 1:
                nombre = nombre + " calculo integral"; break;
            case 2:
                nombre = nombre + " POO"; break;
            case 3:
                nombre = nombre + " Probabilidad y estadistica"; break;
            case 4:
                nombre = nombre + " Química"; break;
            case 5:
                nombre = nombre + " Administración"; break;
            case 6:
                nombre = nombre + " Física"; break;
            case 7:
                nombre = nombre + " Investigación operaciones"; break;
            case 8:
                nombre = nombre + " topicos avanzados programación"; break;
            case 9:
                nombre = nombre + " base de datos"; break;
            case 10:
                nombre = nombre + " sistemas operativos"; break;
        }
        return nombre;
    }
}