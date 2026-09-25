package com.gisforum;

import com.gisforum.User;
import com.gisforum.UserRepository;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ExportService {

    @Autowired
    private UserRepository userRepository;

    // --- 1. HÀM XUẤT EXCEL TỪ DB ---
    public byte[] exportToExcel() throws Exception {
        String[] columns = {"ID", "Email Đăng Ký", "Vai Trò", "Hạng Tài Khoản", "Doanh Thu (VNĐ)"};

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Thong Ke Doanh Thu");

            org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerCellStyle);
            }

            List<User> users = userRepository.findAll();
            int rowIdx = 1;

            for (User user : users) {
                Row row = sheet.createRow(rowIdx++);
                
                row.createCell(0).setCellValue(user.getId() != null ? user.getId() : 0);
                row.createCell(1).setCellValue(user.getEmail() != null ? user.getEmail() : "N/A");
                
                String role = user.getRole() != null ? user.getRole() : "USER";
                String tier = user.getTier() != null ? user.getTier() : "FREE";
                
                row.createCell(2).setCellValue(role);
                row.createCell(3).setCellValue(tier);
                
                String doanhThu = "VIP".equalsIgnoreCase(tier) ? "50000" : "0";
                row.createCell(4).setCellValue(doanhThu);
            }

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }

    // --- 2. HÀM XUẤT PDF TỪ DB ---
    public byte[] exportToPdf() throws Exception {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);

            document.open();
            
            com.lowagie.text.Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("BAO CAO DOANH THU WEBGIS", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);

            table.addCell(new PdfPCell(new Phrase("ID")));
            table.addCell(new PdfPCell(new Phrase("Email")));
            table.addCell(new PdfPCell(new Phrase("Vai Tro")));
            table.addCell(new PdfPCell(new Phrase("Hang (Tier)")));
            table.addCell(new PdfPCell(new Phrase("Doanh Thu")));

            List<User> users = userRepository.findAll();
            long totalRevenue = 0;

            for (User user : users) {
                table.addCell(String.valueOf(user.getId()));
                table.addCell(user.getEmail() != null ? user.getEmail() : "N/A");
                
                String role = user.getRole() != null ? user.getRole() : "USER";
                String tier = user.getTier() != null ? user.getTier() : "FREE";
                
                table.addCell(role);
                table.addCell(tier);

                if ("VIP".equalsIgnoreCase(tier)) {
                    table.addCell("50,000");
                    totalRevenue += 50000;
                } else {
                    table.addCell("0");
                }
            }
            document.add(table);

            Paragraph total = new Paragraph("Tong doanh thu: " + totalRevenue + " VND", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14));
            total.setAlignment(Element.ALIGN_RIGHT);
            
            // 👇 CHỖ NÀY ĐÃ ĐƯỢC SỬA THÀNH setSpacingBefore 👇
            total.setSpacingBefore(20); 
            
            document.add(total);

            document.close();
            return out.toByteArray();
        }
    }
}