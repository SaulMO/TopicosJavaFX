package sample.Componentes;

public class Documento
{
    private int numero,noHojas;
    private String nombreArchivo,status,pc;

    public Documento(int numero, int noHojas, String nombreArchivo, String status, String pc){
        this.numero = numero;
        this.noHojas = noHojas;
        this.nombreArchivo = nombreArchivo;
        this.status = status;
        this.pc = pc;
    }

    public int getNumero(){ return numero; }
    public int getNoHojas(){ return noHojas; }
    public String getNombreArchivo(){ return nombreArchivo; }
    public String getStatus(){ return status; }
    public String getPc(){ return pc; }

    public void setNumero(int numero) { this.numero = numero; }
    public void setNoHojas(int noHojas) { this.noHojas = noHojas; }
    public void setNombreArchivo(String nombreArchivo) { this.nombreArchivo = nombreArchivo; }
    public void setStatus(String status) { this.status = status; }
    public void setPc(String pc) { this.pc = pc; }
}