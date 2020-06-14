package sample.Restaurante.componentes;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import sample.Restaurante.Modelos.DataAccessObject.VentasIndividualesDAO;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RealizarPDF
{
    private Document document;
    private FileOutputStream ficheroPDF;
    private DecimalFormat df = new DecimalFormat("#.00");
    private PdfPTable pdfTable;
    private PdfPCell pdfCell;

    public RealizarPDF (ArrayList<VentasIndividualesDAO> ventas, Date fecha){
        Rectangle rectangle = new Rectangle(300f,((200f)+(14f*ventas.size())));
        document = new Document(rectangle);
        document.setMargins(10,10,10,10);
        try {
            ficheroPDF = new FileOutputStream("src/sample/Restaurante/resources/tickets/"+ventas.get(0).getNoVenta()+".pdf");
            PdfWriter.getInstance(document,ficheroPDF).setInitialLeading(5);
            document.open();
            Paragraph titulo = new Paragraph("Traficantes de Comida",
                            FontFactory.getFont("MV Boli",
                            15,
                            Font.ITALIC,
                            BaseColor.BLACK));
            titulo.setAlignment(Paragraph.ALIGN_CENTER);
            Paragraph lema = new Paragraph("Comerciando comida para ti",
                            FontFactory.getFont("MV Boli",
                            12,
                            Font.ITALIC,
                            BaseColor.BLACK));
            lema.setAlignment(Paragraph.ALIGN_CENTER);
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY");
            Paragraph descripcion = new Paragraph(format.format(fecha)+"         noVenta:"+ventas.get(0).getNoVenta(),
                            FontFactory.getFont("MV Boli",
                            12,
                            Font.ITALIC,
                            BaseColor.BLACK));
            descripcion.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(titulo);
            document.add(lema);
            document.add(new Paragraph("............................................................................................",
                    FontFactory.getFont("consolas",
                            10,
                            Font.ITALIC,
                            BaseColor.BLACK)));
            document.add(descripcion);
            document.add(new Paragraph("............................................................................................",
                    FontFactory.getFont("consolas",
                            10,
                            Font.ITALIC,
                            BaseColor.BLACK)));
            pdfTable = new PdfPTable(3);
            pdfTable.setWidthPercentage(100);
            pdfTable.setWidths(new float[]{70,10,20});
            pdfCell = new PdfPCell();
            for (int i=0 ; i<ventas.size() ; i++){
                pdfCell = new PdfPCell(new Paragraph("x"+ventas.get(i).getCantidad()+"  "+ventas.get(i).SELECTNameProducto()+" x",
                        FontFactory.getFont("consolas",
                                8,
                                Font.ITALIC,
                                BaseColor.BLACK)));
                pdfCell.setBorder(Rectangle.NO_BORDER);
                pdfTable.addCell(pdfCell);
                pdfCell = new PdfPCell(new Paragraph("-----",
                        FontFactory.getFont("consolas",
                                8,
                                Font.ITALIC,
                                BaseColor.BLACK)));
                pdfCell.setBorder(Rectangle.NO_BORDER);
                pdfTable.addCell(pdfCell);
                pdfCell = new PdfPCell(new Paragraph("$"+df.format(ventas.get(i).SELECTPrecioTotalXProd()),
                        FontFactory.getFont("consolas",
                                8,
                                Font.ITALIC,
                                BaseColor.BLACK)));
                pdfCell.setBorder(Rectangle.NO_BORDER);
                pdfTable.addCell(pdfCell);
            }
            document.add(pdfTable);
            document.add(new Paragraph("............................................................................................",
                            FontFactory.getFont("consolas",
                            10,
                            Font.ITALIC,
                            BaseColor.BLACK)));
            document.add(new Paragraph("TOTAL-------------------------------------------------    $" +
                    ""+df.format(ventas.get(0).SELECT_TOTAL(ventas.get(0).getNoVenta())),
                            FontFactory.getFont("consolas",
                            10,
                            Font.ITALIC,
                            BaseColor.BLACK)));
            document.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (DocumentException e) {
            e.printStackTrace();
        }
    }

}