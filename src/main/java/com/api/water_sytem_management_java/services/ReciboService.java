package com.api.water_sytem_management_java.services;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


@Service
public class ReciboService {

    public void imprimir(File file) throws IOException {
        Desktop.getDesktop().print(file);
    }

    public File atualizarRecibo(String nome, String endereco, String data) throws IOException {
        // Carregar o template
        FileInputStream fis = new FileInputStream("templates/recibo_template.xlsx");
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        // Atualizar as células específicas
        sheet.getRow(7).getCell(1).setCellValue(nome);      // B8 (linha 7, coluna 1)
        sheet.getRow(8).getCell(1).setCellValue(endereco);  // B9 (linha 8, coluna 1)
        sheet.getRow(9).getCell(1).setCellValue(data);      // B10 (linha 9, coluna 1)

        fis.close();

        // Criar novo arquivo atualizado
        File outputFile = new File("recibos/recibo_atualizado.xlsx");
        outputFile.getParentFile().mkdirs(); // Garantir pasta
        FileOutputStream fos = new FileOutputStream(outputFile);
        workbook.write(fos);
        fos.close();
        workbook.close();

        return outputFile;
    }
}
