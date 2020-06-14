package sample.Componentes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;


public class DocumentoAImprimir extends Thread
{
    private ProgressBar progressBar;
    private TableView<Documento> tablaColaImpresion;
    private ObservableList<Documento> observableList;
    public DocumentoAImprimir(ProgressBar progressBar,TableView<Documento> tablaColaImpresion){
        this.tablaColaImpresion = tablaColaImpresion;
        this.progressBar = progressBar;
        this.observableList = FXCollections.observableArrayList();
    }
    public void addDocumento(Documento documento){
        observableList.add(documento);
        tablaColaImpresion.setItems(observableList);
        try{
            start();
        }catch(IllegalThreadStateException ITSE){ }
    }
    @Override
    public void run(){
        super.run();
        System.out.println("im here");
        while(observableList.size() != 0){
            System.out.println("estoy imprimiendo "+observableList.size() );
            observableList.get(0).setStatus("imprimiendo");
            tablaColaImpresion.setItems(observableList);
            double avance = 0;
            while(avance < 1){
                try {
                    System.out.println(avance);
                    avance += Math.random()/10;
                    progressBar.setProgress(avance);
                    sleep((long)(Math.random()*1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            observableList.remove(0);
            tablaColaImpresion.setItems(observableList);
            progressBar.setProgress(0);
        }
    }
}