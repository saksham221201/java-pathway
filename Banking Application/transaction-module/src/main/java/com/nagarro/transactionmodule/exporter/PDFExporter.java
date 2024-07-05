package com.nagarro.transactionmodule.exporter;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.nagarro.transactionmodule.entity.Transaction;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class PDFExporter {

    private final List<Transaction> transactions;

    public PDFExporter(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ID", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Account Number", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("TYPE", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Amount", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Timestamp", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Balance", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (Transaction transaction : transactions) {
            table.addCell(String.valueOf(transaction.getTransactionId()));
            table.addCell(String.valueOf(transaction.getAccountNumber()));
            table.addCell(transaction.getTransactionType());
            table.addCell(String.valueOf(transaction.getAmount()));
            table.addCell(String.valueOf(transaction.getTimestamp()));
            table.addCell(String.valueOf(transaction.getBalance()));
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLACK);

        Paragraph p = new Paragraph("List of Transactions", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.0f, 3.0f, 2.0f, 3.5f, 1.5f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }

}
