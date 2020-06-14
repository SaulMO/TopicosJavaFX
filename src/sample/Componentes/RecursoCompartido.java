package sample.Componentes;

//SINCRONIZACIÓN DE HILOS

public class RecursoCompartido
{
    private int posicion;
    private int[] recurso;
    private boolean monitorStopThread;

    public RecursoCompartido(){
        posicion = 0;
        recurso = new int[5];
        monitorStopThread = false;
    }

    public synchronized void llenarRecurso(int valor){
        try {
            if (monitorStopThread){
                System.out.println("Productor se Detiene");
                wait();
                System.out.println("Productor se reinicia");
            }
        } catch (InterruptedException e) { e.printStackTrace(); }

        recurso[posicion] = valor;
        posicion = posicion + 1;
        if (posicion == recurso.length) {
            posicion = posicion - 1;
            notify();    //MODIFICA Y LIBERA EL RECURSO
            monitorStopThread = true;
        }
    }
    public synchronized void vaciarRecurso(){
        try{
            if (monitorStopThread == false){
                System.out.println("Consumidor se detiene");
                wait();
                System.out.println("Consumidor se reinicia");
            }
        }catch(InterruptedException ie){ ie.printStackTrace();}

        int valor = recurso[posicion] - 1;
        System.out.println("Recurso["+posicion+"] = "+valor);
        posicion = posicion - 1;
        if (posicion < 0){
            posicion = posicion + 1;
            notify();
            monitorStopThread = false;
        }
    }
}