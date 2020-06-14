package sample.Componentes;

//SINCRONIZACIÓN DE HILOS

public class Productor extends Thread
{
    RecursoCompartido objRc;

    public Productor(RecursoCompartido objRc){
        this.objRc = objRc;
    }

    @Override
    public void run(){
        super.run();
        for (int i = 0 ; i < 15 ; i++){
            objRc.llenarRecurso((int)(Math.random()*1000));
        }
    }
}