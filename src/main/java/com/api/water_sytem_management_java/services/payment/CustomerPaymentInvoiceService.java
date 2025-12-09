package com.api.water_sytem_management_java.services.payment;

import com.api.water_sytem_management_java.models.payment.CustomerPaymentInvoice;
import com.api.water_sytem_management_java.models.payment.Payment;
import com.api.water_sytem_management_java.repositories.customer.CustomerRepository;
import com.api.water_sytem_management_java.repositories.payment.CustomerPaymentInvoiceRepository;
import com.api.water_sytem_management_java.repositories.payment.PaymentRepository;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerPaymentInvoiceService {

    private final PaymentRepository paymentRepository;
    private final CustomerPaymentInvoiceRepository customerPaymentInvoiceRepository;

    public CustomerPaymentInvoiceService(PaymentRepository paymentRepository, CustomerRepository customerRepository, CustomerPaymentInvoiceRepository customerPaymentInvoiceRepository) {
        this.paymentRepository = paymentRepository;

        this.customerPaymentInvoiceRepository = customerPaymentInvoiceRepository;
    }


    public List<CustomerPaymentInvoice> listAllCustomerInvoices() {
        return customerPaymentInvoiceRepository.findAll();
    }

    public CustomerPaymentInvoice generateCustomerInvoice(UUID paymentId) throws IOException {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));


        CustomerPaymentInvoice invoice = new CustomerPaymentInvoice("1234", payment);

        // gerar nome do ficheiro e salvar arquivo Excel
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        invoice.setFilePath(populateInvoiceTemplate(invoice).getPath());
        invoice.setFileName("AC_INVOICE_" + payment.getCustomer().getName().toLowerCase() + "_" + timestamp);

        return customerPaymentInvoiceRepository.save(invoice);
    }


    public File populateInvoiceTemplate(CustomerPaymentInvoice customerPaymentInvoice) throws IOException {
        // Usando ClassPathResource para ler o template dentro do resources
        ClassPathResource resource = new ClassPathResource("templates/recibo_template.xlsx");

        try (InputStream fis = resource.getInputStream();
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            // Dados do cliente
            sheet.getRow(6).getCell(1).setCellValue(customerPaymentInvoice.getCustomerName());
            sheet.getRow(7).getCell(1).setCellValue(customerPaymentInvoice.getCustomerPhone());
            //    sheet.getRow(8).getCell(1).setCellValue(customerPaymentInvoice.getPayment().getCreatedAt().toLocalDate().toString());
            sheet.getRow(8).getCell(1).setCellValue(customerPaymentInvoice.getCustomerAddress());
            sheet.getRow(9).getCell(1).setCellValue(customerPaymentInvoice.getCustomerEmail());
            sheet.getRow(10).getCell(1).setCellValue("ATIVO");

            sheet.getRow(6).getCell(3).setCellValue(customerPaymentInvoice.getInvoiceCode());
            sheet.getRow(8).getCell(3).setCellValue(customerPaymentInvoice.getCustomerCode());


            sheet.getRow(13).getCell(1).setCellValue(customerPaymentInvoice.getMonthDescription());
            sheet.getRow(13).getCell(2).setCellValue(customerPaymentInvoice.getNumMonth());
            sheet.getRow(13).getCell(3).setCellValue(customerPaymentInvoice.getUnitPrice());
            sheet.getRow(13).getCell(4).setCellValue(customerPaymentInvoice.getAmount());

            sheet.getRow(14).getCell(4).setCellValue(customerPaymentInvoice.getSubtotal());
            sheet.getRow(15).getCell(4).setCellValue(customerPaymentInvoice.getTaxRate());
            sheet.getRow(16).getCell(4).setCellValue(customerPaymentInvoice.getTax());
            sheet.getRow(17).getCell(4).setCellValue(customerPaymentInvoice.getTotal());

            String formattedName = customerPaymentInvoice.getCustomerName().replaceAll("\\s+", "_");
            String formattedDateTime = customerPaymentInvoice.getCreatedAt()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm"));
            String fileName = String.format("Invoice_%s_%s.xlsx", formattedName, formattedDateTime);

            File outputFile = new File("storage/invoices/" + fileName);
            outputFile.getParentFile().mkdirs(); // cria pasta se não existir
            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                workbook.write(fos);
            }

            return outputFile;
        }
    }

    public Optional<CustomerPaymentInvoice> fetchInvoiceById(UUID id) {
        return customerPaymentInvoiceRepository.findById(id);
    }


    public CustomerPaymentInvoice fetchInvoiceByPaymentId(UUID id) {
        return customerPaymentInvoiceRepository.findByPaymentId(id);
    }

}
