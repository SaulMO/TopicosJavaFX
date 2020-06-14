package sample.Componentes;

//SINCRONIZACIÓN DE HILOS

public class Consumidor extends Thread
{
    RecursoCompartido objRc;

    public Consumidor(RecursoCompartido objRc){
        this.objRc = objRc;
    }

    @Override
    public void run(){
        super.run();
        for (int i = 0 ; i < 15 ; i++){
            objRc.vaciarRecurso();
            try {
                sleep(1000);
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
}