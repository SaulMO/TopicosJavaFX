package sample.Componentes;

import javafx.scene.control.ProgressBar;

public class Vengador extends Thread
{
    private ProgressBar pb;

    public Vengador(String name, ProgressBar pb){
        super(name);
	    this.pb = pb;
    }

    @Override
    public void run() {
        super.run();
        /*try {
            System.out.println("Sale: "+getName());
            for (int i=1 ; i<=10; i++) {
                sleep((long) (Math.random() * 1000));
                System.out.println("Vengador "+getName()+" km "+i);
            }
            System.out.println("Llego a la meta: "+getName());
        }catch (InterruptedException ie){
        }*/

        double avance = 0;
        while(avance < 1){
            try{
                avance += Math.random()/10;
                pb.setProgress(avance);
                sleep((long)(Math.random()*1000));
            }catch (InterruptedException ie){}
        }
    }
}