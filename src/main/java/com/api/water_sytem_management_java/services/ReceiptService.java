package com.api.water_sytem_management_java.services;

import com.api.water_sytem_management_java.models.Payment;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ReceiptService {

    public void generateInvoice(Payment payment) {
        Document document = new Document();
        try {
            // Formatar a data
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy HH-mm-ss");
            String formattedDate = dateFormat.format(new Date());

            // Substituindo espaços por underscores (_) no nome do cliente
            String clientName = payment.getCustomer().getName().replaceAll("\\s", "_");

            // Caminho para a pasta 'recibos'
            File recibosDir = new File("recibos");
            if (!recibosDir.exists()) {
                recibosDir.mkdir(); // Criar a pasta se não existir
            }

            // Nome do arquivo com o nome do cliente e a data no formato especificado
            String fileName = "recibos/" + clientName + "_" + formattedDate + ".pdf";

            // Cria o arquivo PDF na pasta 'recibos'
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            // Desenhando a borda ao redor da página
            addPageBorder(writer);

            // Adicionando a marca d'água "Transportes Chiziane"
            addWatermark(writer, "Transportes Chiziane");

            // Dados da empresa no canto superior esquerdo com "Transportes Chiziane" em azul e maior
            Font companyNameFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLUE);
            Paragraph companyName = new Paragraph("Aguas Chiziane", companyNameFont);
            document.add(companyName);

            // Dados adicionais da empresa com fonte menor
            Font companyInfoFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
            Paragraph companyInfo = new Paragraph("Cumbeza, Rua da esqudra 524\nMaputo, Mozambique\nPhone: (+258) 845098583", companyInfoFont);
            document.add(companyInfo);

            // Espaço entre os dados da empresa e o título
            document.add(new Paragraph("\n"));

            // Título com fundo preto
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.WHITE);
            Paragraph title = new Paragraph("Fatura de Pagamento", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);

            // Cria uma tabela de 1 célula para colocar o título com fundo preto
            PdfPTable titleTable = new PdfPTable(1);
            titleTable.setWidthPercentage(100);

            // Cria uma célula com fundo preto
            PdfPCell titleCell = new PdfPCell(title);
            titleCell.setBackgroundColor(BaseColor.BLACK);  // Cor de fundo preta
            titleCell.setBorder(Rectangle.NO_BORDER);  // Remover borda da célula

            // Adiciona a célula com o título à tabela
            titleTable.addCell(titleCell);

            // Adiciona a tabela ao documento
            document.add(titleTable);

            // Espaço entre o título e os detalhes do cliente
            document.add(new Paragraph("\n"));

            // Título "Dados do Cliente" com fonte azul e maior, alinhado à direita
            Font customerTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLUE);
            Paragraph customerTitle = new Paragraph("Dados do Cliente", customerTitleFont);
            customerTitle.setAlignment(Element.ALIGN_RIGHT);  // Alinhado à direita
            document.add(customerTitle);

            // Espaço entre o título "Dados do Cliente" e os dados do cliente
            document.add(new Paragraph("\n"));

            // Dados do cliente (Endereço, Telefone, e Email) alinhados à direita
            Font customerFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);

            // Criar uma tabela para alinhar os dados do cliente à direita
            PdfPTable customerTable = new PdfPTable(1);
            customerTable.setWidthPercentage(100);

            // Remover bordas da tabela
            customerTable.getDefaultCell().setBorderWidth(0);

            customerTable.addCell(new Phrase("Cliente: " + payment.getCustomer().getName(), customerFont));
            customerTable.addCell(new Phrase("Endereço: Zimpeto", customerFont)); // Endereço
            customerTable.addCell(new Phrase("Telefone: +258 825252523", customerFont));  // Telefone
            customerTable.addCell(new Phrase("Email: Eddybruno43@gmail.com", customerFont));  // Email

            // Alinhar os dados à direita
            for (int i = 0; i < customerTable.getRows().size(); i++) {
                customerTable.getRow(i).getCells()[0].setHorizontalAlignment(Element.ALIGN_RIGHT);
            }

            // Adicionando a tabela ao documento
            document.add(customerTable);

            // Espaço entre o texto de recebimento
            document.add(new Paragraph("\n"));

            // Texto de recebimento do pagamento com formatação em itálico e linha abaixo
            Font italicFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
            Paragraph paymentText = new Paragraph();
            paymentText.add("Recebemos do ");
            paymentText.add(new Chunk(payment.getCustomer().getName(), italicFont));
            paymentText.add(", valor ");
            paymentText.add(new Chunk("€" + payment.getAmount(), italicFont));
            paymentText.add(" referentes ao pagamento de água de ");
            paymentText.add(new Chunk(String.valueOf(payment.getNumMonths()), italicFont));
            paymentText.add(" meses: ");
            paymentText.add(new Chunk(payment.getReferenceMonth(), italicFont));
            paymentText.add(". Obrigado!");
            document.add(paymentText);

            // Espaço entre o texto e os detalhes do pagamento
            document.add(new Paragraph("\n"));

            // Criando a tabela de detalhes de pagamento
            PdfPTable paymentDetailsTable = new PdfPTable(2);
            paymentDetailsTable.setWidths(new float[]{2, 3});

            // Cabeçalho da tabela
            paymentDetailsTable.addCell("Descrição");
            paymentDetailsTable.addCell("Valor");

            // Adicionando dados à tabela de detalhes de pagamento
            paymentDetailsTable.addCell("Método de Pagamento");
            paymentDetailsTable.addCell(payment.getPaymentMethod());

            paymentDetailsTable.addCell("Valor");
            paymentDetailsTable.addCell("€" + payment.getAmount());

            paymentDetailsTable.addCell("Referência Mês");
            paymentDetailsTable.addCell(payment.getReferenceMonth());

            paymentDetailsTable.addCell("Número de Meses");
            paymentDetailsTable.addCell(String.valueOf(payment.getNumMonths()));

            paymentDetailsTable.addCell("Data de Criação");
            paymentDetailsTable.addCell(payment.getCreatedAt().toString());

            // Adicionando a tabela de detalhes de pagamento ao documento
            document.add(paymentDetailsTable);

            // Espaço abaixo da tabela
            document.add(new Paragraph("\n"));

            // Confirmar pagamento
            document.add(new Paragraph("Pagamento Confirmado: " + (payment.getConfirmed() ? "Sim" : "Não"), customerFont));

            // Adicionar linha de assinatura centralizada e com 50% da largura da página
            document.add(new Paragraph("\n\n"));
            LineSeparator lineSeparator = new LineSeparator();
            lineSeparator.setPercentage(50f);  // Definir a largura da linha para 50%
            document.add(lineSeparator);

            // Adicionar data
            SimpleDateFormat dateOnlyFormat = new SimpleDateFormat("dd MMMM yyyy");
            String currentDate = dateOnlyFormat.format(new Date());
            Paragraph dateParagraph = new Paragraph("Data: " + currentDate, FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK));
            dateParagraph.setAlignment(Element.ALIGN_CENTER);  // Centralizar a data
            document.add(dateParagraph);

            // Adicionar rodapé
            Phrase footer = new Phrase("Transportes Chiziane 2025 Volte sempre, obrigado pela preferência", FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.GRAY));
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, footer, 297.5f, 30, 0);

            // Fechar documento
            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private void addPageBorder(PdfWriter writer) {
        // Adiciona uma borda ao redor da página inteira
        PdfContentByte canvas = writer.getDirectContent();
        Rectangle pageSize = writer.getPageSize();
        float margin = 20; // Margem da borda

        // Desenhar a borda
        canvas.setLineWidth(1f);
        canvas.rectangle(margin, margin, pageSize.getWidth() - 2 * margin, pageSize.getHeight() - 2 * margin);
        canvas.stroke();
    }

    private void addWatermark(PdfWriter writer, String watermarkText) {
        // Obter o conteúdo do documento
        PdfContentByte content = writer.getDirectContentUnder();

        // Definir a fonte para a marca d'água
        Font watermarkFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 100, BaseColor.LIGHT_GRAY);
        Phrase watermark = new Phrase(watermarkText, watermarkFont);

        // Definir a posição da marca d'água (centrada na página)
        float x = 297.5f;
        float y = 421.0f;

        // Adicionar a marca d'água no fundo
        ColumnText.showTextAligned(content, Element.ALIGN_CENTER, watermark, x, y, 45);
    }
}
