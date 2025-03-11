package com.api.water_sytem_management_java.services;

import com.api.water_sytem_management_java.models.Payment;
import com.api.water_sytem_management_java.repositories.PaymentRepository;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.stereotype.Service;


import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import java.io.*;
import java.time.format.DateTimeFormatter;

@Service
public class ReceiptService {
    private static final String RECEIPT_PATH = "recibos/";
    private final PaymentRepository paymentRepository;

    public ReceiptService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public String generateFormattedReceipt(Payment payment) throws IOException {
        // Criar diretório se não existir
        File directory = new File(RECEIPT_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }



        // Formatar a data no padrão "DD MM AAAA HH:MM"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy HH-mm");
        String formattedDate = payment.getCreatedAt().format(formatter);

        // Remover caracteres especiais do nome do cliente para evitar problemas no nome do arquivo
        String customerName = payment.getCustomer().getName().replaceAll("[^a-zA-Z0-9 ]", "").replace(" ", "_");

        // Nome do arquivo no formato "Nome do Cliente - Data(DD MM AAAA HH:MM).pdf"
        String fileName = RECEIPT_PATH + customerName + " - " + formattedDate + ".pdf";

        try (FileOutputStream fos = new FileOutputStream(fileName);
             PdfWriter writer = new PdfWriter(fos);
             PdfDocument pdfDoc = new PdfDocument(writer);
             Document document = new Document(pdfDoc, PageSize.A5)) {

            pdfDoc.addNewPage();

            // Cabeçalho
            Paragraph title = new Paragraph("RECIBO DE PAGAMENTO")
                    .setBold()
                    .setFontSize(16)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(title);

            document.add(new Paragraph(" "));

            // Criar tabela de informações do pagamento
            Table table = new Table(UnitValue.createPercentArray(new float[]{30, 70}))
                    .useAllAvailableWidth()
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY);

            table.addCell( createStyledCell("Nome do Cliente:", true));
            table.addCell(createStyledCell(payment.getCustomer().getName(), false));

            table.addCell(createStyledCell("Data do Pagamento:", true));
            table.addCell(createStyledCell(payment.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), false));

            table.addCell(createStyledCell("Valor Pago:", true));
            table.addCell(createStyledCell(String.format("%.2f MT", payment.getAmount()), false));

            table.addCell(createStyledCell("Meses Referentes:", true));
            table.addCell(createStyledCell(payment.getReferenceMonth(), false));

            table.addCell(createStyledCell("Método de Pagamento:", true));
            table.addCell(createStyledCell(payment.getPaymentMethod(), false));

            table.addCell(createStyledCell("Estado do Pagamento:", true));
            table.addCell(createStyledCell(payment.getConfirmed() ? "Confirmado" : "Pendente", false));

            document.add(table);
            document.add(new Paragraph(" "));

            // Mensagem final
            Paragraph thanks = new Paragraph("Obrigado pela preferência!")
                    .setItalic()
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(thanks);
        }

        return fileName;
    }

    private Cell createStyledCell(String content, boolean isHeader) {
        // Definir o estilo da célula dependendo se é cabeçalho ou não
        Cell cell = new Cell()
                .add(new Paragraph(content))
                .setPadding(5)
                .setTextAlignment(TextAlignment.LEFT)
                .setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE);

        if (isHeader) {
            cell.setBold()
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY);
        } else {
            cell.setFontSize(12);
        }

        return cell;
    }

    private void printReceipt(String filePath) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            Doc pdfDoc = new SimpleDoc(fis, DocFlavor.INPUT_STREAM.AUTOSENSE, null);

            PrintRequestAttributeSet printAttributes = new HashPrintRequestAttributeSet();
            printAttributes.add(MediaSizeName.ISO_A5);
            printAttributes.add(new Copies(1)); // Definir número de cópias
            printAttributes.add(OrientationRequested.PORTRAIT);

            PrintService printService = PrintServiceLookup.lookupDefaultPrintService(); // Obtém a impressora padrão
            if (printService != null) {
                DocPrintJob printJob = printService.createPrintJob();
                printJob.print(pdfDoc, printAttributes);
                System.out.println("Recibo enviado para impressão!");
            } else {
                System.err.println("Nenhuma impressora disponível!");
            }

            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao imprimir o recibo: " + e.getMessage());
        }
    }
}
